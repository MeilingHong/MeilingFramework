package com.meiling.framework.app.mvp.view;

import com.meiling.framework.app.mvp.base.MyBaseView;
import com.meiling.framework.app.mvp.entity.version.VersionEntity;

/**
 * Created by marisareimu@126.com on 2020-11-16  14:50
 * project MeilingFramework
 */

public interface AppVersionView extends MyBaseView {
    void getAppVersion(VersionEntity result);
}
