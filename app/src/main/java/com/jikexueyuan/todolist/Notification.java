package com.jikexueyuan.todolist;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by rogersun on 16/5/2.
 */
public class Notification extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Notification", "Start");

        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Notification.this);

        mBuilder.setContentTitle("Event Alarm"); //设置通知栏标题
        mBuilder.setWhen(System.currentTimeMillis()); //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setDefaults(android.app.Notification.DEFAULT_VIBRATE);

        int notifyId = 1;

        nm.notify(notifyId, mBuilder.build());

        finish();
    }
}
