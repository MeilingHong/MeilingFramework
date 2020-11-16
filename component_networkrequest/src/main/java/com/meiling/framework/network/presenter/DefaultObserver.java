package com.meiling.framework.network.presenter;


import com.meiling.framework.network.view.BaseView;

import io.reactivex.observers.DisposableObserver;

public abstract class DefaultObserver<T> extends DisposableObserver<T> {

    protected BaseView view;

    public DefaultObserver(BaseView view) {
        this.view = view;
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
