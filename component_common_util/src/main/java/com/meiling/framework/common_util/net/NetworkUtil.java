package com.meiling.framework.common_util.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by marisareimu@126.com on 2020-11-24  17:16
 * project MeilingFramework
 */

public class NetworkUtil {
    /**
     * 检查网络是否可用----------这个检测存在问题（链上WiFi但WiFi本身无法上网的话仍然认为是网络连接正常）
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        @SuppressLint("MissingPermission") NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable() || !networkinfo.isConnected()) {
            return false;
        }
        return true;
    }

    public static boolean isMobileAvailable(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable() && mWiFiNetworkInfo.isConnected();
            }
        }
        return false;
    }

    public static boolean isWiFiAvailable(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable() && mWiFiNetworkInfo.isConnected();
            }
        }
        return false;
    }
}
