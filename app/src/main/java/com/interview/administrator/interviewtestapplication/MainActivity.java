package com.interview.administrator.interviewtestapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Test1Activity.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.w(TAG,"onConfigurationChanged    "+newConfig.orientation);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onStart() {
        Log.w(TAG,"onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.w(TAG,"onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.w(TAG,"onRestart");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.w(TAG,"onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.w(TAG,"onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.w(TAG,"onDestroy");
        super.onDestroy();
    }
}
