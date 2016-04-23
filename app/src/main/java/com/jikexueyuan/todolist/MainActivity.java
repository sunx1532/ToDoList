package com.jikexueyuan.todolist;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"reminder.db3",null);

        listView = (ListView) findViewById(R.id.listView);


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
                startActivityForResult(reminderIntent,0);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //DB相关
}
