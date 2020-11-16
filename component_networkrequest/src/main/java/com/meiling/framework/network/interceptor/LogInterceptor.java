package com.meiling.framework.network.interceptor;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LogInterceptor implements Interceptor {
    private static final String TAG = "_AndroidRuntime";
    private boolean isNeedShowLog = false;
    private static Gson mGson = new Gson();

    public LogInterceptor(boolean isNeedShowLog) {
        this.isNeedShowLog = isNeedShowLog;
    }

    //辅助拦截用户的网络请求
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (isNeedShowLog) {
            Request request = chain.request();
            Response response = chain.proceed(chain.request());
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();

            //请求参数的打印最好在转换RequestBody时进行，RequestBody本身没有存储请求参数信息，相关信息在实现子类中保存

            i("---URL--->\n" + request.url().toString() +
                    "\n---RequestMethod--->\n" + request.method() +
                    "\n---RequestBody--->\n" + mGson.toJson(request.body()) +
                    "\n---Content-Type--->\n" + mediaType.toString() +
                    "\n---Response--->\n" + content);

            //ResponseBody ,其中的string();只可调用一次,调用完毕会执行closed相关方法.再调用会报错, 切记!!
            //所以这里不能够直接返回response
            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        } else {
            return chain.proceed(chain.request());
        }
    }

    private static final int LENGTH_LIMIT = 2000;

    public static void i(String msg) {
        if (msg != null && msg.length() > 0) {
            if (msg.length() > LENGTH_LIMIT) {
                int chunkCount = msg.length() / LENGTH_LIMIT;     // integer division
                for (int i = 0; i <= chunkCount; i++) {
                    int max = LENGTH_LIMIT * (i + 1);
                    if (max >= msg.length()) {
                        Log.i(TAG, msg.substring(LENGTH_LIMIT * i));
                    } else {
                        Log.i(TAG, msg.substring(LENGTH_LIMIT * i, max));
                    }
                }
            } else {
                Log.i(TAG, msg.toString());
            }
        }
    }

    public static void i(Object obj) {
        String msg = mGson.toJson(obj);
        if (msg != null && msg.length() > 0) {
            if (msg.length() > LENGTH_LIMIT) {
                int chunkCount = msg.length() / LENGTH_LIMIT;     // integer division
                for (int i = 0; i <= chunkCount; i++) {
                    int max = LENGTH_LIMIT * (i + 1);
                    if (max >= msg.length()) {
                        Log.i(TAG, msg.substring(LENGTH_LIMIT * i));
                    } else {
                        Log.i(TAG, msg.substring(LENGTH_LIMIT * i, max));
                    }
                }
            } else {
                Log.i(TAG, msg.toString());
            }
        }
    }
}
