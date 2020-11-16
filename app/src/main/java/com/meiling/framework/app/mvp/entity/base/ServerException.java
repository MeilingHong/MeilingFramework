package com.meiling.framework.app.mvp.entity.base;
/**
 * Created by marisareimu@126.com on 2020-11-16  16:03
 * project MeilingFramework
 */

public class ServerException extends Exception{
    private String errorCode;
    private String errorMsg;

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
}
