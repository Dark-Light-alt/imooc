package com.imooc.alipay;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 支付宝配置信息
 */
@Data
@Component
@PropertySource("classpath:application.yml")
public class AlipayConfig {

    // 应用ID
    @Value("${alipay.appId}")
    private String appId;

    // 私钥
    @Value("${alipay.privateKey}")
    private String privateKey;

    // 支付宝公钥
    @Value("${alipay.publicKey}")
    private String publicKey;

    // 服务器异步通知路径
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    // 页面跳转同步通知路径
    @Value("${alipay.returnUrl}")
    private String returnUrl;

    // 签名方式
    @Value("${alipay.signType}")
    private String signType;

    // 字符编码格式
    @Value("${alipay.charset}")
    private String charset;

    // 支付宝网关
    @Value("${alipay.gatewayUrl}")
    private String gatewayUrl;
}
