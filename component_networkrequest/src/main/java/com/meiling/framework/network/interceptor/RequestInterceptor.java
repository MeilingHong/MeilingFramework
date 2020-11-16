package com.meiling.framework.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {

    private IRequestInterceptorCallback interceptorCallback;

    public RequestInterceptor(IRequestInterceptorCallback interceptorCallback) {
        this.interceptorCallback = interceptorCallback;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(interceptorCallback!=null){
            return interceptorCallback.doReqesutExchange(chain);//走转换的回调
        }else{
            return chain.proceed(chain.request());
        }
    }
}
