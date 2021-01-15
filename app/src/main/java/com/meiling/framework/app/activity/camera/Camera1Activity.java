package com.meiling.framework.app.activity.camera;
/**
 * Created by marisareimu@126.com on 2021-01-14  15:58
 * project MeilingFramework
 */

import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.app.base.BaseApplication;
import com.meiling.framework.app.utils.bugly.CrashReportUtil;
import com.meiling.framework.app.utils.directory.DirectoryUtil;
import com.meiling.framework.app.utils.permission.PermissionHelper;
import com.meiling.framework.app.utils.permission.PermissionType;
import com.meiling.framework.base_activity.BaseActivity;
import com.meiling.framework.base_activity.util.info.CameraCheckUtil;
import com.meiling.framework.common_util.datahandle.TimeFormat;
import com.meiling.framework.common_util.datahandle.UnitExchangeUtil;
import com.meiling.framework.common_util.log.Ulog;
import com.meiling.framework.common_util.toast.ToastUtil;

import java.io.File;
import java.io.IOException;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;

public class Camera1Activity extends BaseActivity {

    private ImageView ivComponentActionBarBack;
    private SurfaceView shotSvPreview;
    private TextView switchCamera;
    private TextView switchFlash;
    private TextView doShot;
    private RelativeLayout shotRlFocusBg;
    private View shotRlFocusIcon;

    @Override
    public void configActivity() {
        setConfigHasStatusBarWithBlackFont();
        isBlockBackMode = true;// 阻止返回键响应
        isKeepScreenMode = true;// 保持屏幕常亮，避免由于息屏引起的资源释放
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_camera1;
    }

    @Override
    public void afterSetContentView(@Nullable Bundle savedInstanceState) {
        initComponent();
    }

    private void initComponent() {
        initComponentView();
        initListener();

        initStoragePath();// 重新获取文件路径
        initCameraAvalible();// 检查权限与设备硬件是否可用

        initSwitchCamera();
        initFlash();
    }

    private void initComponentView() {
        ivComponentActionBarBack = findViewById(R.id.ivComponentActionBarBack);
        shotSvPreview = findViewById(R.id.shotSvPreview);
        switchCamera = findViewById(R.id.switchCamera);
        switchFlash = findViewById(R.id.switchFlash);
        doShot = findViewById(R.id.doShot);
        shotRlFocusBg = findViewById(R.id.shotRlFocusBg);
        shotRlFocusIcon = findViewById(R.id.shotRlFocusIcon);
    }

    private void initListener() {
        ivComponentActionBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // 切换摄像头
        switchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // 切换闪光灯状态
        switchFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // 执行拍照
        doShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        addZoomTouchListener();
    }

