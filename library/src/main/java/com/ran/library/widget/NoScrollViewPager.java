package com.ran.library.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * effect：禁用水平滑动的ViewPager（一般用于APP主页的ViewPager + Fragment）
 */
public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 不拦截这个事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    // 不处理这个事件
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

}