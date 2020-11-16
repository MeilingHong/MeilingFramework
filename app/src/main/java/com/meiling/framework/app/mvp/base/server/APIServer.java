package com.meiling.framework.app.mvp.base.server;


import com.meiling.framework.network.config.APIKey;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface APIServer {
    String TOKEN = "token";

    /**
     * 获取验证码接口
     *
     * @param contentType
     * @param target
     * @param country
     * @return
     */
    @Headers({APIKey.URL + ":main"})
    @GET("/api/opuser/getCodeNoAuth")
    Observable<String> getSms(@Header("Content-Type") String contentType, @Query("target") String target, @Query("country") String country);

}
