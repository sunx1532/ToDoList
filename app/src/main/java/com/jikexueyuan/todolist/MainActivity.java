package com.jikexueyuan.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ListView listView;
    String dbPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshList();

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
    }

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
    protected void onDestroy() {
        super.onDestroy();

        if (db != null && db.isOpen()){
            db.close();
        }
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
}
