package com.meiling.framework.app.utils.glide;
/**
 * Created by marisareimu@126.com on 2020-12-21  18:57
 * project Ubanquan
 */

public interface ProgressListener {
    void onStart();
    void onProgress(int progress);
    void onFailure(Exception e);
    void onSuccess();
}
