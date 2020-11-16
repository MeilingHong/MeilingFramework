package com.meiling.framework.app.mvp.base;


import com.meiling.framework.app.mvp.base.server.APIFactory;
import com.meiling.framework.app.mvp.base.server.APIServer;
import com.meiling.framework.network.model.BaseModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public abstract class MyBaseModel<T> extends BaseModel<T> {
    protected APIServer apiServer = (APIServer) APIFactory.getApiInstances().getApiService(APIServer.class);

    public abstract Observable<T> getRequest(Object... params);

    public Map<String, Object> getDefaultQueryMap(){
        Map<String, Object> map = new HashMap<>();
        return map;
    }
}
