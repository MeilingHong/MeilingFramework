package com.meiling.framework.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.app.adatper.viewpager.TabFragmentPagerAdapter;
import com.meiling.framework.app.fragment.Text1Fragment;
import com.meiling.framework.app.fragment.Text2Fragment;
import com.meiling.framework.app.fragment.Text3Fragment;
import com.meiling.framework.app.fragment.Text4Fragment;
import com.meiling.framework.base_activity.BaseActivity;
import com.meiling.framework.base_activity.BaseFragment;

import java.util.ArrayList;

import androidx.annotation.ColorInt;
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
        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelectText(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fragment1 = findViewById(R.id.fragment1);
        fragment2 = findViewById(R.id.fragment2);
        fragment3 = findViewById(R.id.fragment3);
        fragment4 = findViewById(R.id.fragment4);

        ArrayList<BaseFragment> list = new ArrayList<>();
        list.add(new Text1Fragment());
        list.add(new Text2Fragment());
        list.add(new Text3Fragment());
        list.add(new Text4Fragment());
        TabFragmentPagerAdapter mAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list);
        myViewPager.setAdapter(mAdapter);
        myViewPager.setCurrentItem(0);  //初始化显示第一个页面
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
//        super.onSaveInstanceState(outState);

        // 这种
        super.onSaveInstanceState(new Bundle());
    }

    private void setSelectText(int position) {
        setTextColor(fragment1, Color.parseColor(position == 0 ? "#3296fa" : "#999999"));
        setTextColor(fragment2, Color.parseColor(position == 1 ? "#3296fa" : "#999999"));
        setTextColor(fragment3, Color.parseColor(position == 2 ? "#3296fa" : "#999999"));
        setTextColor(fragment4, Color.parseColor(position == 3 ? "#3296fa" : "#999999"));
    }

    private void setTextContent(TextView textView, String content) {
        if (textView != null) {
            textView.setText(!TextUtils.isEmpty(content) ? content : "无");
        }
    }

    private void setTextColor(TextView textView, @ColorInt int color) {
        if (textView != null) {
            textView.setTextColor(color);
        }
    }
}