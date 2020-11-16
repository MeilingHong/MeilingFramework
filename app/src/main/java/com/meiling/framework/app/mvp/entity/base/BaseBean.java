package com.meiling.framework.app.mvp.entity.base;

import com.meiling.framework.common_util.gson.BeanToString;

/**
 * Created by marisareimu@126.com on 2020-11-16  15:51
 * project MeilingFramework
 */
public class BaseBean<T> extends BeanToString {
    private String errorCode;
    private String errorMsg;
    private Boolean success;
    private T data;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
