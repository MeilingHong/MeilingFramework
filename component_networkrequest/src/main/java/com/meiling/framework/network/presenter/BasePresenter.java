package com.meiling.framework.network.presenter;


import com.meiling.framework.network.view.BaseView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter<T extends BaseView> {
    protected CompositeDisposable compositeDisposable;
    protected T callback;

    public BasePresenter(T callback) {
        this.callback = callback;
    }

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    //子类可以针对该方法进行覆盖
    public void addDisposable(Observable<?> observable, DefaultObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    public void detachView() {
        callback = null;
        removeDisposable();
        compositeDisposable = null;

    }

    public void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public T getCallback() {
        return callback;
    }
}
