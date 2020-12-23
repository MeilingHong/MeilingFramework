package com.meiling.framework.app.local_bean.progress;
/**
 * Created by marisareimu@126.com on 2020-12-23  17:06
 * project Ubanquan
 */

import com.meiling.framework.common_util.gson.BeanToString;

public class ProgressMessage extends BeanToString {
    private long currentSize;
    private long totalSize;

    public ProgressMessage(long currentSize, long totalSize) {
        this.currentSize = currentSize;
        this.totalSize = totalSize;
    }

    public long getCurrentSize() {
        return currentSize;
    }

    public long getTotalSize() {
        return totalSize;
    }
}
