package com.imooc.security;

import com.alibaba.fastjson.JSON;
import com.imooc.utils.common.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录失败处理器
 */
@Component
public class LoginErrorHandel implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        System.out.println("登录出错");

        httpServletResponse.setCharacterEncoding("UTF-8");

        httpServletResponse.setContentType("application/json");

        PrintWriter writer = httpServletResponse.getWriter();

        Result result = new Result();
        result.error(500, e.getMessage());

        writer.print(JSON.toJSON(result));
        System.out.println(JSON.toJSON(result));
        writer.flush();
    }
}
