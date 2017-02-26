package com.whuzfb.myapplication;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by zfb15 on 2017/2/10.
 */

public class MyDeviceAdmin extends DeviceAdminReceiver{
    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        //这里处理不可编辑设备，可以造成死机状态
        showToast(context,"禁用设备管理器请求",Toast.LENGTH_SHORT);
        return super.onDisableRequested(context, intent);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        showToast(context,"设备管理器，收到消息",Toast.LENGTH_SHORT);
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        //设备管理可用
        showToast(context,"启用设备管理器",Toast.LENGTH_SHORT);
        super.onEnabled(context, intent);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        //设备管理不可用
        showToast(context,"设备管理器激活失败，用户取消",Toast.LENGTH_SHORT);
        super.onDisabled(context, intent);
    }

    public void showToast(Context context,String str,int duration) {
        //Toast.makeText(this, str, duration).show();
        Toast toast = Toast.makeText(context, str, duration);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }
}
