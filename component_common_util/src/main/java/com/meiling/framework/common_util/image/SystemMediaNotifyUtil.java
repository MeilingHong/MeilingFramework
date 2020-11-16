package com.meiling.framework.common_util.image;


import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;


import com.meiling.framework.common_util.log.Ulog;

import java.io.File;

public class SystemMediaNotifyUtil {

    private static MediaScannerConnection scanner;

    public static void notifyMdediaNow(Context context, final File file, final String mimeType, final ScanCallback scanCallback) {
        if (scanner == null) {
            MediaScannerConnection.MediaScannerConnectionClient mClient = new MediaScannerConnection.MediaScannerConnectionClient() {
                @Override
                public void onMediaScannerConnected() {
                    Ulog.e(String.format("%s------path:%s", "onMediaScannerConnected", file.getAbsolutePath()));
                    if (scanner != null) {
                        scanner.scanFile(file.getAbsolutePath(), mimeType);
                        Ulog.e("开始扫描指定文件");
                    }
                }

                @Override
                public void onScanCompleted(String path, Uri uri) {
                    if (scanCallback != null) {
                        scanCallback.onScanCompleted(path, uri);
                        Ulog.e("scanCallback");
                    }
                    Ulog.e(String.format("onScanCompleted  path:%s\n%s", path != null ? path : "-null-", uri != null ? uri.toString() : "-null-"));
                    if (scanner != null) {
                        Ulog.e("disconnect");
                        scanner.disconnect();
                        scanner = null;
                    }
                }
            };
            scanner = new MediaScannerConnection(context, mClient);
            scanner.connect();
        }
    }

    public interface ScanCallback {
        void onScanCompleted(String path, Uri uri);
    }
}
