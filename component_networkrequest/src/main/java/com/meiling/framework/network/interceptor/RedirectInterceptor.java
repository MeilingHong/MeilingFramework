package com.meiling.framework.network.interceptor;


import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RedirectInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        int code = response.code();
        if (code == 307) {
            //获取重定向的地址
            String location = response.headers().get("Location");
            Log.e("_AndroidRuntime",location);
            //重新构建请求
            Request newRequest = request.newBuilder()
                    .url(location)
                    .headers(request.headers())
//                    .post(request.body())
                    .build();
            response = chain.proceed(newRequest);
        }
        return response;
    }
}
