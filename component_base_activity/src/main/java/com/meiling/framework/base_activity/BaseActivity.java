package com.meiling.framework.base_activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.meiling.framework.base_activity.util.statusbar.QMUIStatusBarHelper;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by marisareimu@126.com on 2020-11-17  17:29
 * project MeilingFramework
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected boolean isPortraitMode = true;// 默认竖屏显示
    protected boolean isFullScreenMode = false;// 默认不是全屏
    protected boolean isStatusBarFontColorWhite = true; // 默认状态栏字体颜色为为白色
    protected boolean isKeepScreenMode = false;// 是否是屏幕保持模式【使得屏幕处于一直常亮的状态】
    protected boolean isBlockBackMode = false;

    /**
     * 配置一些Activity显示相关的参数
     * 例如标题栏字体颜色（白色/黑色）
     * 是否全屏显示
     * 是否是横屏显示
     */
    public abstract void configActivity();

    /**
     * 需要子类返回一个指定的布局资源ID
     *
     * @return
     */
    public abstract int getContentViewLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configActivity();
        if (getContentViewLayoutId() == 0) {
            throw new RuntimeException("Invalid activity layout resource id!");
        }
        applyConfiguration();
        setContentView(getContentViewLayoutId());
        afterSetContentView(savedInstanceState);
    }

    public abstract void afterSetContentView(@Nullable Bundle savedInstanceState);

    /**
     * 应用配置信息，使得配置生效
     */
    private void applyConfiguration() {
        // todo 设置屏幕显示方向
        setRequestedOrientation(isPortraitMode ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // todo 设置屏幕是否全屏，设置状态栏字体颜色
        getWindow().getDecorView().setSystemUiVisibility(
                isFullScreenMode && isStatusBarFontColorWhite ? View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE :
                        isFullScreenMode && !isStatusBarFontColorWhite ? View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR :
                                !isFullScreenMode && isStatusBarFontColorWhite ? View.SYSTEM_UI_FLAG_LAYOUT_STABLE : View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (isFullScreenMode) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // 状态栏（以上几行代码必须，参考setStatusBarColor|setNavigationBarColor方法源码）
        // todo 设置底部导航栏的颜色
        getWindow().setNavigationBarColor(Color.TRANSPARENT);

        setStatusFontColor(isStatusBarFontColorWhite);

        // todo 是否保持该页面的屏幕处于常亮状态
        if (isKeepScreenMode) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    protected void setStatusFontColor(boolean isWhite) {
        QMUIStatusBarHelper.translucent(this);
        if (isWhite) {
            QMUIStatusBarHelper.setStatusBarDarkMode(this);
        } else {
            QMUIStatusBarHelper.setStatusBarLightMode(this);
        }
    }

    /**
     * todo 将下方的系统导航栏修改成指定的颜色
     *
     * @param colorId
     */
    protected void setActivityNavigationBarColor(@ColorInt int colorId) {
        // todo 该方法在API21 （Android 5.0 以及以上的版本才支持）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(colorId);
        }
    }

    public abstract void releaseAfterDestroy();

    /*
     *********************************************************************************************************
     * todo 释放Handler的信息，避免造成内存泄漏
     */

    /**
     * todo 移除Handler消息队列中的全部信息
     *
     * @param handler
     */
    public void removeHandlerMessages(Handler handler) {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * todo 移除Handler消息队列中的全部信息，并释放Handler对象
     *
     * @param handler
     */
    public void removeHandlerMessagesAndRelease(Handler handler) {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    /*
     *********************************************************************************************************
     * todo 打开其他页面的的方法，可通过重写来覆盖实现
     */

    protected void setActivitySwitchAnimation() {
        //设置页面进出动画
        overridePendingTransition(R.anim.up_in, R.anim.up_out);//往上进入，往上出去
    }

    public void skipIntent(Bundle bundle, @NonNull Class<?> clz) {
        setActivitySwitchAnimation();
        //设置页面进出动画
        skipIntent(bundle, clz, -1);
    }

    /**
     * 跳转方法
     *
     * @param bundle
     * @param clz
     * @param requestCode
     */
    public void skipIntent(Bundle bundle, @NonNull Class<?> clz, @IntRange(from = -1) int requestCode) {
        Intent intent = new Intent(this, clz);
        //如果需要传参数
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        setActivitySwitchAnimation();
        //是否进行有返回值得跳转
        if (requestCode != -1) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }
    }

    /*
     *********************************************************************************************************
     */

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (isBlockBackMode) {// todo 屏蔽系统返回按钮的响应，避免通过按系统返回键关闭Activity的情况
                    return true;
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // todo 清除屏幕常亮，与开启屏幕常亮对应
        if (isKeepScreenMode) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        // todo 提供一个需要释放对象的方法
        releaseAfterDestroy();
    }
}
