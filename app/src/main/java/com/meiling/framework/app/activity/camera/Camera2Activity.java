package com.meiling.framework.app.activity.camera;
/**
 * Created by marisareimu@126.com on 2021-01-14  15:58
 * project MeilingFramework
 */

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.app.base.BaseApplication;
import com.meiling.framework.app.base.IntentParameterKeys;
import com.meiling.framework.app.utils.directory.DirectoryUtil;
import com.meiling.framework.app.utils.permission.PermissionHelper;
import com.meiling.framework.app.utils.permission.PermissionType;
import com.meiling.framework.base_activity.BaseActivity;
import com.meiling.framework.common_util.datahandle.TimeFormat;
import com.meiling.framework.common_util.toast.ToastUtil;

import java.io.File;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

public class Camera2Activity extends BaseActivity {

    private TextView systemCamera;
    private TextView camera1;
    private TextView camera2;
    @Override
    public void configActivity() {
        setConfigHasStatusBarWithBlackFont();
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_camera2;
    }

    @Override
    public void afterSetContentView(@Nullable Bundle savedInstanceState) {
        initComponent();
    }

    private void initComponent(){
        systemCamera = findViewById(R.id.systemCamera);
        systemCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSystemCamera();
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
                    String dir = DirectoryUtil.getExternalDir(Camera2Activity.this);
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
                    ToastUtil.toastShortCenter(Camera2Activity.this, "没有系统相机");
                }
            }
        });
        permissionHelper.requestPermission(this, PermissionType.TYPE_CAMERA_STORAGE);
    }

    private void toLocalPreview(String cameraImagePath){
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
            case CAMERA_1:{
                if (resultCode == RESULT_OK && data!=null && !TextUtils.isEmpty(data.getStringExtra(IntentParameterKeys.image_path))) {
                    toLocalPreview(data.getStringExtra(IntentParameterKeys.image_path));
                }
                break;
            }
            case CAMERA_2:{
                if (resultCode == RESULT_OK && data!=null && !TextUtils.isEmpty(data.getStringExtra(IntentParameterKeys.image_path))) {
                    toLocalPreview(data.getStringExtra(IntentParameterKeys.image_path));
                }
                break;
            }
        }
    }
}
