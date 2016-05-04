package com.jikexueyuan.todolist;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AddReminder extends Activity {

    private EditText etClock;
    private EditText etReminder;

    SQLiteDatabase db;

    int clock_int;


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

                    setResult(RESULT_OK);

                    Toast.makeText(AddReminder.this,"Saved successfully",Toast.LENGTH_SHORT).show();

                    finish();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()){
            db.close();
        }
    }
}
