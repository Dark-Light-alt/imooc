package com.imooc.utils.email;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;

/**
 * 邮箱服务
 */
public interface EmailService {

    // 注册邮箱标题
    String registerTile = "【慕课】激活邮箱账号";

    /**
     * 生成验证码
     *
     * @param len 验证码长度
     * @return
     */
    default String generationVerificationCode(int len) {
        return ArrayUtil.join(NumberUtil.generateRandomNumber(0, 9, len), "");
    }

    /**
     * 邮件发送
     *
     * @param email   邮箱地址
     * @param title   标题
     * @param content 发送内容
     */
    void send(String email, String title, String content);
}
