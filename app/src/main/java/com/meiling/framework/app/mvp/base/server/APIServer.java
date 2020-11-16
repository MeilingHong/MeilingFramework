package com.meiling.framework.app.mvp.base.server;


import com.meiling.framework.app.mvp.entity.base.BaseBean;
import com.meiling.framework.app.mvp.entity.version.VersionEntity;
import com.meiling.framework.network.config.APIKey;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;


public interface APIServer {
    String TOKEN = "token";

    /**
     * 获取版本信息
     *
     * @param contentType
     * @return
     */
    @Headers({APIKey.URL + ":main"})
    @GET("/api/oppreservation/system/app")
    Observable<BaseBean<VersionEntity>> getVersion(@Header("Content-Type") String contentType);

}
