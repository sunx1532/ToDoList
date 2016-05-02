package com.jikexueyuan.todolist;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import com.jikexueyuan.todolist.Receiver.*;

import java.util.ArrayList;

public class MyService extends Service {
//    public MyService() {
//    }

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

//        final Intent i= intent;
//
//        new Thread(){
//            public  void run(){
//                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        Intent i = new Intent(this,MainActivity.class);
//
//                ArrayList list = i.getIntegerArrayListExtra("list");
//
//                PendingIntent pi = PendingIntent.getBroadcast(MyService.this,0,i,0);
//
////        for (int i = 0; i < )
//                alarmManager.set(alarmManager.RTC_WAKEUP, (long) list.get(5), pi);
//
//                NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//
//                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MyService.this);
//
//                mBuilder.setContentTitle("Event Alarm"); //设置通知栏标题
//                mBuilder.setWhen(System.currentTimeMillis()); //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
//                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//                mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
//
//                int notifyId = 1;
//
//                nm.notify(notifyId, mBuilder.build());

//        nm.cancel(notifyId);
//
//                Notification notification = mBuilder.build();
//                notification.flags = Notification.FLAG_AUTO_CANCEL;
//            }
//        }.start();

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
////        Intent intentR = new Intent(this,Receiver.class);
        intent = new Intent(this, Receiver.class);
//
//        ArrayList list = intent.getIntegerArrayListExtra("list");
//        System.out.println("service:"+list);
//
        PendingIntent pi = PendingIntent.getBroadcast(this,0,intent,0);
//
        alarmManager.set(alarmManager.RTC_WAKEUP, 1462287600000L, pi);

//        for (int i = 0; i < list.size();i++){
//            alarmManager.set(alarmManager.RTC_WAKEUP, (long) list.get(i), pi);
//        }

//        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//
//        mBuilder.setContentTitle("Event Alarm"); //设置通知栏标题
//        mBuilder.setWhen(System.currentTimeMillis()); //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
//        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
//
//        int notifyId = 1;
//
//        nm.notify(notifyId, mBuilder.build());
//
////        nm.cancel(notifyId);
//
//        Notification notification = mBuilder.build();
//        notification.flags = Notification.FLAG_AUTO_CANCEL;


//        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        int icon = android.R.drawable.sym_def_app_icon;
//        long when = System.currentTimeMillis() + 2000;
//        Notification n = new Notification.Builder();
//        n.defaults = Notification.DEFAULT_SOUND;
//        n.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        n.setLatestEventInfo(this, "通知栏demo提醒title", "通知栏demo提醒text", pi);
//        nm.notify(0, n);
//
//        return super.onStartCommand(intent,flags,startId);

        //处理闹钟提醒

        // 创建PendingIntent对象
//        final PendingIntent pi = PendingIntent.getService(MyService.this, 0, intent, 0);
//
//        AlarmManager aManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
//
//        aManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+2000, pi);
//
//



        return super.onStartCommand(intent, flags, startId);
    }




}
