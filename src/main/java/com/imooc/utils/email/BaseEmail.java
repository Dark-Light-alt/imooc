package com.imooc.utils.email;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;

import java.util.Map;

public interface BaseEmail {

    /**
     * 生成验证码
     *
     * @param len 验证码个数
     * @return
     */
    default String generationVerificationCode(int len) {
        return ArrayUtil.join(NumberUtil.generateRandomNumber(0, 9, len), "");
    }

    void sendEmail(Map<String, String> params);
}
