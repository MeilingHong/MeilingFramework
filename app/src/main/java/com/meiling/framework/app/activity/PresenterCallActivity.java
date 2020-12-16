package com.meiling.framework.app.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.app.mvp.entity.version.VersionEntity;
import com.meiling.framework.app.mvp.presenter.AppVersionPresenter;
import com.meiling.framework.app.mvp.view.AppVersionView;
import com.meiling.framework.base_activity.BaseActivity;
import com.meiling.framework.common_util.gson.Gsons;
import com.meiling.framework.common_util.log.Ulog;
import com.meiling.framework.common_util.toast.ToastUtil;

import androidx.annotation.Nullable;

public class PresenterCallActivity extends BaseActivity implements AppVersionView {

    private AppVersionPresenter appVersionPresenter;
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
                ToastUtil.toastShortCenter(getApplicationContext(), "点击Toast");
                if (appVersionPresenter != null) {
                    appVersionPresenter.getAppVersion();
                }
            }
        });
    }

    // todo Glide加载图片的问题，对于旧手机，在加载时可能会出现显示混乱的问题【猜测是内存缓存、磁盘缓存满了引起的问题】

    @Override
    public void releaseAfterDestroy() {
        if (appVersionPresenter != null) {
            appVersionPresenter.detachView();
        }
    }

    @Override
    public void initPresenter() {
        appVersionPresenter = new AppVersionPresenter(this);
    }

    @Override
    protected void lasyloadCall() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*
     ****************************************************************************************************************
     */

    @Override
    public void getAppVersion(VersionEntity result) {
        Ulog.i("收到版本信息接口返回【Gson】:" + Gsons.getInstance().toJson(result));
    }

    @Override
    public void getAppVersion(String result) {
        Ulog.i("收到版本信息接口返回【String】:" + result);
    }

    @Override
    public void showDialog(String msg) {
        Ulog.i("应该执行显示等待对话框");// 如果msg不为空，则表示等待对话框中需要跟着显示msg的提示信息
    }

    @Override
    public void dismissDialog() {
        Ulog.i("应该执行关闭等待对话框");
    }

    @Override
    public void requestFail(int businessCode, String errorCode, String errorMsg) {
        Ulog.i("businessCode：在APP内部用于标记属于哪一种业务或接口请求\nerrorCode：服务端用于标记属于哪一种特定业务的错误码\nerrorMsg：服务端提供的错误提示信息");
        // todo 但是对于各种原因引起的网络接口请求失败【请求发送出去了，但并为及时从服务端返回正确结果】需要自己额外进行处理
    }
}