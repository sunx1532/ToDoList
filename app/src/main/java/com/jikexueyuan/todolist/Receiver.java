package com.jikexueyuan.todolist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
    public Receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"哈哈哈",Toast.LENGTH_LONG).show();
        System.out.println("Receiver: 启动哈哈");
        Intent i = new Intent(context,MyService.class);
        context.startService(i);
    }
}
