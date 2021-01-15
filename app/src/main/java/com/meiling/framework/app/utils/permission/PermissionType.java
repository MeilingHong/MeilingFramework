package com.meiling.framework.app.utils.permission;

/**
 * Created by marisareimu@126.com on 2020-12-17  10:54
 * project Ubanquan
 */

public enum PermissionType {
    TYEE_STORAGE,// 仅存储权限
    TYEE_CALLPHONE,// 仅存储权限
    TYPE_CAMERA_STORAGE,// 摄像头与存储权限
    TYPE_LOCATION,// 仅定位信息
    TYPE_CAMERA_STORAGE_LOCATION_INFO,// 照相【摄像头、存储、定位、设备信息】
    TYPE_CAMERA_STORAGE_LOCATION_VOICE_INFO,//录像【摄像头、存储、定位、录音、设备信息】
    TYEE_STORAGE_LOCATION_INFO,//  【存储、定位、设备信息】
    TYEE_STORAGE_LOCATION_VOICE_INFO,//录音【存储、定位、录音、设备信息】
    TYEE_STORAGE_LOCATION_VOICE_INFO_WINDOE,//录屏【存储、定位、录音、设备信息、悬浮窗】
    TYEE_STORAGE_INSTALL,// 更新【存储、安装更新】



    TYPE_CALL_PHONE//   【打电话】
    ;//   【开启浏览器】
}
