package com.meiling.framework.app.activity.camera;
/**
 * Created by marisareimu@126.com on 2021-01-14  15:58
 * project MeilingFramework
 */

import android.os.Bundle;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.meiling.framework.app.R;
import com.meiling.framework.app.base.IntentParameterKeys;
import com.meiling.framework.app.utils.glide.GlideUtil;
import com.meiling.framework.base_activity.BaseActivity;

import androidx.annotation.Nullable;

/**
 * Created by huangzhou@ulord.net on 2021-01-14  15:58
 * project MeilingFramework
 */
public class CameraPreviewLocalImageActivity extends BaseActivity {
    private PhotoView previewImage;
    private String mLocalPath;

    @Override
    public void configActivity() {
        setConfigHasStatusBarWithBlackFont();
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_local_image_preview;
    }

    @Override
    public void afterSetContentView(@Nullable Bundle savedInstanceState) {
        initParameter();
        previewImage = findViewById(R.id.previewImage);
        previewImage.enable();// 允许进行缩放
        GlideUtil.loadLocalImage(this, mLocalPath, previewImage);
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

    private void initParameter() {
        mLocalPath = getIntent().getStringExtra(IntentParameterKeys.image_path);
    }
}
