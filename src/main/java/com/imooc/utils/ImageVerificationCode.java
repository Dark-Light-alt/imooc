package com.imooc.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.imooc.utils.common.CommonUtils;

import javax.servlet.http.HttpSession;

/**
 * 图片验证码实现
 */
public class ImageVerificationCode {

    private static final String IMAGE_VERIFICATION_CODE = "imageVerificationCode";

    /**
     * 生成验证码并存储到 session
     *
     * @param session
     * @return 返回 base64 格式的图片
     */
    public String generateVerification(HttpSession session) {

        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(120, 40);

        session.setAttribute(IMAGE_VERIFICATION_CODE, captcha.getCode());

        // 获取 base64 格式的图片
        String verificationImage = "data:image/png;base64," + captcha.getImageBase64();

        return verificationImage;
    }

    /**
     * 验证验证码
     *
     * @param session
     * @param code    前端用户传来的验证码
     * @return
     */
    public boolean verification(HttpSession session, String code) {

        Object sessionCode = session.getAttribute(IMAGE_VERIFICATION_CODE);

        if (null != sessionCode && !CommonUtils.isNotEmpty(code)) {

            String c = String.valueOf(sessionCode);

            if (c.toUpperCase().equals(code.toUpperCase())) {
                return true;
            }
        }

        return false;
    }
}
