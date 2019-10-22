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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.interview.administrator.interviewtestapplication.DynamicSizeImageView;
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
        setHeaderTopBottomOffset(parent,child,mOriginalOffsetTop);
        return handled;
    }

    @Override
    int setHeaderTopBottomOffset(CoordinatorLayout parent, CustomHeadLayout header, int newOffset, int minOffset, int maxOffset) {
        int cnsumed = super.setHeaderTopBottomOffset(parent,header,newOffset,minOffset,maxOffset);
        float rate =(float)(mMinOffsetTop - getTopAndBottomOffset())/(float)(mMinOffsetTop - mOriginalOffsetTop);
        header.dispatchOffsetUpdates(getTopAndBottomOffset(),rate);
        return cnsumed;
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
            int maxOffset = 0;
            if(type == ViewCompat.TYPE_NON_TOUCH){
                maxOffset = mOriginalOffsetTop;
            }
            consumed[1] = scroll(coordinatorLayout, child, dy, getMaxDragOffset(child), maxOffset);
        }
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull CustomHeadLayout child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if (dyUnconsumed < 0) {
            int maxOffset = 0;
            if(type == ViewCompat.TYPE_NON_TOUCH){
                maxOffset = mOriginalOffsetTop;
            }
            // If the scrolling view is scrolling down but not consuming, it's probably be at
            // the top of it's content
            scroll(coordinatorLayout, child, dyUnconsumed,
                    getMaxDragOffset(child), maxOffset);
        }
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull CustomHeadLayout child, @NonNull View target, int type) {
        if(type == ViewCompat.TYPE_TOUCH ){
            if(getTopAndBottomOffset() > mOriginalOffsetTop){
                fling(coordinatorLayout,child,mOriginalOffsetTop,mOriginalOffsetTop,50);
            }
        }
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
                int offset = (dependency.getBottom() - child.getTop())
                + getVerticalLayoutGap()
                        - getOverlapPixelsForOffset(dependency);
                ViewCompat.offsetTopAndBottom(child, offset);
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



    public static class ImageBehavior extends CoordinatorLayout.Behavior<DynamicSizeImageView>{
        public ImageBehavior() {
        }

        public ImageBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, DynamicSizeImageView child, View dependency) {
            return dependency instanceof CustomHeadLayout;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, DynamicSizeImageView child, View dependency) {
            CustomHeadLayout layout = (CustomHeadLayout) dependency;
            CustomBehavior behavior = (CustomBehavior) ((CoordinatorLayout.LayoutParams)layout.getLayoutParams()).getBehavior();
//            child.setPadding(0,0,0,-behavior.getTopAndBottomOffset());
            ViewGroup.LayoutParams params = dependency.getLayoutParams();
//            Log.w("AAA",layout.getMeasuredHeight()+behavior.getTopAndBottomOffset()+"");
            child.setImageHeight(layout.getMeasuredHeight()+behavior.getTopAndBottomOffset());
//            Log.w("AAA",layout.getMeasuredHeight()+"     "+behavior.getTopAndBottomOffset());
//            params.height = layout.getMeasuredHeight() + behavior.getTopAndBottomOffset();
////            child.setLayoutParams(params);
//            Log.w("AAA","height-->"+sum+"   "+params.height);
            return false;
        }

        @Override
        public boolean onMeasureChild(CoordinatorLayout parent, DynamicSizeImageView child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
            int height = (int) (UiUtils.getScreenWidth() * 1.5f);
            child.measure(parentWidthMeasureSpec,View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
//            ViewGroup.LayoutParams params = child.getLayoutParams();
////            Log.w("AAA","onMeasureChild-->"+sum+"   "+params.height);
//            sum++;
//            child.measure(parentWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(params.height, View.MeasureSpec.EXACTLY));
//            final int childLpHeight = child.getLayoutParams().height;
//            if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT
//                    || childLpHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
//                // If the menu's height is set to match_parent/wrap_content then measure it
//                // with the maximum visible height
//
//                final List<View> dependencies = parent.getDependencies(child);
//                final CustomHeadLayout header = findFirstDependency(dependencies);
//                if (header != null) {
//                    CustomBehavior behavior = (CustomBehavior) ((CoordinatorLayout.LayoutParams)header.getLayoutParams()).getBehavior();
//                    child.measure(parentWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(header.getMeasuredHeight() + behavior.mOriginalOffsetTop, View.MeasureSpec.EXACTLY));
//                    return true;
//                }
//            }
            return true;
        }

        protected CustomHeadLayout findFirstDependency(List<View> views) {
            for (int i = 0, z = views.size(); i < z; i++) {
                View view = views.get(i);
                if (view instanceof CustomHeadLayout) {
                    return (CustomHeadLayout) view;
                }
            }
            return null;
        }


        @Override
        public boolean onLayoutChild(CoordinatorLayout parent, DynamicSizeImageView child, int layoutDirection) {
//            Log.w("AAA","onLayoutChild-->");
            parent.onLayoutChild(child,layoutDirection);
            return true;
        }
    }
}
