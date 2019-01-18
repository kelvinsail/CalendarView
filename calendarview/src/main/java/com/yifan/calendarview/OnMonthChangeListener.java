package com.yifan.calendarview;

import android.support.annotation.IntDef;
import android.support.annotation.IntegerRes;

import com.yifan.calendarview.view.AbstractMonthView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

/**
 * 月份切换监听
 * <p>
 * Created by yifan on 2019/1/18.
 */
public interface OnMonthChangeListener {

    @IntDef({Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH, Calendar.APRIL,
            Calendar.MAY, Calendar.JUNE, Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER,
            Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER, Calendar.UNDECIMBER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Months {
    }

    void onMonthChanged(AbstractMonthView view, Calendar calendar, @Months int month);
}
