package com.jikexueyuan.todolist;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddReminder extends Activity {

    private EditText etClock;
    private EditText etReminder;

    SQLiteDatabase db;

    int clock_int;

    AlarmManager alarmMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        etClock = (EditText) findViewById(R.id.etClock);
        etReminder = (EditText) findViewById(R.id.etReminder);

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String clock = etClock.getText().toString();
                String reminder = etReminder.getText().toString();

                clock_int = Integer.parseInt(clock);

                if (clock_int >= 0 && clock_int < 24){
                    Intent i = getIntent();

                    String dbPath = i.getStringExtra("dbPath");

                    db = SQLiteDatabase.openOrCreateDatabase(dbPath + "/reminder.db3",null);

                    insertData(db,clock,reminder);

                    setAlarm();

                    setResult(0);

                    Toast.makeText(AddReminder.this,"Saved successfully",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(AddReminder.this,"Please input a clock between 0 - 24.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //插入数据
    private void insertData(SQLiteDatabase db, String clock, String reminder){
        db.execSQL("insert into event values(null,?,?)", new String[] {clock,reminder});
    }

    //事件和事件列表排序
    private void setAlarm() {
        Calendar cal = Calendar.getInstance();
        int hourNow = cal.get(cal.HOUR_OF_DAY);
        int dateNow = cal.get(cal.DAY_OF_MONTH);

        alarmMgr = (AlarmManager)getSystemService(Service.ALARM_SERVICE);
        Intent intent = new Intent(this,Receiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);

        if (clock_int > hourNow){
            final Calendar mCalendar = Calendar.getInstance();

            mCalendar.set(mCalendar.HOUR_OF_DAY,clock_int);
            mCalendar.set(mCalendar.MINUTE,0);
            mCalendar.set(mCalendar.SECOND,0);
            //转换格式
            long alarmClock = mCalendar.getTimeInMillis();

            System.out.println("long:" + alarmClock);

            alarmMgr.set(AlarmManager.RTC_WAKEUP, alarmClock, pi);

            System.out.println("添加闹钟成功");

        }else{
            //设置闹钟在次日播放的时间
            final Calendar mCalendar = Calendar.getInstance();

            mCalendar.set(mCalendar.DAY_OF_MONTH,dateNow+1);
            mCalendar.set(mCalendar.HOUR_OF_DAY,clock_int);
            mCalendar.set(mCalendar.MINUTE,0);
            mCalendar.set(mCalendar.SECOND,0);
            //转换格式
            long alarmClock = mCalendar.getTimeInMillis();

            System.out.println("long:" + alarmClock);

            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, alarmClock, 24*60*60*1000, pi);

            System.out.println("添加闹钟成功");
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()){
            db.close();
        }
    }
}
