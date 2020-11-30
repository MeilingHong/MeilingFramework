package com.meiling.framework.pay.weixin;

import android.content.Context;

import com.meiling.framework.pay.weixin.bean.ReturnCode;
import com.meiling.framework.pay.weixin.bean.WeixinInfoBean;
import com.meiling.framework.pay.weixin.callback.WeixinInfoCallback;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.UUID;


public class WeixinLoginUtil implements IWXAPIEventHandler {

    private IWXAPI api;
    private WeixinInfoCallback weixinInfoCallback;
    private WeixinInfoBean weixinInfoBean;

    private WeixinLoginUtil() {

    }

    public static WeixinLoginUtil getInstances() {
        return WeixinLoginUtilHolder.instances;
    }

    //---------------------------------------------------------------------------------
    //微信
    /*
     * TODO 建议的Application中
     */
    public void initWeixin(Context context, String APP_ID) {
        api = WXAPIFactory.createWXAPI(context.getApplicationContext(), APP_ID, false);
        api.registerApp(APP_ID);
        weixinInfoBean = new WeixinInfoBean();
    }

    /*
     * 1、在WXEntryActivity中设置回调，
     * 2、直接使用在在WXEntryActivity中调用
     *      WeixinLoginUtil.getInstances().getWXCallback().onReq()
     *      WeixinLoginUtil.getInstances().getWXCallback().onResp()
     */
    public IWXAPIEventHandler getWXCallback() {
        return this;
    }

    /*
     * 调用页面设置回调
     */
    public void setWeixinInfoCallback(WeixinInfoCallback weixinInfoCallback) {
        this.weixinInfoCallback = weixinInfoCallback;
    }

    /*
     * 执行登录
     */
    public void doWeixinLogin() {
        if (api != null && api.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = UUID.randomUUID().toString();
            api.sendReq(req);
            /*
            onResp----  返回数据
            openId：oOJEs1GsW4jqeIXUvbHMY4myHF1I----
            transaction：null----
            errStr：null----
            errCode：-6----
            getType：1----
            checkArgs：true----
            com.tencent.mm.opensdk.modelmsg.SendAuth$Resp@f1f9ff5
             */
        } else {
            if (api != null) {
                //提示未安装微信
                if (weixinInfoCallback != null) {
                    weixinInfoCallback.getWeixinInfo(ReturnCode.TYPE_NO_INSTALL, null);
                }
            } else {
                if (weixinInfoCallback != null) {
                    weixinInfoCallback.getWeixinInfo(ReturnCode.TYPE_NO_INIT, null);
                }
            }
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:

//                LogUtils.e("onResp\n----\ncode：" + authResult.code +
//                        "\n----openId："+baseResp.openId+
//                        "\n----\ntransaction："+baseResp.transaction+
//                        "\n----\nerrStr："+baseResp.errStr+
//                        "\n----\nerrCode："+baseResp.errCode+
//                        "\n----\ncountry：" + authResult.country +
//                        "\n----\ncheckArgs()：" + authResult.checkArgs() +
//                        "\n----\nlang：" + authResult.lang +
//                        "\n----\nstate：" + authResult.state +
//                        "\n----\nurl：" + authResult.url +
//                        "\n----\n" + authResult.toString());
                try {//TODO 判断数据类型
                    if (baseResp != null && baseResp instanceof SendAuth.Resp) {
                        SendAuth.Resp authResult = (SendAuth.Resp) baseResp;
                        weixinInfoBean.setCode(authResult.code);
                        weixinInfoBean.setOpenId(authResult.openId);
                        if (weixinInfoCallback != null) {
                            weixinInfoCallback.getWeixinInfo(ReturnCode.TYPE_LOGIN_INFO, weixinInfoBean);
                        }
                    } else {
                        if (weixinInfoCallback != null) {
                            weixinInfoCallback.getWeixinInfo(ReturnCode.TYPE_UNKNOWN_ERROR, null);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (weixinInfoCallback != null) {
                        weixinInfoCallback.getWeixinInfo(ReturnCode.TYPE_UNKNOWN_ERROR, null);
                    }
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (weixinInfoCallback != null) {
                    weixinInfoCallback.getWeixinInfo(ReturnCode.TYPE_CANCEL, null);
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                if (weixinInfoCallback != null) {
                    weixinInfoCallback.getWeixinInfo(ReturnCode.TYPE_AUTH_DENIED, null);
                }
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                if (weixinInfoCallback != null) {
                    weixinInfoCallback.getWeixinInfo(ReturnCode.TYPE_UNSUPPORT, null);
                }
                break;
            default:
                if (weixinInfoCallback != null) {
                    weixinInfoCallback.getWeixinInfo(ReturnCode.TYPE_UNKNOWN_ERROR, null);
                }
                break;
        }
    }

    //---------------------------------------------------------------------------------
    private static class WeixinLoginUtilHolder {
        private static final WeixinLoginUtil instances = new WeixinLoginUtil();
    }
}
