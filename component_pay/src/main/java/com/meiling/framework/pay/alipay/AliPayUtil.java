package com.meiling.framework.pay.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.meiling.framework.pay.alipay.bean.PayResult;
import com.meiling.framework.pay.alipay.callback.AliPayCallback;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 支付宝支付
 * <p>
 * <p>
 * <p>
 */

public class AliPayUtil {

    private AliPayCallback aliPayCallback;
    private ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
    private String orderInfoString;
    private final int MSG_PAY = 1000;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_PAY: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        if (aliPayCallback != null) {
                            aliPayCallback.payAlipaySuccess();
                        }
                    } else {
//                        CrashReport.postCatchedException(new Exception("支付宝支付【失败上报】:参数：" + (orderInfoString != null ? orderInfoString : "") +
//                                "--返回：" + resultInfo + "<->" + resultStatus));
//                        Ulog.e("支付宝支付【失败上报】:参数：" + (orderInfoString != null ? orderInfoString : "") +
//                                "--返回：" + resultInfo + "<->" + resultStatus);
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        if (aliPayCallback != null) {
                            aliPayCallback.payAlipayFail(resultStatus, resultInfo);
                        }
                    }
                    break;
                }
            }
        }
    };

    private AliPayUtil() {

    }

    public static AliPayUtil getInstances() {
        return AliPayUtilHolder.instances;
    }

    //---------------------------------------------------------------------------------

    /**
     * 1、调用步骤，获取instance对象，然后，设置支付回调接口 setAliPayCallback ；
     * 2、调用获取订单信息成功后，调用支付方法 startPayAction
     */

    public void setAliPayCallback(AliPayCallback aliPayCallback) {
        this.aliPayCallback = aliPayCallback;
    }

    /**
     * 需要由服务端进行签名
     *
     * @param activity
     * @param orderInfo
     */
    public void startPayAction(final Activity activity, final String orderInfo) {
        orderInfoString = orderInfo;
        singleThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = MSG_PAY;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        });
    }

    //---------------------------------------------------------------------------------
    private static class AliPayUtilHolder {
        private static final AliPayUtil instances = new AliPayUtil();
    }
}
