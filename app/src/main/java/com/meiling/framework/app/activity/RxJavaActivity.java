package com.meiling.framework.app.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.app.dialog.CommonHintDialog;
import com.meiling.framework.app.mvp.entity.version.VersionEntity;
import com.meiling.framework.app.mvp.presenter.AppVersionPresenter;
import com.meiling.framework.base_activity.BaseActivity;
import com.meiling.framework.common_util.gson.Gsons;
import com.meiling.framework.common_util.log.Ulog;
import com.meiling.framework.common_util.toast.ToastUtil;
import com.meiling.framework.dialog.callback.IDialogDismissCallback;
import com.meiling.framework.dialog.callback.IDialogShowCallback;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends BaseActivity {

    private TextView click;

    @Override
    public void configActivity() {
        isPortraitMode = true;
        isFullScreenMode = false;
        isStatusBarFontColorWhite = false;
    }

    /*

      1、线程池，在提交任务上，比异步任务好，但是，在任务执行顺序，与执行时机上我发进行管理，在数据的线程切换上也需要自己进行管理；
      2、异步任务在单任务执行上
     */
    private AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    };

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void afterSetContentView(@Nullable Bundle savedInstanceState) {
        setActivityNavigationBarColor(getResources().getColor(R.color.color_3296fa));// 确实将导航栏背景色修改成了指定的颜色

        click = findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // RxJava 调用以及源码
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                    }
                }).
                        /**
                         * todo 1、该方法比较适合需要连续调用的情况【链式调用，如果其中一个出错，则通过抛出异常来终止整个调用链路】
                         *  否则通过返回值传递给下一个调用环节
                         */
                        // 输入参数类型---上一个方法的返回值类型   // 返回值类型
                flatMap(new Function<String                    ,ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(String s) throws Exception {
                        // 可通过这个方法进行数据类型的转换
                        return null;
                    }
                }).
                        subscribeOn(Schedulers.io()).// 订阅执行在IO线程中
                        observeOn(AndroidSchedulers.mainThread()).// 观察处于UI主线程中
                        /**
                         * 1、观察者进行订阅，在执行到对应方法时，回调对应的方法
                         *  接收基于推送通知的机制
                         */
                        subscribe(new Observer<Object>() { // 订阅回调
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Object o) {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
    }

    // todo Glide加载图片的问题，对于旧手机，在加载时可能会出现显示混乱的问题【猜测是内存缓存、磁盘缓存满了引起的问题】

    @Override
    public void releaseAfterDestroy() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void lasyloadCall() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}