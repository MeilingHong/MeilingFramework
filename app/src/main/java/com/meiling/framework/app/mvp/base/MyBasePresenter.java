package com.meiling.framework.app.mvp.base;


import com.meiling.framework.network.presenter.BasePresenter;
import com.meiling.framework.network.view.BaseView;

public class MyBasePresenter<T extends MyBaseView> extends BasePresenter<T> {
    public MyBasePresenter(T callback) {
        super(callback);
    }
}
