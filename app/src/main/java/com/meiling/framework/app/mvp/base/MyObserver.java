package com.meiling.framework.app.mvp.base;


import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.meiling.framework.network.presenter.DefaultObserver;
import com.meiling.framework.network.view.BaseView;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;


public abstract class MyObserver<T> extends DefaultObserver<T> {

    private boolean isShowLoading = true;// 默认显示加载等待对话框
    private String mLoadingMsg;
    private String mUrlString;
    private String apiExceptionString;

    public MyObserver(BaseView view, boolean isShowLoading,
                      String loadingMsg, String urlString) {
        super(view);
        this.isShowLoading = isShowLoading;
        this.mLoadingMsg = loadingMsg;
        this.mUrlString = urlString;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (view != null && isShowLoading) {// 当指定了回调用于显示
            view.showDialog(!TextUtils.isEmpty(mLoadingMsg) ? mLoadingMsg : "");
        }
    }

    @Override
    public void onNext(T resultObject) {
        super.onNext(resultObject);
        // todo 将resultObject对象进行转换，变成对应的实体类，可以抽离出其中公共部分进行判断，如果不符合判断条件，则默认出错
        //这部分处理需要根据实际的数据返回格式进行变动
        // 处理成功【存在符合预期的对象，且用于判断的字段为指定的数据】
        onRequestSuccess(resultObject);
        // 处理失败【1、存在符合预期的对象，但判断字段不为指定数据；2、不存在符合预期的对象，数据也不为已知的结构】
        onError(new Exception());
        if (view != null) {
            view.dismissDialog();
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        //
        if (view != null) {
            view.dismissDialog();
        }
        if (e != null && e instanceof HttpException) {
            onRequestError("", e.getMessage());
        } else if (e != null && e instanceof ConnectException) {
            onRequestError("", e.getMessage());
        } else if (e != null && e instanceof UnknownHostException) {
            onRequestError("", e.getMessage());
        } else if (e != null && e instanceof InterruptedIOException) {
            onRequestError("", e.getMessage());
        } else if (e != null && e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            onRequestError("", e.getMessage());
        } else {
            if (e != null) {
                onRequestError("", e.getMessage());
            } else {
                onRequestError("", "");
            }
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
    }

    public abstract void onRequestSuccess(T result);

    public abstract void onRequestError(String errorCode, String errorMessage);
}