    @Override
    public void releaseAfterDestroy() {
        releaseCameraResource();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void lasyloadCall() {

    }

    /*
     ************************************************************************************************************************************************
     */

    private String cameraImagePath;

    private void initStoragePath() {
        String dir = DirectoryUtil.getExternalDir(Camera1Activity.this);
        File nameAuthDir = new File(dir);
        if (!nameAuthDir.exists()) {
            nameAuthDir.mkdirs();
        }
        String cameraTempFileString = dir + File.separator + TimeFormat.formatTime_yyyyMMddHHmmss_line(System.currentTimeMillis()) + ".png";
//        File cameraTempFile = new File(cameraTempFileString);
        cameraImagePath = cameraTempFileString;
    }

    private void initCameraAvalible() {
        PermissionHelper permissionHelper = new PermissionHelper();
        permissionHelper.setResult(new PermissionHelper.PermissionResult() {
            @Override
            public void success() {
                if (CameraCheckUtil.hasBackCamera(BaseApplication.getInstance()) ||
                        CameraCheckUtil.hasFrontCamera(BaseApplication.getInstance())) {
                    // todo 可以执行拍照
                    initCameraDevice(false);
                } else {
                    finish();
                    ToastUtil.toastShortCenter(Camera1Activity.this, "无相机硬件无法执行拍照需求");
                }
            }
        });
        permissionHelper.setDismissResult(new PermissionHelper.PermissionDismissResult() {
            @Override
            public void dismiss() {
                finish();
                ToastUtil.toastShortCenter(Camera1Activity.this, "委授予相机与存储权限，无法执行拍照需求");
            }
        });
        permissionHelper.requestPermission(this, PermissionType.TYPE_CAMERA_STORAGE);
    }

    /*
     ************************************************************************************************************************************************
     */

    private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                if (mCamera != null) {
                    //todo 设置预览方向
                    mCamera.setDisplayOrientation(90);
                    //把这个预览效果展示在SurfaceView上面
                    mCamera.setPreviewDisplay(holder);
                    //开启预览效果
                    mCamera.startPreview();
                }
            } catch (IOException e) {
                e.printStackTrace();
                CrashReportUtil.report("surfaceCreated-", e);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (holder == null || holder.getSurface() == null) {
                return;
            }
            if (mCamera != null) {
                try {
                    //停止预览效果
                    mCamera.stopPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashReportUtil.report("surfaceChanged-1", e);
                }
                //重新设置预览效果
                try {
                    mCamera.setPreviewDisplay(mHolder);
                    mCamera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                    CrashReportUtil.report("surfaceChanged-2", e);
                }
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    /*
     ************************************************************************************************************************************************
     */
    private final int FLASH_OFF = 0;
    private final int FLASH_AUTO = 1;
    private final int FLASH_ON = 2;
    private int currentFlash = FLASH_OFF;

    private void setFlash(@IntRange(from = 0, to = 2) int currentFlash) {
        try {
            if (mCamera != null) {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFlashMode(currentFlash == FLASH_ON ? Camera.Parameters.FLASH_MODE_TORCH :
                        currentFlash == FLASH_AUTO ? Camera.Parameters.FLASH_MODE_AUTO : Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);//如果设置失败，则状态不会变更
                this.currentFlash = currentFlash;//
                setTextValue(switchFlash, currentFlash == FLASH_ON ? "闪光灯开" :
                        currentFlash == FLASH_AUTO ? "闪光自动" : "闪光灯关");
            }
        } catch (Exception e) {
            e.printStackTrace();
            CrashReportUtil.report("设置闪光灯异常-", e);
        }
    }

    /**
     * 执行对焦操作
     */
    private void doFocusAction() {
        try {
            if (mCamera != null) {
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            CrashReportUtil.report("执行对焦异常-", e);
        }
    }

    private final int BACK_CAMERA_ROTATE = 90;
    private final int FRONT_CAMERA_ROTATE_90 = 90;
    private final int FRONT_CAMERA_ROTATE_270 = 270;
    private volatile boolean isBackCamera = true;

    private boolean isBackCamera() {
        return isBackCamera;
    }

    private void setParameterRotation() {
        try {
            if (mCamera != null) {
                Camera.Parameters parameters = mCamera.getParameters();
                //设置照片的旋转角度
                if (isBackCamera()) {// 后置摄像头
                    parameters.setRotation(BACK_CAMERA_ROTATE);//
                } else {//前置摄像头
                    // todo Android 10  Android R 版本，这个部分显示有问题，需要调整
                    parameters.setRotation(Build.VERSION.SDK_INT <= 22 ? FRONT_CAMERA_ROTATE_270 : // 6.0 以下
                            Build.VERSION.SDK_INT == 28 ? FRONT_CAMERA_ROTATE_270 : // android 9 版本
                                    FRONT_CAMERA_ROTATE_90);// 6.0 ~ 8.1 版本 以及Android10版本
                }
                mCamera.setParameters(parameters);
            }
        } catch (Exception e) {
            e.printStackTrace();
            CrashReportUtil.report("设置旋转角度异常-", e);
        }
    }

    private void setPictureFormat() {
        try {
            if (mCamera != null) {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setPictureFormat(ImageFormat.JPEG);
                mCamera.setParameters(parameters);
            }
        } catch (Exception e) {
            e.printStackTrace();
            CrashReportUtil.report("设置照片格式异常-", e);
        }
    }

    /*
     ************************************************************************************************************************************************
     */
    private boolean isSupportZoom() {
        if (mCamera != null && mCamera.getParameters() != null) {
            if (mCamera.getParameters().getMaxZoom() != 0 &&
                    mCamera.getParameters().isZoomSupported()) {// 当预览变化时，这个值也可能出现变化，需要重新调用获取
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    // 放大
    private void zoomPlus() {
        if (mCamera != null && mCamera.getParameters() != null && isSupportZoom()) {
            int maxZoom = mCamera.getParameters().getMaxZoom();
            int currentZoom = mCamera.getParameters().getZoom();
            if (currentZoom < maxZoom) {
                try {
                    if (mCamera != null) {
                        Camera.Parameters parameters = mCamera.getParameters();
                        parameters.setZoom(currentZoom + 1);
                        mCamera.setParameters(parameters);
                    }
                    lastZoom = currentZoom + 1;
//                    if (shotTvZoom != null) shotTvZoom.setText(lastZoom + "x");
//                    if (shotTvZoom != null) shotTvZoom.setVisibility(View.VISIBLE);
                    Ulog.d("【zoomPlus】当前缩放倍数" + (currentZoom + 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
//            if (shotTvZoom != null) shotTvZoom.setVisibility(View.GONE);
        }
    }

    private void zoomMinus() {
        if (mCamera != null && mCamera.getParameters() != null && isSupportZoom()) {
            int currentZoom = mCamera.getParameters().getZoom();
            if (currentZoom > 1) {
                try {
                    if (mCamera != null) {
                        Camera.Parameters parameters = mCamera.getParameters();
                        parameters.setZoom(currentZoom - 1);
                        mCamera.setParameters(parameters);
                    }
                    lastZoom = currentZoom - 1;
//                    if (shotTvZoom != null) shotTvZoom.setText(lastZoom + "x");
//                    if (shotTvZoom != null) shotTvZoom.setVisibility(View.VISIBLE);
                    Ulog.d("【zoomMinus】当前缩放倍数" + (currentZoom - 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
//            if (shotTvZoom != null) shotTvZoom.setVisibility(View.GONE);
        }
    }

    private int lastZoom = 1;

    private void setZoom(int zoom) {
        if (mCamera != null && mCamera.getParameters() != null && isSupportZoom()) {
            int maxZoom = mCamera.getParameters().getMaxZoom();
            if (zoom >= 1 && zoom <= maxZoom) {
                try {
                    if (mCamera != null) {
                        Camera.Parameters parameters = mCamera.getParameters();
                        parameters.setZoom(zoom);
                        mCamera.setParameters(parameters);
                    }
                    Ulog.d("【setZoom】当前缩放倍数" + (zoom));
//                    if (shotTvZoom != null) shotTvZoom.setText(zoom + "x");
//                    if (shotTvZoom != null) shotTvZoom.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashReportUtil.report("设置缩放倍数异常-", e);
                }
            }
        } else {
//            if (shotTvZoom != null) shotTvZoom.setVisibility(View.GONE);
        }
    }

    private float point0x;
    private float point0y;
    private float point1x;
    private float point1y;
    private double distance;
    private double currentDistance;
    private long lastActionTime = 0;

    private long focusTime = 0;
    private float lastX = 0;
    private float lastY = 0;
    private float sumHeight = 0;

    private void addZoomTouchListener() {
        final int dp38 = UnitExchangeUtil.dip2px(getApplicationContext(), 38);
        final int widthPx = getResources().getDisplayMetrics().widthPixels;
        shotRlFocusBg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    if (event.getPointerCount() >= 2) {
                        int index = event.getActionIndex();// 获取当前时间的下标序号
                        switch (event.getActionMasked()) {
                            case MotionEvent.ACTION_POINTER_DOWN:
                                // 判断是否是第2个手指按下
                                break;
                            case MotionEvent.ACTION_POINTER_UP:
                                // 判断抬起的手指是否是第2个
                                distance = 0;// 重置数据，避免上一次的移动影响当当前滑动手势的预期缩放效果
                                break;
                            case MotionEvent.ACTION_MOVE:
                                if (event != null && event.getPointerCount() >= 2) {// 判断当前事件中含有2个或以上手指进行操作
                                    // 通过 pointerId 来获取 pointerIndex
                                    if ((System.currentTimeMillis() - lastActionTime) > 50) {// 【时间差，超过50毫秒进行响应】
                                        lastActionTime = System.currentTimeMillis();// 记录操作时间
                                        int pointerIndex0 = event.findPointerIndex(0);
                                        int pointerIndex1 = event.findPointerIndex(1);
                                        // 通过 pointerIndex 来取出对应的坐标
                                        point0x = event.getX(pointerIndex0);
                                        point0y = event.getY(pointerIndex0);
                                        // 获取 对应点的坐标
                                        point1x = event.getX(pointerIndex1);
                                        point1y = event.getY(pointerIndex1);
                                        // 计算两点的距离

                                        // 通过变量的赋值，减少后续的数据再计算，从而减少潜在的时间消耗
                                        currentDistance = Math.sqrt(Math.pow(point0x - point1x, 2) + Math.pow(point0y - point1y, 2));
                                        if (distance == 0) {
                                            distance = currentDistance;
                                        } else {
                                            // 通过两点间的距离的增减判断应该执行放大，还是缩小
                                            if ((currentDistance - distance) > 0) {
                                                // 增加缩放倍数
                                                zoomPlus();
                                                distance = currentDistance;
                                            } else if ((currentDistance - distance) < 0) {
                                                // 缩小缩放倍数
                                                zoomMinus();
                                                distance = currentDistance;
                                            }
                                        }
                                    }
                                }
                                break;
                        }
                    } else {// 处理聚焦的触摸事件
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                focusTime = System.currentTimeMillis();// 记录当前对焦时间
                                lastX = event.getX();// 记录当前对焦的位置
                                lastY = event.getY();
                                break;
                            }
                            case MotionEvent.ACTION_MOVE: {
                                break;
                            }
                            case MotionEvent.ACTION_UP: {
                                // 当点击时间小于1秒，且移动范围小于指定距离时，执行聚焦操作
                                // 且认定该动作为聚焦的点击事件
                                Ulog.d("对焦点击？");
                                if (shotRlFocusIcon != null &&
                                        shotRlFocusIcon.getVisibility() == View.GONE &&// 限制，对焦框不可见时，才能够响应对焦
                                        (System.currentTimeMillis() - focusTime) < 1000 && // 对焦点击操作限定为需要1秒内完成动作，否则，视为其他动作，而非对焦操作
                                        Math.abs(event.getX() - lastX) < 300 && Math.abs(event.getY() - lastY) < 300) {// 限定对焦移动后的范围，需要在300px的半径内，否则也不视为进行对焦的点击操作
                                    // 记录当前位置
                                    lastX = event.getX();
                                    lastY = event.getY();
                                    // 计算对焦框的位置
                                    if (lastX < dp38) {
                                        lastX = dp38;
                                    } else if (lastX > (widthPx - dp38)) {
                                        lastX = widthPx - dp38;
                                    }
                                    if (lastY < dp38) {
                                        lastY = dp38;
                                    } else if (lastY > (sumHeight - dp38)) {
                                        lastX = sumHeight - dp38;
                                    }
                                    // 显示移动后的位置
                                    if (shotRlFocusIcon != null) {
                                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) shotRlFocusIcon.getLayoutParams();
                                        if (layoutParams != null) {
                                            layoutParams.topMargin = (int) (lastY - dp38);
                                            layoutParams.leftMargin = (int) (lastX - dp38);
                                            shotRlFocusIcon.setLayoutParams(layoutParams);// 设置对焦框的位置
                                        }
                                        shotRlFocusIcon.setVisibility(View.VISIBLE);// 设置可见，
                                        shotRlFocusIcon.postDelayed(new Runnable() {// 1.5 秒后隐藏对焦框
                                            @Override
                                            public void run() {
                                                if (shotRlFocusIcon != null) {
                                                    shotRlFocusIcon.setVisibility(View.GONE);
                                                }
                                            }
                                        }, 1000);
                                    }
                                    // 执行对焦操作
                                    doFocusAction();// 触摸手动对焦

                                }
                                break;
                            }
                        }
                    }
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashReportUtil.report("缩放或点击对焦时出现异常【拍照】-", e);
                }
                return false;
            }
        });
    }

    /*
     ************************************************************************************************************************************************
     */

    private void setTextValue(TextView textValue, String value) {
        if (textValue != null) {
            textValue.setText(!TextUtils.isEmpty(value) ? value : "");
        }
    }

    /*
     ************************************************************************************************************************************************
     */

    private void releaseCameraResource() {
        if (mCamera != null) {//释放资源，
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private void initSwitchCamera() {
        if (CameraCheckUtil.hasBackCamera(BaseApplication.getInstance()) &&
                CameraCheckUtil.hasFrontCamera(BaseApplication.getInstance())) {
            // 如果没有目录，则创建目录
            // todo 可以进行摄像头切换
            switchCamera.setVisibility(View.VISIBLE);
        } else {
            switchCamera.setVisibility(View.GONE);
        }
    }

    private void initFlash() {
        if (CameraCheckUtil.hasFlashlight(BaseApplication.getInstance())) {
            // 如果没有目录，则创建目录
            // todo 可以进行摄像头切换
            switchFlash.setVisibility(View.VISIBLE);
        } else {
            switchFlash.setVisibility(View.GONE);
        }
    }

    /*
     ************************************************************************************************************************************************
     */

    private SurfaceHolder mHolder;
    private Camera mCamera;

    private void initCameraDevice(boolean isDoFocus) {
        releaseCameraResource();// 先释放相机资源
        try {
            if (CameraCheckUtil.hasBackCamera(BaseApplication.getInstance())) {
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);//todo 设置为后置摄像头
                isBackCamera = true;
            } else if (CameraCheckUtil.hasFrontCamera(BaseApplication.getInstance())) {
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);//todo 设置为前置摄像头
                isBackCamera = false;
            } else {
                finish();
            }
            if (mHolder == null) {
                if (shotSvPreview == null) {
                    shotSvPreview = (SurfaceView) findViewById(R.id.shotSvPreview);
                }
                mHolder = shotSvPreview.getHolder();
                mHolder.addCallback(mCallback);
                mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            }

            //设置预览方向【默认摄像头是横拍】
            mCamera.setDisplayOrientation(90);
            //把这个预览效果展示在SurfaceView上面
            mCamera.setPreviewDisplay(mHolder);
            //开启预览效果
            mCamera.startPreview();

            // 设置闪光灯
            setFlash(FLASH_OFF);

            // 设置相机的旋转
            setParameterRotation();
            //设置图片格式
            setPictureFormat();

            // 执行对焦
            if (isDoFocus) {
                // 执行对焦，onCreate方法内执行对焦其实不会有作用，需要等到页面完全显示之后，执行对焦才会真正生效
                // 这个执行在onWindowFocusChange方法中执行会比较好
                doFocusAction();
            }

            // 设置初始的缩放倍数
            setZoom(lastZoom);
        } catch (Exception e) {
            CrashReportUtil.report("初始化相机异常-", e);
        }
    }

    private void switchCameraDevice(boolean isBack, boolean isDoFocus) {
        releaseCameraResource();// 先释放相机资源
        try {
            if (isBack && CameraCheckUtil.hasBackCamera(BaseApplication.getInstance())) {
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);//todo 设置为后置摄像头
                isBackCamera = true;
            } else if (!isBack && CameraCheckUtil.hasFrontCamera(BaseApplication.getInstance())) {
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);//todo 设置为前置摄像头
                isBackCamera = false;
            } else {
                finish();
            }
            if (mHolder == null) {
                if (shotSvPreview == null) {
                    shotSvPreview = (SurfaceView) findViewById(R.id.shotSvPreview);
                }
                mHolder = shotSvPreview.getHolder();
                mHolder.addCallback(mCallback);
                mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            }

            //设置预览方向【默认摄像头是横拍】
            mCamera.setDisplayOrientation(90);
            //把这个预览效果展示在SurfaceView上面
            mCamera.setPreviewDisplay(mHolder);
            //开启预览效果
            mCamera.startPreview();

            // 设置闪光灯
            setFlash(FLASH_OFF);

            // 设置相机的旋转
            setParameterRotation();
            //设置图片格式
            setPictureFormat();

            // 执行对焦
            if (isDoFocus) {
                // 执行对焦，onCreate方法内执行对焦其实不会有作用，需要等到页面完全显示之后，执行对焦才会真正生效
                // 这个执行在onWindowFocusChange方法中执行会比较好
                doFocusAction();
            }

            // 设置初始的缩放倍数
            setZoom(lastZoom);
        } catch (Exception e) {
            CrashReportUtil.report("初始化相机异常-", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switchCameraDevice(isBackCamera, false);// 避免类似三星这种在跳转页面后释放掉设备的情况出现
    }
}
