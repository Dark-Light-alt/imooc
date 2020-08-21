package com.imooc.utils.ocr;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONArray;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份证识别
 */
public class IdentityCardOCR implements BaseOCR {

    private static final String GATEWAY_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";

    /**
     * 进行识别
     *
     * @param imgPath
     * @return
     */
    @Override
    public Map<String, Object> discern(String imgPath) {

        byte[] bytes = FileUtil.readBytes(imgPath);

        String encode = Base64Util.encode(bytes);

        String imgEncode = null;

        String encoding = "UTF-8";

        try {
            imgEncode = URLEncoder.encode(encode, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String accessToken = getAccessToken();

        String requestUrl = GATEWAY_URL + "?access_token=" + accessToken;

        BufferedReader reader = null;

        StringBuffer result = new StringBuffer();

        try {
            URL url = new URL(requestUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求属性
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            StringBuffer params = new StringBuffer();
            params.append("id_card_side=").append("front");
            params.append("&");
            params.append("image=").append(imgEncode);

            // 得到请求的输出流对象
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(params.toString().getBytes(encoding));
            out.flush();
            out.close();

            // 建立连接
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

        Map<String, Object> resultMap = JSONArray.parseObject(result.toString(), HashMap.class);

        Map<String, Object> wordsResult = JSONArray.parseObject(resultMap.get("words_result").toString(), HashMap.class);

        Map<String, Object> idcardInfo = new HashMap();

        Map<String, Object> word = null;

        for (String key : wordsResult.keySet()) {
            word = JSONArray.parseObject(wordsResult.get(key).toString(), HashMap.class);
            idcardInfo.put(key, word.get("words"));
        }

        return idcardInfo;
    }

    /**
     * 获取的 accessToken
     *
     * @return
     */
    private String getAccessToken() {

        StringBuffer request = new StringBuffer();
        request.append("https://aip.baidubce.com/oauth/2.0/token").append("?");
        request.append("grant_type=").append("client_credentials");
        request.append("&");
        request.append("client_id=").append(API_KEY);
        request.append("&");
        request.append("client_secret=").append(SECRET_KEY);

        BufferedReader reader = null;

        StringBuffer result = new StringBuffer();

        try {
            URL url = new URL(request.toString());

            // 获取 HTTP 连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法
            connection.setRequestMethod("GET");

            // 建立连接
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

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

        HashMap hashMap = JSONArray.parseObject(result.toString(), HashMap.class);

        return hashMap.get("access_token").toString();
    }

    public static void main(String[] args) {

        IdentityCardOCR test = new IdentityCardOCR();

        Map<String, Object> idcard = test.discern("C:\\Users\\Administrator\\Desktop\\idcard.png");

        System.out.println(idcard);
    }
}
