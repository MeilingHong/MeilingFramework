package com.meiling.framework.app.utils.permission;

import android.Manifest;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.meiling.framework.app.dialog.permission.PermissionDialog;
import com.meiling.framework.base_activity.BaseActivity;
import com.meiling.framework.dialog.callback.IDialogDismissCallback;

import java.util.Arrays;
import java.util.List;

/**
 * Created by marisareimu@126.com on 2020-12-17  10:32
 * project Ubanquan
 */
public class PermissionHelper {

    private static PermissionDialog dialogFragment = null;

    private PermissionResult result;

    private PermissionDismissResult dismissResult;

    public interface PermissionResult {
        void success();
    }

    public interface PermissionDismissResult {
        void dismiss();
    }

    public void setResult(PermissionResult result) {
        this.result = result;
    }

    public void setDismissResult(PermissionDismissResult dismissResult) {
        this.dismissResult = dismissResult;
    }

    /*
        switch (type) {
            case TYEE_STORAGE: {
                break;
            }
            case TYPE_MAIL: {
                break;
            }
            case TYPE_EXPLORER: {
                break;
            }
            case TYPE_LOCATION: {
                break;
            }
            case TYPE_CALL_PHONE: {
                break;
            }
            case TYPE_CAMERA_STORAGE: {
                break;
            }
            case TYEE_STORAGE_INSTALL: {
                break;
            }
            case TYEE_STORAGE_LOCATION_INFO: {
                break;
            }
            case TYPE_LOCATION_STORAGE_STATUS: {
                break;
            }
            case TYEE_STORAGE_LOCATION_VOICE_INFO: {
                break;
            }
            case TYPE_CAMERA_STORAGE_LOCATION_INFO: {
                break;
            }
            case TYEE_STORAGE_LOCATION_VOICE_INFO_WINDOE: {
                break;
            }
            case TYPE_CAMERA_STORAGE_LOCATION_VOICE_INFO: {
                break;
            }
        }
     */
    public void requestPermission(final BaseActivity activity, final PermissionType type) {
        List<String> permission = Arrays.asList(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
        switch (type) {
            case TYEE_STORAGE: {
                permission = Arrays.asList(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
                break;
            }
            case TYEE_CALLPHONE:{
                permission = Arrays.asList(new String[]{
                        Manifest.permission.CALL_PHONE});
                break;
            }
            case TYPE_LOCATION: {
                permission = Arrays.asList(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
                break;
            }
            case TYPE_CALL_PHONE: {
                permission = Arrays.asList(new String[]{Manifest.permission.CALL_PHONE});
                break;
            }
            case TYPE_CAMERA_STORAGE: {
                permission = Arrays.asList(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA});
                break;
            }
            case TYEE_STORAGE_INSTALL: {
                permission = Arrays.asList(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.REQUEST_INSTALL_PACKAGES});
                break;
            }
            case TYEE_STORAGE_LOCATION_INFO: {
                permission = Arrays.asList(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE});
                break;
            }
            case TYEE_STORAGE_LOCATION_VOICE_INFO: {
                permission = Arrays.asList(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS,
                        Manifest.permission.READ_PHONE_STATE});
                break;
            }
            case TYPE_CAMERA_STORAGE_LOCATION_INFO: {
                permission = Arrays.asList(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE});
                break;
            }
            case TYEE_STORAGE_LOCATION_VOICE_INFO_WINDOE: {
                permission = Arrays.asList(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.READ_PHONE_STATE});
                break;
            }
            case TYPE_CAMERA_STORAGE_LOCATION_VOICE_INFO: {
                permission = Arrays.asList(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS,
                        Manifest.permission.READ_PHONE_STATE});
                break;
            }
        }
        XXPermissions.with(activity).permission(permission).request(new OnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {
                if (isAll && result != null) {
                    result.success();
                }
            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {//在权限被永久拒绝时才会显示，否则在下次会直接弹出权限申请对话框（系统的）
                if (dialogFragment == null) {
                    dialogFragment = new PermissionDialog();
                    dialogFragment.setDialogConfig(activity, null, new IDialogDismissCallback() {
                        @Override
                        public void afterDialogDismiss() {
                            if (dialogFragment != null) {
                                dialogFragment = null;
                            }
                            if (dismissResult != null) {
                                dismissResult.dismiss();
                            }
                        }
                    });
                    dialogFragment.setParams(type);
                    dialogFragment.show(activity.getSupportFragmentManager(), "Permission");
                }
            }
        });
    }
}
