package com.yifan.calendarview.object;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

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
    public int dayInMonth;

    /**
     * 月
     */
    public int month;

    @IntDef({Calendar.JANUARY,Calendar.FEBRUARY,Calendar.MARCH,Calendar.APRIL,
            Calendar.MAY,Calendar.JUNE,Calendar.JULY,Calendar.AUGUST,Calendar.SEPTEMBER,
            Calendar.OCTOBER,Calendar.NOVEMBER,Calendar.DECEMBER,Calendar.UNDECIMBER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Months {
    }

    public Days(boolean lastMonth, boolean nextMonth, boolean isToday, int dayInMonth, @Months int month) {
        this.lastMonth = lastMonth;
        this.nextMonth = nextMonth;
        this.isToday = isToday;
        this.dayInMonth = dayInMonth;
        this.month = month;
    }

}
