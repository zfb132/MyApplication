package com.whuzfb.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
     StringBuilder str=new StringBuilder();
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getExtras();
        //此处pdu（Protocol Description Unit），通过Bundle类对象获取pdus
        Object[] pdus=(Object[])bundle.get("pdus");
        SmsMessage[] msg=new SmsMessage[pdus.length];
        for(int i=0;i<pdus.length;i++){
            msg[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
        }
        for(SmsMessage msms:msg){
            str.append("发信人：+\n");
            str.append(msms.getDisplayOriginatingAddress());
            str.append("\n信息内容：\n");
            str.append(msms.getDisplayMessageBody());
        }
        Toast.makeText(context,str.toString(),Toast.LENGTH_LONG).show();
    }
}
