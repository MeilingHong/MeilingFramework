package com.meiling.framework.app.adatper;

import android.view.View;

import androidx.annotation.IdRes;

public interface IAdapterItemCallback<T> {
    /**
     *
     * @param bean
     * @param viewId
     * @param position
     * @param clickedView 便於處理需要針對View做改動，顯示Dialog/PopupWindow等
     */
    void adapterItemClick(T bean, @IdRes int viewId, int position, View clickedView);
}
