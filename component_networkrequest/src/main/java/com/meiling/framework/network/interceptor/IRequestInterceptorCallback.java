package com.meiling.framework.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public interface IRequestInterceptorCallback {
    Response doReqesutExchange(Interceptor.Chain chain) throws IOException;
}
