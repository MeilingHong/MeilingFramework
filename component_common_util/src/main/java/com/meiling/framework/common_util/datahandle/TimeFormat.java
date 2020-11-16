package com.meiling.framework.common_util.datahandle;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeFormat {
    public static final long SECOND = 1000;//1000
    public static final long MINUTE = 60000;//1000*60
    public static final long MINUTE_3 = 180000;//1000*60*3
    public static final long HOUR = 3600000;//1000*60*60
    public static final long DAY = 86400000;//1000*60*60*24
    public static final long DAY_2 = 172800000;//1000*60*60*24
    public static final long FIVE_DAY = 432000000;//1000*60*60*24*5
    public static final long WEEK = 604800000;//1000*60*60*24*7
    public static final long MONTH = 2592000000l;//1000*60*60*24*30
    public static final long YEAR = 31536000000l;//1000*60*60*24*365

    public static final long MINUTE_SECOND = 60;//1000*60
    public static final long HOUR_SECOND = 3600;//1000*60*60
    public static final long DAY_SECOND = 86400;//1000*60*60*24
    public static final long MONTH_SECOND = 2592000l;//1000*60*60*24*30
    public static final long YEAR_SECOND = 31536000l;//1000*60*60*24*365

    private static final SimpleDateFormat format_short_yyyy = new SimpleDateFormat("yyyy");
    private static final SimpleDateFormat format_short_yyMMdd = new SimpleDateFormat("yy-MM-dd");
    private static final SimpleDateFormat format_short_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat format_short_MMdd = new SimpleDateFormat("MM-dd");//
    private static final SimpleDateFormat format_short_MMddHHmm = new SimpleDateFormat("MM-dd HH:mm");//
    private static final SimpleDateFormat format_short_HHmm = new SimpleDateFormat("HH:mm");//
    private static final SimpleDateFormat format_short_yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
    private static final SimpleDateFormat format_short_yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");//
    private static final SimpleDateFormat format_short_yyyyMMddHHmmss_slash = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//
    private static final SimpleDateFormat format_short_yyyyMMddHHmmss_line = new SimpleDateFormat("yyyyMMddHHmmss");//
    private static final SimpleDateFormat format_short_yyyyMMddHHmm_slash = new SimpleDateFormat("yyyy/MM/dd HH:mm");//

    public static String formatTime_YYYYMMDD(long timestamp) {
        Date date = new Date(timestamp);
        return format_short_yyyyMMdd.format(date);
    }

    public static String formatTime_yyyyMMddHHmmss(long timestamp) {
        Date date = new Date(timestamp);
        return format_short_yyyyMMddHHmmss.format(date);
    }

    public static String formatTime_yyyyMMddHHmmss_line(long timestamp) {
        Date date = new Date(timestamp);
        return format_short_yyyyMMddHHmmss_line.format(date);
    }

    public static String formatTime_yyyyMMddHHmmss_slash(long timestamp) {
        Date date = new Date(timestamp);
        return format_short_yyyyMMddHHmmss_slash.format(date);
    }

    public static String formatTime_yyyyMMddHHmm(long timestamp) {
        Date date = new Date(timestamp);
        return format_short_yyyyMMddHHmm.format(date);
    }

    public static String formatTime_yyyyMMddHHmm_slash(long timestamp) {
        Date date = new Date(timestamp);
        return format_short_yyyyMMddHHmm_slash.format(date);
    }

    public static String formatTime_MMdd(long timestamp) {
        Date date = new Date(timestamp);
        return format_short_MMdd.format(date);
    }

//    public static String formatTimeByTime(Context context, long timestamp) {
//        long current = System.currentTimeMillis();
//        long yesterday = current - DAY;
//        long befor_yesterday = current - DAY_2;
//        if (current - timestamp < MINUTE) {//刚刚
//            return context.getString(R.string.just_now);
//        } else if (format_short_yyyyMMdd.format(new Date(current)).equals(format_short_yyyyMMdd.format(new Date(timestamp)))) {//今天
//            return context.getString(R.string.today);
//        } else if (format_short_yyyyMMdd.format(new Date(yesterday)).equals(format_short_yyyyMMdd.format(new Date(timestamp)))) {//昨天
//            return context.getString(R.string.yesterday);
//        } else if (format_short_yyyyMMdd.format(new Date(befor_yesterday)).equals(format_short_yyyyMMdd.format(new Date(timestamp)))) {//前天
//            return context.getString(R.string.before_yesterday);
//        } else if (format_short_yyyy.format(new Date(current)).equals(format_short_yyyy.format(new Date(timestamp)))) {//今年，不显示年份
//            return format_short_MMdd.format(new Date(timestamp));
//        } else {// 去年，以及以前
//            return format_short_yyyyMMdd.format(new Date(timestamp));
//        }
//    }

//    public static String formatTime_Trade(Context context, long timestamp) {
//        long current = System.currentTimeMillis();
//        long yesterday = current - DAY;
//        long befor_yesterday = current - DAY_2;
//        if (format_short_yyyyMMdd.format(new Date(current)).equals(format_short_yyyyMMdd.format(new Date(timestamp)))) {//今天
//            return context.getString(R.string.today)+format_short_HHmm.format(new Date(timestamp));
//        } else if (format_short_yyyyMMdd.format(new Date(yesterday)).equals(format_short_yyyyMMdd.format(new Date(timestamp)))) {//昨天
//            return context.getString(R.string.yesterday)+format_short_HHmm.format(new Date(timestamp));
//        } else if (format_short_yyyyMMdd.format(new Date(befor_yesterday)).equals(format_short_yyyyMMdd.format(new Date(timestamp)))) {//前天
//            return context.getString(R.string.before_yesterday)+format_short_HHmm.format(new Date(timestamp));
//        } else if (format_short_yyyy.format(new Date(current)).equals(format_short_yyyy.format(new Date(timestamp)))) {//今年，不显示年份
//            return format_short_MMddHHmm.format(new Date(timestamp));
//        } else {// 去年，以及以前
//            return format_short_yyyyMMdd.format(new Date(timestamp));
//        }
//    }

    // mortgage time length
//    public static String setTimeLength(Context mContext, long timeSecond) {
//        if (timeSecond >= TimeFormat.YEAR_SECOND) {
//            return mContext.getString(R.string.time_year, timeSecond / TimeFormat.YEAR_SECOND);
//        } else if (timeSecond >= TimeFormat.MONTH_SECOND) {
//            return mContext.getString(R.string.time_month, timeSecond / TimeFormat.MONTH_SECOND);
//        } else if (timeSecond >= TimeFormat.DAY_SECOND) {
//            return mContext.getString(R.string.time_day, timeSecond / TimeFormat.DAY_SECOND);
//        } else if (timeSecond >= TimeFormat.HOUR_SECOND) {
//            return mContext.getString(R.string.time_hour, timeSecond / TimeFormat.HOUR_SECOND);
//        } else if (timeSecond >= TimeFormat.MINUTE_SECOND) {
//            return mContext.getString(R.string.time_minute, timeSecond / TimeFormat.MINUTE_SECOND);
//        } else {
//            return mContext.getString(R.string.time_second, timeSecond);
//        }
//    }

    public static String formatByString(String format, long timestamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(timestamp));
    }
}
