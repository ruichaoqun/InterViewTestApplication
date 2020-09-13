package com.interview.administrator.interviewtestapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

import java.lang.ref.WeakReference;

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
 * <td>2018-09-11 17:00</td>
 * <td>${User}</td>
 * <td>All</td>
 * <td>Created.</td>
 * </tr>
 * </table>
 */
@CoordinatorLayout.DefaultBehavior(CustomAppbarLayout.Behavior.class)
public class CustomAppbarLayout extends AppBarLayout {
    public CustomAppbarLayout(Context context) {
        super(context);
    }

    public CustomAppbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public static class Behavior extends AppBarLayout.Behavior {
        private static final int ANIMATE_TO_START_DURATION = 300;
        private static final float DECELERATE_INTERPOLATION_FACTOR = 2f;
        private int initialOffset;
        private int mFrom;
        private WeakReference<AppBarLayout> layoutWeakReference;

        private final Animation mAnimateToStartPosition = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                moveToStart(interpolatedTime);
            }
        };


        void moveToStart(float interpolatedTime) {
            int targetTop = 0;
            targetTop = (mFrom + (int) ((initialOffset - mFrom) * interpolatedTime));
            int offset = targetTop - layoutWeakReference.get().getTop();
            setTopAndBottomOffset(offset);
        }


        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {
            return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type);
        }

        @Override
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        }

        @Override
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        }

        @Override
        public boolean onMeasureChild(CoordinatorLayout parent, AppBarLayout child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
            final int height = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 1.5);
            child.measure(parentWidthMeasureSpec,MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY));
            return true;
        }

        @Override
        public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl, int layoutDirection) {
            layoutWeakReference = new WeakReference<>(abl);
            boolean b = super.onLayoutChild(parent, abl, layoutDirection);
            initialOffset = -abl.getMeasuredHeight() / 3 - 100;
            setTopAndBottomOffset(initialOffset);
            return b;
        }

        @Override
        public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
            switch (ev.getActionMasked()){
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    int offset = getTopAndBottomOffset();
                    if(offset > initialOffset){
                        animateOffsetToStartPosition(mFrom,child);
                        return true;
                    }
            }
            return super.onTouchEvent(parent, child, ev);
        }

        private void animateOffsetToStartPosition(int mFrom,View view) {
            this.mFrom = mFrom;
            mAnimateToStartPosition.reset();
            mAnimateToStartPosition.setDuration(ANIMATE_TO_START_DURATION);
            mAnimateToStartPosition.setInterpolator(new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR));
            view.clearAnimation();
            view.startAnimation(mAnimateToStartPosition);
        }
    }
}
