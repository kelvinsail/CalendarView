package com.yifan.calendarview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yifan.calendarview.R;
import com.yifan.calendarview.model.MonthData;
import com.yifan.calendarview.model.MonthModel;
import com.yifan.calendarview.OnDaysClickListener;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * 月视图View，可单独使用
 * <p>
 * Created by yifan on 2018/10/23.
 */
public abstract class AbstractMonthView extends View {

    private static final String TAG = "MonthView";

    /**
     * 是否点击了日期，如果移动，则false，防止误触
     */
    private boolean isClickDays;

    /**
     * 点击X轴Y轴坐标记录
     */
    float mDownClickY;
    float mDownClickX;

    /**
     * 点击事件
     */
    OnDaysClickListener mListsner;

    /**
     * 数据model
     */
    private MonthData mModel;

    /**
     * 视图绘制器
     */
    private Drawer mDrawer;

    public AbstractMonthView(Context context) {
        this(context, null);
    }

    public AbstractMonthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    /**
     * 初始化
     */
    private void init() {
        mModel = new MonthModel(Calendar.getInstance());
        mDrawer = new MonthDrawer();

        //标题
        mModel.setTitle(getResources().getStringArray(R.array.week_title));
        mModel.setTitleSun(getResources().getStringArray(R.array.week_title_sun_start));

        //初始化绘制者
        mDrawer.initDrawer(getResources());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDrawer.drawMonth(canvas, mModel, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(widthMeasureSpec));
    }


    /**
     * 设置标记数据
     *
     * @param month
     * @param list
     */
    public void addMarks(int month, List<Integer> list) {
        mModel.addMarks(month, list);
        invalidate();
    }

    /**
     * 移除标记
     *
     * @param month
     */
    public void removeMarks(int month) {
        mModel.removeMarks(month);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (y < mDrawer.getHeight() / 7) {
                    Log.d(TAG, "onTouchEvent: 点击了标题");
                    return false;
                }
                mDownClickY = event.getY();
                mDownClickX = event.getX();
                isClickDays = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(mDownClickY - event.getY()) > 15 ||
                        Math.abs(mDownClickX - event.getX()) > 15) {
                    isClickDays = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isClickDays && null != mListsner) {

                    //计算行数
                    int indexY = 0;
                    for (int h = 1; h < 8; h++) {
                        if (h * mDrawer.getMargeV() > y) {
                            indexY = h - 1;
                            break;
                        }
                    }
                    //计算列数
                    int indexX = 0;
                    for (int w = 1; w < 8; w++) {
                        if (w * mDrawer.getMargeH() > x) {
                            indexX = w - 1;
                            break;
                        }
                    }
                    //得到所在数组坐标
                    mModel.setSelectionIndex(indexX + (indexY - 1) * 7);
                    mListsner.onClick(this, mModel.getSelectionIndex(), mModel.getSelectedDays());
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置点击事件
     *
     * @param l
     */
    public void setOnDaysClickListener(@Nullable OnDaysClickListener l) {
        this.mListsner = l;
    }

    public void setDate(Calendar calendar) {
        mModel.calculate(calendar);
        invalidate();
    }

    public Calendar getDate() {
        return mModel.getDate();
    }
}
