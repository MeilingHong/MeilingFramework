package com.meiling.framework.pay.weixin.callback;


public interface WeixinPayCallback {
    void paySuccess();//支付成功
    void payFail(int errorCode, String errorMsg);
}
