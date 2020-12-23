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
    protected boolean isFullScreenMode = true;// 默认不是全屏
    protected boolean isStatusBarFontColorWhite = true; // 默认状态栏字体颜色为为白色
    protected boolean isKeepScreenMode = false;// 是否是屏幕保持模式【使得屏幕处于一直常亮的状态】
    protected boolean isBlockBackMode = false;
    protected boolean isDoubleBackExit = false;
    protected boolean isTranslucent = true;// 状态栏是否透明

    protected int navigatorBarColor = -1;

//    private Unbinder unbinder = null;

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

    /**
     * 如有必要，进行页面的恢复
     *
     * @param savedInstanceState
     */
    public abstract void afterSetContentView(@Nullable Bundle savedInstanceState);

    /**
     * 页面销毁时释放相应的数据
     */
    public abstract void releaseAfterDestroy();

    /**
     * 子类实现对Presenter类的初始化
     */
    public abstract void initPresenter();

    /**
     * 延迟加载
     */
    protected abstract void lasyloadCall();

    /**
     * 设置无状态栏模式【状态栏不可见】
     */
    protected void setConfigNoStatusBarWithBlackFont() {
        isTranslucent = false;
        isStatusBarFontColorWhite = false;
    }

    protected void setConfigNoStatusBarWithWhiteFont() {
        isTranslucent = false;
        isStatusBarFontColorWhite = true;
    }

    /**
     * 设置状态栏可见，同时标题文字为白色
     */
    protected void setConfigHasStatusBarWithWhiteFont() {
        isTranslucent = true;
        isStatusBarFontColorWhite = true;
    }

    /**
     * 设置状态栏可见，同时标题文字为黑色
     */
    protected void setConfigHasStatusBarWithBlackFont() {
        isTranslucent = true;
        isStatusBarFontColorWhite = false;
    }

//    private void initButterKnife() {
//        unbinder = ButterKnife.bind(this);
//    }
//
//    private void releaseButterKnife() {
//        if (unbinder != null) {
//            unbinder.unbind();
//        }
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configActivity();
        if (getContentViewLayoutId() == 0) {
            throw new RuntimeException("Invalid activity layout resource id!");
        }
        applyConfiguration();// 应用设置的配置信息
        setContentView(getContentViewLayoutId());
