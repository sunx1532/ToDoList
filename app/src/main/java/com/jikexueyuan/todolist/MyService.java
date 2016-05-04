package com.jikexueyuan.todolist;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate()
    {
        Log.i("Service","onCreate");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Service","Service Start");

        return super.onStartCommand(intent, flags, startId);
    }




}
