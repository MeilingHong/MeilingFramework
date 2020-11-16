package com.meiling.framework.network.api;


import com.meiling.framework.network.config.ApiConfig;
import com.meiling.framework.network.config.DefaultX509TrustManager;
import com.meiling.framework.network.convert.gson.GsonFactory;
import com.meiling.framework.network.interceptor.LogInterceptor;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    /**
     * 目标：通过Config配置
     * <p>
     * 同时提供一个方法构建指定的的
     */
    private Retrofit retrofit;

    private static ApiConfig apiConfig;
    private static final int DEFAULT_TIMEOUT = 30;
    private static final String baseUrl = "http://www.baidu.com";

    //
    public API(ApiConfig config) {
        if (config == null) {
            initAPIConfig();
        } else {
            this.apiConfig = config;
        }
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //当配置中存在拦截器时，添加拦截器
        if (apiConfig != null && apiConfig.getInterceptorList() != null && apiConfig.getInterceptorList().size() > 0) {
            List<Interceptor> list = apiConfig.getInterceptorList();
            for (Interceptor interceptor : list) {
                httpClientBuilder.addInterceptor(interceptor);
            }
        }
        //设置超时时间
        if (apiConfig != null) {
            httpClientBuilder.connectTimeout(apiConfig.getConnectTimeout(), TimeUnit.SECONDS);
            httpClientBuilder.readTimeout(apiConfig.getReadTimeout(), TimeUnit.SECONDS);
            httpClientBuilder.writeTimeout(apiConfig.getWriteTimeout(), TimeUnit.SECONDS);
        } else {
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        //
        if (apiConfig != null && apiConfig.getIgnoreHostnameVerifier() != null) {
            httpClientBuilder.hostnameVerifier(apiConfig.getIgnoreHostnameVerifier());
        }
        //
        if (apiConfig != null && apiConfig.getSslcontext() != null) {
            httpClientBuilder.sslSocketFactory(apiConfig.getSslcontext().getSocketFactory());
        }
        //
        if (apiConfig != null && apiConfig.getLegacyTls() != null) {
            httpClientBuilder.connectionSpecs(Arrays.asList(apiConfig.getLegacyTls(), ConnectionSpec.CLEARTEXT));
        }
        //
        if (apiConfig != null) {
            httpClientBuilder.retryOnConnectionFailure(apiConfig.isRetryOnFail());
        } else {
            httpClientBuilder.retryOnConnectionFailure(true);
        }

        httpClientBuilder.followRedirects(false);

        httpClientBuilder.followSslRedirects(false);

        Retrofit.Builder builder = new Retrofit.Builder();
        if (apiConfig != null && apiConfig.getBaseUrl() != null) {
            builder.baseUrl(apiConfig.getBaseUrl());
        } else {
            builder.baseUrl(baseUrl);
        }

        //请求相应参数格式：json,String等在这里进行控制
        if (apiConfig != null && apiConfig.getConverterFactory() != null) {
            builder.addConverterFactory(apiConfig.getConverterFactory());
        }
        //默认AdapterFactory
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        builder.client(httpClientBuilder.build());

        retrofit = builder.build();
    }

    private static void initAPIConfig() {
        SSLContext sslcontext = null;
        try {
            //支持Https链接
            sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{new DefaultX509TrustManager()}, new java.security.SecureRandom());

            HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
                public boolean verify(String s, SSLSession sslsession) {
                    return true;
                }
            };

            List<CipherSuite> cipherSuites = new ArrayList<>();
            cipherSuites.addAll(ConnectionSpec.MODERN_TLS.cipherSuites());
            cipherSuites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA);
            cipherSuites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA);
            ConnectionSpec legacyTls = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .cipherSuites(cipherSuites.toArray(new CipherSuite[0]))
                    .build();

            //构建一个gson的转换工厂
            Converter.Factory factory = GsonConverterFactory.create(GsonFactory.build());

            apiConfig = new ApiConfig.Builder().
                    addInterceptor(new LogInterceptor(true)).
                    setConnectionTimeout(DEFAULT_TIMEOUT).
                    setReadTimeout(DEFAULT_TIMEOUT).
                    setWriteTimeout(DEFAULT_TIMEOUT).
                    setSSLContext(sslcontext).
                    setHostnameVerifier(ignoreHostnameVerifier).
                    setConnectionSpec(legacyTls).
                    setRetryStrategy(true).
                    setConverterFactory(factory).
                    build();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public Object getApiService(Class tClass) {
        return retrofit.create(tClass);
    }

}
