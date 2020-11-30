package com.meiling.framework.pay.weixin.bean;


public interface ReturnCode {
    int TYPE_UNSUPPORT = -8;
    int TYPE_AUTH_DENIED = -7;
    int TYPE_NO_INIT = -6; //未初始化
    int TYPE_UNKNOWN_ERROR = -5;
    int TYPE_NO_INSTALL = -4;
    int TYPE_ERROR_JSON_FORMAT = -3;
    int TYPE_CANCEL = -2;
    int TYPE_ERROR = -1;
    int TYPE_NOT_LOGIN = 0;
    int TYPE_LOGIN_INFO = 1;
    int TYPE_INVALID_INFO = 2;
    int TYPE_UNION_ID = 3;
    int TYPE_NO_UNION_ID = 4;
}
