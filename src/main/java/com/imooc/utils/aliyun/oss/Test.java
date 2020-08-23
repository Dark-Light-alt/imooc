package com.imooc.utils.aliyun.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Test {

    public static void main(String[] args) throws Exception {
        //oss();
    }

    public static String oss() throws FileNotFoundException {

        String endPoint = "http://oss-cn-shanghai.aliyuncs.com";

        String accessKeyId = "LTAI4GCx7gPz7p6Ex3XAH1nK";

        String accessKeySecret = "W6WloKxtfoPS5yKtCuYXGFddVl7t0g";

        OSS build = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        FileInputStream inputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\idcard.png");

        PutObjectResult moocs = build.putObject("imoocs", "idcard/idcard.png", inputStream);

        System.out.println(moocs.toString());

        build.shutdown();

        return "";
    }
}
