package com.meiling.framework.network.utils;

/**
 * Created by marisareimu@126.com on 2020-11-16  14:59
 * project MeilingFramework
 */

public interface RequestContentType {
    String JSON = "application/json;charset=UTF-8";
    String TEXT = "text/plain;charset=UTF-8";
    String FORM = "application/x-www-form-urlencoded;charset=UTF-8";
    String MULTI_PART = "multipart/form-data;charset=UTF-8";
    String FORM_ONLY = "multipart/form-data;charset=UTF-8";
}
