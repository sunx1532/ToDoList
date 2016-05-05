package com.jikexueyuan.todolist;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;
import java.util.ArrayList;
import java.util.Calendar;


public class MyService extends Service {

    SQLiteDatabase db;
    String dbPath;

    ArrayList listHour = null;
    ArrayList listReminder = null;
    ArrayList listId = null;

    AlarmManager alarmMgr;

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

        setAlarm();

        return super.onStartCommand(intent, flags, startId);
    }

    //获取时间和事件,生成列表
    private void getData () {

        dbPath = this.getFilesDir().toString();

        db = SQLiteDatabase.openOrCreateDatabase(dbPath + "/reminder.db3",null);

        String hour = "";
        String reminder = "";
        String id = "";

        String query = "SELECT * FROM event";

        Cursor cursor = db.rawQuery(query, null);

        listHour = new ArrayList();
        listReminder = new ArrayList();
        listId = new ArrayList();

        if (cursor.moveToFirst()) {

            do {
                hour = cursor.getString(cursor.getColumnIndex("clock"));
                listHour.add(hour);

                reminder = cursor.getString(cursor.getColumnIndex("reminder"));
                listReminder.add(reminder);

                id = cursor.getString(cursor.getColumnIndex("_id"));
                listId.add(id);
            }
            while (cursor.moveToNext());
        }

//        System.out.println("listReminder: " + listReminder);
//        System.out.println("listHour: " + listHour);
//        System.out.println("listId: " + listId);
    }

    //事件和事件列表排序
    private void setAlarm() {

        getData ();

        Calendar cal = Calendar.getInstance();
        int hourNow = cal.get(cal.HOUR_OF_DAY);
        int dateNow = cal.get(cal.DAY_OF_MONTH);

        alarmMgr = (AlarmManager)getSystemService(Service.ALARM_SERVICE);
        Intent intent = new Intent(this,Receiver.class);

        for (int i = 0; i < listId.size(); i++){
            if ( Integer.valueOf(listHour.get(i).toString()) > hourNow){
                final Calendar mCalendar = Calendar.getInstance();

                mCalendar.set(mCalendar.HOUR_OF_DAY,Integer.valueOf(listHour.get(i).toString()));
                mCalendar.set(mCalendar.MINUTE,0);
                mCalendar.set(mCalendar.SECOND,0);
                mCalendar.set(mCalendar.MILLISECOND,0);
                //转换格式
                long alarmClock = mCalendar.getTimeInMillis();

//                System.out.println("long:" + alarmClock);
                PendingIntent pi = PendingIntent.getBroadcast(this, Integer.valueOf(listId.get(i).toString()), intent, 0);
                alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmClock, AlarmManager.INTERVAL_DAY, pi);

//                System.out.println("添加闹钟成功");

            }else{
                //设置闹钟在次日播放的时间
                final Calendar mCalendar = Calendar.getInstance();

                mCalendar.set(mCalendar.DAY_OF_MONTH,dateNow+1);
                mCalendar.set(mCalendar.HOUR_OF_DAY, Integer.valueOf(listHour.get(i).toString()));
                mCalendar.set(mCalendar.MINUTE,0);
                mCalendar.set(mCalendar.SECOND,0);
                mCalendar.set(mCalendar.MILLISECOND,0);
                //转换格式
                long alarmClock = mCalendar.getTimeInMillis();

//                System.out.println("long:" + alarmClock);
                PendingIntent pi = PendingIntent.getBroadcast(this, Integer.valueOf(listId.get(i).toString()), intent, 0);
                alarmMgr.set(AlarmManager.RTC_WAKEUP, alarmClock , pi);

//                System.out.println("添加闹钟成功");
            }
        }


    }

}
