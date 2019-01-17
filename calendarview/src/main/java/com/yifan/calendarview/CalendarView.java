package com.yifan.calendarview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yifan on 2018/10/23.
 */
public class CalendarView extends ViewPager {

    public CalendarView(@NonNull Context context) {
        this(context,null);
    }

    public CalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}