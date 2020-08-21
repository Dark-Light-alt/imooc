package com.imooc.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 支付宝支付接口
 */
@Component
public class Alipay {

    @Resource
    private AlipayConfig alipayConfig;

    //https://blog.csdn.net/qq_37345604/article/details/93855402

    /**
     * 支付接口
     *
     * @param payDto 支付信息
     * @return
     * @throws AlipayApiException
     */
    public String pay(PayDto payDto) throws AlipayApiException {

        String serverUrl = alipayConfig.getGatewayUrl();

        String appId = alipayConfig.getAppId();

        String privateKey = alipayConfig.getPrivateKey();

        String format = "json";

        String charset = alipayConfig.getCharset();

        String alipayPublicKey = alipayConfig.getPublicKey();

        String signType = alipayConfig.getSignType();

        String returnUrl = alipayConfig.getReturnUrl();

        String notifyUrl = alipayConfig.getNotifyUrl();

        // 初始化 AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);

        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        // 页面跳转同步通知路径
        alipayRequest.setReturnUrl(returnUrl);

        // 服务器异步通知路径
        alipayRequest.setNotifyUrl(notifyUrl);

        // 封装参数
        alipayRequest.setBizContent(JSON.toJSONString(payDto));

        // 请求支付宝进行验签，并返回form表单
        String payfrom = alipayClient.pageExecute(alipayRequest).getBody();

        // 返回付款信息
        return payfrom;
    }
}
