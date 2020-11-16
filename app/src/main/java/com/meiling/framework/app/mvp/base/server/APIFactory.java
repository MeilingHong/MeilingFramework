package com.meiling.framework.app.mvp.base.server;


import com.meiling.framework.network.api.API;
import com.meiling.framework.network.config.ApiConfig;
import com.meiling.framework.network.config.DefaultX509TrustManager;
import com.meiling.framework.network.convert.gson.GsonFactory;
import com.meiling.framework.network.interceptor.HostReplaceInterceptor;
import com.meiling.framework.network.interceptor.LogInterceptor;
import com.meiling.framework.network.interceptor.RedirectInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;

public class APIFactory {
    private static final int DEFAULT_TIMEOUT = 30;
    private static Map<String, String> hostReplaceMapping;
    private API api;

    private APIFactory() {
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{new DefaultX509TrustManager()}, new java.security.SecureRandom());

            HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession sslsession) {
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

            ApiConfig config = new ApiConfig.Builder().
                    addInterceptor(new HostReplaceInterceptor(hostReplaceMapping)).
                    addInterceptor(new RedirectInterceptor()).//
                    addInterceptor(new LogInterceptor(true)).
                    setConnectionTimeout(DEFAULT_TIMEOUT).
                    setReadTimeout(DEFAULT_TIMEOUT).
                    setWriteTimeout(DEFAULT_TIMEOUT).
                    setSSLContext(sslcontext).
                    setHostnameVerifier(ignoreHostnameVerifier).
                    setConnectionSpec(legacyTls).
                    setRetryStrategy(true).
                    setConverterFactory( com.meiling.framework.network.convert.gson.GsonConverterFactory.create(GsonFactory.build())).
                    build();
            api = new API(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static API getApiInstances() {
        return APIFactoryHolder.factory.api;
    }

    /**
     * @param hostReplaceMapping
     */
    public static void setHostReplaceMapping(Map<String, String> hostReplaceMapping) {
        APIFactory.hostReplaceMapping = hostReplaceMapping;
    }

    public static void resetHostReplaceMapping(String key, String value) {
        APIFactory.hostReplaceMapping.put(key, value);
    }

    private static class APIFactoryHolder {
        private static APIFactory factory = new APIFactory();
    }
}
