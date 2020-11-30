package com.meiling.framework.pay.weixin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.meiling.framework.pay.weixin.callback.WeixinShareCallback;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 微信分享文档
 * <p>
 * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317340&token=&lang=zh_CN
 * <p>
 * <p>
 */

public class WeixinShareUtil implements IWXAPIEventHandler {

    private WeixinShareCallback weixinShareCallback;
    private IWXAPI api;

    private WeixinShareUtil() {

    }

    public static WeixinShareUtil getInstances() {
        return WeixinShareUtil.WeixinShareUtilHolder.instances;
    }

    //---------------------------------------------------------------------------------

    public boolean isInstallWeixin(){
        return api.isWXAppInstalled();
    }
    /**
     * 在使用页调用
     *
     * @param context
     * @param APP_ID
     */
    public void initShare(Context context, String APP_ID) {
        api = WXAPIFactory.createWXAPI(context, APP_ID, false);
        api.registerApp(APP_ID);
    }

    /**
     * 在使用页调用，设置回调，否则即便分享成功了也拿不到微信给的回调
     * <p>
     * 1、在WXEntryActivity中设置回调，
     * 2、直接使用在在WXEntryActivity中调用
     * WeixinShareUtil.getInstances().onReq()
     * WeixinShareUtil.getInstances().onResp()
     */

    /*
     * 调用页面设置回调
     */
    public void setWeixinShareCallback(WeixinShareCallback weixinShareCallback) {
        this.weixinShareCallback = weixinShareCallback;
    }

    /**
     * 发送小程序，仅可使用会话的形式进行发送（不可分享至朋友圈）
     *
     * @param context
     * @param iconRes
     * @param webpageUrl
     * @param originalID
     * @param path
     * @param title
     * @param desc
     */
    public void shareMiniApp(Context context, int iconRes, String webpageUrl, String originalID, String path, String title, String desc) {
        WXMiniProgramObject wxMiniProgramObject = new WXMiniProgramObject();
        wxMiniProgramObject.webpageUrl = webpageUrl;
        wxMiniProgramObject.userName = originalID; // "gh_35d9f520a8f5";//原始ID
        wxMiniProgramObject.path = path;// https://ushare.xuanwuyun.com/qrcode/index/index?inviteCode=39e405f539d23ef2b44b85bb5acac4e9
        WXMediaMessage mediaMessage = new WXMediaMessage(wxMiniProgramObject);
        mediaMessage.title = title;
        mediaMessage.description = desc;
        mediaMessage.thumbData = setThumbImage(BitmapFactory.decodeResource(context.getResources(), iconRes));

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "miniProgram";
        req.message = mediaMessage;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
        api.sendReq(req);
    }

    /**
     * 分享小程序二维码
     *
     * 正常的二维码最好使用微信本身的接口去进行生产，然后将生成的图分享出去
     *
     * @param context
     * @param path
     * @param isWeixinCircle
     */
//    public void shareMiniAppImg(Context context, final String path, String title, String desc, boolean isWeixinCircle) {
//        Bitmap bmp = ZxingUtils.create2DCode(path, UnitExchangeUtil.dip2px(context, 128), UnitExchangeUtil.dip2px(context, 128));
//        WXImageObject imgObj = new WXImageObject(bmp);
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = imgObj;
//        //设置缩略图
//        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, UnitExchangeUtil.dip2px(context, 128), UnitExchangeUtil.dip2px(context, 128), true);
//        msg.thumbData = setThumbImage(thumbBmp);
//        bmp.recycle();
//        msg.title = title;
//        msg.description = desc;
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = "img";
//        req.message = msg;
//        if (isWeixinCircle) {
//            req.scene = SendMessageToWX.Req.WXSceneTimeline;
//        } else {
//            req.scene = SendMessageToWX.Req.WXSceneSession;
//        }
//
////        req.userOpenId = "";
//        //调用api接口，发送数据到微信
//        api.sendReq(req);
//    }

    /**
     * 分享文字
     *
     * @param title
     * @param desc
     * @param isWeixinCircle
     */
    public void shareText(String title, String desc, boolean isWeixinCircle) {
        WXTextObject textObj = new WXTextObject();
        textObj.text = desc;

        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;

        msg.title = title;
        msg.description = desc;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "text";
        req.message = msg;
        if (isWeixinCircle) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        //调用api接口，发送数据到微信
        api.sendReq(req);
    }

