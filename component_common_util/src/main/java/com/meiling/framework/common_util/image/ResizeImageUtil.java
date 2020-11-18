package com.meiling.framework.common_util.image;

/**
 *
 *
 * Created by marisareimu@126.com on 2020-02-14  10:21
 * project MeilingFramework
 */

public class ResizeImageUtil {

    /**
     *
     * 计算根据图像比例得到的组件宽度
     *
     * @param realHeight
     * @param imageWidth
     * @param imageHeight
     * @return
     */
    public static int resizeWidth(int realHeight, int imageWidth, int imageHeight) {
        return (int) (1.0f * realHeight * imageWidth / imageHeight);
    }

    /**
     * 计算根据图像比例得到的组件高度
     *
     * @param realWidth
     * @param imageWidth
     * @param imageHeight
     * @return
     */
    public static int resizeHeight(int realWidth, int imageWidth, int imageHeight) {
        return (int) (1.0f * realWidth * imageHeight / imageWidth);
    }
}
