package com.jikexueyuan.todolist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Receiver extends BroadcastReceiver {
    public Receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() == "android.intent.action.BOOT_COMPLETED"){
            Intent i = new Intent(context,MyService.class);
            context.startService(i);
        }else{
            Intent i = new Intent(context,Notification.class);
            i.addFlags(i.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }


    }
}
