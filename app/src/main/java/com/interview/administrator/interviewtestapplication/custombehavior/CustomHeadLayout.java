package com.interview.administrator.interviewtestapplication.custombehavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class CustomHeadLayout extends RelativeLayout {
    public CustomHeadLayout(Context context) {
        super(context);
    }

    public CustomHeadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHeadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.w("AAA",ev.getActionMasked()+"");
        return super.dispatchTouchEvent(ev);
    }
}
