package com.yifan.calendarview.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

/**
 * 工具类
 * Created by yifan on 2018/10/25.
 */
public class CalendarUtils {

    @IntDef({Calendar.JANUARY,Calendar.FEBRUARY,Calendar.MARCH,Calendar.APRIL,
            Calendar.MAY,Calendar.JUNE,Calendar.JULY,Calendar.AUGUST,Calendar.SEPTEMBER,
            Calendar.OCTOBER,Calendar.NOVEMBER,Calendar.DECEMBER,Calendar.UNDECIMBER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Months {
    }

    /**
     * 获取月份对应的String类型
     * @param month
     * @return
     */
    public static String  getStringOfMonths(@Months int month){
        return String.valueOf(getIntOfMonth(month));
    }

    /**
     * 获取月份对应的Int类型
     *
     * @param month
     * @return
     */
    public static int getIntOfMonth(@Months int month){
        switch(month){
            case Calendar.JANUARY:
                return 1;
            case Calendar.FEBRUARY:
                return 2;
            case Calendar.MARCH:
                return 3;
            case Calendar.APRIL:
                return 4;
            case Calendar.MAY:
                return 5;
            case Calendar.JUNE:
                return 6;
            case Calendar.JULY:
                return 7;
            case Calendar.AUGUST:
                return 8;
            case Calendar.SEPTEMBER:
                return 9;
            case Calendar.OCTOBER:
                return 10;
            case Calendar.NOVEMBER:
                return 11;
            case Calendar.DECEMBER:
                return 12;
            case Calendar.UNDECIMBER:
            default:
                return 13;
        }
    }
}
