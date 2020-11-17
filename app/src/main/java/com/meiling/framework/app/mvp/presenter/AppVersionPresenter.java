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
        // todo 1、使用Gson进行对象转换
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
        // todo 2、使用String进行转换
//        addDisposable(model.getAppVersion(), new MyObserver<String>(getCallback(), true, "", "") {
//            @Override
//            public void onRequestSuccess(String result) {
//                if (getCallback() != null) {
//                    // todo 这里需要再次承担数据转换的职责，将String类型的数据转换成对象【相比Gson类型，在转换上需要多几次，但能够处理的数据类型可以更多】
//                    getCallback().getAppVersion(result);
////                    if (result != null && result.getSuccess() != null && result.getSuccess() && result.getData() != null) {
////                        getCallback().getAppVersion(result.getData());
////                    } else {
////                        onRequestError("-408", BaseApplication.getResString(R.string.network_data_convert_error));
////                    }
//                }
//            }
//
//            @Override
//            public void onRequestError(String errorCode, String errorMessage) {
//                if (getCallback() != null) {
//                    getCallback().requestFail(1, errorCode, errorMessage);
//                }
//            }
//        });
    }
}
