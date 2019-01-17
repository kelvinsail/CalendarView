package com.yifan.calendarview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Calendar;

/**
 * 日历视图
 * <p>
 * Created by yifan on 2018/10/23.
 */
public class CalendarView extends ViewPager {

    private static final String TAG = "CalendarView";

    public CalendarView(@NonNull Context context) {
        this(context, null);
    }

    public CalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setAdapter(new MonthViewAdapter());

        setCurrentItem(Integer.MAX_VALUE / 2);
    }


    public class MonthViewAdapter extends PagerAdapter {

        public MonthViewAdapter() {
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            MonthView mv = new MonthView(container.getContext());
            int offset = position - (Integer.MAX_VALUE / 2);
            Calendar calendar = Calendar.getInstance();
            if (offset != 0) {
                calendar.set(Calendar.MONTH, offset);
            }
            Toast.makeText(getContext(), "instantiateItem: " + calendar.get(Calendar.MONTH), Toast.LENGTH_SHORT).show();
            mv.setDate(calendar);
            container.addView(mv);
            return mv;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}