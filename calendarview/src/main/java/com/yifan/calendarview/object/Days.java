package com.yifan.calendarview.object;

/**
 * 天 数据类
 *
 * Created by yifan on 2018/10/25.
 */
public class Days {

    /**
     * 是否为上个月
     */
    public boolean lastMonth;

    /**
     * 是否为下个月
     */
    public boolean nextMonth;

    /**
     * 是否为今天
     */
    public boolean isToday;

    /**
     * 天
     */
    public int day;

    /**
     * 月
     */
    public int month;

    public Days(boolean lastMonth, boolean nextMonth, boolean isToday, int day, int month) {
        this.lastMonth = lastMonth;
        this.nextMonth = nextMonth;
        this.isToday = isToday;
        this.day = day;
        this.month = month;
    }

}
