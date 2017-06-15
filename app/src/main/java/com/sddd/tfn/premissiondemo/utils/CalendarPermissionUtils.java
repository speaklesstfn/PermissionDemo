package com.sddd.tfn.premissiondemo.utils;

/**
 * Created by tfn on 17-6-14.
 * <p>
 * 日历权限判断
 * <p>
 * todo 日历权限针对谷歌原生的日历控件，因此目前无所谓
 */

public class CalendarPermissionUtils {

    public static synchronized boolean isCalendarCanUse() {
        return true;
    }
}
