package com.meiling.framework.common_util.messagedigest;

import java.security.MessageDigest;

public class MessageDigestSHA512 {

    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(str.getBytes());
            return MessageDigestUtil.getFormattedOfLowerCase(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
