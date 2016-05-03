package com.jikexueyuan.todolist;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ListView listView;
    String dbPath;
    ArrayList listHour = null;
    ArrayList listReminder = null;

    public ArrayList listHourNew = null;

    AlarmManager alarmMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //刷新列表
        refreshList();

        //响应长按删除
        longClick();

//        //处理闹钟提醒
//        // 指定启动ChangeService组件
//        Intent intent = new Intent(MainActivity.this, MyService.class);
//        startService(intent);

        //设置闹钟
//        setAlarm();


    }

    // 创建一个菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //设置监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addEvent:
                Intent reminderIntent = new Intent(this,AddReminder.class);
                reminderIntent.putExtra("dbPath",dbPath);
                startActivityForResult(reminderIntent,0);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //刷新列表
    private void refreshList (){
        dbPath = this.getFilesDir().toString();

        db = SQLiteDatabase.openOrCreateDatabase(dbPath + "/reminder.db3",null);

        System.out.println(dbPath + "/reminder.db3");

        listView = (ListView) findViewById(R.id.listView);


        try{
            Cursor cursor = db.rawQuery("select * from event", null);
            inflateList(cursor);
        }catch(SQLiteException se){
            db.execSQL("create table event (_id integer" + " primary key autoincrement, " + "clock varchar(50)," + " reminder varchar(255))");
        }

        //生成时间和事件列表
        getData();


    }

    //创建ListView
    private void inflateList(Cursor cursor){
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                MainActivity.this,
                R.layout.line, cursor,
                new String[] { "clock", "reminder" },
                new int[] {R.id.clock, R.id.reminder },
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 0:
                switch (resultCode){
                    case 0:
                        refreshList();
                        break;
                }
                break;
        }
    }

    //长按删除
    private void longClick() {
        //设置长按监听
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                //建立一个对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Real delete the data?");

                String strYes = "Yes";
                String strNo = "No";

                final String[] options = {strYes, strNo};

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Cursor cursor = (Cursor) listView.getAdapter().getItem(position);
                                String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("_id")));
                                db.delete("event","_id = ?",new String[]{id});
                                Toast.makeText(MainActivity.this,"Delete Successful.",Toast.LENGTH_SHORT).show();
                                refreshList();
                                break;
                            case 1:
                                Toast.makeText(MainActivity.this,"Delete Canceled.",Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
                builder.show();

                return false;
            }
        });
    }

    //获取时间和事件,生成列表
    private void getData () {

        String hour = "";
        String reminder = "";

        String query = "SELECT * FROM event";

        Cursor cursor = db.rawQuery(query, null);

        listHour = new ArrayList();
        listReminder = new ArrayList();

        if (cursor.moveToFirst()) {

            do {
                hour = cursor.getString(cursor.getColumnIndex("clock"));
                listHour.add(hour);

                reminder = cursor.getString(cursor.getColumnIndex("reminder"));
                listReminder.add(reminder);
            }
            while (cursor.moveToNext());
        }

        System.out.println(listReminder);
        System.out.println(listHour);
    }

    //事件和事件列表排序
    private void setAlarm(){

        listHourNew = new ArrayList();

//        alarmMgr = (AlarmManager)getSystemService(Service.ALARM_SERVICE);

//        Intent intent = new Intent(MainActivity.this,MyService.class);
        // 创建PendingIntent对象
//        final PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);


        //获取当前的日期和小时数用于对比
        Calendar cal = Calendar.getInstance();
        int hourNow = cal.get(cal.HOUR_OF_DAY);
        int dateNow = cal.get(cal.DAY_OF_MONTH);

        //排序

        for (int i = 0; i < listHour.size(); i++){
            if (Integer.valueOf(listHour.get(i).toString()) > hourNow){
                //设置闹钟在当天播放的时间
                final Calendar mCalendar = Calendar.getInstance();

                mCalendar.set(mCalendar.HOUR_OF_DAY,Integer.valueOf(listHour.get(i).toString()));
                mCalendar.set(mCalendar.MINUTE,0);
                mCalendar.set(mCalendar.SECOND,0);
                //转换格式
                long alarmClock = mCalendar.getTimeInMillis();

                System.out.println("long:" + alarmClock);

                listHourNew.add(alarmClock);

//                alarmMgr.set(AlarmManager.RTC_WAKEUP, alarmClock, pi);
            }
            else{
                //设置闹钟在次日播放的时间
                final Calendar mCalendar = Calendar.getInstance();

                mCalendar.set(mCalendar.DAY_OF_MONTH,dateNow+1);
                mCalendar.set(mCalendar.HOUR_OF_DAY,Integer.valueOf(listHour.get(i).toString()));
                mCalendar.set(mCalendar.MINUTE,0);
                mCalendar.set(mCalendar.SECOND,0);
                //转换格式
                long alarmClock = mCalendar.getTimeInMillis();

                System.out.println("long:" + alarmClock);

                listHourNew.add(alarmClock);

//                alarmMgr.set(AlarmManager.RTC_WAKEUP, alarmClock, pi);
            }
        }

        Intent serviceIntent = new Intent(this,MyService.class);

        serviceIntent.putIntegerArrayListExtra("list",listHourNew);

        System.out.println("MainActivity:"+listHourNew);

        this.startService(serviceIntent);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (db != null && db.isOpen()){
            db.close();
        }
    }
}
