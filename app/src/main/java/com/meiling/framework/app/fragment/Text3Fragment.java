package com.meiling.framework.app.fragment;
/**
 * Created by marisareimu@126.com on 2021-01-07  18:08
 * project MeilingFramework
 */

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.base_activity.BaseFragment;

public class Text3Fragment extends BaseFragment {

    private TextView click;

    @Override
    public void configFragment() {

    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_text_1;
    }

    @Override
    public void initContentView(View root, Bundle savedInstanceState) {
        click = root.findViewById(R.id.click);
        click.setText("Fragment   3");
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void releaseAfterDestroy() {

    }
}
