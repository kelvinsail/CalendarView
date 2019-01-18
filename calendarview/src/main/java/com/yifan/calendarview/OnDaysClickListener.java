package com.yifan.calendarview;

import com.yifan.calendarview.object.Days;
import com.yifan.calendarview.view.AbstractMonthView;

/**
 * 日期点击事件
 * <p>
 * Created by yifan on 2019/1/18.
 */
public interface OnDaysClickListener {

    void onClick(AbstractMonthView view, int position, Days days);
}
