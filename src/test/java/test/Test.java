package test;

import com.alibaba.fastjson.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class Test {

    public static void main(String[] args) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";

        try {
            // 本地文件路径
            String filePath = "C:\\Users\\Administrator\\Desktop\\idcard.png";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "id_card_side=" + "front" + "&image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAccessToken();

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getAccessToken() {

        StringBuffer request = new StringBuffer();
        request.append("https://aip.baidubce.com/oauth/2.0/token").append("?");
        request.append("grant_type=").append("client_credentials");
        request.append("&");
        request.append("client_id=").append("1oZPpd1jCMGinzm9ca8CjyPv");
        request.append("&");
        request.append("client_secret=").append("sdlmaUxQdxxMsdnWvmegx843MiRsfBke");

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
}
