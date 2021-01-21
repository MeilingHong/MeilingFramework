package com.meiling.framework.app.activity.video_player;
/**
 * Created by marisareimu@126.com on 2021-01-18  11:10
 * project MeilingFramework
 */

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.meiling.framework.app.R;
import com.meiling.framework.base_activity.BaseActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import androidx.annotation.Nullable;

/**
 * 用于自定义播放器UI
 *
 * https://github.com/CarGuo/GSYVideoPlayer/blob/master/app/src/main/java/com/example/gsyvideoplayer/video/SampleCoverVideo.java
 */
public class CustomGSYPlayerActivity extends BaseActivity {

    StandardGSYVideoPlayer videoPlayer;

    OrientationUtils orientationUtils;

    @Override
    public void configActivity() {
        setConfigHasStatusBarWithWhiteFont();
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_gsyplayer;
    }

    @Override
    public void afterSetContentView(@Nullable Bundle savedInstanceState) {
        videoPlayer =  (StandardGSYVideoPlayer)findViewById(R.id.video_player);

        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        videoPlayer.setUp(source1, true, "测试视频");

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
