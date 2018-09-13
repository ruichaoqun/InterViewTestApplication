package com.interview.administrator.interviewtestapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    @Override
    public void onCreate() {
        Log.w("AAA","onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.w("AAA","onDestroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w("AAA","onStartCommand");
        int type = intent.getIntExtra("type",0);
        if(type == 1){
            stopSelf();
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.w("AAA","onBind");
        return new Binder();
    }
}
