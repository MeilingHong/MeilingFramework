package com.meiling.framework.app.mvp.presenter;

import com.meiling.framework.app.R;
import com.meiling.framework.app.base.BaseApplication;
import com.meiling.framework.app.mvp.base.MyBasePresenter;
import com.meiling.framework.app.mvp.base.MyObserver;
import com.meiling.framework.app.mvp.entity.base.BaseBean;
import com.meiling.framework.app.mvp.entity.version.VersionEntity;
import com.meiling.framework.app.mvp.model.AppVersionModel;
import com.meiling.framework.app.mvp.view.AppVersionView;

/**
 * Created by marisareimu@126.com on 2020-11-16  14:52
 * project MeilingFramework
 */
public class AppVersionPresenter extends MyBasePresenter<AppVersionView> {
    private AppVersionModel model;

    public AppVersionPresenter(AppVersionView callback) {
        super(callback);
        model = new AppVersionModel();
    }

    public void getAppVersion() {
        addDisposable(model.getAppVersion(), new MyObserver<BaseBean<VersionEntity>>(getCallback(), true, "", "") {
            @Override
            public void onRequestSuccess(BaseBean<VersionEntity> result) {
                if (getCallback() != null) {
                    if (result != null && result.getSuccess() != null && result.getSuccess() && result.getData() != null) {
                        getCallback().getAppVersion(result.getData());
                    } else {
                        onRequestError("-408", BaseApplication.getResString(R.string.network_data_convert_error));
                    }
                }
            }

            @Override
            public void onRequestError(String errorCode, String errorMessage) {
                if (getCallback() != null) {
                    getCallback().requestFail(1, errorCode, errorMessage);
                }
            }
        });

    }
}
