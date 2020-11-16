package com.meiling.framework.common_util.handler;


import android.os.Handler;

/**
 * 清除Handler当前消息队列中的全部消息
 */
public class HandlerReleaseUtil {
    public static void release(Handler handler){
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);//清除所有的已存在信息
        }
    }
}
