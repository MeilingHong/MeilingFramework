package com.meiling.framework.app.mvp.environment;

import android.content.Context;

import com.meiling.framework.common_util.environment.EvnSwitcher;

public class EvnGetter {
    public static String getMainUrl(Context context) {
        switch (EvnSwitcher.getCurrentEvn(context)) {
            case DEV:
                return "https://ubanquan.cn";
            case PROD:
                return "https://ubanquan.cn";
            case TEST:
                return "https://ubanquan.cn";
            case PRE:
                return "https://ubanquan.cn";
            default:
                return "https://ubanquan.cn";
        }
    }
}
