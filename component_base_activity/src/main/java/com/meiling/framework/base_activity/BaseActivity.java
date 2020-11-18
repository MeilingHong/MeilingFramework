package com.meiling.framework.base_activity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
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

        // todo 是否保持该页面的屏幕处于常亮状态
        if (isKeepScreenMode) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
