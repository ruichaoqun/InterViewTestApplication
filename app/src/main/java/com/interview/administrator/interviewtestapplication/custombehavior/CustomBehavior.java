package com.interview.administrator.interviewtestapplication.custombehavior;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.interview.administrator.interviewtestapplication.UiUtils;
import com.interview.administrator.interviewtestapplication.custombehavior.CustomHeaderScrollingViewBehavior;

import java.util.List;

/**
 * <p>Description.</p>
 * <p>
 * <b>Maintenance History</b>:
 * <table>
 * <tr>
 * <th>Date</th>
 * <th>Developer</th>
 * <th>Target</th>
 * <th>Content</th>
 * </tr>
 * <tr>
 * <td>2018-09-11 15:39</td>
 * <td>${User}</td>
 * <td>All</td>
 * <td>Created.</td>
 * </tr>
 * </table>
 */
public class CustomBehavior extends CoordinatorLayout.Behavior<View>{

    public CustomBehavior() {
    }

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        int height = (int) (UiUtils.getScreenWidth() * 1.5f);
        parent.onMeasureChild(child,parentWidthMeasureSpec,0, View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY),0);
        return true;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        parent.onLayoutChild(child, ViewCompat.LAYOUT_DIRECTION_LTR);
        return true;
    }

    public static class ScrollingViewBehavior extends CustomHeaderScrollingViewBehavior{

        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
            return dependency instanceof View;
        }

        @Override
        protected View findFirstDependency(List<View> views) {
            for (int i = 0, z = views.size(); i < z; i++) {
                View view = views.get(i);
                if (view instanceof RelativeLayout) {
                    return (RelativeLayout) view;
                }
            }
            return null;
        }

        @Override
        int getScrollRange(View v) {
            return v.getMeasuredHeight() - UiUtils.getStatusBarHeight() - UiUtils.dp2px(45);
        }
    }
}
