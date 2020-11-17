package com.meiling.framework.app.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.dialog.base.BaseFragmentDialog;
import com.meiling.framework.dialog.base.DialogConfig;
import com.meiling.framework.dialog.callback.IDialogDismissCallback;
import com.meiling.framework.dialog.callback.IDialogShowCallback;

import androidx.annotation.NonNull;

public class CommonHintDialog extends BaseFragmentDialog {

    private String titleString;
    private String contentString;
    private String cancelString;
    private String confirmString;
    private View.OnClickListener confirmClickListener;
    private View.OnClickListener cancelClickListener;

    @Override
    public void setDialogConfig(@NonNull Context context, @NonNull IDialogShowCallback iDialogShowCallback, @NonNull IDialogDismissCallback iDialogDismissCallback) {
        this.config = new DialogConfig.Builder().
                setDialogStyle(R.style.Dialog_NoTitle_BottomIn).
                setDialogShowPosition(Gravity.CENTER).
                setContentViewLayout(R.layout.dialog_common_hint).
                setCancelable(false).
                setCancelOutside(false).
                setCancelForBackKey(false).
                setShowCallback(iDialogShowCallback).// 方便有需要时，显示键盘
                setDismissCallback(iDialogDismissCallback).// 方便有需要在对话框消失时进行收尾的处理
                setDialogWidth(ViewGroup.LayoutParams.WRAP_CONTENT).
                setDialogHeight(ViewGroup.LayoutParams.WRAP_CONTENT).
                build();
    }

    public void setParams(String titleString, String contentString, String cancelString, String confirmString){
        this.titleString = titleString;
        this.contentString = contentString;
        this.cancelString = cancelString;
        this.confirmString = confirmString;
    }

    public void setConfirmClickListener(View.OnClickListener confirmClickListener) {
        this.confirmClickListener = confirmClickListener;
    }

    public void setCancelClickListener(View.OnClickListener cancelClickListener) {
        this.cancelClickListener = cancelClickListener;
    }

    @Override
    public void initContentView(View view) {
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvContent = view.findViewById(R.id.tvContent);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvConfirm = view.findViewById(R.id.tvConfirm);

        if(!TextUtils.isEmpty(titleString)){
            tvTitle.setText(titleString);
        }
        if(!TextUtils.isEmpty(contentString)){
            tvContent.setText(contentString);
        }
        if(!TextUtils.isEmpty(cancelString)){
            tvCancel.setText(cancelString);
        }
        if(!TextUtils.isEmpty(confirmString)){
            tvConfirm.setText(confirmString);
        }

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(confirmClickListener !=null){
                    confirmClickListener.onClick(v);
                }
                dismiss();
            }
        });
    }
}
