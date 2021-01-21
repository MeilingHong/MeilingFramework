package com.meiling.framework.app.activity;
/**
 * Created by marisareimu@126.com on 2021-01-14  15:58
 * project MeilingFramework
 */

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.app.activity.camera.Camera1Activity;
import com.meiling.framework.app.activity.camera.Camera2Activity;
import com.meiling.framework.app.activity.camera.CameraPreviewLocalImageActivity;
import com.meiling.framework.app.base.BaseApplication;
import com.meiling.framework.app.base.IntentParameterKeys;
import com.meiling.framework.app.dialog.picker.TimePickerDialog;
import com.meiling.framework.app.dialog.picker.data.Type;
import com.meiling.framework.app.dialog.picker.listener.OnDateSetListener;
import com.meiling.framework.app.utils.directory.DirectoryUtil;
import com.meiling.framework.app.utils.permission.PermissionHelper;
import com.meiling.framework.app.utils.permission.PermissionType;
import com.meiling.framework.base_activity.BaseActivity;
import com.meiling.framework.base_activity.util.info.CameraCheckUtil;
import com.meiling.framework.common_util.datahandle.TimeFormat;
import com.meiling.framework.common_util.log.Ulog;
import com.meiling.framework.common_util.toast.ToastUtil;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

/**
 * Created by huangzhou@ulord.net on 2021-01-14  15:58
 * project MeilingFramework
 */
public class TimePickerActivity extends BaseActivity {

    private TextView systemCamera;
    private TextView camera1;
    private TextView camera2;
    private TextView timepicker;

