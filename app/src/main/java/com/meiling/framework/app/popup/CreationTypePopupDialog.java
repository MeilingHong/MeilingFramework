package com.meiling.framework.app.popup;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.meiling.framework.app.R;
import com.meiling.framework.app.adatper.IAdapterItemCallback;
import com.meiling.framework.app.adatper.popup.CreationTypeEnum;
import com.meiling.framework.app.adatper.popup.PopupCreationTypeAdapter;
import com.meiling.framework.common_util.log.Ulog;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by marisareimu@126.com on 2020-12-18  10:31
 * project Ubanquan
 */
public class CreationTypePopupDialog extends PopupWindow {
    private Context mContext;
    private List<CreationTypeEnum> mData;
    private IAdapterItemCallback<CreationTypeEnum> mCallback;
    private IPopupDismissCallback mPopupDismissCallback;
    private RelativeLayout outside;
    private RecyclerView listview;

    public CreationTypePopupDialog(Context context, List<CreationTypeEnum> mData, IAdapterItemCallback<CreationTypeEnum> callback, IPopupDismissCallback iPopupDismissCallback) {
        super(context);
        this.mContext = context;
        this.mData = mData;
        this.mCallback = callback;
        this.mPopupDismissCallback = iPopupDismissCallback;
        initView();
    }

    private void initView() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_creation_status, null);
        initComponentView(contentView);// 实例化布局对应的子组件
        setContentView(contentView);// 设置PopupWindow的View
        // 设置Window的宽高
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置显示的动画
        setAnimationStyle(R.style.popupAnimation);
        // 设置背景色【如果不设置，将不会显示出来】
        ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.color_transparent));
        setBackgroundDrawable(dw);
        // 设置其他属性
        setFocusable(false);
        setOutsideTouchable(false);
        setTouchable(true);
//        setClippingEnabled(false);// 解决背景超出Fragment的问题-----【实际发现没有效果】
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mPopupDismissCallback != null) {
                    mPopupDismissCallback.onDismiss();
                }
            }
        });
    }

    private PopupCreationTypeAdapter mAdapter;

    private void initComponentView(View root) {
        // 使得点击后手动强制关闭
        outside = root.findViewById(R.id.outside);
        outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        listview = root.findViewById(R.id.listview);// 初始化
        // 设置ListView对应的参数
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//设置为横向滚动
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//设置为纵向滚动
        listview.setLayoutManager(linearLayoutManager);//todo  需要设置这个LayoutManager才能够保证数据显示出来
        listview.setNestedScrollingEnabled(false);
        // 设置对应的适配器
        mAdapter = new PopupCreationTypeAdapter(mData, mCallback);
        listview.setAdapter(mAdapter);
    }

    // 避免高版本出现显示位置异常
    @Override
    public void showAsDropDown(View anchor) {//靠左显示
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            Ulog.e("popup h:" + h + "--heightPixels:" + anchor.getResources().getDisplayMetrics().heightPixels + "--bottom:" + rect.bottom);
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
