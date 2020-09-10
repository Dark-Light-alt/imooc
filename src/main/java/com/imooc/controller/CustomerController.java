package com.imooc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Customer;
import com.imooc.entity.SysNotice;
import com.imooc.exception.ApiException;
import com.imooc.service.impl.CustomerServiceImpl;
import com.imooc.service.impl.SysNoticeServiceImpl;
import com.imooc.utils.ImageVerificationCode;
import com.imooc.utils.SymmetryCryptoUtil;
import com.imooc.utils.aliyun.oss.FileStorageService;
import com.imooc.utils.aliyun.oss.FileStorageServiceImpl;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import com.imooc.utils.email.BaseEmailConfig;
import com.imooc.utils.email.EmailServiceImpl;
import com.imooc.utils.sms.SMSServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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

    @Resource
    private EmailServiceImpl emailServiceImpl;

    @Resource
    private BaseEmailConfig baseEmailConfig;

    @Resource
    FileStorageServiceImpl fileStorageServiceImpl;

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
     * 发送邮箱验证码，用于邮箱绑定
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "sendEmailCode", method = RequestMethod.POST)
    public Result sendEmailCode(@RequestBody Map<String, String> params) {

        Result result = new Result();

        String email = params.get("email");

        // 验证邮箱
        verifyEmail(email);

        String code = emailServiceImpl.generationVerificationCode(6);

        // 发送邮箱验证码
        emailServiceImpl.send(email, EmailServiceImpl.registerTile, baseEmailConfig.registerTemplate("喵喵喵", code, 15));

        // 对验证码进行加密
        result.putData("code", symmetryCryptoUtil.encode(code));

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 绑定邮箱
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "bindEmail", method = RequestMethod.POST)
    public Result bindEmail(@RequestBody Map<String, String> params) {

        System.out.println(params);

        Result result = new Result();

        // 获取到用户 id
        String customerId = params.get("customerId");
        // 获取到邮箱
        String email = params.get("email");
        // 获取到用户输入的邮箱验证码
        String emailCode = params.get("emailCode");
        // 获取到存储到浏览器的邮箱验证码，并进行解密
        String code = symmetryCryptoUtil.decode(params.get("code"));

        if (!CommonUtils.isNotEmpty(emailCode)) {
            throw new ApiException(500, "邮箱验证码不能为空");
        }

        if (!code.equals(emailCode)) {
            throw new ApiException(500, "邮箱验证码不正确");
        }

        customerServiceImpl.bindEmail(customerId, email);

        result.success(200, "邮箱绑定成功");

        return result;
    }

    /**
     * 验证密码
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "verifyPassword", method = RequestMethod.POST)
    public Result verifyPassword(@RequestBody Map<String, String> params) {

        Result result = new Result();

        // 获取到用户 id
        String customerId = params.get("customerId");

        // 获取用户输入的密码
        String password = params.get("password");

        if (!CommonUtils.isNotEmpty(password)) {
            throw new ApiException(500, "密码不能为空");
        }

        // 根据用户 id 查询到用户信息
        Customer customer = customerServiceImpl.getById(customerId);

        // 对密码进行解密
        String dePassword = symmetryCryptoUtil.decode(customer.getCustomerPassword());

        if (!dePassword.equals(password)) {
            throw new ApiException(500, "密码验证失败");
        }

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 验证原始手机号
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "verifyPhone", method = RequestMethod.POST)
    public Result verifyPhone(@RequestBody Map<String, String> params) {

        Result result = new Result();

        // 获取用户 id
        String customerId = params.get("customerId");

        // 获取原始手机号
        String phone = params.get("phone");

        if (!CommonUtils.isNotEmpty(phone)) {
            throw new ApiException(500, "手机号不能为空");
        }

        if (!CommonUtils.isCorrectPhone(phone)) {
            throw new ApiException(500, "手机号格式错误");
        }

        // 获取到用户信息
        Customer customer = customerServiceImpl.getById(customerId);

        // 验证手机号是否匹配
        if (!customer.getCustomerPhone().equals(phone)) {
            throw new ApiException(500, "手机号不匹配");
        }

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 发送手机号验证码
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "sendPhoneCode", method = RequestMethod.POST)
    public Result sendPhoneCode(@RequestBody Map<String, String> params) {

        Result result = new Result();

        String phone = params.get("phone");

        if (!CommonUtils.isNotEmpty(phone)) {
            throw new ApiException(500, "手机号不能为空");
        }

        if (!CommonUtils.isCorrectPhone(phone)) {
            throw new ApiException(500, "手机号格式错误");
        }

        if (customerServiceImpl.findPhoneCount(phone) != 0) {
            throw new ApiException(500, "手机号已被注册");
        }

        // 发送手机验证码
        String code = smsServiceImpl.register(phone);

        // 加密处理返回至页面
        result.putData("code", symmetryCryptoUtil.encode(code));

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 更改手机号
     *
     * @return
     */
    @RequestMapping(value = "updatePhone", method = RequestMethod.POST)
    public Result updatePhone(Map<String, String> params) {

        Result result = new Result();

        // 获取到用户 id
        String customerId = params.get("customerId");
        // 获取到用户新的手机号
        String newPhone = params.get("newPhone");
        // 获取用户填写的手机验证码
        String phoneCode = params.get("phoneCode");
        // 获取存储在用户浏览器的手机验证码
        String code = params.get("code");

        if (!CommonUtils.isNotEmpty(phoneCode)) {
            throw new ApiException(500, "手机验证码不能为空");
        }
        // 对存储在用户浏览器加密的手机验证码进行解密
        code = symmetryCryptoUtil.decode(code);

        // 进行验证码匹配
        if (!phoneCode.equals(code)) {
            throw new ApiException(500, "手机验证码错误");
        }

        // 将用户的手机号进行修改
        customerServiceImpl.updatePhone(customerId, newPhone);

        result.success(200, "手机号更换成功");

        return result;
    }

    /**
     * 修改密码
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public Result updatePassword(@RequestBody Map<String, String> params) {

        Result result = new Result();

        // 获取到用户 id
        String customerId = params.get("customerId");
        // 当前密码
        String nowPassword = params.get("nowPassword");
        // 新密码
        String newPassword = params.get("newPassword");
        // 确认密码
        String checkPassword = params.get("checkPassword");

        if (!CommonUtils.isNotEmpty(nowPassword)) {
            throw new ApiException(500, "当前密码不能为空");
        }
        if (!CommonUtils.isNotEmpty(newPassword)) {
            throw new ApiException(500, "新密码不能为空");
        }
        if (!CommonUtils.isNotEmpty(checkPassword)) {
            throw new ApiException(500, "确认密码不能为空");
        }
        if (!newPassword.equals(checkPassword)) {
            throw new ApiException(500, "确认密码不一致");
        }

        // 根据 id 查询到用户信息
        Customer customer = customerServiceImpl.getById(customerId);

        // 匹配密码
        if (!nowPassword.equals(symmetryCryptoUtil.decode(customer.getCustomerPassword()))) {
            throw new ApiException(500, "当前密码错误");
        }

        // 对新密码进行加密
        customer.setCustomerPassword(symmetryCryptoUtil.encode(newPassword));

        // 修改
        customerServiceImpl.updateById(customer);

        result.success(200, "密码修改成功");

        return result;
    }

    /**
     * 分页查询所有用户信息
     *
     * @param pages
     * @return
     */
    @RequestMapping(value = "findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody Pages pages) {

        Result result = new Result();

        Page<Customer> data = customerServiceImpl.findAll(pages);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());
        result.setPages(pages);

        result.putData("customerList", data.getRecords());

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 查询用户数量
     * @return
     */
    @RequestMapping(value = "findCustomerCount", method = RequestMethod.GET)
    public Result findCustomerCount() {

        Result result = new Result();

        result.putData("count", customerServiceImpl.count());

        result.success(200, "SUCCESS");

        return result;
    }




    /**
     * 验证邮箱
     *
     * @param email
     */
    private void verifyEmail(String email) {

        if (!CommonUtils.isNotEmpty(email)) {
            throw new ApiException(500, "邮箱不能为空");
        }

        if (!CommonUtils.isCorrectEmail(email)) {
            throw new ApiException(500, "请填写正确的邮箱地址");
        }

        if (customerServiceImpl.findEmailCount(email) != 0) {
            throw new ApiException(500, "邮箱已被注册");
        }
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

    @RequestMapping("upload")
    public Result upload(@RequestParam("file") MultipartFile file) throws IOException {

        Result result = new Result();

        InputStream inputStream = file.getInputStream();

        String fileName = file.getOriginalFilename();

        String url = fileStorageServiceImpl.upload(inputStream, fileName, FileStorageService.IMG);

        result.putData("url",url);

        result.success(200,"SUCCESS");

        return result;
    }


    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result update(@RequestBody Customer customer){

        Result result = new Result();

        System.out.println(customer);

        customerServiceImpl.update(customer);

        result.success(200,"修改成功");

        return result;
    }

    @RequestMapping(value = "findById/{customerId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String customerId){

        Result result = new Result();

        result.putData("customer",customerServiceImpl.findById(customerId));

        result.success(200,"SUCCESS");

        return result;
    }


}
