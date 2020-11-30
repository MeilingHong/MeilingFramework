package com.meiling.framework.common_util.video;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * 获取视频的第一帧图像【本地、网络】
 *
 * Created by marisareimu@126.com on 2020-11-24  17:08
 * project MeilingFramework
 */
public class VideoFirstFrameUtil {
    public static Bitmap getLocalVideoFirstFrame(String localVideoFilePath) {
        if (TextUtils.isEmpty(localVideoFilePath)) {
            throw new RuntimeException("Invalid Local Video File Path!");
        }
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        try {
            media.setDataSource(localVideoFilePath);
            Bitmap bitmap = media.getFrameAtTime();
            return bitmap;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            if (media != null) {
                media.release();
            }
        }
        return null;
    }

    public static Bitmap getNetVideoFirstFrame(String netVideoFilePath) {
        if (TextUtils.isEmpty(netVideoFilePath)) {
            throw new RuntimeException("Invalid Net Video File Path!");
        }
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        try {
            media.setDataSource(netVideoFilePath, new HashMap());
            Bitmap bitmap = media.getFrameAtTime();
            return bitmap;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            if (media != null) {
                media.release();
            }
        }
        return null;
    }
}
