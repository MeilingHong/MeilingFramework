package com.meiling.framework.app.mvp.model;

import com.meiling.framework.app.mvp.base.MyBaseModel;
import com.meiling.framework.app.mvp.entity.base.BaseBean;
import com.meiling.framework.app.mvp.entity.version.VersionEntity;
import com.meiling.framework.network.utils.RequestContentType;

import io.reactivex.Observable;

/**
 * Created by marisareimu@126.com on 2020-11-16  14:52
 * project MeilingFramework
 */

public class AppVersionModel extends MyBaseModel<String> {

    @Override
    public Observable<String> getRequest(Object... params) {
        return null;
    }

    public Observable<BaseBean<VersionEntity>> getAppVersion() {
        return apiServer.getVersion(RequestContentType.JSON);
    }

}
