package com.meiling.framework.app.utils.file_size;

import android.content.Context;
import android.widget.TextView;

import com.meiling.framework.app.R;


/**
 * Created by marisareimu@126.com on 2020-12-21  20:22
 * project Ubanquan
 */
public class FileSizeUtil {
    /**
     *
     *
     * @param mContext
     * @param tvSize
     * @param size todo Size 单位为KB，进行单位计算
     */
    public static void setFileSize_WithOutSpace(Context mContext, TextView tvSize, double size) {
        if (tvSize != null) {
            double sizeMB = size / 1024.0f;//避免为空的处理
            double sizeGB = size / (1024.0f * 1024);//避免为空的处理
            if (size < 0.01) {// KB 小于0.01 ,单位显示为B
                tvSize.setText(mContext.getString(R.string.size_unit_byte, (int) (size * 1024)));
            } else {// KB 小于0.01 ,单位显示为B
                if (sizeMB < 1) {// 小于1MB，单位显示为KB
                    tvSize.setText(mContext.getString(R.string.size_unit_kb_2, size));
                } else if (sizeGB < 1) {// 小于1GB，单位显示为MB
                    tvSize.setText(mContext.getString(R.string.size_unit_mb_2, sizeMB));
                } else {// 大于1GB，单位显示为GB
                    tvSize.setText(mContext.getString(R.string.size_unit_gb_2, sizeGB));
                }
            }
        }
    }

    /**
     *
     *
     * @param mContext
     * @param tvSize
     * @param size todo Size 单位为B，进行单位计算
     */
    public static void setFileSize_WithOutSpace(Context mContext, TextView tvSize, long size) {
        if (tvSize != null) {
            double sizeKB = size / 1024.0f;//避免为空的处理
            double sizeMB = size / (1024.0f * 1024);//避免为空的处理
            double sizeGB = size / (1024.0f * 1024 * 1024);//避免为空的处理
            if (sizeKB < 0.01) {// KB 小于0.01 ,单位显示为B
                tvSize.setText(mContext.getString(R.string.size_unit_byte, size));
            } else {// KB 小于0.01 ,单位显示为B
                if (sizeMB < 1) {// 小于1MB，单位显示为KB
                    tvSize.setText(mContext.getString(R.string.size_unit_kb_2, sizeKB));
                } else if (sizeGB < 1) {// 小于1GB，单位显示为MB
                    tvSize.setText(mContext.getString(R.string.size_unit_mb_2, sizeMB));
                } else {// 大于1GB，单位显示为GB
                    tvSize.setText(mContext.getString(R.string.size_unit_gb_2, sizeGB));
                }
            }
        }
    }
}
