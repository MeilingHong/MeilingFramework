package com.meiling.framework.app.dialog.time_select;

import java.util.Calendar;

/**
 * Created by marisareimu@126.com on 2021-01-15  13:44
 * project MeilingFramework
 */
public class TimeContainer {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public TimeContainer(long timestamp) {
        initData(timestamp);
    }

    private void initData(long millseconds) {
        if (millseconds == 0) {
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
        second = calendar.get(Calendar.SECOND);
    }
}
