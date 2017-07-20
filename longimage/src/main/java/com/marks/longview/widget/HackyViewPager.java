package com.marks.longview.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/***
 * @author marks.luo
 * @Description: ()
 * @date:2017-03-17 16:01
 */
public class HackyViewPager extends ViewPager {
    private boolean isLocked;

    public HackyViewPager(Context context) {
        this(context, null);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        isLocked = false;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        if (!isLocked) {
            try {
                return super.onInterceptHoverEvent(event);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !isLocked && super.onTouchEvent(ev);
    }

    public void toggleLock() {//切换
        isLocked = !isLocked;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
