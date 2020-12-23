package com.meiling.framework.app.activity.a_test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.meiling.framework.app.R;
import com.meiling.framework.app.utils.permission.PermissionHelper;
import com.meiling.framework.app.utils.permission.PermissionType;
import com.meiling.framework.app.widget.status.StatusLayout;
import com.meiling.framework.base_activity.BaseActivity;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class TestCallSystemActivity extends BaseActivity {

    @BindView(R.id.slComponentActionBarStatus)
    StatusLayout slComponentActionBarStatus;
    @BindView(R.id.ivComponentActionBarBack)
    ImageView ivComponentActionBarBack;
    @BindView(R.id.tvComponentActionBarTitle)
    TextView tvComponentActionBarTitle;
    @BindView(R.id.tvComponentActionBarRightTextMenu)
    TextView tvComponentActionBarRightTextMenu;
    @BindView(R.id.ivComponentActionBarRightImageMenu)
    ImageView ivComponentActionBarRightImageMenu;
    @BindView(R.id.callPhone)
    TextView callPhone;
    @BindView(R.id.sendMail)
    TextView sendMail;
    @BindView(R.id.netExplorer)
    TextView netExplorer;

    @Override
    public void configActivity() {
        setConfigHasStatusBarWithWhiteFont();
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_test_system_call;
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
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void lasyloadCall() {

    }

    private void initTitle() {
        if (tvComponentActionBarTitle != null) {
            tvComponentActionBarTitle.setText("");
            tvComponentActionBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tvComponentActionBarTitle.getPaint().setFakeBoldText(true);
            tvComponentActionBarTitle.setTextColor(getResources().getColor(R.color.color_white));
        }
    }

    private void initParams() {
    }

    private void initWebView() {
    }

    private void loadUrl() {
    }

    private void exit() {
        finish();
    }

    @OnClick({R.id.ivComponentActionBarBack,
            R.id.callPhone, R.id.sendMail, R.id.netExplorer,
            R.id.tvComponentActionBarTitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivComponentActionBarBack: {
                exit();
                break;
            }
            case R.id.tvComponentActionBarTitle: {
                break;
            }
            case R.id.callPhone: {
                PermissionHelper permissionHelper = new PermissionHelper();
                permissionHelper.setResult(new PermissionHelper.PermissionResult() {
                    @Override
                    public void success() {
                        // 执行打电话
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + "13212345678"));
                        startActivity(intent);
                    }
                });
                permissionHelper.requestPermission(this, PermissionType.TYPE_CALL_PHONE);
                break;
            }
            case R.id.sendMail: {
                // 调用系统的Email，需要用户手动发送
//                String[] reciver = new String[]{getString(R.string.email_address)};
//                String[] mySubject = new String[]{getString(R.string.subject)};
//                String myCc = getString(R.string.cc);
//                String mybody = getString(R.string.email_content);
//                Intent myIntent = new Intent(Intent.ACTION_SEND);
//                myIntent.setType(getString(R.string.plain_text));
//                myIntent.putExtra(Intent.EXTRA_EMAIL, reciver);
//                myIntent.putExtra(Intent.EXTRA_CC, myCc);
//                myIntent.putExtra(Intent.EXTRA_SUBJECT, mySubject);
//                myIntent.putExtra(Intent.EXTRA_TEXT, mybody);
//                startActivity(Intent.createChooser(myIntent, getString(R.string.send_email)));
                break;
            }
            case R.id.netExplorer: {
                // 跳转到应用外打开链接
//                final Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("https://ubanquan.cn"));
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(Intent.createChooser(intent, getString(R.string.explorer_hint1)));
//                } else {
//                    showHintCenterOrderMsg(getString(R.string.explorer_hint2));
//                }
                break;
            }
        }
    }
}
