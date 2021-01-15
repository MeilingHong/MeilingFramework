package com.meiling.framework.app.dialog.time_select;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.meiling.framework.app.R;
import com.meiling.framework.dialog.base.BaseFragmentDialog;
import com.meiling.framework.dialog.base.DialogConfig;
import com.meiling.framework.dialog.callback.IDialogDismissCallback;
import com.meiling.framework.dialog.callback.IDialogShowCallback;

import androidx.annotation.NonNull;

public class TimeSelectDialog extends BaseFragmentDialog {
    private boolean isLoop;//释放循环
    @Override
    public void setDialogConfig(@NonNull Context context, @NonNull IDialogShowCallback iDialogShowCallback, @NonNull IDialogDismissCallback iDialogDismissCallback) {
        this.config = new DialogConfig.Builder().
                setDialogStyle(R.style.Dialog_NoTitle_BottomIn).
                setDialogShowPosition(Gravity.CENTER).
                setContentViewLayout(R.layout.dialog_time_select).
                setCancelable(false).
                setCancelOutside(false).
                setCancelForBackKey(false).
                setShowCallback(iDialogShowCallback).// 方便有需要时，显示键盘
                setDismissCallback(iDialogDismissCallback).// 方便有需要在对话框消失时进行收尾的处理
                setDialogWidth(ViewGroup.LayoutParams.MATCH_PARENT).
                setDialogHeight(ViewGroup.LayoutParams.WRAP_CONTENT).
                build();
    }

    @Override
    public void initContentView(View view) {
    }
}
