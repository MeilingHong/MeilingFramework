package com.meiling.framework.app.dialog.picker.data;

/**
 * 功能描述：
 * <p> 五种选择模式，年月日时分，年月日，时分，月日时分，年月 其他字符串
 * Created by RuanCaiPu on 2017/9/1.
 */
public enum Type {

    ALL,

    HOURS_MINS_SECOND,
    HOURS_MINS,

    MONTH_DAY_HOUR_MIN_SECOND,
    MONTH_DAY_HOUR_MIN,

    YEAR_MONTH_DAY_HOUR_MIN_SECOND,
    YEAR_MONTH_DAY_HOUR_MIN,

    YEAR_MONTH_DAY,
    YEAR_MONTH,
    YEAR,

    OTHER
}
