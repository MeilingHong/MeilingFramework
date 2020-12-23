package com.meiling.framework.app.activity.a_test;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.app.adatper.IAdapterItemCallback;
import com.meiling.framework.app.adatper.popup.CreationStatusEnum;
import com.meiling.framework.app.adatper.popup.CreationTypeEnum;
import com.meiling.framework.app.dialog.CommonHintDialog;
import com.meiling.framework.app.popup.CreationStatusPopupDialog;
import com.meiling.framework.app.popup.CreationTypePopupDialog;
import com.meiling.framework.app.popup.IPopupDismissCallback;
import com.meiling.framework.base_activity.BaseActivity;
import com.meiling.framework.base_activity.util.statusbar.QMUIStatusBarHelper;
import com.meiling.framework.common_util.datahandle.UnitExchangeUtil;
import com.meiling.framework.common_util.toast.ToastUtil;
import com.meiling.framework.dialog.callback.IDialogDismissCallback;
import com.meiling.framework.dialog.callback.IDialogShowCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;

public class PopupDialogCallActivity extends BaseActivity {

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
        setActivityNavigationBarColor(getResources().getColor(R.color.color_main));// 确实将导航栏背景色修改成了指定的颜色

        click = findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.toastShortCenter(getApplicationContext(), "点击Toast");
                showStatusSwitchDialog();
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

    private CreationStatusPopupDialog creationStatusPopupDialog;
    private CreationTypePopupDialog creationTypePopupDialog;

    private void showStatusSwitchDialog() {
        // 打开【状态选择】对话框
        List<CreationStatusEnum> mStatusList = new ArrayList<>();
        mStatusList.add(CreationStatusEnum.ALL);
        mStatusList.add(CreationStatusEnum.DEFAULT);
        mStatusList.add(CreationStatusEnum.SUCCESS);
        mStatusList.add(CreationStatusEnum.FAILURE);
        if (creationStatusPopupDialog == null) {
            creationStatusPopupDialog = new CreationStatusPopupDialog(getApplicationContext(), mStatusList,
                    new IAdapterItemCallback<CreationStatusEnum>() {
                @Override
                public void adapterItemClick(CreationStatusEnum bean, int viewId, int position, View clickedView) {
                    switch (viewId) {
                        case R.id.itemName: {
//                            statusEnum = bean;
//                            firstPage();
//                            createTvStatus.setText(bean.getDesc());
//                            setDrawableRight(createIvStatus, false);
//                            dismissStatusDialog();
                            break;
                        }
                    }
                }
            }, new IPopupDismissCallback() {
                @Override
                public void onDismiss() {
                    if (creationStatusPopupDialog != null) {
                        creationStatusPopupDialog = null;
                    }
//                    setDrawableRight(createIvStatus, false);
//                    if (cover != null) {
//                        cover.setVisibility(View.GONE);
//                    }
                }
            });
            // 使用该方法时，针对Fragment中显示，不论设置Popup的Height，还是设置位置，在Android 9 先存在超出Fragment显示范围的问题
            // 【就像PopupWindow是在所有页面上面的感觉！】
//            creationStatusPopupDialog.showAsDropDown(createRlStatus);
            // 在使用该方法时，PopupWindow的背景颜色设置没有生效了【或者其实背景就是整个PopupWindow实际有效的大小】
            creationStatusPopupDialog.showAtLocation(click, Gravity.TOP, 0,
                    UnitExchangeUtil.dip2px(getApplicationContext(), 92) +
                            QMUIStatusBarHelper.getStatusbarHeight(getApplicationContext()));
//            if (cover != null) {
//                cover.setVisibility(View.VISIBLE);
//            }
        }
    }

    private void showTypeSwitchDialog() {
//        setDrawableRight(createIvStatus, false);
//        setDrawableRight(createIvType, true);
        // 打开【类型选择】选择对话框
        List<CreationTypeEnum> mStatusList = new ArrayList<>();
        mStatusList.add(CreationTypeEnum.ALL);
        mStatusList.add(CreationTypeEnum.TEXT);
        mStatusList.add(CreationTypeEnum.PNG);
        mStatusList.add(CreationTypeEnum.AVI);
        mStatusList.add(CreationTypeEnum.AUDIO);
        mStatusList.add(CreationTypeEnum.OTHER);
        if (creationTypePopupDialog == null) {
            creationTypePopupDialog = new CreationTypePopupDialog(getApplicationContext(), mStatusList, new IAdapterItemCallback<CreationTypeEnum>() {
                @Override
                public void adapterItemClick(CreationTypeEnum bean, int viewId, int position, View clickedView) {
                    switch (viewId) {
                        case R.id.itemName: {
//                            typeEnum = bean;
//                            firstPage();
//                            createTvType.setText(bean.getDesc());
//                            setDrawableRight(createIvType, false);
//                            dismissTypeDialog();
                            break;
                        }
                    }
                }
            }, new IPopupDismissCallback() {
                @Override
                public void onDismiss() {
                    if (creationTypePopupDialog != null) {
                        creationTypePopupDialog = null;
                    }
//                    setDrawableRight(createIvType, false);
//                    if (cover != null) {
//                        cover.setVisibility(View.GONE);
//                    }
                }
            });
//            creationTypePopupDialog.showAsDropDown(createRlStatus);//
            creationTypePopupDialog.showAtLocation(click, Gravity.TOP, 0,
                    UnitExchangeUtil.dip2px(getApplicationContext(), 92) +
                            QMUIStatusBarHelper.getStatusbarHeight(getApplicationContext()));
        }
    }

    /*
     ****************************************************************************************************************
     */
}