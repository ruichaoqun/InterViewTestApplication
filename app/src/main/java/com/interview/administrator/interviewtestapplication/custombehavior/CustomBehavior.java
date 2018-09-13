package com.interview.administrator.interviewtestapplication.custombehavior;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.interview.administrator.interviewtestapplication.UiUtils;
import com.interview.administrator.interviewtestapplication.custombehavior.CustomHeaderScrollingViewBehavior;

import java.lang.ref.WeakReference;
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
public class CustomBehavior extends HeaderBehavior<CustomHeadLayout>{
    private int mOriginalOffsetTop;
    private int mMinOffsetTop;
    private int mScrollRange;

    private ValueAnimator mOffsetAnimator;
    private WeakReference<View> mLastNestedScrollingChildRef;


    public CustomBehavior() {
    }

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, CustomHeadLayout child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        int height = (int) (UiUtils.getScreenWidth() * 1.5f);
        child.measure(parentWidthMeasureSpec,View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        mOriginalOffsetTop = -child.getMeasuredHeight() / 3 - UiUtils.dp2px(20);
        int minHeaderHeight = 0;
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);
            if(view instanceof Toolbar){
                minHeaderHeight = view.getMeasuredHeight();
                break;
            }
        }
        mMinOffsetTop = - child.getMeasuredHeight()  + minHeaderHeight;
        mScrollRange = -mMinOffsetTop;
        return true;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, CustomHeadLayout child, int layoutDirection) {
        boolean handled = super.onLayoutChild(parent, child, layoutDirection);
        setTopAndBottomOffset(mOriginalOffsetTop);
        return handled;
    }

    @Override
    int setHeaderTopBottomOffset(CoordinatorLayout parent, CustomHeadLayout header, int newOffset, int minOffset, int maxOffset) {
        return super.setHeaderTopBottomOffset(parent,header,newOffset,minOffset,maxOffset);
//        final int curOffset = getTopBottomOffsetForScrollingSibling();
//        int consumed = 0;
//
//        if (minOffset != 0 && curOffset >= minOffset && curOffset <= maxOffset) {
//            // If we have some scrolling range, and we're currently within the min and max
//            // offsets, calculate a new offset
//            newOffset = MathUtils.clamp(newOffset, minOffset, maxOffset);
//            if (curOffset != newOffset) {
//
//                final boolean offsetChanged = setTopAndBottomOffset(newOffset);
//
//                // Update how much dy we have consumed
//                consumed = curOffset - newOffset;
//
////                if (!offsetChanged && appBarLayout.hasChildWithInterpolator()) {
////                    // If the offset hasn't changed and we're using an interpolated scroll
////                    // then we need to keep any dependent views updated. CoL will do this for
////                    // us when we move, but we need to do it manually when we don't (as an
////                    // interpolated scroll may finish early).
////                    parent.dispatchDependentViewsChanged(appBarLayout);
////                }
////
////                // Dispatch the updates to any listeners
////                appBarLayout.dispatchOffsetUpdates(getTopAndBottomOffset());
////
////                // Update the AppBarLayout's drawable state (for any elevation changes)
////                updateAppBarLayoutDrawableState(coordinatorLayout, appBarLayout, newOffset,
////                        newOffset < curOffset ? -1 : 1, false);
//            }
//        }
//
//        return consumed;
    }

    @Override
    int getScrollOriginalOffset(CustomHeadLayout view) {
        return mOriginalOffsetTop;
    }

    @Override
    boolean canDragView(CustomHeadLayout view) {
        return true;
    }

    @Override
    int getMaxDragOffset(CustomHeadLayout view) {
        return mMinOffsetTop;
    }

    @Override
    int getScrollRangeForDragFling(CustomHeadLayout view) {
        return -mMinOffsetTop;
    }

    public int getScrollRange() {
        return mScrollRange;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull CustomHeadLayout child, @NonNull View directTargetChild, @NonNull View target, int nestedScrollAxes, int type) {
        final boolean started = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0
                && coordinatorLayout.getHeight() - directTargetChild.getHeight() <= child.getHeight();
        if (started && mOffsetAnimator != null) {
            // Cancel any offset animation
            mOffsetAnimator.cancel();
        }

        // A new nested scroll has started so clear out the previous ref
        mLastNestedScrollingChildRef = null;

        return started;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull CustomHeadLayout child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (dy > 0) {
//            Log.w("AAA","dy-->"+dy);
            Log.w("AAA","onNestedPreScroll-->");
            consumed[1] = scroll(coordinatorLayout, child, dy, getMaxDragOffset(child), 0);
        }
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull CustomHeadLayout child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if (dyUnconsumed < 0) {
            // If the scrolling view is scrolling down but not consuming, it's probably be at
            // the top of it's content
            Log.w("AAA","onNestedScroll-->");
            scroll(coordinatorLayout, child, dyUnconsumed,
                    getMaxDragOffset(child), 0);
        }
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull CustomHeadLayout child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        Log.w("AAA","onNestedFling-->");
        return false;
    }

    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull CustomHeadLayout child, @NonNull View target, float velocityX, float velocityY) {
        Log.w("AAA","onNestedPreFling-->");
        return false;
    }

    public static class ScrollingViewBehavior extends CustomHeaderScrollingViewBehavior{

        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
            return dependency instanceof CustomHeadLayout;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
            offsetChildAsNeeded(parent, child, dependency);
            return false;
        }

        private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {
            final CoordinatorLayout.Behavior behavior =
                    ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
            if (behavior instanceof CustomBehavior) {
                ViewCompat.offsetTopAndBottom(child, (dependency.getBottom() - child.getTop())
                        + getVerticalLayoutGap()
                        - getOverlapPixelsForOffset(dependency));
            }
        }

        @Override
        protected View findFirstDependency(List<View> views) {
            for (int i = 0, z = views.size(); i < z; i++) {
                View view = views.get(i);
                if (view instanceof CustomHeadLayout) {
                    return (CustomHeadLayout) view;
                }
            }
            return null;
        }

        @Override
        int getScrollRange(View v) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) v.getLayoutParams();
            CustomBehavior behavior = (CustomBehavior) params.getBehavior();
            return behavior.getScrollRange();
        }
    }
}
