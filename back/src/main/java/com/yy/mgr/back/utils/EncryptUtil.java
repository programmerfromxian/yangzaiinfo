package com.yy.mgr.back.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author yang
 * @date 2020/03/20 21:23
 */
public class EncryptUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptUtil.class);
    private static final String SALT = "320fdafer(*()♦*__&*^&%8○&*(789YyCn";


    /**
     * 对字符串加密
     */
    public static String encryptData(String str) {
        return str;
    }

    /**
     * 对字符串解密
     */
    public static String decryptData(String encryptData) {
        return encryptData;
    }

}
