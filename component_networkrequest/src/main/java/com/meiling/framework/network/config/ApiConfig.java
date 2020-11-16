package com.meiling.framework.network.config;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import retrofit2.Converter;

public class ApiConfig {
    //
    /**
     * 例如：替换url的拦截器
     * 请求的日志打印拦截器
     *
     */
    private List<Interceptor> interceptorList;//
    //------------------------------------
    //
    /**
     * 用于支持HTTPS的配置
     */
    private SSLContext sslcontext;//

    private HostnameVerifier ignoreHostnameVerifier;

    private ConnectionSpec legacyTls;
    //------------------------------------
    private String baseUrl;//

    private int connectTimeout = 30;

    private int readTimeout = 30;

    private int writeTimeout = 30;
    //
    private boolean isRetryOnFail = true;
    //------------------------------------
    //自定义数据转换格式类型---由用户指定数据格式的转换类型【String/JSON/XML】
    private Converter.Factory converterFactory;

    private ApiConfig(){

    }

    public List<Interceptor> getInterceptorList() {
        return interceptorList;
    }

    public void setInterceptorList(List<Interceptor> interceptorList) {
        this.interceptorList = interceptorList;
    }

    public SSLContext getSslcontext() {
        return sslcontext;
    }

    public void setSslcontext(SSLContext sslcontext) {
        this.sslcontext = sslcontext;
    }

    public HostnameVerifier getIgnoreHostnameVerifier() {
        return ignoreHostnameVerifier;
    }

    public void setIgnoreHostnameVerifier(HostnameVerifier ignoreHostnameVerifier) {
        this.ignoreHostnameVerifier = ignoreHostnameVerifier;
    }

    public ConnectionSpec getLegacyTls() {
        return legacyTls;
    }

    public void setLegacyTls(ConnectionSpec legacyTls) {
        this.legacyTls = legacyTls;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public boolean isRetryOnFail() {
        return isRetryOnFail;
    }

    public void setRetryOnFail(boolean retryOnFail) {
        isRetryOnFail = retryOnFail;
    }

    public Converter.Factory getConverterFactory() {
        return converterFactory;
    }

    public void setConverterFactory(Converter.Factory converterFactory) {
        this.converterFactory = converterFactory;
    }

    public static class Builder{
        private List<Interceptor> interceptorList;

        private SSLContext sslcontext;

        private HostnameVerifier ignoreHostnameVerifier;

        private ConnectionSpec legacyTls;

        private String baseUrl;

        private int connectTimeout = 30;

        private int readTimeout = 30;

        private int writeTimeout = 30;

        private boolean isRetryOnFail = true;

        private Converter.Factory converterFactory;

        public Builder addInterceptor(Interceptor interceptor){
            if(interceptorList==null){
                interceptorList = new ArrayList<>();
            }
            interceptorList.add(interceptor);
            return this;
        }

        public Builder setSSLContext(SSLContext sslContext){
            this.sslcontext = sslContext;
            return this;
        }

        public Builder setHostnameVerifier(HostnameVerifier hostnameVerifier){
            this.ignoreHostnameVerifier = hostnameVerifier;
            return this;
        }

        public Builder setConnectionSpec(ConnectionSpec legacyTls){
            this.legacyTls = legacyTls;
            return this;
        }

        public Builder setBaseUrl(String baseUrl){
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setConnectionTimeout(int connectTimeout){
            if(connectTimeout<1){
                connectTimeout = 30;
            }
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setReadTimeout(int readTimeout){
            if(readTimeout<1){
                readTimeout = 30;
            }
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder setWriteTimeout(int writeTimeout){
            if(writeTimeout<1){
                writeTimeout = 30;
            }
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder setRetryStrategy(boolean isRetry){
            this.isRetryOnFail = isRetry;
            return this;
        }

        public Builder setConverterFactory(Converter.Factory converterFactory){
            this.converterFactory = converterFactory;
            return this;
        }

        public ApiConfig build(){
            ApiConfig apiConfig = new ApiConfig();
            if(interceptorList!=null){
                apiConfig.setInterceptorList(interceptorList);
            }
            if(sslcontext!=null){
                apiConfig.setSslcontext(sslcontext);
            }
            if(ignoreHostnameVerifier!=null){
                apiConfig.setIgnoreHostnameVerifier(ignoreHostnameVerifier);
            }
            if(legacyTls!=null){
                apiConfig.setLegacyTls(legacyTls);
            }
            if(baseUrl!=null){
                apiConfig.setBaseUrl(baseUrl);
            }

            apiConfig.setConnectTimeout(connectTimeout);
            apiConfig.setReadTimeout(readTimeout);
            apiConfig.setWriteTimeout(writeTimeout);
            apiConfig.setRetryOnFail(isRetryOnFail);

            if(converterFactory!=null){
                apiConfig.setConverterFactory(converterFactory);
            }

            return apiConfig;
        }

    }
}
