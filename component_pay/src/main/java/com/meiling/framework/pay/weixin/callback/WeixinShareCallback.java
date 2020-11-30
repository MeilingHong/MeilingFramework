package com.meiling.framework.pay.weixin.callback;


public interface WeixinShareCallback {
    void onWeixinShareSuccess();
    void onWeixinShareCancel();
    void onWeixinShareFail();
    void onWeixinShareDenied();
    void onWeixinShareFail(int type, String message);
}
