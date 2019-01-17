package com.yifan.calendarview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yifan.calendarview.object.Days;
import com.yifan.calendarview.utils.CalendarUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * 月视图View，可单独使用
 *
 * Created by yifan on 2018/10/23.
 */
public class MonthView extends View {

    private static final String TAG = "MonthView";

    /**
     * 绘制测试基线
     */
    boolean test = false;

    private int mTextSize;
    private int mTextColor;
    private int mLastMonthTextColor;
    private int mCurrentTextColor;

    private int mSelectTextColor;
    private int mSelectBackgroundColor;
    private int mSelectMarkColor;
    private int mSelectBackgroungRadius;

    /**
     * 标记颜色
     */
    private int mMarkColor;

    /**
     * 标记圆点半径
     */
    private float mMarkRadius;

    /**
     * X轴坐标
     */
    long nameIndexH;
    /**
     * X轴每一行文本的间距
     */
    long margeH;
    /**
     * Y轴坐标
     */
    long nameIndexV;
    /**
     * Y轴每一行文本的间距
     */
    long margeV;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 布局宽度
     */
    private long mWidth;
    /**
     * 布局高度
     */
    private long mHeight;

    /**
     * 当月的天数
     */
    private int mCurrentDays;

    /**
     * 是否周日为第一天
     */
    private boolean isSunStart = true;

    /**
     * 周期标题，周一开始
     */
    private String[] mDaysTitle;
    /**
     * 周期标题，周日开始
     */
    private String[] mDaysTitleSun;

    /**
     * 该月份中的日期数据
     */
    private List<Days> mList = new ArrayList<>();

    /**
     * 标记数据
     */
    private HashMap<Integer, List<Integer>> mMarkData;

    /**
     * 点击事件
     */
    public OnDaysClickListener mListsner;

    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    /**
     * 初始化
     */
    private void init() {
        //标记数据
        mMarkData = new HashMap<>();
//        //模拟数据
//        List<Integer> list = new ArrayList<>();
//        list.add(12);
//        list.add(16);
//        list.add(21);
//        list.add(30);
//        mMarkData.put(10, list);

        //颜色
        mTextColor = getResources().getColor(R.color.text_month_view_day);
        mLastMonthTextColor = getResources().getColor(R.color.text_month_view_no_current);
        mCurrentTextColor = getResources().getColor(R.color.text_month_view_current_day);
        mMarkColor = getResources().getColor(R.color.text_month_view_current_day);
        mMarkRadius = getResources().getDimensionPixelSize(R.dimen.size_day_mark_radius);
        mTextSize = getResources().getDimensionPixelSize(R.dimen.text_day);

        mSelectTextColor = getResources().getColor(R.color.text_selected_text_color);
        mSelectBackgroundColor = getResources().getColor(R.color.text_selected_backgroung_color);
        mSelectMarkColor = getResources().getColor(R.color.text_selected_backgroung_color);
        mSelectBackgroungRadius = getResources().getDimensionPixelSize(R.dimen.size_select_background_radius);

        //标题
        mDaysTitle = getResources().getStringArray(R.array.week_title);
        mDaysTitleSun = getResources().getStringArray(R.array.week_title_sun_start);

        //画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setTextSize(mTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);

        //计算时间等数据
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_MONTH);
        int month = CalendarUtils.getIntOfMonth(calendar.get(Calendar.MONTH));//本月int

        calendar.set(Calendar.DATE, 1);//把日期设置为当月第一天
        //获得1号是星期几
        int oneWhere = calendar.get(Calendar.DAY_OF_WEEK) - (isSunStart ? 0 : 1);//Calendar.MONDAY == 2
        oneWhere = oneWhere == 0 ? 7 : oneWhere;//如果为0.则是周日

        //计算当月天数
        calendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        mCurrentDays = calendar.get(Calendar.DATE);

        //回归今天
        calendar.set(Calendar.DATE, today);

