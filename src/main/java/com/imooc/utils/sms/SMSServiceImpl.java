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
     * @return 验证码
     */
    @Override
    public String register(String... phone) {

        String code = generationVerificationCode(6);

        String registerTemplate = baseSMSConfig.registerTemplate(code, 15);

        String request = init(baseSMSConfig.getUrl(), baseSMSConfig.getUsername(), baseSMSConfig.getPassword(), registerTemplate, phone);

        send(request);

        return code;
    }
}
