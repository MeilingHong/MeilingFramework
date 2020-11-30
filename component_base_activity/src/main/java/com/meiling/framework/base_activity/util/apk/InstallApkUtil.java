package com.meiling.framework.base_activity.util.apk;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;

import androidx.core.content.FileProvider;

public class InstallApkUtil {
    public static void installApk(Context mContext, String authority, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uriData;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uriData = FileProvider.getUriForFile(mContext, authority, file);
        } else {
            uriData = Uri.fromFile(file);
        }
        intent.setDataAndType(uriData, type);
        mContext.startActivity(intent);
    }
}
