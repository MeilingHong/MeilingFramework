package com.meiling.framework.common_util.messagedigest;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class MessageDigestSHA256 {

    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes());
            return MessageDigestUtil.getFormattedOfLowerCase(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static final int LENGTH = 64;
    public static String getFileSHA256(File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[4096];
        int len;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 4096)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        String stringBefore = bigInt.toString(16);
        //保证MD5 长度固定（32位）
        if (stringBefore.length() >= LENGTH) {
            return stringBefore;
        } else {
            int div = LENGTH - stringBefore.length();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < div; i++) {
                stringBuilder.append("0");
            }
            stringBuilder.append(stringBefore);
            return stringBuilder.toString();
        }
    }
}
