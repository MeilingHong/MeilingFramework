package com.meiling.framework.app.activity.system_call;
/**
 * Created by marisareimu@126.com on 2021-01-14  15:58
 * project MeilingFramework
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.app.activity.camera.CameraPreviewLocalImageActivity;
import com.meiling.framework.app.base.IntentParameterKeys;
import com.meiling.framework.app.utils.permission.PermissionHelper;
import com.meiling.framework.app.utils.permission.PermissionType;
import com.meiling.framework.base_activity.BaseActivity;
import com.meiling.framework.common_util.toast.ToastUtil;

import androidx.annotation.Nullable;

/**
 * Created by huangzhou@ulord.net on 2021-01-14  15:58
 * project MeilingFramework
 */
public class SystemCallActivity extends BaseActivity {

    private TextView systemCamera;
    private TextView camera1;
    private TextView camera2;

    @Override
    public void configActivity() {
        setConfigHasStatusBarWithBlackFont();
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_system_call;
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
                callPhone();
            }
        });
        camera1 = findViewById(R.id.camera1);
        camera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOutBrowser();
            }
        });
        camera2 = findViewById(R.id.camera2);
        camera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEmail();
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

    private void callPhone() {
        PermissionHelper permissionHelper = new PermissionHelper();
        permissionHelper.setResult(new PermissionHelper.PermissionResult() {
            @Override
            public void success() {
                // 执行打电话
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "0731-12345678"));
                startActivity(intent);
            }
        });
        permissionHelper.requestPermission(this, PermissionType.TYPE_CALL_PHONE);
    }

    private void openOutBrowser() {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.baidu.com"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            ToastUtil.toastShortCenter(SystemCallActivity.this, "请下载浏览器或可打开该链接的应用");
        }
    }

    private void openEmail() {
        String[] reciver = new String[]{"邮件地址"};
        String[] mySubject = new String[]{""};
        String myCc = "cc";
        String mybody = "邮件内容";
        Intent myIntent = new Intent(android.content.Intent.ACTION_SEND);
        myIntent.setType("plain/text");
        myIntent.putExtra(android.content.Intent.EXTRA_EMAIL, reciver);
        myIntent.putExtra(android.content.Intent.EXTRA_CC, myCc);
        myIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mySubject);
        myIntent.putExtra(android.content.Intent.EXTRA_TEXT, mybody);
        startActivity(Intent.createChooser(myIntent, "发送邮件"));
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
