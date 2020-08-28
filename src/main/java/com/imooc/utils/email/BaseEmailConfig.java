package com.imooc.utils.email;

import org.springframework.stereotype.Component;

@Component
public class BaseEmailConfig {

    /**
     * 邮箱注册模板
     *
     * @param user 用户名
     * @param code 验证码
     * @param time 超时时间
     * @return
     */
    public String registerTemplate(String user, String code, Integer time) {

        StringBuffer template = new StringBuffer();

        template.append("<p>尊敬的" + user + "，您好！</p>");
        template.append("<p>您的验证码为：<span style=\"color: blue\">" + code + "</span></p>");
        template.append("<p>为保障您的账号安全，请在<span style=\"color: red\">" + time + "</span>分钟内进行验证。");
        template.append("如果您并未尝试激活邮箱，请忽略本邮件，由此给您带来的不便请谅解。</p>");
        template.append("<br/>");
        template.append("<br/>");
        template.append("<p>本邮件由系统自动发出，请勿直接回复！</p>");

        return template.toString();
    }
}
