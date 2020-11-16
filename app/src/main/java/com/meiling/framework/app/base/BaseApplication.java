package com.meiling.framework.app.base;

import com.meiling.framework.app.mvp.base.server.APIFactory;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.StringRes;
import androidx.multidex.MultiDexApplication;

/**
 * Created by marisareimu@126.com on 2020-11-16  10:43
 * project MeilingFramework
 */
public class BaseApplication extends MultiDexApplication {
    private static BaseApplication instances;
    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
    }

    private void initUrlMap() {
        //多域名配置方式
        Map<String, String> replaceMapping = new HashMap<>();
        replaceMapping.put("main", "");
        APIFactory.setHostReplaceMapping(replaceMapping);// 配置域名替换规则
    }

    public static BaseApplication getInstance() {
        return instances;
    }

    public static String getResString(@StringRes int stringRes) {
        return instances.getString(stringRes);
    }
}
