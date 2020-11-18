package com.meiling.framework.base_activity.util.drawable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;


public class DrawableGetUtil {
    public static Drawable getPaddingDrawable(Context context, @DrawableRes int drawableRes) {
        Drawable drawable = context.getResources().getDrawable(drawableRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    /**
     *  todo 设置TextView内部四周的图标
     *
     * @param textView
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setTextViewCompoundDrawable(TextView textView, @Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        if (left != null) {
            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());
        }
        if (top != null) {
            top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
        }
        if (right != null) {
            right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, bottom.getMinimumWidth(), bottom.getMinimumHeight());
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }
}
