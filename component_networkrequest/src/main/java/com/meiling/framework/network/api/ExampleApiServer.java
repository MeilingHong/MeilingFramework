package com.meiling.framework.network.api;


import com.meiling.framework.network.config.APIKey;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;


public interface ExampleApiServer {//示例性的写法
    @Headers({APIKey.URL+":main"})
    @GET("/api/testget")
    Observable<String> getTestGet(@QueryMap Map<String, Object> par);

    @Headers({APIKey.URL+":main"})
    @FormUrlEncoded
    @POST("/api/testpostform")
    Observable<String> getTestPostForm(@FieldMap Map<String, Object> par);

    @Headers({APIKey.URL+":main"})
    @POST("/api/testpost")
    Observable<String> getTestPost(@Body RequestBody par);

    @Headers({APIKey.URL+":main"})
    @PUT("/api/testput")
    Observable<String> getTestPut(@Body RequestBody par);

    @Headers({APIKey.URL+":test"})
    @DELETE("/api/testdelete")
    Observable<String> getTestDelete(@Body RequestBody par);
}
