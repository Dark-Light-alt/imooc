package com.imooc.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserConfig userConfig;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private AccessErrorHandel accessErrorHandel;

    @Resource
    private LoginSuccessHandel loginSuccessHandel;

    @Resource
    private LoginErrorHandel loginErrorHandel;

    @Resource
    private LogoutSeccussHandel logoutSeccussHandel;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 创建身份校验
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        // 定义 password 编码器
        daoAuthenticationProvider.setPasswordEncoder(new PasswordEncoder() {

            /**
             * 编码
             * @param charSequence
             * @return
             */
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            /**
             * 密码匹配
             * @param charSequence 表单提交的密码
             * @param s 原始密码
             * @return
             */
            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        });

        // 用户详细信息实现
        daoAuthenticationProvider.setUserDetailsService(userConfig);

        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest)
                .permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessErrorHandel)
                .and()
                .formLogin()
                .successHandler(loginSuccessHandel)
                .failureHandler(loginErrorHandel)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(logoutSeccussHandel)
                .permitAll()
                .and()
                .cors()
                .and()
                .csrf().disable();

//        http.addFilterBefore();
    }
}
