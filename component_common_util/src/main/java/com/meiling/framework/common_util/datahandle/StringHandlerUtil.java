package com.meiling.framework.common_util.datahandle;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHandlerUtil {
    /**
     * 返回非空字符串
     *
     * @param input
     * @return
     */
    public static String getNotNullString(String input) {
        return !TextUtils.isEmpty(input) ? input : "";
    }

    /**
     * 将电话中间的隐藏
     *
     * @param phone
     * @return
     */
    public static String getTransformPhone(String phone) {
        char[] charPhone = phone.toCharArray();
        int len = charPhone.length;
        char[] newPhoneString = new char[len];
        for (int i = 0; i < len; i++) {
            if (i < 3 || i > len - 5) {
                newPhoneString[i] = charPhone[i];
            } else {
                newPhoneString[i] = '*';
            }
        }
        return new String(newPhoneString);
    }

    public static String getTransformMail(String mail) {
        int index = mail.indexOf("@");
        if (index < 2) {
            return mail;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(mail.charAt(0));
            stringBuilder.append("***");
            stringBuilder.append(mail.substring(index - 1));
            return stringBuilder.toString();
        }
    }

    public static String getTransformName(String name) {
        char[] charPhone = name.toCharArray();
        int len = charPhone.length;
        char[] newPhoneString;
        if (len >= 2) {
            newPhoneString = new char[len];
        } else {
            newPhoneString = new char[2];
        }

        for (int i = 0; i < (len >= 2 ? len : 2); i++) {
            if (len > 2 && (i < 1 || i > len - 2)) {
                newPhoneString[i] = charPhone[i];
            } else if (len < 3 && i == 0) {
                newPhoneString[i] = charPhone[i];
            } else {
                newPhoneString[i] = '*';
            }
        }
        return new String(newPhoneString);
    }

    public static String getTransformString(String name, int start, int end) {
        if (name == null || name.length() < 1) {
            return name;
        }
        char[] charPhone = name.toCharArray();
        int len = charPhone.length;
        if (len < 2) {//不处理少于2的字符串
            return name;
        }
        char[] newPhoneString;
        //---------------------------------
        if (start > len / 2) {//start 位置不超过一半
            start = len / 2;
        }
        if (start < 1) {//默认第一个字符不进行转换
            start = 1;
        }
        //---------------------------------
        if (end + start > len) {//
            end = len - start - 1;
        }
        if (end < 0) {//默认第一个字符不进行转换
            end = 0;
        }
        if (len >= 2) {
            newPhoneString = new char[len];
        } else {
            newPhoneString = new char[2];
        }

        for (int i = 0; i < (len >= 2 ? len : 2); i++) {
            if (len > 2 && (i < start || i > len - end)) {
                newPhoneString[i] = charPhone[i];
            } else if (len < 3 && i == 0) {
                newPhoneString[i] = charPhone[i];
            } else {
                newPhoneString[i] = '*';
            }
        }
        return new String(newPhoneString);
    }

    /**
     * 找到字符串中的中文字符并拼接成字符串
     *
     * @param paramValue
     * @return
     */
    public static String getChinese(String paramValue) {
        StringBuilder str = new StringBuilder();
        String regex = "([\u4e00-\u9fa5]+)";
        Matcher matcher = Pattern.compile(regex).matcher(paramValue);
        while (matcher.find()) {
            str.append(matcher.group(0));
        }
        return str.toString();
    }

    /**
     * 获取app的名称
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        String appName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            appName =  context.getResources().getString(labelRes);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return appName;
    }
}
