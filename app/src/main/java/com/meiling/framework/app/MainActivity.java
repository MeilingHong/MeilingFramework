package com.meiling.framework.app;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.meiling.framework.app.mvp.entity.version.VersionEntity;
import com.meiling.framework.app.mvp.presenter.AppVersionPresenter;
import com.meiling.framework.app.mvp.view.AppVersionView;
import com.meiling.framework.common_util.gson.Gsons;
import com.meiling.framework.common_util.log.Ulog;
import com.meiling.framework.common_util.toast.ToastUtil;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AppVersionView {

    private AppVersionPresenter appVersionPresenter;
    private TextView click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
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
        appVersionPresenter = new AppVersionPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (appVersionPresenter != null) {
            appVersionPresenter.detachView();
        }
    }

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