    /**
     * 分享图片
     *
     * @param context
     * @param bitmap
     * @param title
     * @param desc
     * @param isWeixinCircle
     */
//    public void shareImage(Context context, Bitmap bitmap, String title, String desc, boolean isWeixinCircle) {
//        WXImageObject imgObj = new WXImageObject(bitmap); // 大小不能超过10M
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = imgObj;
//        //设置缩略图
//        /*
//         * checkArgs fail, thumbData is invalid ；文档中说明为：限制内容大小不超过32KB
//         * 当缩略图大小超过限制时，会报这个错误，并且无法调起微信
//         */
//        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, UnitExchangeUtil.dip2px(context, 128), UnitExchangeUtil.dip2px(context, 128), true);
//        msg.thumbData = setThumbImage(thumbBmp);
//        bitmap.recycle();
//        msg.title = title;
//        msg.description = desc;
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = "img";
//        req.message = msg;
//        if (isWeixinCircle) {
//            req.scene = SendMessageToWX.Req.WXSceneTimeline;
//        } else {
//            req.scene = SendMessageToWX.Req.WXSceneSession;
//        }
//        //调用api接口，发送数据到微信
//        api.sendReq(req);
//    }

    /**
     * 分享网页
     *
     * @param context
     * @param webUrl
     * @param bitmap         缩略图
     * @param title
     * @param desc
     * @param isWeixinCircle
     */
//    public void shareUrl(Context context, String webUrl, Bitmap bitmap, String title, String desc, boolean isWeixinCircle) {
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = webUrl;
//
//        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = webpage;
//        msg.title = title;
//        msg.description = desc;
//        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, UnitExchangeUtil.dip2px(context, 128), UnitExchangeUtil.dip2px(context, 128), true);
//        msg.thumbData = setThumbImage(thumbBmp);
//        bitmap.recycle();
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = "webpage";
//        req.message = msg;
//        if (isWeixinCircle) {
//            req.scene = SendMessageToWX.Req.WXSceneTimeline;
//        } else {
//            req.scene = SendMessageToWX.Req.WXSceneSession;
//        }
//        //调用api接口，发送数据到微信
//        api.sendReq(req);
//    }

    /**
     * 分享音乐
     *
     * @param context
     * @param musicUrl
     * @param bitmap
     * @param title
     * @param desc
     * @param isWeixinCircle
     */
//    public void shareMusic(Context context, String musicUrl, Bitmap bitmap, String title, String desc, boolean isWeixinCircle) {
//        WXMusicObject music = new WXMusicObject();
//        music.musicUrl = musicUrl;
//
//        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = music;
//
//        msg.title = title;
//        msg.description = desc;
//
//        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, UnitExchangeUtil.dip2px(context, 128), UnitExchangeUtil.dip2px(context, 128), true);
//        msg.thumbData = setThumbImage(thumbBmp);
//        bitmap.recycle();
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = "music";
//        req.message = msg;
//        if (isWeixinCircle) {
//            req.scene = SendMessageToWX.Req.WXSceneTimeline;
//        } else {
//            req.scene = SendMessageToWX.Req.WXSceneSession;
//        }
//        //调用api接口，发送数据到微信
//        api.sendReq(req);
//    }


    /**
     * 分享视频
     *
     * @param context
     * @param videoUrl
     * @param bitmap
     * @param title
     * @param desc
     * @param isWeixinCircle
     */
//    public void shareVideo(Context context, String videoUrl, Bitmap bitmap, String title, String desc, boolean isWeixinCircle) {
//        WXVideoObject music = new WXVideoObject();
//        music.videoUrl  = videoUrl;
//
//        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = music;
//
//        msg.title = title;
//        msg.description = desc;
//
//        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, UnitExchangeUtil.dip2px(context, 128), UnitExchangeUtil.dip2px(context, 128), true);
//        msg.thumbData = setThumbImage(thumbBmp);
//        bitmap.recycle();
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = "music";
//        req.message = msg;
//        if (isWeixinCircle) {
//            req.scene = SendMessageToWX.Req.WXSceneTimeline;
//        } else {
//            req.scene = SendMessageToWX.Req.WXSceneSession;
//        }
//        //调用api接口，发送数据到微信
//        api.sendReq(req);
//    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                try {
                    if (baseResp != null && baseResp instanceof SendMessageToWX.Resp) {
                        if (weixinShareCallback != null) {
                            weixinShareCallback.onWeixinShareSuccess();
                        }
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (weixinShareCallback != null) {
                    weixinShareCallback.onWeixinShareCancel();
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                if (weixinShareCallback != null) {
                    weixinShareCallback.onWeixinShareDenied();
                }
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                if (weixinShareCallback != null) {
                    weixinShareCallback.onWeixinShareFail();
                }
                break;
            default:
                if (weixinShareCallback != null) {
                    weixinShareCallback.onWeixinShareFail();
                }
                break;
        }
    }

    public static final byte[] setThumbImage(Bitmap bitmap) {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, os);
            return os.toByteArray();
        } catch (Exception var12) {
            var12.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException var11) {
                var11.printStackTrace();
            }
        }
        return null;
    }

    //---------------------------------------------------------------------------------
    private static class WeixinShareUtilHolder {
        private static final WeixinShareUtil instances = new WeixinShareUtil();
    }
}
