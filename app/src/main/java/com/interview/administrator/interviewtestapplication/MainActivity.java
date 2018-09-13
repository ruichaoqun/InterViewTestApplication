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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


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
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("asdas")
//                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        }).create().show();
//                Intent intent = new Intent(MainActivity.this,MyService.class);
//                startService(intent);
//                bindService(intent, new ServiceConnection() {
//                    @Override
//                    public void onServiceConnected(ComponentName name, IBinder service) {
//                        Log.w("AAA","onServiceConnected");
//                    }
//
//                    @Override
//                    public void onServiceDisconnected(ComponentName name) {
//
//                    }
//                }, Service.BIND_AUTO_CREATE);
            }
        });

//        findViewById(R.id.text2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,MyService.class);
//                intent.putExtra("type",1);
//                startService(intent);
//            }
//        });
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
