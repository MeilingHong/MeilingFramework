package com.meiling.framework.base_activity.util.systemcall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;


public class ToolsUtils {
    /**
     * 啟動系統設置
     */
    public static void launchAppDetailsSettings(Context mContext) {
        String packageName = mContext.getPackageName();
        if (isSpace(packageName)) return;
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
