package com.meiling.framework.base_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meiling.framework.base_activity.util.statusbar.QMUIStatusBarHelper;
import com.meiling.framework.common_util.log.Ulog;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by marisareimu@126.com on 2020-12-15  15:53
 * project Ubanquan
 */

public abstract class BaseFragment extends Fragment {

    protected boolean isTranslucent = true;// 状态栏是否透明

    protected View mRootView;//当前
//    private Unbinder unbinder = null;

    public abstract void configFragment();

    public abstract int getContentViewLayoutId();

    // 方便子类实现时，针
    public abstract void initContentView(View root, Bundle savedInstanceState);

    /**
     * 子类实现对Presenter类的初始化
     */
    public abstract void initPresenter();

    /**
     * 页面销毁时释放相应的数据
     */
    public abstract void releaseAfterDestroy();

    /**
     * 延迟加载
     */
    protected void lasyloadCall() {

    }// 暂时不会被调用，子类不能在该方法中进行操作----

//    private void initButterKnife(View view) {
//        unbinder = ButterKnife.bind(this, view);
//    }

    private void releaseButterKnife() {
//        if (unbinder != null) {
//            unbinder.unbind();
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {//表面页面没有初始化过
            configFragment();
            mRootView = inflater.inflate(getContentViewLayoutId(), container, false);//初始化View
//            initButterKnife(mRootView);
            initPresenter();//在View实例化前进行Presenter初始化
            initContentView(mRootView, savedInstanceState);
        }
        //当根View添加过
        ViewGroup group = (ViewGroup) mRootView.getParent();
        if (group != null) {
            group.removeView(mRootView);
        }
        return mRootView;
    }

    /*
     *********************************************************************************************************
     * todo 等页面实例化完成后，进行延迟调用
     */

    @Override
    public void onResume() {
        super.onResume();
        //            lasyloadCall();// 仍然可能被重复调用
        Ulog.w("---onResume---" + this.getClass().getName());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
// 实际上，仅在第二次切换回来的时候才会被调用
            Ulog.w("---onHiddenChanged--- " + hidden + "---" + this.getClass().getName());
        }
    }

    /*
     *********************************************************************************************************
     */

    /**
     * 设置状态栏透明，状态栏字体颜色
     *
     * @param isWhite
     */
    protected void setStatusFontColor(boolean isWhite) {
        if (isTranslucent) {
            QMUIStatusBarHelper.translucent(getActivity());
        }
        if (isWhite) {
            QMUIStatusBarHelper.setStatusBarDarkMode(getActivity());
        } else {
            QMUIStatusBarHelper.setStatusBarLightMode(getActivity());
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

    /**
     * 释放presenter对象
     *
     * @param
     */
//    public void releasePresenter(MyBasePresenter presenter) {
//        if (presenter != null) {
//            presenter.detachView();
//            presenter = null;
//        }
//    }

    /*
     *********************************************************************************************************
     */
    public void skipIntent(Bundle bundle, @NonNull Class<?> clz) {
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
        Intent intent = new Intent(getContext(), clz);
        //如果需要传参数
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        //设置页面进出动画
        getActivity().overridePendingTransition(R.anim.up_in, R.anim.up_out);//往上进入，往上出去
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

//    public void showHintCenterOrderMsg(String msg) {
//        try {
//            if (!TextUtils.isEmpty(msg)) {
//                View view = LayoutInflater.from(getActivity()).inflate(R.layout.toast_center, null);
//                TextView tvToast = view.findViewById(R.id.tvToast);
//                tvToast.setText(msg);
//                ToastUtil.toastShortOrder(getActivity(), view, Gravity.CENTER);
//            }
//        } catch (Exception e) {
//            CrashReportUtil.report(Ulog.getThrowableStackTrace(e), e);
//            Looper.prepare();
//            if (!TextUtils.isEmpty(msg)) {
//                View view = LayoutInflater.from(getActivity()).inflate(R.layout.toast_center, null);
//                TextView tvToast = view.findViewById(R.id.tvToast);
//                tvToast.setText(msg);
//                ToastUtil.toastShortOrder(getActivity(), view, Gravity.CENTER);
//            }
//            Looper.loop();
//        }
//    }
//
//    public void showHintCenterOrderMsgRound100(String msg) {
//        try {
//            if (!TextUtils.isEmpty(msg)) {
//                View view = LayoutInflater.from(getActivity()).inflate(R.layout.toast_round_100_center, null);
//                TextView tvToast = view.findViewById(R.id.tvToast);
//                tvToast.setText(msg);
//                ToastUtil.toastShortOrder(getActivity(), view, Gravity.CENTER);
//            }
//        } catch (Exception e) {
//            CrashReportUtil.report(Ulog.getThrowableStackTrace(e), e);
//            Looper.prepare();
//            if (!TextUtils.isEmpty(msg)) {
//                View view = LayoutInflater.from(getActivity()).inflate(R.layout.toast_round_100_center, null);
//                TextView tvToast = view.findViewById(R.id.tvToast);
//                tvToast.setText(msg);
//                ToastUtil.toastShortOrder(getActivity(), view, Gravity.CENTER);
//            }
//            Looper.loop();
//        }
//    }

    /*
     *********************************************************************************************************
     */

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // todo 提供一个需要释放对象的方法
        releaseAfterDestroy();

        releaseButterKnife();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Ulog.e("BaseFragment：" + (resultCode == Activity.RESULT_OK) + "-----" + requestCode + "-----" + resultCode + "-----" + this.getClass().getName());
    }

    /*
     *********************************************************************************************************
     */

//    private LoadingDialog loadingDialog;
//
//    public void showLoadingDialog(String msg) {
//        try {
//            if (loadingDialog == null) {
//                loadingDialog = new LoadingDialog();
//                loadingDialog.setDialogConfig(getActivity(), null, new IDialogDismissCallback() {
//                    @Override
//                    public void afterDialogDismiss() {
//                        if (loadingDialog != null) {
//                            loadingDialog = null;
//                        }
//                    }
//                });
//                if (!TextUtils.isEmpty(msg)) {
//                    loadingDialog.setHintMsg(msg);
//                }
//                loadingDialog.show(getFragmentManager(), "loading", 2000);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void showLoadingDialog(String msg, boolean isCancel, int delayTime) {
//        try {
//            if (loadingDialog == null) {
//                loadingDialog = new LoadingDialog();
//                loadingDialog.setCancel(isCancel);
//                loadingDialog.setDialogConfig(getActivity(), null, new IDialogDismissCallback() {
//                    @Override
//                    public void afterDialogDismiss() {
//                        if (loadingDialog != null) {
//                            loadingDialog = null;
//                        }
//                    }
//                });
//                if (!TextUtils.isEmpty(msg)) {
//                    loadingDialog.setHintMsg(msg);
//                }
//                loadingDialog.show(getFragmentManager(), "loading", delayTime);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void modifyHintMsg(String hintMsg) {
//        if (loadingDialog != null && loadingDialog.isShowing()) {
//            loadingDialog.modifyHintMsg(hintMsg);
//        }
//    }
//
//    public void dismissLoadingDialog() {
//        try {
//            if (loadingDialog != null) {
//                loadingDialog.dismiss();
//                loadingDialog = null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
