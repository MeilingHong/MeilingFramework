package com.meiling.framework.dialog.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;

/**
 * Created by marisareimu@126.com on 2021-01-21  16:34
 * project MeilingFramework
 */
public abstract class BasePopupWindow extends PopupWindow {
    protected Context mContext;
    protected PopupConfig mPopupConfig;

    public BasePopupWindow(Context context, @NonNull PopupConfig popupConfig) {
        super(context);
        this.mContext = context;
        this.mPopupConfig = popupConfig;
        initPopupWindowView();
    }

    private void initPopupWindowView() {
        View contentView = LayoutInflater.from(mContext).inflate(mPopupConfig.getLayoutId(), null);
        afterInitLayoutView(contentView);//
        setContentView(contentView);// 设置PopupWindow的View

        // 设置Window的宽高
        setWidth(mPopupConfig.getmWidth() > 0 ? mPopupConfig.getmWidth() : WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(mPopupConfig.getmHeight() > 0 ? mPopupConfig.getmHeight() : WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置显示的动画
//        setAnimationStyle(R.style.popupAnimation);
        // 设置背景色【如果不设置，将不会显示出来】
        ColorDrawable dw = new ColorDrawable(mPopupConfig.getBgColor() > 0 ? mPopupConfig.getBgColor() : Color.parseColor("#00000000"));//默认透明
        setBackgroundDrawable(dw);
        // 设置其他属性
        setFocusable(mPopupConfig.isFocusAble());
        setOutsideTouchable(mPopupConfig.isOutsideTouchable());
        setTouchable(mPopupConfig.isTouchable());
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mPopupConfig != null && mPopupConfig.getiPopupDismissCallback() != null) {
                    mPopupConfig.getiPopupDismissCallback().onPopupWindowDismiss();
                }
            }
        });
    }

    public abstract void afterInitLayoutView(View contentRootView);//todo 由子类实现对应的交互等

    @Override
    public void showAsDropDown(View anchor) {//靠左显示
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;// 实际增减没发现背景的下方位置有变化，
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }
}
