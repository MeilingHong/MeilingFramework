package com.meiling.framework.base_activity.util.notification;


import androidx.annotation.DrawableRes;

public class NotificationConfig {
    private boolean isShowStartNotification;
    private boolean isShowDownloadProgressNotification;
    private boolean isShowInstallNotification;//当需要点击通知栏进行安装时：true;false则下载完成后直接进行安装

    //单个任务同时使用相同的notifyId，当对应状态变化时，显示不同的
    private int notifyId;
    //配置ChannelId、ChannelName，方便兼容高版本通知（26？）
    private String channelId;
    private String channelName;
    //开始下载提醒（很可能只显示一下就被下载进度通知替换掉了）
    private String titleStart;
    private String contentStart;
    //下载进度
    private String titleDownload;
    private String contentDownload;
    //下载完成，点击通知进行安装
    private String titleInstall;
    private String contentInstall;
    private int drawableRes;

    public boolean isShowStartNotification() {
        return isShowStartNotification;
    }

    public void setShowStartNotification(boolean showStartNotification) {
        isShowStartNotification = showStartNotification;
    }

    public boolean isShowDownloadProgressNotification() {
        return isShowDownloadProgressNotification;
    }

    public void setShowDownloadProgressNotification(boolean showDownloadProgressNotification) {
        isShowDownloadProgressNotification = showDownloadProgressNotification;
    }

    public boolean isShowInstallNotification() {
        return isShowInstallNotification;
    }

    public void setShowInstallNotification(boolean showInstallNotification) {
        isShowInstallNotification = showInstallNotification;
    }

    public int getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(int notifyId) {
        this.notifyId = notifyId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getTitleStart() {
        return titleStart;
    }

    public void setTitleStart(String titleStart) {
        this.titleStart = titleStart;
    }

    public String getContentStart() {
        return contentStart;
    }

    public void setContentStart(String contentStart) {
        this.contentStart = contentStart;
    }

    public String getTitleDownload() {
        return titleDownload;
    }

    public void setTitleDownload(String titleDownload) {
        this.titleDownload = titleDownload;
    }

    public String getContentDownload() {
        return contentDownload;
    }

    public void setContentDownload(String contentDownload) {
        this.contentDownload = contentDownload;
    }

    public String getTitleInstall() {
        return titleInstall;
    }

    public void setTitleInstall(String titleInstall) {
        this.titleInstall = titleInstall;
    }

    public String getContentInstall() {
        return contentInstall;
    }

    public void setContentInstall(String contentInstall) {
        this.contentInstall = contentInstall;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public void setDrawableRes(@DrawableRes int drawableRes) {
        this.drawableRes = drawableRes;
    }
}
