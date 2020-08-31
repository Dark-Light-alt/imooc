package com.imooc.utils.email;

import cn.hutool.extra.mail.MailUtil;
import com.imooc.exception.ApiException;
import com.imooc.utils.common.CommonUtils;
import org.springframework.stereotype.Component;

/**
 * 邮箱服务实现
 */
@Component
public class EmailServiceImpl implements EmailService {

    @Override
    public void send(String email, String title, String content) {

        if (!CommonUtils.isCorrectEmail(email)) {
            throw new ApiException(500, "邮箱地址不合法");
        }

        MailUtil.send(email, title, content, true);
    }
}
