package com.jikexueyuan.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddReminder extends Activity {

    private EditText etClock;
    private EditText etReminder;

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


            }
        });
    }
}
