package com.meiling.framework.app.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiling.framework.app.R;
import com.meiling.framework.base_activity.BaseActivity;

import androidx.annotation.Nullable;

public class MultiPointerTouchActivity extends BaseActivity {

    private TextView click;

    @Override
    public void configActivity() {
        isPortraitMode = true;
        isFullScreenMode = false;
        isStatusBarFontColorWhite = false;
    }

    /*

      1、线程池，在提交任务上，比异步任务好，但是，在任务执行顺序，与执行时机上我发进行管理，在数据的线程切换上也需要自己进行管理；
      2、异步任务在单任务执行上
     */
    private AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    };

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void afterSetContentView(@Nullable Bundle savedInstanceState) {
        setActivityNavigationBarColor(getResources().getColor(R.color.color_3296fa));// 确实将导航栏背景色修改成了指定的颜色

        click = findViewById(R.id.click);
        click.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                try {
                    if (event.getPointerCount() >= 2) {// 多点触摸事件
                        int index = event.getActionIndex();// 获取当前时间的下标序号
                        switch (event.getActionMasked()) {
                            case MotionEvent.ACTION_POINTER_DOWN:
                                // 判断是否是第2个手指按下
                                break;
                            case MotionEvent.ACTION_POINTER_UP:
                                // 判断抬起的手指是否是第2个
                                break;
                            case MotionEvent.ACTION_MOVE:
                                if (event != null && event.getPointerCount() >= 2) {// 判断当前事件中含有2个或以上手指进行操作
                                    // todo 由于多点触摸处理的特殊性，在处理一个多点事件时，
                                    //  存在由于手指离开导致的指针下标导致的数组越界异常，
                                    //  需要catch住异常，并忽略掉，这个异常的事件
                                }
                                break;
                        }
                    } else {// 单点触摸事件
                        // todo 单点触摸事件则不需要考虑指针下标越界的问题，可以直接进行处理
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                break;
                            }
                            case MotionEvent.ACTION_MOVE: {
                                break;
                            }
                            case MotionEvent.ACTION_UP: {
                                // 当点击时间小于1秒，且移动范围小于指定距离时，执行聚焦操作
                                // 且认定该动作为聚焦的点击事件
                                break;
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }
        });
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    // todo Glide加载图片的问题，对于旧手机，在加载时可能会出现显示混乱的问题【猜测是内存缓存、磁盘缓存满了引起的问题】

    @Override
    public void releaseAfterDestroy() {

    }

    /*
     ****************************************************************************************************************
     */
}