package com.yifan.calendarview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.yifan.calendarview.view.AbstractMonthView;

/**
 * 月视图View，可单独使用
 * <p>
 * Created by yifan on 2018/10/23.
 */
public class MonthView extends AbstractMonthView {

    private static final String TAG = "MonthView";


    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

}
