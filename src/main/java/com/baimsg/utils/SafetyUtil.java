package com.baimsg.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * create by baimsg 2021/11/17
 * Email 1469010683@qq.com
 **/
public class SafetyUtil {
    private static final char[] HEX_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
            'd', 'e', 'f',
    };

    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return bytesToHexString(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String bytesToHexString(byte[] input) {
        if (input == null)
            throw new IllegalArgumentException("input should not be null");

        StringBuilder builder = new StringBuilder(input.length * 2);
        for (byte b : input) {
            builder.append(HEX_DIGITS[b >>> 4 & 0xf]);
            builder.append(HEX_DIGITS[b & 0xf]);
        }
        return builder.toString();
    }


    public static String shaEncode(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA").digest(str.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuffer = new StringBuilder();
            for (byte b : digest) {
                if ((int) b < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(b));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            return "";
        }
    }


}
