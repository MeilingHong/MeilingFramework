package com.meiling.framework.app.dialog.permission;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.permissions.XXPermissions;
import com.meiling.framework.app.R;
import com.meiling.framework.app.utils.permission.PermissionType;
import com.meiling.framework.dialog.base.BaseFragmentDialog;
import com.meiling.framework.dialog.base.DialogConfig;
import com.meiling.framework.dialog.callback.IDialogDismissCallback;
import com.meiling.framework.dialog.callback.IDialogShowCallback;

import androidx.annotation.NonNull;


public class PermissionDialog extends BaseFragmentDialog implements View.OnClickListener {

    private PermissionType permission;

    @Override
    public void setDialogConfig(@NonNull Context context, @NonNull IDialogShowCallback iDialogShowCallback, @NonNull IDialogDismissCallback iDialogDismissCallback) {
        this.config = new DialogConfig.Builder().
                setDialogStyle(R.style.Dialog_NoTitle_BottomIn).
                setDialogShowPosition(Gravity.CENTER).
                setContentViewLayout(R.layout.dialog_request_permission).
                setCancelable(false).
                setCancelOutside(false).
                setCancelForBackKey(false).
                setShowCallback(iDialogShowCallback).
                setDismissCallback(iDialogDismissCallback).
                setDialogWidth(ViewGroup.LayoutParams.MATCH_PARENT).
                setDialogHeight(ViewGroup.LayoutParams.WRAP_CONTENT).
                build();
    }

    @Override
    public void initContentView(View view) {
        view.findViewById(R.id.cancel).setOnClickListener(this);
        ImageView hintIcon = view.findViewById(R.id.hintIcon);

        TextView msg = view.findViewById(R.id.hintMsg);
        switch (permission) {
            case TYEE_STORAGE: {
                hintIcon.setImageResource(R.drawable.permission_dialog_address);
                msg.setText(getActivity().getString(R.string.permission_dialog_msg_storage));
                break;
            }
            case TYEE_CALLPHONE: {
                hintIcon.setImageResource(R.drawable.permission_dialog_address);
                msg.setText(getActivity().getString(R.string.permission_dialog_msg_callphone));
                break;
            }
            case TYPE_LOCATION: {
                hintIcon.setImageResource(R.drawable.permission_dialog_address);
                msg.setText(getActivity().getString(R.string.permission_dialog_msg_location));
                break;
            }
            case TYPE_CALL_PHONE: {
                hintIcon.setImageResource(R.drawable.permission_dialog_address);
                msg.setText(getActivity().getString(R.string.permission_dialog_msg_callphone));
                break;
            }
            case TYPE_CAMERA_STORAGE: {
                hintIcon.setImageResource(R.drawable.permission_dialog_address);
                msg.setText(getActivity().getString(R.string.permission_dialog_msg_camera_storage));
                break;
            }
            case TYEE_STORAGE_INSTALL: {
                hintIcon.setImageResource(R.drawable.permission_dialog_address);
                msg.setText(getActivity().getString(R.string.permission_dialog_msg_storage_and_install));
                break;
            }
            case TYEE_STORAGE_LOCATION_INFO: {
                hintIcon.setImageResource(R.drawable.permission_dialog_address);
                msg.setText(getActivity().getString(R.string.permission_dialog_msg_storage_location_info));
                break;
            }
            case TYEE_STORAGE_LOCATION_VOICE_INFO: {
                hintIcon.setImageResource(R.drawable.permission_dialog_address);
                msg.setText(getActivity().getString(R.string.permission_dialog_msg_storage_location_voice_info));
                break;
            }
            case TYPE_CAMERA_STORAGE_LOCATION_INFO: {
                hintIcon.setImageResource(R.drawable.permission_dialog_address);
                msg.setText(getActivity().getString(R.string.permission_dialog_msg_camera_storage_location_info));
                break;
            }
            case TYEE_STORAGE_LOCATION_VOICE_INFO_WINDOE: {
                hintIcon.setImageResource(R.drawable.permission_dialog_address);
                msg.setText(getActivity().getString(R.string.permission_dialog_msg_storage_location_voice_info_window));
                break;
            }
            case TYPE_CAMERA_STORAGE_LOCATION_VOICE_INFO: {
                hintIcon.setImageResource(R.drawable.permission_dialog_address);
                msg.setText(getActivity().getString(R.string.permission_dialog_msg_camera_storage_location_voice_info));
                break;
            }
        }

        TextView confirm = view.findViewById(R.id.confirm);
        confirm.setText(R.string.go_setting);
        confirm.setOnClickListener(this);
    }

    public void setParams(PermissionType permission) {
        this.permission = permission;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.confirm:
                dismiss();
                XXPermissions.gotoPermissionSettings(getContext());
                break;
        }
    }

    public interface onCustomKeyListener {
        void dismiss();
    }
}
