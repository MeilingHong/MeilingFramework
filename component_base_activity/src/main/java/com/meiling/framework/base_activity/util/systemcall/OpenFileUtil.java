package com.meiling.framework.base_activity.util.systemcall;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.annotation.StringRes;

public class OpenFileUtil {
    /**
     * 如果是本地的路径，需要转换成对应的URI地址
     *
     * @param context
     * @param path
     */
    public static void openFileBySystem(Context context, String path, @StringRes int stringExceptionHint, @StringRes int stringNoMatchApp) {
        try {
            Intent intent = new Intent();
            //设置intent的Action属性
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            Uri uri = Uri.parse(path);
            intent.setDataAndType(uri, "*/*");
            if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                context.startActivity(intent);
            } else {
//                ToastUtil.toastLong(context, context.getString(stringNoMatchApp));
            }
        } catch (Exception e) {
//            ToastUtil.toastLong(context, context.getString(stringExceptionHint));
        }
    }

    public static void openFileBySystem(Context context, String path, String type, @StringRes int stringExceptionHint, @StringRes int stringNoMatchApp) {
        try {
            Intent intent = new Intent();
            //设置intent的Action属性
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            Uri uri = Uri.parse(path);
            intent.setDataAndType(uri, type);
            if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                context.startActivity(intent);
            } else {
//                ToastUtil.toastLong(context, context.getString(stringNoMatchApp));
            }
        } catch (Exception e) {
//            ToastUtil.toastLong(context, context.getString(stringExceptionHint));
        }
    }
}
