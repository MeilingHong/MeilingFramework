package com.meiling.framework.app.dialog.picker.data;

import java.util.Calendar;

/**
 * 功能描述：
 * <p>
 * Created by RuanCaiPu on 2017/9/1.
 */
public class WheelCalendar {

    public int year, month, day, hour, minute;

    private boolean noRange;

    public WheelCalendar(long millseconds) {
        initData(millseconds);
    }

    private void initData(long millseconds) {
        if (millseconds == 0) {
            noRange = true;
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(millseconds);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    public boolean isNoRange() {
        return noRange;
    }


}
