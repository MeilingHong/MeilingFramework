package com.meiling.framework.pay.weixin.callback;


import com.meiling.framework.pay.weixin.bean.WeixinInfoBean;

public interface WeixinInfoCallback {
    void getWeixinInfo(int type, WeixinInfoBean weixinInfoBean);
}
