package com.interview.administrator.interviewtestapplication.custombehavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;


import com.interview.administrator.interviewtestapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CustomHeadLayout extends LinearLayout {
    private List<OnOffsetChangedListener> mListeners;
    public CustomHeadLayout(Context context) {
        this(context,null);
    }

    public CustomHeadLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomHeadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
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

    void dispatchOffsetUpdates(int offset,float rate) {
        getChildAt(0).setAlpha(rate);
        if(rate == 0){
            findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
            findViewById(R.id.iv_search).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.iv_back).setVisibility(View.GONE);
            findViewById(R.id.iv_search).setVisibility(View.GONE);
        }
        if (mListeners != null) {
            for (int i = 0, z = mListeners.size(); i < z; i++) {
                final OnOffsetChangedListener listener = mListeners.get(i);
                if (listener != null) {
                    listener.onOffsetChanged(this, offset,rate);
                }
            }
        }
    }


    public interface OnOffsetChangedListener {

        void onOffsetChanged(CustomHeadLayout headLayout, int verticalOffset,float rate);
    }
}
