package com.yifan.calendarview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yifan.calendarview.object.Days;
import com.yifan.calendarview.utils.CalendarUtils;
import com.yifan.calendarview.view.AbstractMonthView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.yifan.calendarview.CalendarView.MonthViewAdapter.INIT_POSITION;

/**
 * 日历视图
 * <p>
 * Created by yifan on 2018/10/23.
 */
public class CalendarView extends ViewPager {

    private static final String TAG = "CalendarView";

    private MonthViewAdapter mAdapter;

    public CalendarView(@NonNull Context context) {
        this(context, null);
    }

    public CalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mAdapter = new MonthViewAdapter();
        setAdapter(mAdapter);
        setCurrentItem(INIT_POSITION);
    }

    @Nullable
    @Override
    public MonthViewAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(@Nullable MonthViewAdapter adapter) {
        this.mAdapter = adapter;
        super.setAdapter(adapter);
    }

    @Override
    @Deprecated
    public void setAdapter(@Nullable PagerAdapter adapter) {
    }

    public class MonthViewAdapter extends PagerAdapter {

        private AbstractMonthView mCurrentItem;

        private OnMonthChangeListener mListener;

        public final static int INIT_POSITION = Integer.MAX_VALUE / 2;

        public MonthViewAdapter() {
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int offset = position - INIT_POSITION;
            Calendar calendar = Calendar.getInstance();
            if (offset != 0) {
                calendar.add(Calendar.MONTH, offset);
            }

            AbstractMonthView mv = initMonthView(container.getContext());
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

        AbstractMonthView initMonthView(Context context) {
            MonthView monthView = new MonthView(context);
            return monthView;
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (object != mCurrentItem) {
                mCurrentItem = ((AbstractMonthView) object);
                if (null != mListener) {
                    mListener.onMonthChanged(mCurrentItem, mCurrentItem.getDate(), mCurrentItem.getDate().get(Calendar.MONTH));
                }
            }
            super.setPrimaryItem(container, position, object);
        }

        public AbstractMonthView getCurrentItem() {
            return mCurrentItem;
        }

        public OnMonthChangeListener getOnMonthChangeListener() {
            return mListener;
        }

        public void setOnMonthChangeListener(OnMonthChangeListener mListener) {
            this.mListener = mListener;
        }
    }
}