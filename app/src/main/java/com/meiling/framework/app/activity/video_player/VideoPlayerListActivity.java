package com.meiling.framework.app.activity.video_player;
/**
 * Created by marisareimu@126.com on 2021-01-14  15:58
 * project MeilingFramework
 */

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.base_activity.BaseActivity;

import androidx.annotation.Nullable;

/**
 * Created by huangzhou@ulord.net on 2021-01-14  15:58
 * project MeilingFramework
 */
public class VideoPlayerListActivity extends BaseActivity {

    private TextView systemCamera;
    private TextView camera1;
    private TextView camera2;
    private TextView timepicker;

    @Override
    public void configActivity() {
        setConfigHasStatusBarWithBlackFont();
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    public void afterSetContentView(@Nullable Bundle savedInstanceState) {
        initComponent();
    }

    private void initComponent() {
        systemCamera = findViewById(R.id.systemCamera);
        systemCamera.setText("简单播放器");
        systemCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        camera1 = findViewById(R.id.camera1);
        camera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        camera2 = findViewById(R.id.camera2);
        camera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        timepicker = findViewById(R.id.timepicker);
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
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

}
