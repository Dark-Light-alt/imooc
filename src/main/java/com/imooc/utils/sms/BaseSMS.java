package com.imooc.utils.sms;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * SMS 接口
 */
public interface BaseSMS {

    String url = "http://api.smsbao.com/sms";

    String username = "1163743662";

    String password = "1163743662";

    /**
     * 初始化请求 URL
     *
     * @param params 参数
     * @param phone
     * @return
     */
    String init(Map<String, String> params, String... phone);

    /**
     * 生成验证码
     *
     * @param len 验证码个数
     * @return
     */
    default String generationVerificationCode(int len) {
        return ArrayUtil.join(NumberUtil.generateRandomNumber(0, 9, len), "");
    }

    /**
     * 发送短信
     *
     * @param params 参数
     * @param phone
     * @return
     */
    default String sendMsg(Map<String, String> params, String... phone) {

        BufferedReader reader = null;

        StringBuffer result = new StringBuffer();

        try {
            URL url = new URL(init(params, phone));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

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
}
