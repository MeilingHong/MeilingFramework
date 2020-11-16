package com.meiling.framework.common_util.notify;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

public class NotificationUtil {
    private Context mContext;

    public NotificationUtil(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /**
     * todo 【需要保证在发送通知方法调用前是第一个被调用的】
     * 设置通知渠道信息，在8.0以上的系统中，如果没有事先调用过这个，将导致通知无法显示出来
     *
     * @param channelId
     * @param channelName
     */
    public void setNotificationChannel(String channelId, String channelName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(channelId, channelName);
        }
    }

    /**
     * 显示通知信息
     *
     * @param notifyId
     * @param channelId
     * @param icon
     * @param title
     * @param content
     */
    public void showNotification(int notifyId, String channelId, @DrawableRes int icon, CharSequence title, CharSequence content, boolean isAutoCancel) {
        NotificationCompat.Builder builder = buildNotification(channelId, icon, title, content);
        builder.setAutoCancel(isAutoCancel);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notifyNotification(notifyId, notification);
    }

    /**
     * 发送一个通知
     *
     * @param notifyId
     * @param channelId
     * @param channelName
     * @param icon
     * @param title
     * @param content
     */
    public void showStartNotification(int notifyId, String channelId, String channelName, @DrawableRes int icon, CharSequence title, CharSequence content, boolean autoCancel) {
        setNotificationChannel(channelId, channelName);
        NotificationCompat.Builder builder = buildNotification(channelId, icon, title, content);
        builder.setAutoCancel(autoCancel);
        notifyNotification(notifyId, builder.build());
    }

    /**
     * 显示下载中的通知（更新进度）
     *
     * @param notifyId
     * @param channelId
     * @param icon
     * @param title
     * @param content
     * @param progress
     * @param size
     */
    public void showProgressNotification(int notifyId, String channelId, @DrawableRes int icon, CharSequence title, CharSequence content, int progress, int size) {
        NotificationCompat.Builder builder = buildNotification(channelId, icon, title, content, progress, size);
        builder.setAutoCancel(false);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONLY_ALERT_ONCE;
        notifyNotification(notifyId, notification);
    }

    /**
     * 显示下载完成时的通知（点击安装）
     *
     * @param notifyId
     * @param channelId
     * @param icon
     * @param title
     * @param content
     * @param file
     */
    public void installApkNotification(int notifyId, String channelId, @DrawableRes int icon, CharSequence title, CharSequence content, File file, String authority) {
        cancelNotification(notifyId);
        NotificationCompat.Builder builder = buildNotification(channelId, icon, title, content);
        builder.setAutoCancel(true);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uriData;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uriData = FileProvider.getUriForFile(mContext, authority, file);
        } else {
            uriData = Uri.fromFile(file);
        }
        intent.setDataAndType(uriData, type);
        PendingIntent clickIntent = PendingIntent.getActivity(mContext, notifyId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(clickIntent);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notifyNotification(notifyId, notification);
    }

    public void sendIntentNotification(int notifyId, String channelId, @DrawableRes int icon, CharSequence title, CharSequence content, boolean isSendIntent, Intent intent) {
        NotificationCompat.Builder builder = buildNotification(channelId, icon, title, content);
        builder.setAutoCancel(true);
        if (isSendIntent) {//
            PendingIntent clickIntent = PendingIntent.getService(mContext, notifyId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(clickIntent);
        } else {
            PendingIntent clickIntent = PendingIntent.getService(mContext, notifyId, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(clickIntent);
        }
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notifyNotification(notifyId, notification);
    }

    /**
     * @param notifyId
     */
    private void cancelNotification(int notifyId) {
        getNotificationManager().cancel(notifyId);
    }


    /**
     * 获取通知管理器
     *
     * @return
     */
    private NotificationManager getNotificationManager() {
        return (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 创建一个通知渠道（兼容0以上版本）
     *
     * @param channelId
     * @param channelName
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        getNotificationManager().createNotificationChannel(channel);
    }

    /**
     * 构建一个通知构建器
     *
     * @param channelId
     * @param icon
     * @param title
     * @param content
     * @return
     */
    private NotificationCompat.Builder buildNotification(String channelId, @DrawableRes int icon, CharSequence title, CharSequence content) {
        return buildNotification(channelId, icon, title, content, Constants.NONE, Constants.NONE);
    }

    /**
     * 构建一个通知构建器
     *
     * @param channelId
     * @param icon
     * @param title
     * @param content
     * @param progress
     * @param size
     * @return
     */
    private NotificationCompat.Builder buildNotification(String channelId, @DrawableRes int icon, CharSequence title, CharSequence content, int progress, int size) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, channelId);
        builder.setSmallIcon(icon);

        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setOngoing(true);

        if (progress != Constants.NONE && size != Constants.NONE) {
            builder.setProgress(size, progress, false);
        }

        return builder;
    }

    /**
     * 更新通知栏
     *
     * @param id
     * @param notification
     */
    private void notifyNotification(int id, Notification notification) {
        getNotificationManager().notify(id, notification);
    }
}
