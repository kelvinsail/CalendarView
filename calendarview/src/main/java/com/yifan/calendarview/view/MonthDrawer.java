package com.yifan.calendarview.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.yifan.calendarview.R;
import com.yifan.calendarview.model.MonthData;
import com.yifan.calendarview.object.Days;

import java.util.List;

/**
 * 视图绘制器实现类
 * <p>
 * Created by yifan on 2019/1/18.
 */
public class MonthDrawer implements Drawer {

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
     * 布局宽度
     */
    private long mWidth;
    /**
     * 布局高度
     */
    private long mHeight;

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

    @Override
    public void initDrawer(Resources resources) {
        //颜色
        mTextColor = resources.getColor(R.color.text_month_view_day);
        mLastMonthTextColor = resources.getColor(R.color.text_month_view_no_current);
        mCurrentTextColor = resources.getColor(R.color.text_month_view_current_day);
        mMarkColor = resources.getColor(R.color.text_month_view_current_day);
        mMarkRadius = resources.getDimensionPixelSize(R.dimen.size_day_mark_radius);
        mTextSize = resources.getDimensionPixelSize(R.dimen.text_day);

        mSelectTextColor = resources.getColor(R.color.text_selected_text_color);
        mSelectBackgroundColor = resources.getColor(R.color.text_selected_backgroung_color);
        mSelectMarkColor = resources.getColor(R.color.text_selected_backgroung_color);
        mSelectBackgroungRadius = resources.getDimensionPixelSize(R.dimen.size_select_background_radius);


        //画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setTextSize(mTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void drawMonth(Canvas canvas, MonthData data, int height, int width) {
        if (0 == mWidth || 0 == height) {
            mWidth = width;
            mHeight = height;
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
        for (String name : data.getTitles()) {
            //Y轴纠正坐标，增加1/2字体大小，使居中
            drawEveryDayTitle(mPaint, canvas, mTextColor, nameIndexH, nameIndexV + (int) mPaint.getTextSize() / 2, name);
            nameIndexH += margeH;
        }
        //x轴坐标回归第一列，y轴坐标下移一行
        nameIndexH = margeH / 2;
        nameIndexV += margeV;

        //绘制每一天
        for (int i = 0; i < data.getDaysSize(); i++) {
            Days days = data.getDays(i);

            //判断颜色类型
            int color;
            if (days.lastMonth || days.nextMonth) {//非本月
                color = mLastMonthTextColor;
            } else if (i == data.getSelectionIndex()) {//选中
                color = mSelectTextColor;
            } else if (days.isToday) {//是否为今天
                color = mCurrentTextColor;
            } else {//默认
                color = mTextColor;
            }

            //绘制每天的内容文本
            drawEveryDay(mPaint, canvas, color, nameIndexH,
                    nameIndexV + (int) mPaint.getTextSize() / 3, nameIndexV, days.dayInMonth, days.isToday,
                    i == data.getSelectionIndex(), mSelectBackgroungRadius, mSelectBackgroundColor);

            //绘制标记
            List<Integer> list = data.getMarks(days.month);
            if (null != list && list.contains(days.dayInMonth)) {
                drawEveryDayMark(mPaint, canvas, mMarkColor, mMarkRadius,
                        nameIndexH, nameIndexV + (int) (mPaint.getTextSize() * 1.2), days.dayInMonth);
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

    @Override
    public long getHeight() {
        return mHeight;
    }

    @Override
    public long getWidth() {
        return mWidth;
    }

    @Override
    public long getMargeV() {
        return margeV;
    }

    @Override
    public long getMargeH() {
        return margeH;
    }
}
