package com.meiling.framework.app.activity;

import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.base_activity.BaseActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class FragmentSwipeActivity extends BaseActivity {

    private HorizontalScrollView swipe;
    private LinearLayout swipeContainer;
    private TextView fragment1;
    private TextView fragment2;
    private TextView fragment3;
    private TextView fragment4;
    private ViewPager myViewPager;
    @Override
    public void configActivity() {
        isPortraitMode = true;
        isFullScreenMode = false;
        isStatusBarFontColorWhite = false;
    }


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_fragment_swipe;
    }

    @Override
    public void afterSetContentView(@Nullable Bundle savedInstanceState) {
        setActivityNavigationBarColor(getResources().getColor(R.color.color_3296fa));// 确实将导航栏背景色修改成了指定的颜色
        swipe = findViewById(R.id.swipe);
        swipeContainer = findViewById(R.id.swipeContainer);
        myViewPager = findViewById(R.id.myViewPager);

        fragment1 = findViewById(R.id.fragment1);
        fragment2 = findViewById(R.id.fragment2);
        fragment3 = findViewById(R.id.fragment3);
        fragment4 = findViewById(R.id.fragment4);
    }

    // todo Glide加载图片的问题，对于旧手机，在加载时可能会出现显示混乱的问题【猜测是内存缓存、磁盘缓存满了引起的问题】

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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // 当由于崩溃导致页面恢复时，可能会存在问题，如果Fragment内又有Fragment时，可能会出现Fragment聂的Fragment无法显示的问题
        super.onSaveInstanceState(outState);
    }
}