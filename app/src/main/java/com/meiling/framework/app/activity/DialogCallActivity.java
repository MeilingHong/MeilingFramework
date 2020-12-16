package com.meiling.framework.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.app.dialog.CommonHintDialog;
import com.meiling.framework.base_activity.BaseActivity;
import com.meiling.framework.common_util.toast.ToastUtil;
import com.meiling.framework.dialog.callback.IDialogDismissCallback;
import com.meiling.framework.dialog.callback.IDialogShowCallback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;

public class DialogCallActivity extends BaseActivity {

    private TextView click;
    private ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

    @Override
    public void configActivity() {
        isPortraitMode = true;
        isFullScreenMode = false;
        isStatusBarFontColorWhite = false;
    }

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
                showHintDialog();
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
        if (singleThreadPool != null) {
            singleThreadPool.shutdown();
            singleThreadPool = null;
        }
    }

    /*
     ****************************************************************************************************************
     * Dialog 显示与使用
     */

    private CommonHintDialog commonHintDialog;

    private void showHintDialog() {
        if (commonHintDialog != null) {// 这个可控制使得Dialog仅显示一个
            // todo 该类型的Dialog在示例化之后如果调用过show方法，不能再次调用，否则将抛出Fragment已添加过的异常
            return;
        }
        commonHintDialog = new CommonHintDialog();
        // todo 具体的参数配置，在Dialog的实现类中根据实际需要进行配置
        // 如果不需要进行对应的功能实现，回调方法对应的参数设置为null即可
        commonHintDialog.setDialogConfig(this, new IDialogShowCallback() {
            @Override
            public void afterDialogShow() {
                // todo 该回调方法方便有EditText类型的对话框获取焦点，然后显示输入键盘
            }
        }, new IDialogDismissCallback() {
            @Override
            public void afterDialogDismiss() {
                if (commonHintDialog != null) {// todo 对于需要控制不多次显示的Dialog，可以在这里释放对象的引用
                    commonHintDialog = null;
                }
            }
        });
        commonHintDialog.setCancelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        commonHintDialog.setConfirmClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        commonHintDialog.setParams("标题", "内容", "取消", "确定");
        commonHintDialog.show(getSupportFragmentManager(), "hint");
    }

    private void showHintMultiDialog() {
        // todo 该中方式的写法将使得多次点击会显示多个Dialog
        final CommonHintDialog commonHintDialog = new CommonHintDialog();
        // todo 具体的参数配置，在Dialog的实现类中根据实际需要进行配置
        commonHintDialog.setDialogConfig(this, new IDialogShowCallback() {
            @Override
            public void afterDialogShow() {
                // todo 该回调方法方便有EditText类型的对话框获取焦点，然后显示输入键盘
            }
        }, new IDialogDismissCallback() {
            @Override
            public void afterDialogDismiss() {

            }
        });
        commonHintDialog.setCancelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        commonHintDialog.setConfirmClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        commonHintDialog.setParams("标题", "内容", "取消", "确定");
        commonHintDialog.show(getSupportFragmentManager(), "hint");
    }

    /*
     ****************************************************************************************************************
     */
}