package com.meiling.framework.app.utils.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.meiling.framework.common_util.log.Ulog;

public class GlideUtil {
    /**
     * 该工具栏依赖第三方，暂时不进行实现
     */
//    /**
//     * 不使用加载动画（更快速的加载图片）
//     *
//     * @param mContext
//     * @param imagePath
//     * @param drawableError
//     * @param imageView
//     */
//    public static void loadDontAnimate(Context mContext, String imagePath, @DrawableRes int drawableError, ImageView imageView) {
//        Glide.with(mContext).load(imagePath).error(drawableError).dontAnimate().into(imageView);
//    }
//
//    public static void loadDontAnimate(BaseActivity mContext, String imagePath, @DrawableRes int drawableError, ImageView imageView) {
//        Glide.with(mContext).load(imagePath)/*.diskCacheStrategy(DiskCacheStrategy.SOURCE)*/.error(drawableError).dontAnimate().into(imageView);
//    }
//
//    public static void loadDontAnimate(BaseActivity mContext, int resId, @DrawableRes int drawableError, ImageView imageView) {
//        Glide.with(mContext).load(resId).error(drawableError).dontAnimate().into(imageView);
//    }

    /**
     * 需要显示进度的图片加载
     *
     * @param mContext
     * @param imagePath
     * @param progressListener
     * @param imageView
     */
    public static void loadNetImage(Context mContext, String imagePath, ProgressListener progressListener, ImageView imageView) {
        if (mContext == null || TextUtils.isEmpty(imagePath)) {
            Ulog.e("无效的图片加载参数，请确认参数的有效性!");
            return;
        }
        if (progressListener != null) {
            ProgressInterceptor.addListener(imagePath, progressListener);
        }
        // todo 需要严格注意的点，使用Glide加载图片的View不能够使用setTag方法【源码内部逻辑使用了setTag，否则将造成异常】
        Glide.with(mContext)
                .load(imagePath)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        // 如果需要显示进度的话，在这里开始显示
                        if (progressListener != null) {
                            progressListener.onStart();
                        }
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        ProgressInterceptor.removeListener(imagePath);
                        if (progressListener != null) {
                            progressListener.onSuccess();
                        }
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        ProgressInterceptor.removeListener(imagePath);
                        // 图片加载失败，向上抛出异常，方便后续
                        if (progressListener != null) {
                            progressListener.onFailure(e);
                        }
                    }
                });
    }

    public static void loadLocalImage(Context mContext, String imagePath, ImageView imageView) {
        if (mContext == null || TextUtils.isEmpty(imagePath)) {
            Ulog.e("无效的图片加载参数，请确认参数的有效性!");
            return;
        }
        // todo 需要严格注意的点，使用Glide加载图片的View不能够使用setTag方法【源码内部逻辑使用了setTag，否则将造成异常】
        Glide.with(mContext)
                .load(imagePath)
                .dontAnimate()
                .into(imageView);
    }
}
