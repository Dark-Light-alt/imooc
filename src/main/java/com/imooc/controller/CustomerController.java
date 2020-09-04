package com.imooc.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.imooc.entity.Customer;
import com.imooc.entity.EmployeeInfo;
import com.imooc.entity.SysNotice;
import com.imooc.exception.ApiException;
import com.imooc.service.impl.CustomerServiceImpl;
import com.imooc.service.impl.SysNoticeServiceImpl;
import com.imooc.utils.ImageVerificationCode;
import com.imooc.utils.SymmetryCryptoUtil;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Result;
import com.imooc.utils.jwt.JwtUtils;
import com.imooc.utils.sms.SMSServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("CustomerController")
public class CustomerController {

    @Resource
    private CustomerServiceImpl customerServiceImpl;

    @Resource
    private ImageVerificationCode imageVerificationCode;

    @Resource
    private SMSServiceImpl smsServiceImpl;

    @Resource
    private SymmetryCryptoUtil symmetryCryptoUtil;

    @Resource
    private SysNoticeServiceImpl sysNoticeServiceImpl;

    private String secretKey;


    /**
     * 生成图片验证码
     *
     * @return
     */
    @RequestMapping(value = "generateImageCaptcha", method = RequestMethod.GET)
    public Result generateImageCaptcha() {

        Result result = new Result();

        result.putData("imageCaptcha", imageVerificationCode.generateVerification());

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 校验图片验证码
     *
     * @return
     */
    @RequestMapping(value = "verifyImageCaptcha", method = RequestMethod.POST)
    public Result verifyImageCaptcha(@RequestBody Map<String, String> params) {

        Result result = new Result();

        String phone = params.get("phone");
        String imageCode = params.get("imageCode");
        String code = params.get("code");

        // 验证
        verifyImage(phone, imageCode, code);

        // 验证成功发送手机验证码
        String phoneCode = smsServiceImpl.register(phone);

        System.out.println(phoneCode);

        result.putData("phoneCode", symmetryCryptoUtil.encode(phoneCode));

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 注册
     *
     * @param params
     * @param httpServletResponse
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result register(@RequestBody Map<String, String> params, HttpServletResponse httpServletResponse) {

        Result result = new Result();

        String phone = params.get("phone");

        String phoneCode = params.get("phoneCode");

        String code = params.get("code");

        registerVerify(phone, phoneCode, code);

        String password = params.get("password");

        Boolean rememberMe = Boolean.valueOf(params.get("rememberMe"));

        Customer customer = new Customer();
        customer.setCustomerNickname("爱慕课" + smsServiceImpl.generationVerificationCode(6));
        customer.setCustomerPhone(phone);
        customer.setCustomerPassword(password);
        customer.setCustomerPhoto("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");

        customerServiceImpl.append(customer);

        if (rememberMe) {
            Cookie cookie = new Cookie("customerId", customer.getCustomerId());

            // 设置 7 天有效期
            cookie.setMaxAge(7 * 24 * 60 * 60);

            httpServletResponse.addCookie(cookie);
        }

        SysNotice sysNotice = new SysNotice();
        sysNotice.setCustomerId(customer.getCustomerId());
        sysNotice.setTitle("慕课");
        sysNotice.setContent("欢迎 " + customer.getCustomerNickname() + " 加入慕课 ！");

        // 添加系统提示信息
        sysNoticeServiceImpl.append(sysNotice);

        result.putData("customer", customer);

        result.success(200, "注册成功");

        return result;
    }

    /**
     * 安全退出，清除 cookie 信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public Result logout(HttpServletRequest request, HttpServletResponse response) {

        Result result = new Result();

        Cookie[] cookies = request.getCookies();

        Cookie customer = new Cookie("customerId", null);

        customer.setMaxAge(0);

        response.addCookie(customer);

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 根据手机号或邮箱进行登录
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> params, HttpServletResponse response) {

        Result result = new Result();

        String username = params.get("username");

        String password = params.get("password");

        Boolean rememberMe = Boolean.valueOf(params.get("rememberMe"));

        loginVerify(username, password);

        Customer customer = customerServiceImpl.login(username, password);

        if (null != customer) {
            result.putData("customer", customer);
            result.success(200, "登录成功");

            if (rememberMe) {
                Cookie cookie = new Cookie("customerId", customer.getCustomerId());

                cookie.setMaxAge(7 * 24 * 60 * 60);

                response.addCookie(cookie);
            }
        } else {
            result.error(500, "密码或账号错误");
        }

        return result;
    }

    /**
     * 自动登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "autoLogin", method = RequestMethod.GET)
    public Result autoLogin(HttpServletRequest request) {

        Result result = new Result();

        Cookie[] cookies = request.getCookies();

        String customerId = null;

        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if ("customerId".equals(cookie.getName())) {
                    customerId = cookie.getValue();
                    result.putData("customer", customerServiceImpl.findById(customerId));
                }
            }
        }

        if (null == customerId) {
            result.putData("customer", null);
        }

        result.success(200, null);
        return result;
    }

    /**
     * 验证
     *
     * @param phone
     * @param imageCode
     * @param code
     */
    private void verifyImage(String phone, String imageCode, String code) {

        if (!CommonUtils.isNotEmpty(phone)) {
            throw new ApiException(500, "手机号不能为空");
        }

        if (!CommonUtils.isCorrectPhone(phone)) {
            throw new ApiException(500, "手机号格式错误");
        }

        if (customerServiceImpl.findPhoneCount(phone) != 0) {
            throw new ApiException(500, "手机号已被注册");
        }

        if (!CommonUtils.isNotEmpty(imageCode)) {
            throw new ApiException(500, "验证码不能为空");
        }

        String c = symmetryCryptoUtil.decode(code);

        if (!imageCode.toUpperCase().equals(c.toUpperCase())) {
            throw new ApiException(500, "验证码错误");
        }
    }

    /**
     * 注册校验
     *
     * @param password  密码
     * @param phoneCode 手机验证码
     * @param code      存储在用户浏览器的手机验证码
     */
    private void registerVerify(String password, String phoneCode, String code) {

        if (!CommonUtils.isNotEmpty(password)) {
            throw new ApiException(500, "密码不能为空");
        }

        if (password.length() > 16 || password.length() < 6) {
            throw new ApiException(500, "密码长度在 6 - 16 位");
        }

        if (!CommonUtils.isNotEmpty(phoneCode)) {
            throw new ApiException(500, "手机验证码不能为空");
        }

        String decode = symmetryCryptoUtil.decode(code);
        System.out.println(decode);
        if (!phoneCode.equals(decode)) {
            throw new ApiException(500, "手机验证码错误");
        }
    }

    /**
     * 登录校验
     *
     * @param username
     * @param password
     */
    private void loginVerify(String username, String password) {

        if (!CommonUtils.isNotEmpty(username)) {
            throw new ApiException(500, "手机号或邮箱不能为空");
        }

        if (!CommonUtils.isNotEmpty(password)) {
            throw new ApiException(500, "密码不能为空");
        }
    }

    /**
     * 加密
     *
     * @return
     */
    @RequestMapping(value = "encryption", method = RequestMethod.GET)
    public Result encryption() {

        Result result = new Result();

        result.putData("passwordEncryption", symmetryCryptoUtil);

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 修改密码
     * @param params
     * @return
     */
    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    public Result changePassword(@RequestBody Map<String, String> params) {

        Result result = new Result();
        /*String token = request.getHeader("token");

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();

        DecodedJWT decodedJWT = verifier.verify(token);*/

        // 获取用户 id
        String customerId = params.get("customerId");


        // 原密码
        String customerPassword = params.get("customerPassword");

        // 新密码
        String newPassword = params.get("newPassword"  );

       /* SymmetryCryptoUtil symmetryCryptoUtil = new SymmetryCryptoUtil();

        //解密
        String decode = symmetryCryptoUtil.decode(newPassword,customerPassword);

        //加密
        String newPassword2 = symmetryCryptoUtil.encode(newPassword);*/

        // 确定密码
        String checkPassword = params.get("checkPassword");

        if (!CommonUtils.isNotEmpty(customerPassword)) {
            throw new ApiException(500, "原密码不能为空");
        }

        if (!CommonUtils.isNotEmpty(newPassword)) {
            throw new ApiException(500, "新密码不能为空");
        }

        if (!CommonUtils.isNotEmpty(checkPassword)) {
            throw new ApiException(500, "确认密码不能为空");
        }

        if (!newPassword.equals(checkPassword)) {
            throw new ApiException(500, "两次密码不一致");
        }

        //从数据库查询用户信息
        Customer customer = customerServiceImpl.findByCustomerId(customerId);

        if (!customer.getCustomerPassword().equals(customerPassword)) {
            throw new ApiException(500, "原密码错误");
        }

        customer.setCustomerPassword(newPassword);

        customerServiceImpl.update(customer);

        result.success(200, "密码修改成功");

        return result;
    }

    /**
     * 根据id查询用户信息
     * @param customerId
     * @return
     */
    @RequestMapping(value = "findById/{customerId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String customerId) {

        Result result = new Result();

        result.putData("customer", customerServiceImpl.findById(customerId));

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 修改个人信息
     * @param customer
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result update(@RequestBody Customer customer) {

        Result result = new Result();
        customerServiceImpl.update(customer);

        result.success(200, "修改成功");
        return result;
    }

}
