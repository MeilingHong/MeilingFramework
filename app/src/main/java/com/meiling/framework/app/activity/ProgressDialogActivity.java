package com.meiling.framework.app.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.app.dialog.CommonHintDialog;
import com.meiling.framework.app.dialog.progress.ProgressUploadDialog;
import com.meiling.framework.app.local_bean.progress.ProgressMessage;
import com.meiling.framework.app.mvp.entity.version.VersionEntity;
import com.meiling.framework.app.mvp.presenter.AppVersionPresenter;
import com.meiling.framework.app.mvp.view.AppVersionView;
import com.meiling.framework.base_activity.BaseActivity;
import com.meiling.framework.common_util.gson.Gsons;
import com.meiling.framework.common_util.log.Ulog;
import com.meiling.framework.common_util.toast.ToastUtil;
import com.meiling.framework.dialog.callback.IDialogDismissCallback;
import com.meiling.framework.dialog.callback.IDialogShowCallback;

import androidx.annotation.Nullable;

public class ProgressDialogActivity extends BaseActivity implements AppVersionView {

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

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void afterSetContentView(@Nullable Bundle savedInstanceState) {
        setActivityNavigationBarColor(getResources().getColor(R.color.color_main));// 确实将导航栏背景色修改成了指定的颜色

        click = findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    //****************************************************************************************************************************************************
    // 显示上传进度对话框

    private final int MSG_SHOW_PROGRESS = 1000;
    private final int MSG_DISMISS_PROGRESS = 1001;
    private final int MSG_DISMISS_PROGRESS_SUCCESS = 1002;
    private final int MSG_SUCCESS = 1003;
    private final int MSG_FAIL = 1004;
    private ProgressUploadDialog progressUploadDialog = null;
    private Handler mProgressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SHOW_PROGRESS: {
                    ProgressMessage progressMessage = (ProgressMessage) msg.obj;
                    if (progressUploadDialog != null) {
                        progressUploadDialog.setProgress(progressMessage.getCurrentSize(), progressMessage.getTotalSize());
                    } else {
                        progressUploadDialog = new ProgressUploadDialog();
                        progressUploadDialog.setDialogConfig(getApplicationContext(), null, null);
                        progressUploadDialog.show(getSupportFragmentManager(), "progress");
                        progressUploadDialog.setProgress(progressMessage.getCurrentSize(), progressMessage.getTotalSize());
                    }
                    break;
                }
                case MSG_DISMISS_PROGRESS: {
                    dismissUploadDialog();
                    break;
                }
                case MSG_DISMISS_PROGRESS_SUCCESS: {
                    dismissUploadDialog();
                    break;
                }
                case MSG_SUCCESS: {
                    if (progressUploadDialog != null) {
                        if (progressUploadDialog.getDialog() != null) {
                            progressUploadDialog.getDialog().setCanceledOnTouchOutside(true);
                        }
                        progressUploadDialog.success();
                        mProgressHandler.sendEmptyMessageDelayed(MSG_DISMISS_PROGRESS, 1000);//1秒后消失
                    }
                    break;
                }
                case MSG_FAIL: {
                    if (progressUploadDialog != null) {
                        progressUploadDialog.fail();
                        mProgressHandler.sendEmptyMessageDelayed(MSG_DISMISS_PROGRESS, 3000);//3秒后消失
                    }
                    break;
                }
            }
        }
    };

    private void dismissUploadDialog() {
        try {
            if (progressUploadDialog != null) {
                progressUploadDialog.dismiss();
                progressUploadDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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