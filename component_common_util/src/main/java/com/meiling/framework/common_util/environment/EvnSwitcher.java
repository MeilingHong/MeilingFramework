package com.meiling.framework.common_util.environment;

import android.content.Context;

import com.meiling.framework.common_util.sharedpreferences.SharedPreferencesUtil;

import androidx.annotation.IntRange;

/**
 * Created by marisareimu@126.com on 2020-11-16  13:40
 * project MeilingFramework
 */

/**
 * 记录当前环境属于那种
 */
public class EvnSwitcher {
    public static void saveCurrentEvn(Context context, EVN evn) {
        SharedPreferencesUtil.saveInt(context,
                "env_switch",
                "evn_key",
                evn.getValue()
        );
    }

    public static EVN getCurrentEvn(Context context) {
        return exchange(SharedPreferencesUtil.readInt(context,
                "env_switch",
                "evn_key"));
    }

    private static EVN exchange(@IntRange(from = 0, to = 3) int value) {
        switch (value) {
            case 0: {
                return EVN.PROD;
            }
            case 1: {
                return EVN.DEV;
            }
            case 2: {
                return EVN.TEST;
            }
            case 3: {
                return EVN.PRE;
            }
            default: {
                return EVN.PROD;
            }
        }
    }
}
