package com.meiling.framework.app.dialog.picker.config;


import com.meiling.framework.app.dialog.picker.data.Type;
import com.meiling.framework.app.dialog.picker.data.WheelCalendar;
import com.meiling.framework.app.dialog.picker.listener.OnDateSetListener;

/**
 * 功能描述：
 * <p>  Wheel Dialog Config
 * Created by RuanCaiPu on 2017/9/1.
 */
public class PickerConfig {

    public Type mType = DefaultConfig.TYPE;
    public int mThemeColor = DefaultConfig.COLOR;

    public String mCancelString = DefaultConfig.CANCEL;
    public int mSureTextColor = DefaultConfig.COLOR;
    public int mCancelTextColor = DefaultConfig.COLOR;
    public String mSureString = DefaultConfig.SURE;
    public String mTitleString = DefaultConfig.TITLE;
    public int mToolBarTVColor = DefaultConfig.TOOLBAR_TV_COLOR;

    public int mWheelTVNormalColor = DefaultConfig.TV_NORMAL_COLOR;
    public int mWheelTVSelectorColor = DefaultConfig.TV_SELECTOR_COLOR;
    public int mWheelTVSize = DefaultConfig.TV_SIZE;
    public boolean cyclic = DefaultConfig.CYCLIC;
    public boolean cyclicYear = DefaultConfig.UNCYCLIC;
    public boolean cyclicMonth = DefaultConfig.UNCYCLIC;
    public boolean cyclicDay = DefaultConfig.UNCYCLIC;
    public boolean cyclicHour = DefaultConfig.UNCYCLIC;
    public boolean cyclicMinute = DefaultConfig.UNCYCLIC;
    public boolean cyclicSecond = DefaultConfig.UNCYCLIC;

    public String mYear = DefaultConfig.YEAR;
    public String mMonth = DefaultConfig.MONTH;
    public String mDay = DefaultConfig.DAY;
    public String mHour = DefaultConfig.HOUR;
    public String mMinute = DefaultConfig.MINUTE;

    /**
     * The min timeMillseconds
     */
    public WheelCalendar mMinCalendar = new WheelCalendar(0);

    /**
     * The max timeMillseconds
     */
    public WheelCalendar mMaxCalendar = new WheelCalendar(0);

    /**
     * The default selector timeMillseconds
     */
    public WheelCalendar mCurrentCalendar = new WheelCalendar(System.currentTimeMillis());

    public OnDateSetListener mCallBack;

}
