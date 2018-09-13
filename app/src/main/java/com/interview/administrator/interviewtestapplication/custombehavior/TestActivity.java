package com.interview.administrator.interviewtestapplication.custombehavior;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.interview.administrator.interviewtestapplication.R;
import com.interview.administrator.interviewtestapplication.UiUtils;

public class TestActivity extends AppCompatActivity {
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_test);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setPadding(0, UiUtils.getStatusBarHeight(),0,0);
    }
}