//        initButterKnife();
        afterSetContentView(savedInstanceState);
        initPresenter();
    }

    /**
     * 应用配置信息，使得配置生效
     */
    private void applyConfiguration() {
        firstFocusCount = 1;
        // todo 设置屏幕显示方向
        setRequestedOrientation(isPortraitMode ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        /*
    View.SYSTEM_UI_FLAG_VISIBLE：显示状态栏，Activity不全屏显示(恢复到有状态的正常情况)。
    View.INVISIBLE：隐藏状态栏，同时Activity会伸展全屏显示。
    View.SYSTEM_UI_FLAG_FULLSCREEN：Activity全屏显示，且状态栏被隐藏覆盖掉。
    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住。
    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    View.SYSTEM_UI_LAYOUT_FLAGS：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION：隐藏虚拟按键(导航栏)。有些手机会用虚拟按键来代替物理按键。
    View.SYSTEM_UI_FLAG_LOW_PROFILE：状态栏显示处于低能显示状态(low profile模式)，状态栏上一些图标显示会被隐藏。
         */
        // todo 设置屏幕是否全屏，设置状态栏字体颜色
        getWindow().getDecorView().setSystemUiVisibility(
                isFullScreenMode && isStatusBarFontColorWhite ? (View.SYSTEM_UI_FLAG_FULLSCREEN) | View.SYSTEM_UI_FLAG_LAYOUT_STABLE :
                        isFullScreenMode && !isStatusBarFontColorWhite ? (View.SYSTEM_UI_FLAG_FULLSCREEN) | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR :
                                !isFullScreenMode && isStatusBarFontColorWhite ? View.SYSTEM_UI_FLAG_LAYOUT_STABLE : View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // 状态栏（以上几行代码必须，参考setStatusBarColor|setNavigationBarColor方法源码）
//        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setStatusFontColor(isStatusBarFontColorWhite);// 实际上，仅调用这一个方法就行了，上面的方法不需要再额外进行调用

        // todo 设置底部导航栏的颜色
        if (navigatorBarColor != -1) {
            setNavigationBarColor(navigatorBarColor);// 可根据需要自定义
        }

        // todo 是否保持该页面的屏幕处于常亮状态
        if (isKeepScreenMode) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    protected void setStatusBarInvisible() {
        setStatusFontColor(false);
        // todo 部分手机底部虚拟按键背景色不是黑色
        setNavigationBarColor(Color.BLACK);
    }

    protected void setStatusBarVisible() {
        setStatusFontColor(isStatusBarFontColorWhite);
        if (navigatorBarColor != -1) {
            setNavigationBarColor(navigatorBarColor);
        } else {
            setNavigationBarColor(Color.WHITE);
        }
    }

    protected void setNavigationBarColor(@ColorInt int color){
        getWindow().setNavigationBarColor(color);
    }

    /**
     * 设置状态栏透明，状态栏字体颜色
     *
     * @param isWhite
     */
    protected void setStatusFontColor(boolean isWhite) {
        if (isTranslucent) {
            QMUIStatusBarHelper.translucent(this);
        }
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

    /*
     *********************************************************************************************************
     * todo 等页面实例化完成后，进行延迟调用
     */

    private int firstFocusCount = 1;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (firstFocusCount < 2) {
            firstFocusCount++;
            lasyloadCall();
        }
    }

    /*
     *********************************************************************************************************
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
     */

//    /**
//     * 释放presenter对象
//     *
//     * @param presenter
//     */
//    public void releasePresenter(MyBasePresenter presenter) {
//        if (presenter != null) {
//            presenter.detachView();
//            presenter = null;
//        }
//    }
    /*
     *********************************************************************************************************
     * todo 打开其他页面的的方法，可通过重写来覆盖实现
     */

    protected void setActivitySwitchAnimation() {
        //设置页面进出动画
        overridePendingTransition(com.meiling.framework.base_activity.R.anim.up_in, com.meiling.framework.base_activity.R.anim.up_out);//往上进入，往上出去
    }

    public void skipIntent(Bundle bundle, @NonNull Class<?> clz) {
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
        // 设置页面切换动画
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
    private long firstTime = 0; // 双击退出

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (isBlockBackMode) {// todo 屏蔽系统返回按钮的响应，避免通过按系统返回键关闭Activity的情况
                    return true;
                } else {
                    if (isDoubleBackExit) {
                        long secondTime = System.currentTimeMillis();
                        if (secondTime - firstTime > 2000) {
//                            showHintCenterOrderMsg(getString(R.string.double_click_quit));
                            firstTime = secondTime;//更新firstTime
                            return true;
                        } else {
                            //两次按键小于2秒时，退出应用
                            finish();
                            System.exit(0);
                        }
                    }
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

//    public void showHintCenterOrderMsg(String msg) {
//        try {
//            if (!TextUtils.isEmpty(msg)) {
//                View view = LayoutInflater.from(this).inflate(R.layout.toast_center, null);
//                TextView tvToast = view.findViewById(R.id.tvToast);
//                tvToast.setText(msg);
//                ToastUtil.toastShortOrder(this, view, Gravity.CENTER);
//            }
//        } catch (Exception e) {
//            CrashReportUtil.report(Ulog.getThrowableStackTrace(e), e);
//            Looper.prepare();
//            if (!TextUtils.isEmpty(msg)) {
//                View view = LayoutInflater.from(this).inflate(R.layout.toast_center, null);
//                TextView tvToast = view.findViewById(R.id.tvToast);
//                tvToast.setText(msg);
//                ToastUtil.toastShortOrder(this, view, Gravity.CENTER);
//            }
//            Looper.loop();
//        }
//    }
//
//    public void showHintCenterOrderMsgRound100(String msg) {
//        try {
//            if (!TextUtils.isEmpty(msg)) {
//                View view = LayoutInflater.from(this).inflate(R.layout.toast_round_100_center, null);
//                TextView tvToast = view.findViewById(R.id.tvToast);
//                tvToast.setText(msg);
//                ToastUtil.toastShortOrder(this, view, Gravity.CENTER);
//            }
//        } catch (Exception e) {
//            CrashReportUtil.report(Ulog.getThrowableStackTrace(e), e);
//            Looper.prepare();
//            if (!TextUtils.isEmpty(msg)) {
//                View view = LayoutInflater.from(this).inflate(R.layout.toast_round_100_center, null);
//                TextView tvToast = view.findViewById(R.id.tvToast);
//                tvToast.setText(msg);
//                ToastUtil.toastShortOrder(this, view, Gravity.CENTER);
//            }
//            Looper.loop();
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // todo 清除屏幕常亮，与开启屏幕常亮对应
        if (isKeepScreenMode) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        // todo 提供一个需要释放对象的方法
        releaseAfterDestroy();

//        releaseButterKnife();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Ulog.e("BaseActivity：" + (resultCode == Activity.RESULT_OK) + "-----" + requestCode + "-----" + resultCode + "-----" + this.getClass().getName());
        super.onActivityResult(requestCode, resultCode, data);
    }
}
