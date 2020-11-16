package com.meiling.framework.common_util.gson;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * Created by marisareimu@126.com on 2020-11-16  15:48
 * project MeilingFramework
 */
public class BeanToString implements Serializable {
    @NonNull
    @Override
    public String toString() {
        return Gsons.getInstance().toJson(this);
    }
}
