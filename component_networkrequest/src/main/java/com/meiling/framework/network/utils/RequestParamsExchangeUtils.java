package com.meiling.framework.network.utils;

import java.io.File;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestParamsExchangeUtils {

    public static RequestBody createJsonRequestBody(String content) {
        return RequestBody.create(MediaType.parse("application/json"), content);
    }

    public static RequestBody createTextRequestBody(String content) {
        return RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), content);
    }

    public static RequestBody createUrlFormRequestBody(String content) {
        return RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), content);
    }

    public static RequestBody createRequestBody(String mediaType, String content) {
        return RequestBody.create(MediaType.parse(mediaType), content);
    }

    public static RequestBody createJsonFormBody(String content) {
        return FormBody.create(MediaType.parse("application/json"), content);
    }

    public static RequestBody createTextFormBody(String content) {
        return FormBody.create(MediaType.parse("text/plain;charset=UTF-8"), content);
    }

    public static RequestBody createUrlFormBody(String content) {
        return FormBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), content);
    }

    public static RequestBody createFormBody(String mediaType, String content) {
        return FormBody.create(MediaType.parse(mediaType), content);
    }

    public static RequestBody createFileFormBody(String mediaType, File content) {
        return FormBody.create(MediaType.parse(mediaType), content);
    }

    public static RequestBody createFileBody(String mediaType, File content) {
        return FormBody.create(MediaType.parse(mediaType), content);
    }
}
