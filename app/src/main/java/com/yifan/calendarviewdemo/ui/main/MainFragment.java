package com.yifan.calendarviewdemo.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yifan.calendarview.CalendarView;
import com.yifan.calendarview.MonthView;
import com.yifan.calendarview.OnDaysClickListener;
import com.yifan.calendarview.OnMonthChangeListener;
import com.yifan.calendarview.object.Days;
import com.yifan.calendarview.utils.CalendarUtils;
import com.yifan.calendarview.view.AbstractMonthView;
import com.yifan.calendarviewdemo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

    private CalendarView mCalendarView;
    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendarView = view.findViewById(R.id.cv_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel

        //设置监听器
        mCalendarView.getAdapter().setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChanged(AbstractMonthView view, Calendar calendar, int month) {
                getActivity().setTitle(new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(calendar.getTime()));
                //设置日期标记
                //填充日期数组
                List<Integer> list = new ArrayList<>();
                list.add(18);
                //设置要标记的月份(这里设置了当月)、日期,
                view.addMarks(month, list);//每个月都设置
                //只有一月才标记
                //view.addMarks(Calendar.getInstance().get(Calendar.MONTH), list);

                //设置点击事件，不设置的话不会触发点击高亮
                view.setOnDaysClickListener(new OnDaysClickListener() {
                    @Override
                    public void onClick(AbstractMonthView view, int position, Days days) {
                        Toast.makeText(view.getContext(), CalendarUtils.getStringOfMonths(days.month) + "-" + days.dayInMonth, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}
