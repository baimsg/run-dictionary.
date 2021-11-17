package com.baimsg.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * create by baimsg 2021/11/17
 * Email 1469010683@qq.com
 **/
public class SafetyUtil {

    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
