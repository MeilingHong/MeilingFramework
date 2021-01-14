package com.meiling.framework.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.app.mvp.presenter.AppVersionPresenter;
import com.meiling.framework.base_activity.BaseActivity;
import com.meiling.framework.common_util.log.Ulog;
import com.obs.services.ObsClient;
import com.obs.services.model.CompleteMultipartUploadResult;
import com.obs.services.model.ObjectMetadata;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;

public class ObsCheckActivity extends BaseActivity {

    private TextView click;
    private ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

    @Override
    public void configActivity() {
        isPortraitMode = true;
        isFullScreenMode = false;
        isStatusBarFontColorWhite = false;
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void afterSetContentView(@Nullable Bundle savedInstanceState) {
        setActivityNavigationBarColor(getResources().getColor(R.color.color_main));// 确实将导航栏背景色修改成了指定的颜色

        click = findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCheck();
            }
        });
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
        if (singleThreadPool != null) {
            singleThreadPool.shutdown();
            singleThreadPool = null;
        }
    }

    /*
     ****************************************************************************************************************
     * Dialog 显示与使用
     */
    private static final String ak = "";
    private static final String sk = "";
    private static final String endPoint = "";

    private void doCheck() {
        singleThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final ObsClient obsClient = new ObsClient(ak, sk, endPoint);
                    ObjectMetadata metadata = obsClient.getObjectMetadata("prod", "obs_prod");
                    if (metadata != null && metadata.getStatusCode() == 200) {
                        Ulog.e("调用成功" + getResponseMetaInfo(metadata));
                    } else {
                        Ulog.e("调用失败" + getResponseMetaInfo(metadata));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Ulog.e(Ulog.getThrowableStackTrace(e));
                }
            }
        });
    }

    private String getResponseInfo(CompleteMultipartUploadResult putObjectResult) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("【上传】结果：").append("\n");
        if (putObjectResult != null) {
            if (putObjectResult.getResponseHeaders().get("request-id") != null) {
                stringBuilder.append("request-id:").append(putObjectResult.getResponseHeaders().get("request-id").toString()).append("-\n");
            }
            stringBuilder.append("getStatusCode:").append(putObjectResult.getStatusCode()).append("-\n");
            stringBuilder.append(putObjectResult.toString()).append("\n");
            if (putObjectResult.getResponseHeaders() != null) {
                for (String keySet : putObjectResult.getResponseHeaders().keySet()) {
                    stringBuilder.append(keySet).append("----").append(putObjectResult.getResponseHeaders().get(keySet)).append("\n");
                }
            }
        } else {
            stringBuilder.append("返回对象为空");
        }
        return stringBuilder.toString();
    }

    private String getResponseMetaInfo(ObjectMetadata putObjectResult) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("【验证】结果：").append("\n");
        if (putObjectResult != null) {
            if (putObjectResult.getResponseHeaders().get("request-id") != null) {
                stringBuilder.append("request-id:").append(putObjectResult.getResponseHeaders().get("request-id").toString()).append("-\n");
            }
            stringBuilder.append("getStatusCode:").append(putObjectResult.getStatusCode()).append("-\n");
            stringBuilder.append(putObjectResult.toString()).append("\n");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (putObjectResult.getLastModified() != null) {
                stringBuilder.append("getLastModified:").append(simpleDateFormat.format(putObjectResult.getLastModified())).append("\n");
            }
            if (putObjectResult.getResponseHeaders() != null) {
                for (String keySet : putObjectResult.getResponseHeaders().keySet()) {
                    stringBuilder.append(keySet).append("----").append(putObjectResult.getResponseHeaders().get(keySet)).append("\n");
                }
            }
        } else {
            stringBuilder.append("返回对象为空");
        }
        return stringBuilder.toString();
    }

    /*
     ****************************************************************************************************************
     */
}