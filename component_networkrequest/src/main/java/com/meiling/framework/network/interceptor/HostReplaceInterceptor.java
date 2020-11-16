package com.meiling.framework.network.interceptor;


import com.meiling.framework.network.config.APIKey;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HostReplaceInterceptor implements Interceptor {
    private Map<String, String> replaceMapping = new HashMap<>();

    /**
     * 当满足replacemapping属性中的Key时，将当次请求的URL替换成映射表中的URL
     *
     * @param replaceMapping
     */
    public HostReplaceInterceptor(Map<String, String> replaceMapping) {
        this.replaceMapping = replaceMapping;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        List<String> headersURL = request.headers(APIKey.URL);
        if (replaceMapping != null && replaceMapping.size() > 0 &&//保证映射表不为空
                headersURL != null && headersURL.size() > 0) {//保证配置的键也不为空
            /**
             * 当存在配置的映射时，将其进行替换
             */
            HttpUrl oldUrl = request.url();//临时保存就请求链
            HttpUrl newUrl = null;
            String key = headersURL.get(0);//前面的优先被替换
            if (replaceMapping.containsKey(key) && replaceMapping.get(key) != null && replaceMapping.get(key).length() > 0) {//保证能够匹配到对应的Key,且保证对应的Key有效
                newUrl = HttpUrl.parse(replaceMapping.get(key));
                HttpUrl finalUrl = oldUrl
                        .newBuilder()
                        .scheme(newUrl.scheme())//更换网络协议
                        .host(newUrl.host())//更换主机名
                        .port(newUrl.port())//更换端口
                        .build();
                //准备构建Url
                Request.Builder builder = request.newBuilder();
                Request newRequest = builder.url(finalUrl).build();
//                LogInterceptor.i(newRequest);
                return chain.proceed(newRequest);
            } else {//
                return chain.proceed(chain.request());
            }
        } else {
            return chain.proceed(chain.request());
        }
    }
}
