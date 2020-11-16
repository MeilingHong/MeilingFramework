package com.meiling.framework.network.view;

public interface BaseView {

    /**
     * @param msg
     */
    void showDialog(String msg);

    /**
     * 辅助关闭对话框的回调，相当于给前端页面一个回调信号
     */
    void dismissDialog();

    /**
     * @param businessCode 辅助识别当前回调是属于哪一个业务流程
     * @param errorMsg     根据业务码来判断是否需要展示这个错误
     */
    void requestFail(int businessCode, String errorCode, String errorMsg);
}
