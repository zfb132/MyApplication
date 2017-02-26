package com.whuzfb.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by zfb15 on 2017/2/19.
 */

public class AutoBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action_boot="android.intent.action.BOOT_COMPLETED";
        if (intent.getAction().equals(action_boot)){
            Intent mintent=new Intent(context,MainActivity.class); //接收到广播后，跳转到MainActivity
            mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mintent);
        }
    }
}
