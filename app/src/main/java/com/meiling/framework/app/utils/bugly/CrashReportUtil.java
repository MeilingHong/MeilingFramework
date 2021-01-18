package com.meiling.framework.app.utils.bugly;

import android.text.TextUtils;

import com.meiling.framework.common_util.log.Ulog;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by marisareimu@126.com on 2020-12-15  16:24
 * project
 */
public class CrashReportUtil {
    public static void report(StringBuilder stringBuilder, Throwable throwable) {
        Ulog.e(stringBuilder.toString() + "---" + Ulog.getThrowableStackTrace(throwable));
        // todo 文字信息长度存在限制，大约只能设置1000个字符左右
        if (throwable != null) {
            CrashReport.postCatchedException(new Exception((stringBuilder != null && !TextUtils.isEmpty(stringBuilder.toString()))
                    ? stringBuilder.toString() : "Unknown Exception!", throwable));
        } else {
            CrashReport.postCatchedException(new Exception((stringBuilder != null && !TextUtils.isEmpty(stringBuilder.toString()))
                    ? stringBuilder.toString() : "Unknown Exception!"));
        }
    }

    public static void report(String stringBuilder, Throwable throwable) {
        Ulog.e(stringBuilder + "---" + Ulog.getThrowableStackTrace(throwable));
        if (throwable != null) {
            CrashReport.postCatchedException(new Exception((stringBuilder != null && !TextUtils.isEmpty(stringBuilder.toString()))
                    ? stringBuilder.toString() : "Unknown Exception!", throwable));
        } else {
            CrashReport.postCatchedException(new Exception((stringBuilder != null && !TextUtils.isEmpty(stringBuilder.toString()))
                    ? stringBuilder.toString() : "Unknown Exception!"));
        }
    }
}
