package com.imooc.security;

import com.alibaba.fastjson.JSON;
import com.imooc.service.impl.AccountNumberServiceImpl;
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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        // 修改账号最后登录时间
        accountNumberServiceImpl.changeEndLoginTime(httpServletRequest.getParameter("username"));

        // 生成 jwt

        httpServletResponse.setCharacterEncoding("UTF-8");

        httpServletResponse.setContentType("application/json");

        PrintWriter writer = httpServletResponse.getWriter();

        Result result = new Result();

        result.success(200, "登录成功");

        writer.print(JSON.toJSON(result));
        writer.flush();
        writer.close();
    }
}
