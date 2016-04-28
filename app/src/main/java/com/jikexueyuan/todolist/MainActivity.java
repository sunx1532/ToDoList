package com.jikexueyuan.todolist;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ListView listView;
    String dbPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //刷新列表
        refreshList();

        //响应长按删除
        longClick();

        //处理闹钟提醒
        // 指定启动ChangeService组件
        Intent intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);



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
                //reminderIntent.putExtras("Path",1);
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

        getHours();
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

    private String getHours () {

        String hour = "";

        String query = "SELECT * FROM event";

        Cursor cursor = db.rawQuery(query, null);

        ArrayList listHour = null;

        int i = 0;

        if (cursor.moveToFirst()) {

            do {
                hour = cursor.getString(cursor.getColumnIndex("clock"));

                listHour = new ArrayList();

                listHour.add(hour);

//                System.out.println("hourStr1:" + hour);

                i++;
            }
            while (cursor.moveToNext());
        }

        if (listHour != null) {
            for (int j = 0; j <= i; j++) {
                System.out.println(listHour.get(j));
            }
        }

        return hour;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (db != null && db.isOpen()){
            db.close();
        }
    }
}
