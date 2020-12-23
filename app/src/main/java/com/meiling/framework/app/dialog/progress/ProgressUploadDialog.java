package com.meiling.framework.app.dialog.progress;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.meiling.framework.app.R;
import com.meiling.framework.common_util.datahandle.UnitExchangeUtil;
import com.meiling.framework.dialog.base.BaseFragmentDialog;
import com.meiling.framework.dialog.base.DialogConfig;
import com.meiling.framework.dialog.callback.IDialogDismissCallback;
import com.meiling.framework.dialog.callback.IDialogShowCallback;

import androidx.annotation.NonNull;


public class ProgressUploadDialog extends BaseFragmentDialog {

    private ProgressBar pBProgress;


    @Override
    public void setDialogConfig(@NonNull Context context, @NonNull IDialogShowCallback iDialogShowCallback, @NonNull IDialogDismissCallback iDialogDismissCallback) {
        this.config = new DialogConfig.Builder().
                setDialogStyle(R.style.Dialog_NoTitle_BottomIn).
                setDialogShowPosition(Gravity.CENTER).
                setContentViewLayout(R.layout.dialog_progress).
                setCancelable(false).
                setCancelOutside(false).
                setCancelForBackKey(false).
                setShowCallback(iDialogShowCallback).
                setDismissCallback(iDialogDismissCallback).
                setDialogWidth(UnitExchangeUtil.dip2px(context, 300)).
                setDialogHeight(ViewGroup.LayoutParams.WRAP_CONTENT).
                build();
    }

    @Override
    public void initContentView(View view) {
        pBProgress = view.findViewById(R.id.pBProgress);

        pBProgress.setMax(100);//默认按照100，后续修改
    }

    public void setProgress(long currentSize, long totalSize) {
        int progress = (int) (currentSize * 100 / totalSize);
//        Ulog.d(String.format("设置：进度:%d --- 当前:%d --- 总共:%d", progress, currentSize, totalSize));
        if (pBProgress != null) {
            pBProgress.setProgress(progress);
        }
    }

    public void success() {
        if (pBProgress != null) {
            pBProgress.setProgress(100);
        }
    }

    public void fail() {
        if (pBProgress != null) {
            pBProgress.setProgress(0);
        }
    }

}
