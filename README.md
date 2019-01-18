# CalendarView
Android自定义实现日历控件

#2019-01-18，持续更新
## 1、月历视图`MonthView`
### 1) 可单独使用，默认展示当月的月历
```
MonthView monthView = new MonthView(context);
```

### 2) 自定义`Drawer`，自定义实现UI
```
    /**
    * 初始化绘制器，画笔、颜色资源
    */
    void initDrawer(Resources resources);

    /**
     * 绘制UI主函数，在MonthView中调用该方法
     */
    void drawMonth(Canvas canvas, MonthData data, int height, int width);

    /**
     * 绘制标题，drawMonth中调用该函数
     */
    void drawEveryDayTitle(Paint paint, Canvas canvas, int defaultColor, float x, float y, String title);

    /**
     * 绘制每一天，drawMonth中调用该函数
     */
    void drawEveryDay(Paint paint, Canvas canvas, int defaultColor, float x, float y, float textCenterY, int day,
                      boolean isToday, boolean isSelected, float selectedBgRadius, int selectedBgColor);

    /**
     * 绘制标记，drawMonth中调用该函数
     */
    void drawEveryDayMark(Paint paint, Canvas canvas, int defaultColor, float radius, float x, float y, int day);
```


## 2、日历视图`CalendarView`


### 1）ViewPager实现，左右无限滚动
```
public CalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    mAdapter = new MonthViewAdapter();
    setAdapter(mAdapter);
    setCurrentItem(INIT_POSITION);
}
```
### 2）切换监听器
```
mCalendarView.getAdapter().setOnMonthChangeListener(new OnMonthChangeListener() {
    @Override
    public void onMonthChanged(AbstractMonthView view, Calendar calendar, int month) {
        getActivity().setTitle(new SimpleDateFormat("yyyy-MM", 
            Locale.getDefault()).format(calendar.getTime()));
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
            Toast.makeText(view.getContext(), 
            CalendarUtils.getStringOfMonths(days.month) + "-" + days.dayInMonth, 
                Toast.LENGTH_SHORT).show();
            }
        });
    }
});
```