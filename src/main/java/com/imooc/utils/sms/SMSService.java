package com.imooc.utils.sms;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.DigestUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 短讯服务
 */
public interface SMSService {

    /**
     * 生成短信验证码
     *
     * @param len 长度
     * @return
     */
    default String generationVerificationCode(int len) {
        return ArrayUtil.join(NumberUtil.generateRandomNumber(0, 9, len), "");
    }

    /**
     * 初始化请求
     *
     * @param url      请求地址
     * @param username 用户名
     * @param password 密码
     * @param template 模板
     * @param phone    手机号
     * @return 请求
     */
    default String init(String url, String username, String password, String template, String... phone) {

        StringBuffer request = new StringBuffer();

        request.append(url).append("?");
        request.append("u=").append(username).append("&");
        request.append("p=").append(DigestUtil.md5Hex(password)).append("&");
        request.append("m=").append(ArrayUtil.join(phone, ",")).append("&");
        request.append("c=").append(URLUtil.encode(template, "UTF-8"));

        return request.toString();
    }

    /**
     * 信息发送
     *
     * @param request 请求
     */
    default String send(String request) {

        BufferedReader reader = null;

        StringBuffer result = new StringBuffer();

        try {
            URL u = new URL(request);

            HttpURLConnection connection = (HttpURLConnection) u.openConnection();

            connection.setRequestMethod("GET");
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            String rendLine = null;

            while ((rendLine = reader.readLine()) != null) {
                result.append(rendLine);
            }

            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    /**
     * 注册短信发送
     *
     * @param phone 手机号
     * @return 发送结果
     */
    String register(String... phone);
}
