package com.imooc.utils;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.springframework.stereotype.Component;

/**
 * 密码对称加密解密实现
 */
@Component
public class PasswordUtil {

    // 秘钥
    private static final byte[] key = {49, 94, 65, -120, -86, 11, 106, -16, 127, -15, 81, 22, 125, 33, 102, -59};

    /**
     * 对明文密码进行加密
     *
     * @param password 明文密码
     * @return 加密为 16 进制密码
     */
    public String encode(String password) {

        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);

        String encode = aes.encryptHex(password);

        return encode;
    }

    /**
     * 对加密过的密码进行解密
     *
     * @param password 加密过的密码
     * @return 明文密码
     */
    public String decode(String password) {

        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);

        String decode = aes.decryptStr(password);

        return decode;
    }
}
