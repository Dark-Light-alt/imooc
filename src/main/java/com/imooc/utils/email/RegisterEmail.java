package com.imooc.utils.email;

import cn.hutool.extra.mail.MailUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮箱绑定激活
 */
public class RegisterEmail implements BaseEmail {

    private String template = null;

    {
        StringBuffer template = new StringBuffer();
        template.append("<p>尊敬的{user}，您好！</p>");
        template.append("<p>您的验证码为：<span style=\"color: blue\">{code}</span></p>");
        template.append("<p>为保障您的账号安全，请在<span style=\"color: red\">{time}</span>分钟内进行验证。");
        template.append("如果您并未尝试激活邮箱，请忽略本邮件，由此给您带来的不便请谅解。</p>");
        template.append("<br/>");
        template.append("<br/>");
        template.append("<p>本邮件由系统自动发出，请勿直接回复！</p>");

        this.template = template.toString();
    }

    @Override
    public void sendEmail(Map<String, String> params) {

        String email = params.get("email");

        String user = params.get("user");

        String code = params.get("code");

        String time = params.get("time");

        String title = "【慕课】激活邮箱账号";

        this.template = this.template.replace("{user}", user);
        this.template = this.template.replace("{code}", code);
        this.template = this.template.replace("{time}", time);

        String send = MailUtil.send(email, title, this.template, true);

        System.out.println(send);
    }

    public static void main(String[] args) {

        RegisterEmail registerEmail = new RegisterEmail();

        Map<String, String> params = new HashMap<>();

        params.put("email", "984772611@qq.com");
        params.put("user", "许娜");
        params.put("code", registerEmail.generationVerificationCode(6));
        params.put("time","15");

        registerEmail.sendEmail(params);
    }
}
