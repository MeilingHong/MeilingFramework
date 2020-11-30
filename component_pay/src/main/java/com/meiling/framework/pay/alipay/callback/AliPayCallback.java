package com.meiling.framework.pay.alipay.callback;


public interface AliPayCallback {
    void payAlipaySuccess();//支付成功
    void payAlipayFail(String errorCode, String errorMsg);
}
