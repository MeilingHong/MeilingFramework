package com.meiling.framework.common_util.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyWebChromeClient extends WebChromeClient {
    private Context mContext;
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private WebloadCallback webloadCallback;

    public MyWebChromeClient(Context context, ProgressBar progress, TextView textView) {
        this.mContext = context;
        this.mProgressBar = progress;
        this.mTextView = textView;
    }

    public MyWebChromeClient(Context context, ProgressBar progress, TextView textView, WebloadCallback webloadCallback) {
        this.mContext = context;
        this.mProgressBar = progress;
        this.mTextView = textView;
        this.webloadCallback = webloadCallback;
    }

    /**
     * 当前 WebView 加载网页进度
     *
     * @param view
     * @param newProgress
     */
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        setProgress(newProgress);
        if (webloadCallback != null) {
            webloadCallback.loadFinishCallback(view.getUrl(), newProgress);
        }
    }

    private void setProgress(int progress) {
        if (mProgressBar != null) {
            if (progress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(progress);
            }
        }
    }

    /**
     * 接收web页面的 Title
     *
     * @param view
     * @param title
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        setTitle(title);
    }

    private void setTitle(String title) {
        if (mTextView != null) {
            mTextView.setText(title);
        }
    }

    /**
     * 接收web页面的icon
     *
     * @param view
     * @param icon
     */
    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }

    /**
     * Js 中调用 alert() 函数，产生的对话框
     *
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    /**
     * 处理 Js 中的 Confirm 对话框
     *
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    /**
     * 处理 JS 中的 Prompt对话框
     *
     * @param view
     * @param url
     * @param message
     * @param defaultValue
     * @param result
     * @return
     */
    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    //打印JS的控制台输出
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }

}