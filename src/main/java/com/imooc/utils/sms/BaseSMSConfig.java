package com.imooc.utils.sms;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:application.yml")
public class BaseSMSConfig {

    @Value("${sms.url}")
    private String url;

    @Value("${sms.username}")
    private String username;

    @Value("${sms.password}")
    private String password;

    /**
     * 注册短信模板
     *
     * @param code 验证码
     * @param time 有效期
     * @return
     */
    public String registerTemplate(String code, Integer time) {

        String template = "【imooc】尊敬的客户，您的验证码为" + code + "，请于 " + time + "分钟内正确输入。 如非本人操作，请忽略此短信。";

        return template;
    }
}
