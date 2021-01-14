package com.meiling.framework.app.activity;
/**
 * Created by marisareimu@126.com on 2021-01-14  15:58
 * project MeilingFramework
 */

import android.os.Bundle;

import com.meiling.framework.base_activity.BaseActivity;

import androidx.annotation.Nullable;

/**
 * Created by huangzhou@ulord.net on 2021-01-14  15:58
 * project MeilingFramework
 */
public class CameraActivity extends BaseActivity {
    @Override
    public void configActivity() {
        setConfigHasStatusBarWithBlackFont();
    }

    @Override
    public int getContentViewLayoutId() {
        return 0;
    }

    @Override
    public void afterSetContentView(@Nullable Bundle savedInstanceState) {

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
