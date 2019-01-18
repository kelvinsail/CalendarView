package com.yifan.calendarview.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.yifan.calendarview.model.MonthData;

/**
 * 绘制器接口
 * <p>
 * Created by yifan on 2019/1/18.
 */
public interface Drawer {

    /**
     * 初始化绘制器
     *
     * @param resources
     */
    void initDrawer(Resources resources);

    /**
     * 绘制
     *
     * @param canvas
     * @param data
     * @param height
     * @param width
     */
    void drawMonth(Canvas canvas, MonthData data, int height, int width);

    /**
     * 绘制标题
     *
     * @param paint
     * @param canvas
     * @param defaultColor
     * @param x
     * @param y
     * @param title
     */
    void drawEveryDayTitle(Paint paint, Canvas canvas, int defaultColor, float x, float y, String title);

    /**
     * 绘制每一天
     *
     * @param paint
     * @param canvas
     * @param defaultColor
     * @param x
     * @param y
     * @param textCenterY
     * @param day
     * @param isToday
     * @param isSelected
     * @param selectedBgRadius
     * @param selectedBgColor
     */
    void drawEveryDay(Paint paint, Canvas canvas, int defaultColor, float x, float y, float textCenterY, int day,
                      boolean isToday, boolean isSelected, float selectedBgRadius, int selectedBgColor);

    /**
     * 绘制标记
     *
     * @param paint
     * @param canvas
     * @param defaultColor
     * @param radius
     * @param x
     * @param y
     * @param day
     */
    void drawEveryDayMark(Paint paint, Canvas canvas, int defaultColor, float radius, float x, float y, int day);

    /**
     * 获取画笔
     *
     * @return
     */
    Paint getPaint();

    /**
     * 视图高度
     *
     * @return
     */
    long getHeight();

    /**
     * 视图宽度
     *
     * @return
     */
    long getWidth();

    /**
     * 纵向间距
     *
     * @return
     */
    long getMargeV();

    /**
     * 横向间距
     *
     * @return
     */
    long getMargeH();
}
