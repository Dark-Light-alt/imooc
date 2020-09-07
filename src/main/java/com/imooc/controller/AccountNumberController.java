package com.imooc.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.AccountNumber;
import com.imooc.exception.ApiException;
import com.imooc.service.impl.AccountNumberServiceImpl;
import com.imooc.utils.SymmetryCryptoUtil;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import com.imooc.utils.jwt.JwtUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("AccountNumberController")
public class AccountNumberController {

    @Resource
    private AccountNumberServiceImpl accountNumberServiceImpl;

    @Resource
    private SymmetryCryptoUtil symmetryCryptoUtil;

    private static final String AUTHORITIES_KEY = "auth";

    @Resource
    JwtUtils jwtUtils;

    // 签名秘钥
    private String secretKey;

    @RequestMapping(value = "pagingFindAll", method = RequestMethod.POST)
    public Result pagingFindAll(@RequestBody Pages pages) {

        Result result = new Result();

        Page<AccountNumber> data = accountNumberServiceImpl.pagingFindAll(pages);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());
        result.setPages(pages);

        result.putData("accountNumberList", data.getRecords());

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "changeLocked/{accountNumberId}/{islocked}", method = RequestMethod.GET)
    public Result changeLocked(@PathVariable String accountNumberId, @PathVariable Integer islocked) {

        Result result = new Result();

        accountNumberServiceImpl.changeLocked(accountNumberId, islocked);

        result.success(200, islocked == 0 ? "账号解锁成功" : "账号锁定成功");

        return result;
    }

    @RequestMapping(value = "findById/{accountNumberId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String accountNumberId) {

        Result result = new Result();

        result.putData("accountNumber", accountNumberServiceImpl.findById(accountNumberId));

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    public Result changePassword(@RequestBody Map<String, String> params) {

        Result result = new Result();

        // 获取员工 id
        String employeeId = params.get("employeeId");

        // 原密码
        String password = params.get("password");

        // 新密码
        String newPassword = params.get("newPassword");

        // 确定密码
        String checkPassword = params.get("checkPassword");

        if (!CommonUtils.isNotEmpty(password)) {
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

        // 从数据库中查询到 账号信息
        AccountNumber accountNumber = accountNumberServiceImpl.findByEmployeeId(employeeId);


        if (!accountNumber.getPassword().equals(password)) {
            throw new ApiException(500, "原密码错误");
        }

        accountNumber.setPassword(newPassword);

        accountNumberServiceImpl.update(accountNumber);

        result.success(200,"密码修改成功");

        return result;
    }
}
