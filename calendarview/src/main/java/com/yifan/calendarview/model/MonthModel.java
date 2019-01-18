package com.yifan.calendarview.model;

import com.yifan.calendarview.object.Days;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yifan on 2019/1/18.
 */
public class MonthModel implements MonthData {

    /**
     * 当月的天数
     */
    public int mCurrentDays;

    /**
     * 是否周日为第一天
     */
    public boolean isSunStart = true;

    /**
     * 周期标题，周一开始
     */
    public String[] mDaysTitle;
    /**
     * 周期标题，周日开始
     */
    public String[] mDaysTitleSun;

    /**
     * 该月份中的日期数据
     */
    public List<Days> mList;

    /**
     * 标记数据
     */
    public HashMap<Integer, List<Integer>> mMarkData;

    /**
     * 点击选中的序号
     */
    public int mSelectionIndex;

    public Calendar mCalendar;

    public MonthModel(Calendar calendar) {
        this.mList = new ArrayList<>();
        //标记数据
        this.mMarkData = new HashMap<>();
        //计算数据
        this.calculate(calendar);
    }

    /**
     * 计算时间及数据
     */
    @Override
    public void calculate(Calendar calendar) {
        //赋值，初始化
        mCalendar = calendar;
        mSelectionIndex = -1;
        mList.clear();
        //计算时间等数据
        int today = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);//本月

        calendar.set(Calendar.DATE, 1);//把日期设置为当月第一天
        //获得1号是星期几
        int oneWhere = calendar.get(Calendar.DAY_OF_WEEK) - (isSunStart ? 0 : 1);//Calendar.MONDAY == 2
        //一周起始是周日还是周一
        oneWhere = oneWhere == 0 ? 7 : oneWhere;//如果为0.则是周日

        //计算当月天数
        calendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        mCurrentDays = calendar.get(Calendar.DATE);
        Calendar current = Calendar.getInstance();
        boolean isCurrentMonth = calendar.get(Calendar.YEAR) == current.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == current.get(Calendar.MONTH);

        //回归今天
        calendar.set(Calendar.DATE, today);

        //叠加数据
        //补全上月天数
        for (int e = 1; e < oneWhere; e++) {
            Calendar last = (Calendar) calendar.clone();
            last.set(Calendar.DATE, 1);
            last.add(Calendar.DATE, 0 - e);

            mList.add(0, new Days(true, false,
                    false, last.get(Calendar.DATE), last.get(Calendar.MONTH)));
        }
        //当月的天数
        for (int i = 1; i <= mCurrentDays; i++) {
            mList.add(new Days(false, false, isCurrentMonth && i == today, i, month));
            if (isCurrentMonth && i == today) {
                mSelectionIndex = mList.size() - 1;
            }
        }
        //下个月的天数
        int nextCount = 42 - mList.size();
        month++;//月份+1
        for (int n = 1; n <= nextCount; n++) {
            mList.add(new Days(false, true, false, n, month));
        }
    }

    @Override
    public Calendar getDate() {
        return mCalendar;
    }

    @Override
    public void setTitle(String[] titles) {
        this.mDaysTitle = titles;
    }

    @Override
    public void setTitleSun(String[] titles) {
        this.mDaysTitleSun = titles;
    }

    @Override
    public void addMarks(int month, List<Integer> list) {
        if (null == mMarkData) {
            mMarkData = new HashMap<>();
        } else {
            mMarkData.clear();
        }
        mMarkData.put(month, list);
    }

    @Override
    public void removeMarks(int month) {
        if (null == mMarkData) {
            mMarkData = new HashMap<>();
            return;
        }
        mMarkData.remove(month);
    }

    @Override
    public List<Integer> getMarks(int month) {
        return mMarkData.get(month);
    }

    @Override
    public String[] getTitles() {
        return isSunStart ? mDaysTitleSun : mDaysTitle;
    }

    @Override
    public int getSelectionIndex() {
        return mSelectionIndex;
    }

    @Override
    public void setSelectionIndex(int index) {
        this.mSelectionIndex = index;
    }

    @Override
    public Days getSelectedDays() {
        return mList.get(mSelectionIndex);
    }

    @Override
    public Days getDays(int index) {
        return mList.get(index);
    }

    @Override
    public int getDaysSize() {
        return mList.size();
    }
}
