package com.meiling.framework.app.mvp.base;


import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.meiling.framework.app.R;
import com.meiling.framework.app.base.BaseApplication;
import com.meiling.framework.app.mvp.entity.base.BaseCheckBean;
import com.meiling.framework.app.mvp.entity.base.ServerException;
import com.meiling.framework.common_util.gson.GsonUtils;
import com.meiling.framework.common_util.gson.Gsons;
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
        BaseCheckBean check = GsonUtils.getObjFromJSON(Gsons.getInstance().toJson(resultObject), BaseCheckBean.class);
        // todo 如果需要兼容来自不同平台服务的数据，则需要使得BaseCheckBean包含不同服务的标准判断数据格式，
        // todo 并根据对应的格式进行处理【因为不同平台的服务】
        if (check != null && check.success != null && check.success) {
            onRequestSuccess(resultObject);
        } else {
            // 处理失败【1、存在符合预期的对象，但判断字段不为指定数据；2、不存在符合预期的对象，数据也不为已知的结构】
            if (check != null && check.errorCode != null && check.errorMsg != null) {
                // todo 该处为与服务端约定的正确的错误返回格式，如果不满足该格式，表示服务端未按照标准格式处理，或者服务端出了异常
                ServerException serverException = new ServerException();
                serverException.setErrorCode(check.errorCode);
                serverException.setErrorMsg(check.errorMsg);
                onError(serverException);
            } else {
                ServerException serverException = new ServerException();
                onError(serverException);
            }
        }
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
        if (e != null && e instanceof HttpException) {// todo 以下部分是请求未正常返回情况
            onRequestError("-400", BaseApplication.getResString(R.string.network_error));
        } else if (e != null && e instanceof ConnectException) {
            onRequestError("-401", BaseApplication.getResString(R.string.network_error));
        } else if (e != null && e instanceof UnknownHostException) {
            onRequestError("-402", BaseApplication.getResString(R.string.network_error));
        } else if (e != null && e instanceof InterruptedIOException) {
            onRequestError("-403", BaseApplication.getResString(R.string.network_error));
        } else if (e != null && e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            onRequestError("-405", BaseApplication.getResString(R.string.network_error));
        } else if (e != null && e instanceof ServerException) {// todo 统一处理来自服务器的错误信息，其中也有可能是异常的返回信息
            ServerException serverException = (ServerException) e;
            onRequestError(serverException.getErrorCode(), serverException.getErrorMsg());
        } else {
            if (e != null) {// todo  未进行区分的异常类型【APP内部的错误使用负数错误码，方便进行区分来标记】
                onRequestError("-406", BaseApplication.getResString(R.string.network_data_unknown_error));
            } else {
                onRequestError("-407", BaseApplication.getResString(R.string.network_data_unknown_error));
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
