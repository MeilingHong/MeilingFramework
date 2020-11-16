package com.meiling.framework.common_util.web;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.meiling.framework.common_util.log.Ulog;
import com.meiling.framework.common_util.toast.ToastUtil;

import androidx.annotation.RequiresApi;


/**
 * Created by pocketEos on 2018/4/11.
 */

public class MyWebViewClient extends WebViewClient {

    private Activity mContext;
    private String assertJSName;
    private boolean shouldInterrupt = true;
    private WebView webView;

    private String lastLoadUrl;

    public MyWebViewClient(Activity context) {
        mContext = context;
    }

    public MyWebViewClient(Activity mContext, String assertJSName) {
        this.mContext = mContext;
        this.assertJSName = assertJSName;
    }

    public MyWebViewClient(Activity mContext, boolean shouldInterrupt, WebView webView) {
        this.mContext = mContext;
        this.shouldInterrupt = shouldInterrupt;
        this.webView = webView;
    }

    /**
     * 是否在 WebView 内加载页面
     *
     * @param view
     * @param url
     * @return
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!TextUtils.isEmpty(url) && url.toLowerCase().startsWith("http")) {
            Ulog.e("shouldOverrideUrlLoading-----------" + url);
//            view.loadUrl(url);
        } else {
//            openBrowser(url);
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        if (!TextUtils.isEmpty(url) && url.toLowerCase().startsWith("http")) {
            Ulog.e("shouldOverrideUrlLoading-----------" + url);
//            view.loadUrl(url);
        } else {
//            openBrowser(url);// 暂时屏蔽调支持的跳转，避免增加用户操作成本
            return true;
        }
        return false;
    }

//    public void openBrowser(String url) {
//        Ulog.e("openBrowser-----------" + url);
//        final Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(url));
//        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
//            mContext.startActivity(Intent.createChooser(intent, mContext.getString(R.string.explorer_hint1)));
//        } else {
//            ToastUtil.toastShort(mContext, mContext.getString(R.string.explorer_hint2));
//        }
//    }

    /**
     * WebView 开始加载页面时回调，一次Frame加载对应一次回调
     *
     * @param view
     * @param url
     * @param favicon
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
//        Ulog.e("onPageStarted-----------" + url);
        super.onPageStarted(view, url, favicon);
    }

    /**
     * WebView 完成加载页面时回调，一次Frame加载对应一次回调
     *
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
//        Ulog.e("onPageFinished-----------" + url);
        super.onPageFinished(view, url);
    }

    /**
     * WebView 加载页面资源时会回调，每一个资源产生的一次网络加载，除非本地有当前 url 对应有缓存，否则就会加载。
     *
     * @param view WebView
     * @param url  url
     */
    @Override
    public void onLoadResource(WebView view, String url) {
//        Ulog.e("onLoadResource-----------" + url);
        super.onLoadResource(view, url);
    }

    /**
     * WebView 可以拦截某一次的 request 来返回我们自己加载的数据
     *
     * @param view    WebView
     * @param request 当前产生 request 请求
     * @return WebResourceResponse
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(final WebView view, final WebResourceRequest request) {
//        Ulog.e("shouldInterceptRequest-----------" + (request != null && request.getUrl() != null ? request.getUrl().toString() : "空链接"));
        return super.shouldInterceptRequest(view, request);
    }

    /**
     * WebView 访问 url 出错
     *
     * @param view
     * @param request
     * @param error
     */
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//        Ulog.e("onReceivedError-----------" + (request != null && request.getUrl() != null ? request.getUrl().toString() : "空链接"));
        super.onReceivedError(view, request, error);
    }

    /**
     * WebView ssl 访问证书出错，handler.cancel()取消加载，handler.proceed()对然错误也继续加载
     *
     * @param view
     * @param handler
     * @param error
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (error.getPrimaryError() == SslError.SSL_INVALID) {
            handler.proceed();
        } else {
            handler.cancel();
        }
        super.onReceivedSslError(view, handler, error);

    }

    /**
     * 当WebView得页面Scale值发生改变时回调
     */
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
    }


}

