package com.meiling.framework.app.mvp.entity.base;
/**
 * Created by marisareimu@126.com on 2020-11-16  15:47
 * project MeilingFramework
 */

import com.meiling.framework.common_util.gson.BeanToString;

/**
 * Created by marisareimu@126.com on 2020-11-16  15:47
 * project MeilingFramework
 */
public class BaseCheckBean extends BeanToString {
    public String errorCode;
    public String errorMsg;
    public Boolean success;
}
