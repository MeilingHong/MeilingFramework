package com.meiling.framework.common_util.messagedigest;


public class MessageDigestUtil {
    private static final char[] HEX = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] HEX_CAPITAL_LETTER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 将摘要信息格式化成小写
     * @param bytes
     * @return
     */
    public static String getFormattedOfLowerCase(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * 将摘要信息格式化成大写
     * @param bytes
     * @return
     */
    public static String getFormattedOfCapitalLetter(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_CAPITAL_LETTER[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_CAPITAL_LETTER[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
}
