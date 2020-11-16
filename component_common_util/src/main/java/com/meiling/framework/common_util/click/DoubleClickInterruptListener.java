package com.meiling.framework.common_util.click;
/**
 * Created by marisareimu@126.com on 2020-11-16  11:47
 * project MeilingFramework
 */

import android.view.View;

/**
 *
 */
public class DoubleClickInterruptListener implements View.OnClickListener {
    private int INTERVAL_MS = 2000;
    private long current = 0;
    private View.OnClickListener clickListener;

    public DoubleClickInterruptListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public DoubleClickInterruptListener(int interval, View.OnClickListener clickListener) {
        if (interval > 800) {// 限制允许的最小间隔时间为0.8秒
            INTERVAL_MS = interval;
        } else {
            INTERVAL_MS = 800;
        }
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if (current <= 0) {
            current = System.currentTimeMillis();
            if (clickListener != null) {
                clickListener.onClick(v);
            }
        } else if ((System.currentTimeMillis() - current) >= INTERVAL_MS) {
            current = System.currentTimeMillis();
            if (clickListener != null) {
                clickListener.onClick(v);
            }
        } else {
            // 尚处于屏蔽时间内，不响应用户的点击
        }
    }

    public void doClick() {
        if (current <= 0) {
            current = System.currentTimeMillis();
            if (clickListener != null) {
                clickListener.onClick(null);
            }
        } else if ((System.currentTimeMillis() - current) >= INTERVAL_MS) {
            current = System.currentTimeMillis();
            if (clickListener != null) {
                clickListener.onClick(null);
            }
        } else {
            // 尚处于屏蔽时间内，不响应用户的点击
        }
    }
}