    @Override
    public void configActivity() {
        setConfigHasStatusBarWithBlackFont();
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    public void afterSetContentView(@Nullable Bundle savedInstanceState) {
        initComponent();
    }

    private void initComponent() {
        systemCamera = findViewById(R.id.systemCamera);
        systemCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        camera1 = findViewById(R.id.camera1);
        camera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        camera2 = findViewById(R.id.camera2);
        camera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        timepicker = findViewById(R.id.timepicker);
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker();
            }
        });
        currentTime = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public void releaseAfterDestroy() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void lasyloadCall() {

    }

    private String cameraImagePath;

    private void openSystemCamera() {
        PermissionHelper permissionHelper = new PermissionHelper();
        permissionHelper.setResult(new PermissionHelper.PermissionResult() {
            @Override
            public void success() {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // 如果没有目录，则创建目录
                    String dir = DirectoryUtil.getExternalDir(TimePickerActivity.this);
                    File nameAuthDir = new File(dir);
                    if (!nameAuthDir.exists()) {
                        nameAuthDir.mkdirs();
                    }
                    String cameraTempFileString = dir + File.separator + TimeFormat.formatTime_yyyyMMddHHmmss_line(System.currentTimeMillis()) + ".png";
                    File cameraTempFile = new File(cameraTempFileString);
                    cameraImagePath = cameraTempFileString;
                    // 方式1
                    Uri imageUri = FileProvider.getUriForFile(BaseApplication.getInstance(), "com.meiling.framework.app.fileprovider", cameraTempFile);
                    // 方式2
//                     Uri imageUri = Uri.fromFile(cameraTempFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        grantUriPermission(packageName, imageUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    startActivityForResult(takePictureIntent, SYSTEM_CAMERA);
                } else {
                    ToastUtil.toastShortCenter(TimePickerActivity.this, "没有系统相机");
                }
            }
        });
        permissionHelper.requestPermission(this, PermissionType.TYPE_CAMERA_STORAGE);
    }

    private void openCamear1() {
        PermissionHelper permissionHelper = new PermissionHelper();
        permissionHelper.setResult(new PermissionHelper.PermissionResult() {
            @Override
            public void success() {
                if (CameraCheckUtil.hasBackCamera(BaseApplication.getInstance()) ||
                        CameraCheckUtil.hasFrontCamera(BaseApplication.getInstance())) {
                    // 如果没有目录，则创建目录
                    skipIntent(null, Camera1Activity.class, CAMERA_1);
                } else {
                    ToastUtil.toastShortCenter(TimePickerActivity.this, "没有系统相机");
                }
            }
        });
        permissionHelper.requestPermission(this, PermissionType.TYPE_CAMERA_STORAGE);
    }

    private void openCamear2() {
        PermissionHelper permissionHelper = new PermissionHelper();
        permissionHelper.setResult(new PermissionHelper.PermissionResult() {
            @Override
            public void success() {
                if (CameraCheckUtil.hasBackCamera(BaseApplication.getInstance()) ||
                        CameraCheckUtil.hasFrontCamera(BaseApplication.getInstance())) {
                    // 如果没有目录，则创建目录
                    skipIntent(null, Camera2Activity.class, CAMERA_2);
                } else {
                    ToastUtil.toastShortCenter(TimePickerActivity.this, "没有系统相机");
                }
            }
        });
        permissionHelper.requestPermission(this, PermissionType.TYPE_CAMERA_STORAGE);
    }

    private long currentTime;
    private TimePickerDialog mDialogTime;

    /**
     * todo 优化点：
     *  1、年月日时分秒都进行是否循环的参数化
     */

    private void openTimePicker() {
        if (mDialogTime == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.YEAR,1990);
            calendar.set(Calendar.MONTH,0);
            calendar.set(Calendar.DAY_OF_MONTH,1);
            long minTimestamp = calendar.getTimeInMillis();
            calendar.clear();
            calendar.set(Calendar.YEAR,2100);
            calendar.set(Calendar.MONTH,12);
            calendar.set(Calendar.DAY_OF_MONTH,31);
            long maxTimestamp = calendar.getTimeInMillis();
            mDialogTime = new TimePickerDialog.Builder()
                    .setType(Type.YEAR_MONTH)
                    .setThemeColor(getResources().getColor(R.color.white))
//                    .setMaxMillseconds(System.currentTimeMillis())
                    .setWheelItemTextSize(18)
                    .setSureTextColor(R.color.color_06c1ae)
                    .setToolBarTextColor(R.color.color_999999)
                    .setCurrentMillseconds(currentTime)// todo 保证显示的是最后选中的时间------需要在选中后进行更新
                    .setCyclic(false)// 设置滚动不可循环
                    .setMinMillseconds(minTimestamp)// 设置起始时间
                    .setMaxMillseconds(maxTimestamp)// 设置终止时间
                    .setWheelItemTextNormalColor(Color.parseColor("#999999"))// 未选中的文字颜色
                    .setWheelItemTextSelectorColor(Color.parseColor("#3296fa"))// 选中的文字颜色
                    .setCallBack(new OnDateSetListener() {
                        @Override
                        public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(millseconds);

                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            Ulog.e(String.format("选中的年月：%d年，%d月", year, month + 1));
                            currentTime = millseconds;
                        }
                    })
                    .build();
            mDialogTime.setAfterDismissDialog(new TimePickerDialog.AfterDismissDialog() {
                @Override
                public void afterDismiss() {
                    if (mDialogTime != null) {
                        mDialogTime = null;
                    }
                }
            });
            mDialogTime.show(getSupportFragmentManager(), "time");
        }
    }

    private void toLocalPreview(String cameraImagePath) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentParameterKeys.image_path, cameraImagePath);
        skipIntent(bundle, CameraPreviewLocalImageActivity.class, -1);
    }

    private final int SYSTEM_CAMERA = 1000;
    private final int CAMERA_1 = 1001;
    private final int CAMERA_2 = 1002;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SYSTEM_CAMERA: {// 系统相机回调
                if (resultCode == RESULT_OK) {
                    toLocalPreview(cameraImagePath);
                }
                break;
            }
            case CAMERA_1: {
                if (resultCode == RESULT_OK && data != null && !TextUtils.isEmpty(data.getStringExtra(IntentParameterKeys.image_path))) {
                    toLocalPreview(data.getStringExtra(IntentParameterKeys.image_path));
                }
                break;
            }
            case CAMERA_2: {
                if (resultCode == RESULT_OK && data != null && !TextUtils.isEmpty(data.getStringExtra(IntentParameterKeys.image_path))) {
                    toLocalPreview(data.getStringExtra(IntentParameterKeys.image_path));
                }
                break;
            }
        }
    }
}
