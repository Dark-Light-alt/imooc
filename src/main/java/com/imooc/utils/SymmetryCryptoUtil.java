package com.imooc.utils;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.springframework.stereotype.Component;

/**
 * 对称加密解密实现
 */
@Component
public class SymmetryCryptoUtil {

    // 秘钥
    private static final byte[] key = {49, 94, 65, -120, -86, 11, 106, -16, 127, -15, 81, 22, 125, 33, 102, -59};

    /**
     * 对明文进行加密
     *
     * @param content 明文
     * @return 加密为 16 进制字符串
     */
    public String encode(String content) {

        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);

        String encode = aes.encryptHex(content);

        return encode;
    }

    /**
     * 对加密过的字符串进行解密
     *
     * @param content 加密过的字符串
     * @return 明文
     */
    public String decode(String content) {

        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);

        String decode = aes.decryptStr(content);

        return decode;
    }
}
