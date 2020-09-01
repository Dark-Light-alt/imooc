package com.imooc.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片验证码实现
 */
@Component
public class ImageVerificationCode {

    public static final String IMAGE_CODE = "imageCode";

    public static final String IMAGE = "image";

    @Resource
    private SymmetryCryptoUtil symmetryCryptoUtil;

    /**
     * 生成验证码
     *
     * @return
     */
    public Map<String, String> generateVerification() {

        Map<String, String> map = new HashMap<>();

        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(120, 40);

        // 对生成的 code 进行加密
        String code = symmetryCryptoUtil.encode(captcha.getCode());

        // 获取 base64 格式的图片
        String image = "data:image/png;base64," + captcha.getImageBase64();

        map.put(IMAGE_CODE, code);

        map.put(IMAGE, image);

        return map;
    }


    /**
     * 校验验证码
     *
     * @param inputCode 用户传入的验证码
     * @param code      存储在用户浏览器中的验证码
     * @return
     */
    public boolean verification(String inputCode, String code) {

        // 对存储在浏览器中的验证码进行解密
        String decode = symmetryCryptoUtil.decode(code);

        if (inputCode.toUpperCase().equals(code.toUpperCase())) {
            return true;
        }

        return false;
    }
}
