package com.meiling.framework.app.utils.directory;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * Created by marisareimu@126.com on 2021-01-07  20:40
 * project MeilingFramework
 */

public class DirectoryUtil {
    /**
     * 折中办法是在
     * <application
     *     android:requestLegacyExternalStorage="true" //添加这个属性
     *     >
     *  </application>
     */
    public static String getExternalDir(Context mContext) {// 指定的临时目录
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
        if (sdCardExist) {
            if (Build.VERSION.SDK_INT >= 29) {
                //Android10之后-- 避免Android 10以后无法创建文件
                return mContext.getExternalFilesDir(null).getPath();
            } else {
                return Environment.getExternalStorageDirectory().getPath();
            }
        } else {
            return Environment.getRootDirectory().getPath();
        }
    }

    public static File getExternalDirFile(Context mContext) {// 指定的临时目录
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
        if (sdCardExist) {
            if (Build.VERSION.SDK_INT >= 29) {
                //Android10之后-- 避免Android 10以后无法创建文件
                return mContext.getExternalFilesDir(null);
            } else {
                return Environment.getExternalStorageDirectory();
            }
        } else {
            return Environment.getRootDirectory();
        }
    }
}
