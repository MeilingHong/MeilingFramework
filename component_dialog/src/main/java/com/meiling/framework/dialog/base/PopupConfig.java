package com.meiling.framework.dialog.base;

import com.meiling.framework.dialog.callback.IPopupDismissCallback;

import androidx.annotation.ColorInt;

/**
 * Created by marisareimu@126.com on 2021-01-21  16:35
 * project MeilingFramework
 */

public class PopupConfig {
    private int layoutId;// 指定的布局ID
    private int mWidth;
    private int mHeight;
    private int mAnimationStyle;// 设置动画的样式
    @ColorInt
    private int bgColor;// 背景色
    private boolean isFocusAble;
    private boolean isOutsideTouchable;
    private boolean isTouchable;
    private IPopupDismissCallback iPopupDismissCallback;// PopupWindow消失的回调


    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public int getmAnimationStyle() {
        return mAnimationStyle;
    }

    public void setmAnimationStyle(int mAnimationStyle) {
        this.mAnimationStyle = mAnimationStyle;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(@ColorInt int bgColor) {
        this.bgColor = bgColor;
    }

    public boolean isFocusAble() {
        return isFocusAble;
    }

    public void setFocusAble(boolean focusAble) {
        isFocusAble = focusAble;
    }

    public boolean isOutsideTouchable() {
        return isOutsideTouchable;
    }

    public void setOutsideTouchable(boolean outsideTouchable) {
        isOutsideTouchable = outsideTouchable;
    }

    public boolean isTouchable() {
        return isTouchable;
    }

    public void setTouchable(boolean touchable) {
        isTouchable = touchable;
    }

    public IPopupDismissCallback getiPopupDismissCallback() {
        return iPopupDismissCallback;
    }

    public void setiPopupDismissCallback(IPopupDismissCallback iPopupDismissCallback) {
        this.iPopupDismissCallback = iPopupDismissCallback;
    }
}
