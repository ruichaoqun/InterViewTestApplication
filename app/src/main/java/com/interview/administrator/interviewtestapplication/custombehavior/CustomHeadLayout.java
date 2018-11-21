package com.interview.administrator.interviewtestapplication.custombehavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class CustomHeadLayout extends RelativeLayout {
    private List<OnOffsetChangedListener> mListeners;

    public CustomHeadLayout(Context context) {
        super(context);
    }

    public CustomHeadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHeadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addOnOffsetChangedListener(OnOffsetChangedListener listener) {
        if (mListeners == null) {
            mListeners = new ArrayList<>();
        }
        if (listener != null && !mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    public void removeOnOffsetChangedListener(OnOffsetChangedListener listener) {
        if (mListeners != null && listener != null) {
            mListeners.remove(listener);
        }
    }

    void dispatchOffsetUpdates(int offset) {
        // Iterate backwards through the list so that most recently added listeners
        // get the first chance to decide
        if (mListeners != null) {
            for (int i = 0, z = mListeners.size(); i < z; i++) {
                final OnOffsetChangedListener listener = mListeners.get(i);
                if (listener != null) {
                    listener.onOffsetChanged(this, offset);
                }
            }
        }
    }


    public interface OnOffsetChangedListener {

        void onOffsetChanged(CustomHeadLayout headLayout, int verticalOffset);
    }
}
