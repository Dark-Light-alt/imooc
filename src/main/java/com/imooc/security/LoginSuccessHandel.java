package com.imooc.security;

import com.alibaba.fastjson.JSON;
import com.imooc.dao.EmployeeInfoDao;
import com.imooc.entity.EmployeeInfo;
import com.imooc.service.impl.AccountNumberServiceImpl;
import com.imooc.service.impl.RightsServiceImpl;
import com.imooc.utils.common.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LoginSuccessHandel implements AuthenticationSuccessHandler {

    @Resource
    private AccountNumberServiceImpl accountNumberServiceImpl;

    @Resource
    private EmployeeInfoDao employeeInfoDao;

    @Resource
    private RightsServiceImpl rightsServiceImpl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        String username = httpServletRequest.getParameter("username");

        // 修改账号最后登录时间
        accountNumberServiceImpl.changeEndLoginTime(username);

        // 根据用户名获取员工信息
        EmployeeInfo employeeInfo = employeeInfoDao.findByUsername(username);

        // 生成 jwt

        httpServletResponse.setCharacterEncoding("UTF-8");

        httpServletResponse.setContentType("application/json");

        PrintWriter writer = httpServletResponse.getWriter();

        Result result = new Result();

        result.putData("employeeInfo", employeeInfo);

        result.success(200, "登录成功");

        writer.print(JSON.toJSON(result));
        writer.flush();
        writer.close();
    }
}
