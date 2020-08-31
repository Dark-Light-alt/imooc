package com.imooc.utils.sms;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 短信服务实现
 */
@Component
public class SMSServiceImpl implements SMSService {

    @Resource
    private BaseSMSConfig baseSMSConfig;

    /**
     * 注册短信发送
     *
     * @param phone 手机号
     * @return
     */
    @Override
    public String register(String... phone) {

        String registerTemplate = baseSMSConfig.registerTemplate(generationVerificationCode(6), 15);

        String request = init(baseSMSConfig.getUrl(), baseSMSConfig.getUsername(), baseSMSConfig.getPassword(), registerTemplate, phone);

        return send(request);
    }
}
