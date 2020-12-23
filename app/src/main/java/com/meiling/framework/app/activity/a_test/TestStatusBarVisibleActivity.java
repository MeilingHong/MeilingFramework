package com.meiling.framework.app.activity.a_test;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.app.widget.status.StatusLayout;
import com.meiling.framework.base_activity.BaseActivity;
import com.meiling.framework.base_activity.util.brightness.BrightnessUtil;
import com.meiling.framework.common_util.web.MyWebChromeClient;
import com.meiling.framework.common_util.web.MyWebViewClient;
import com.meiling.framework.common_util.web.WebSetting;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;


public class TestStatusBarVisibleActivity extends BaseActivity {
    @BindView(R.id.slComponentActionBarStatus)
    StatusLayout slComponentActionBarStatus;
    @BindView(R.id.ivComponentActionBarBack)
    ImageView ivComponentActionBarBack;
    @BindView(R.id.tvComponentActionBarTitle)
    TextView tvComponentActionBarTitle;
    @BindView(R.id.tvComponentActionBarRightTextMenu)
    TextView tvComponentActionBarRightTextMenu;
    @BindView(R.id.userAgreementPb)
    ProgressBar userAgreementPb;
    @BindView(R.id.userAgreementWv)
    WebView userAgreementWv;

    @BindView(R.id.cover)
    View cover;

    /**
     * 1、使用固定的URL地址
     * <p>
     * 2、通过接口配置URL地址加载
     */
    private String baseUrl;

    @Override
    public void configActivity() {
        setConfigHasStatusBarWithWhiteFont();
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_test_status_bar_visible;
    }

    @Override
    public void afterSetContentView(@Nullable Bundle savedInstanceState) {
        initTitle();
        initParams();
        initWebView();
        loadUrl();
    }

    @Override
    public void releaseAfterDestroy() {
        if (userAgreementWv != null) {
            ViewParent parent = userAgreementWv.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(userAgreementWv);
            }
            userAgreementWv.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            userAgreementWv.getSettings().setJavaScriptEnabled(false);
            userAgreementWv.clearHistory();
            userAgreementWv.clearView();
            userAgreementWv.removeAllViews();
            userAgreementWv.destroy();
        }
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void lasyloadCall() {

    }

    private void initTitle() {
        if (tvComponentActionBarTitle != null) {
            tvComponentActionBarTitle.setText("标题");
            tvComponentActionBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tvComponentActionBarTitle.getPaint().setFakeBoldText(true);
            tvComponentActionBarTitle.setTextColor(getResources().getColor(R.color.color_white));
        }
        if (tvComponentActionBarRightTextMenu != null) {
            tvComponentActionBarRightTextMenu.setVisibility(View.VISIBLE);
            tvComponentActionBarRightTextMenu.setText("菜单");
            tvComponentActionBarRightTextMenu.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tvComponentActionBarRightTextMenu.setTextColor(getResources().getColor(R.color.color_white));
        }
    }

    private void initParams() {
//        baseUrl = getIntent() != null ? getIntent().getStringExtra(IntentParamsKeys.url) : "";
//        if (TextUtils.isEmpty(baseUrl)) {
//            baseUrl = String.format(BaseConstant.user_agreement, EvnGetter.getUrlBase(this));
//        }
//        if (!baseUrl.toLowerCase().startsWith(BaseConstant.http)) {
//            showHintCenterOrderMsg(getString(R.string.explorer_url_error));
//            finish();//
//        }
        baseUrl = "https://www.baidu.com";
    }

    private void initWebView() {
        if (userAgreementWv != null) {
            new WebSetting(userAgreementWv, this, false);
            userAgreementWv.setWebChromeClient(new MyWebChromeClient(this, userAgreementPb, null));
            userAgreementWv.setWebViewClient(new MyWebViewClient(this));
        }
    }

    private void loadUrl() {
        if (userAgreementWv != null) {
            userAgreementWv.loadUrl(baseUrl);
        }
    }

    private void exit() {
        if (userAgreementWv != null && userAgreementWv.canGoBack()) {
            userAgreementWv.goBack();
        } else {
            finish();
        }
    }

    @OnClick({R.id.ivComponentActionBarBack,
            R.id.tvComponentActionBarRightTextMenu,
            R.id.cover,
            R.id.tvComponentActionBarTitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivComponentActionBarBack: {
                exit();
                break;
            }
            case R.id.tvComponentActionBarTitle: {
                // 亮度调到最小，标题栏字体变成黑色，黑色遮罩
                setMinLight();
                setStatusBarInvisible();
                cover.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.tvComponentActionBarRightTextMenu: {
                break;
            }
            case R.id.cover: {
                // 亮度恢复正常，字体颜色恢复成预设值，关闭黑色遮罩
                setNormalLight();
                setStatusBarVisible();
                cover.setVisibility(View.GONE);
                break;
            }
        }
    }

    private int brightnessValue = 0;

    // 设置最低亮度
    private void setMinLight() {
        brightnessValue = BrightnessUtil.getWindowBrightness(this);// 获取当前的亮度值
        BrightnessUtil.setWindowBrightness(this, 0);// 设置最小亮度
    }

    // 还原当前亮度
    private void setNormalLight() {
        BrightnessUtil.setWindowBrightness(this, brightnessValue);// 恢复之前的亮度值
    }
}
