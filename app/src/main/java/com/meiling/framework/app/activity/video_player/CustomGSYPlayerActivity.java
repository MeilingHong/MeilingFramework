package com.meiling.framework.app.activity.video_player;
/**
 * Created by marisareimu@126.com on 2021-01-18  11:10
 * project MeilingFramework
 */

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.app.widget.player.CustomGSYVideoPlayer;
import com.meiling.framework.base_activity.BaseActivity;
import com.meiling.framework.common_util.log.Ulog;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import androidx.annotation.Nullable;

/**
 * 用于自定义播放器UI
 * <p>
 * 如需要自定义可通过继承播放器后，返回自定义布局或重载接口实现
 * <p>
 * https://github.com/CarGuo/GSYVideoPlayer/blob/master/app/src/main/java/com/example/gsyvideoplayer/video/SampleCoverVideo.java
 */
public class CustomGSYPlayerActivity extends BaseActivity {

    private TextView transform;
    private TextView rotation;
    private TextView rotationValue;
    private CustomGSYVideoPlayer videoPlayer;
    private OrientationUtils orientationUtils;

    @Override
    public void configActivity() {
        setConfigHasStatusBarWithWhiteFont();
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_custom_gsyplayer;
    }

    /**
     * 1、缓存文件
     * 2、
     */
    @Override
    public void afterSetContentView(@Nullable Bundle savedInstanceState) {
        videoPlayer = (CustomGSYVideoPlayer) findViewById(R.id.video_player);
        transform = findViewById(R.id.transform);
        transform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoPlayer != null) {
                    videoPlayer.transform();
                    switch (videoPlayer.getTransformSize()) {
                        case 0: {
                            if (transform != null) transform.setText("旋转镜像");
                            break;
                        }
                        case 1: {
                            if (transform != null) transform.setText("左右镜像");
                            break;
                        }
                        case 2: {
                            if (transform != null) transform.setText("上下镜像");
                            break;
                        }
                    }
                }
            }
        });

        rotation = findViewById(R.id.rotation);
        rotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoPlayer != null) {
                    videoPlayer.rotation();
                }
            }
        });
        rotationValue = findViewById(R.id.rotationValue);
        rotationValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoPlayer != null) {
                    Ulog.w("当前画面旋转角度:" + videoPlayer.getVideoRotation());// todo 从值看，这个值就是视频拍摄时旋转的角度
                }
            }
        });
        // 当文件较大（3:00:00）  2GB以上
        String source1 = "https://test-ubq.obs.cn-south-1.myhuaweicloud.com/obs_788805985600001610170926_pre?AccessKeyId=KEYCR8EAOKDERNDWC5UL&Expires=1611585592&Signature=tUanT5yaSLY82CXuHyURmwnzlU8%3D";
        videoPlayer.setUp(source1, false, "测试视频");// 大文件情况下，不能够使用缓冲方式进行预览，否则将会出错
//        videoPlayer.setUp(source1, true, "测试视频");//大文件，该参数似乎无法进行预览
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videoPlayer.startPlayLogic();
    }

    @Override
    public void releaseAfterDestroy() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void lasyloadCall() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}
