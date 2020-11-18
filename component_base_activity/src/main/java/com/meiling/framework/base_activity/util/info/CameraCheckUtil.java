package com.meiling.framework.base_activity.util.info;


import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 *
 */
public class CameraCheckUtil {
    public static boolean hasCamera(Context mContext) {
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) && Camera.getNumberOfCameras() > 0;
    }

    public static boolean hasFlashlight(Context mContext){
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    private static boolean checkCameraFacing(final int facing) {
        final int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, info);
            if (facing == info.facing) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否有后置摄像头
     *
     * @return
     */
    public static boolean hasBackCamera(Context mContext) {
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) && checkCameraFacing(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    /**
     * 判断是否有前置摄像头
     *
     * @return
     */
    public static boolean hasFrontCamera(Context mContext) {
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) && checkCameraFacing(Camera.CameraInfo.CAMERA_FACING_FRONT);
    }
}
