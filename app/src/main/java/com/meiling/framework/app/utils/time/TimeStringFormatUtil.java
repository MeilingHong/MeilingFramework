package com.meiling.framework.app.utils.time;

import android.text.TextUtils;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by marisareimu@126.com on 2020-12-21  20:26
 * project Ubanquan
 */
public class TimeStringFormatUtil {

    /**
     *  将该执行格式的"2020-12-18T06:23:01.000+0000"时间字符串转换成指定类型的格式进行显示
     *
     * @param tvSize
     * @param timeString
     * @param timeFormatString
     */
    public static void setTimeStringFormat(TextView tvSize, String timeString, String timeFormatString) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date createTime = simpleDateFormat.parse(!TextUtils.isEmpty(timeString) ? timeString.
                    replace("T", " ").
                    replace(".000+0000", "").
                    replace(".000+0800", "") : "");
            SimpleDateFormat format = new SimpleDateFormat(timeFormatString);
            if (tvSize != null) {
                tvSize.setText(format.format(createTime.getTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (tvSize != null) {
                tvSize.setText(!TextUtils.isEmpty(timeString) ? timeString : "");
            }
        }
    }

    /**
     * todo 将时间戳转换成指定的格式化字符串
     *
     * @param tvSize
     * @param timeStamp
     * @param timeFormatString
     */
    public static void setTimeStampByFormat(TextView tvSize, long timeStamp, String timeFormatString) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(timeFormatString);
            if (tvSize != null) {
                tvSize.setText(format.format(new Date(timeStamp)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (tvSize != null) {
                tvSize.setText(String.valueOf(timeStamp));
            }
        }
    }
}