        //叠加数据
        //补全上月天数
        for (int e = 1; e < oneWhere; e++) {
            Calendar last = Calendar.getInstance();
            last.set(Calendar.DATE, 1);
            last.add(Calendar.DATE, 0 - e);

            mList.add(new Days(true, false,
                    false, last.get(Calendar.DATE), CalendarUtils.getIntOfMonth(last.get(Calendar.MONTH))));
        }
        //当月天数
        for (int i = 1; i <= mCurrentDays; i++) {
            mList.add(new Days(false, false, i == today, i, month));
            if (i == today) {
                mSelectionIndex = mList.size() - 1;
            }
        }
        //下月天数
        int nextCount = 42 - mList.size();
        month++;//月份+1
        for (int n = 1; n <= nextCount; n++) {
            mList.add(new Days(false, true, false, n, month));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (0 == mWidth || 0 == mHeight) {
            mWidth = getMeasuredWidth();
            mHeight = getMeasuredHeight();
            margeH = mWidth / 7;
            margeV = mHeight / 7;
        }
        nameIndexH = margeH / 2;
        nameIndexV = margeV / 2;

        if (test) {
            //绘制准线
            mPaint.setColor(Color.RED);
            long tempX = mWidth / 7;
            for (int i = 1; i < 7; i++) {
                canvas.drawLine(tempX * i, 0, tempX * i, mHeight, mPaint);
            }
            long tempY = mHeight / 7;
            for (int i = 1; i < 7; i++) {
                canvas.drawLine(0, tempY * i, mWidth, tempY * i, mPaint);
            }
        }

        if (test) {
            canvas.drawLine(0, nameIndexV, mWidth, nameIndexV, mPaint);
        }
        //绘制周期
        for (String name : isSunStart ? mDaysTitleSun : mDaysTitle) {
            //Y轴纠正坐标，增加1/2字体大小，使居中
            drawEveryDayTitle(mPaint, canvas, mTextColor, nameIndexH, nameIndexV + (int) mPaint.getTextSize() / 2, name);
            nameIndexH += margeH;
        }
        //x轴坐标回归第一列，y轴坐标下移一行
        nameIndexH = margeH / 2;
        nameIndexV += margeV;

        //绘制每一天
        for (int i = 0; i < mList.size(); i++) {
            Days days = mList.get(i);

            //判断颜色类型
            int color;
            if (days.lastMonth || days.nextMonth) {//非本月
                color = mLastMonthTextColor;
            } else if (i == mSelectionIndex) {//选中
                color = mSelectTextColor;
            } else if (days.isToday) {//是否为今天
                color = mCurrentTextColor;
            } else {//默认
                color = mTextColor;
            }

            //绘制每天的内容文本
            drawEveryDay(mPaint, canvas, color, nameIndexH,
                    nameIndexV + (int) mPaint.getTextSize() / 3, nameIndexV, days.day, days.isToday,
                    i == mSelectionIndex, mSelectBackgroungRadius, mSelectBackgroundColor);

            //绘制标记
            List<Integer> list = mMarkData.get(days.month);
            if (null != list && list.contains(days.day)) {
                drawEveryDayMark(mPaint, canvas, mMarkColor, mMarkRadius,
                        nameIndexH, nameIndexV + (int) (mPaint.getTextSize() * 1.2), days.day);
            }
            //坐标换行
            if (i != 0 && (i - 6) % 7 == 0) {
                //换行，X轴回归第一列
                nameIndexH = margeH / 2;
                //Y轴坐标增加一行
                nameIndexV += margeV;
            } else {//本行继续
                //x轴坐标移动到下一列
                nameIndexH += margeH;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(widthMeasureSpec));
    }

    /**
     * 获取画笔
     *
     * @return
     */
    public Paint getPaint() {
        return mPaint;
    }

    /**
     * 绘制首行每周标题
     *
     * @param paint
     * @param canvas
     * @param defaultColor
     * @param x
     * @param y
     * @param title
     */
    public void drawEveryDayTitle(Paint paint, Canvas canvas, int defaultColor, float x, float y, String title) {
        paint.setColor(defaultColor);
        canvas.drawText(title, x, y, paint);
    }

    /**
     * 绘制每一天的日期内容
     *
     * @param paint
     * @param canvas
     * @param defaultColor
     * @param x
     * @param y
     * @param day
     */
    public void drawEveryDay(Paint paint, Canvas canvas, int defaultColor, float x, float y, float textCenterY, int day,
                             boolean isToday, boolean isSelected, float selectedBgRadius, int selectedBgColor) {
        if (isSelected) {
            paint.setColor(selectedBgColor);
            canvas.drawCircle(x, textCenterY, selectedBgRadius, paint);
        }
        paint.setColor(defaultColor);
        canvas.drawText(String.valueOf(day), x, y, paint);
    }

    /**
     * 绘制每一天的日期的标记
     *
     * @param paint
     * @param canvas
     * @param defaultColor
     * @param x
     * @param y
     * @param day
     */
    public void drawEveryDayMark(Paint paint, Canvas canvas, int defaultColor, float radius, float x, float y, int day) {
        paint.setColor(defaultColor);
        canvas.drawCircle(x, y, radius, paint);
    }

    /**
     * 设置标记数据
     *
     * @param month
     * @param list
     */
    public void setMarks(int month, List<Integer> list) {
        if (null == mMarkData) {
            mMarkData = new HashMap<>();
        } else {
            mMarkData.clear();
        }
        mMarkData.put(month, list);
        invalidate();
    }

    /**
     * 点击选中的序号
     */
    private int mSelectionIndex;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (y < mHeight / 7) {
                    Log.i(TAG, "onTouchEvent: 点击了标题");
                    return false;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (null != mListsner) {

                    //计算行数
                    int indexY = 0;
                    for (int h = 1; h < 8; h++) {
                        if (h * margeV > y) {
                            indexY = h - 1;
                            break;
                        }
                    }
                    //计算列数
                    int indexX = 0;
                    for (int w = 1; w < 8; w++) {
                        if (w * margeH > x) {
                            indexX = w - 1;
                            break;
                        }
                    }
                    //得到所在数组坐标
                    mSelectionIndex = indexX + (indexY - 1) * 7;
                    mListsner.onClick(mSelectionIndex, mList.get(mSelectionIndex));
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 日期点击事件
     */
    public interface OnDaysClickListener {

        void onClick(int position, Days days);

    }

    /**
     * 设置点击事件
     *
     * @param l
     */
    public void setOnDaysClickListener(@Nullable OnDaysClickListener l) {
        this.mListsner = l;
    }
}
