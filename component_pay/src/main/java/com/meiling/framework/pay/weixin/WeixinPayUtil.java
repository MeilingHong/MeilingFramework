package com.meiling.framework.pay.weixin;

import android.content.Context;
import android.text.TextUtils;

import com.meiling.framework.pay.weixin.callback.WeixinPayCallback;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

/**
 * 微信分享文档
 * <p>
 * <p>
 * <p>
 */

public class WeixinPayUtil implements IWXAPIEventHandler {

    private WeixinPayCallback weixinPayCallback;
    private IWXAPI api;
    private JSONObject jsonObject;

    private WeixinPayUtil() {

    }

    public static WeixinPayUtil getInstances() {
        return WeixinPayUtil.WeixinPayUtilHolder.instances;
    }

    //---------------------------------------------------------------------------------

    /**
     * 在使用页调用
     *
     * @param context
     * @param APP_ID
     */
    public void initPay(Context context, String APP_ID) {
        api = WXAPIFactory.createWXAPI(context, APP_ID, false);
        api.registerApp(APP_ID);
    }

    public boolean isInstallWeixin() {
        return api.isWXAppInstalled();
    }

    public void sendPay(JSONObject json) {
        try {
            if (null != json &&
                    json.has("prepayid") && !TextUtils.isEmpty(json.getString("prepayid"))) {
                jsonObject = json;
                PayReq req = new PayReq();
                //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                req.appId = json.getString("appid");
                req.partnerId = json.getString("partnerid");
                req.prepayId = json.getString("prepayid");
                req.nonceStr = json.getString("noncestr");
                req.timeStamp = json.getString("timestamp");
                req.sign = json.getString("sign");
                //以上部分需要从接口获取
                req.packageValue = "Sign=WXPay";
                req.extData = "APP"; // optional
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                api.sendReq(req);
            } else {
                if (weixinPayCallback != null) {
                    weixinPayCallback.payFail(BaseResp.ErrCode.ERR_COMM, json.getString("retmsg"));
                }
            }
        } catch (Exception e) {
            if (weixinPayCallback != null) {
                weixinPayCallback.payFail(BaseResp.ErrCode.ERR_COMM, e.getMessage());
            }
        }
    }


    /**
     * 在使用页调用，设置回调，否则即便分享成功了也拿不到微信给的回调
     * <p>
     * 1、在WXEntryActivity中设置回调，
     * 2、直接使用在在WXEntryActivity中调用
     * WeixinPayUtil.getInstances().onReq()
     * WeixinPayUtil.getInstances().onResp()
     */
    public void setWeixinPayCallback(WeixinPayCallback weixinPayCallback) {
        this.weixinPayCallback = weixinPayCallback;
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK: {
                    if (weixinPayCallback != null) {
                        weixinPayCallback.paySuccess();
                    }
                    break;
                }
                default: {
                    if (weixinPayCallback != null) {
                        weixinPayCallback.payFail(baseResp.errCode, baseResp.errStr);
                    }
                    break;
                }
            }
        }
    }

    //---------------------------------------------------------------------------------
    private static class WeixinPayUtilHolder {
        private static final WeixinPayUtil instances = new WeixinPayUtil();
    }
}
