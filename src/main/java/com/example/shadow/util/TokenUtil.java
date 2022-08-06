package com.example.shadow.util;

import java.util.UUID;

public class TokenUtil {

    /**
     * 返回一个随机生成的字符串，用作token
     * 为保证唯一性，添加src作为token的一部分
     * 可更改
     * @param src
     * @return
     */
    public static String getToken(String src) {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase() + EncryptrUtil.md5Encrypt(src);
    }

}
