package com.yifan.calendarview.model;

import com.yifan.calendarview.object.Days;

import java.util.Calendar;
import java.util.List;

/**
 * Created by yifan on 2019/1/18.
 */
public interface MonthData {

    /**
     * 计算数据
     *
     * @param calendar
     */
    void calculate(Calendar calendar);

    /**
     * 获取当前月历数据
     *
     * @return
     */
    Calendar getDate();

    void setTitle(String[] titles);

    void setTitleSun(String[] titles);

    void addMarks(int month, List<Integer> list);

    void removeMarks(int month);

    List<Integer> getMarks(int month);

    String[] getTitles();

    int getSelectionIndex();

    void setSelectionIndex(int index);

    Days getSelectedDays();

    Days getDays(int index);

    int getDaysSize();
}
