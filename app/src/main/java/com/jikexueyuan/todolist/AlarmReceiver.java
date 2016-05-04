package com.jikexueyuan.todolist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Receiver: 启动哈哈");
        Intent i = new Intent(context,Notification.class);
        i.addFlags(i.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
