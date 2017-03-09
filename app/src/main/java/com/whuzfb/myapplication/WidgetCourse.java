package com.whuzfb.myapplication;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zfb15 on 2017/3/8.
 */

public class WidgetCourse extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        context.startService(new Intent(context,AppWidgetService.class));
        /*
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        ComponentName provider = new ComponentName(context,WidgetCourse.class);
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widgetcourse);
        //createdirs();
        String m="请先登录保存信息";
        String temp[]={"","",""};
        try {
            m=readSDFile("/sdcard/myWeb/course.txt");
            String[] weekday=m.split("\n\n\n");
            long time=System.currentTimeMillis();
            Date date=new Date(time);
            SimpleDateFormat format=new SimpleDateFormat("E");
            switch (format.format(date)){
                case "周一":
                    for(int i=0;i<weekday.length;i++){
                        if(weekday[i].contains("星期1")){
                            for(int t=0;t<3;t++){
                                temp[t]=weekday[i];
                            }
                            //m=weekday[i];
                        }
                    }
                    break;
                case "周二":
                    for(int i=0;i<weekday.length;i++){
                        if(weekday[i].contains("星期2")){
                            for(int t=0;t<3;t++){
                                temp[t]=weekday[i];
                            }
                        }
                    }
                    break;
                case "周三":
                    for(int i=0;i<weekday.length;i++){
                        if(weekday[i].contains("星期3")){
                            for(int t=0;t<3;t++){
                                temp[t]=weekday[i];
                            }
                        }
                    }
                    break;
                case "周四":
                    for(int i=0;i<weekday.length;i++){
                        if(weekday[i].contains("星期4")){
                            for(int t=0;t<3;t++){
                                temp[t]=weekday[i];
                            }
                        }
                    }
                    break;
                case "周五":
                    for(int i=0;i<weekday.length;i++){
                        if(weekday[i].contains("星期5")){
                            for(int t=0;t<3;t++){
                                temp[t]=weekday[i];
                            }
                        }
                    }
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        views.setTextViewText(R.id.tv_widget,temp[0]);
        Intent intent = new Intent(context,WidgetCourse.class);//intent对象
        intent.setAction("ACTION_CHANGE_TEXT");//设置动作

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);//pendingIntent对象是一个广播（可以是活动或服务）

        views.setOnClickPendingIntent(R.id.tv_widget, pendingIntent);//设置视图上textRemoto控件的点击事件
        //appWidgetManager.updateAppWidget(appWidgetIds, views);

        context.startService(new Intent(context,AppWidgetService.class));
        /*
        //创建一个intent对象
        Intent intent = new Intent(context,NetLogin.class);
        //创建一个PendingIntent
        PendingIntent pending = PendingIntent.getActivity(context, 0, intent, 0);
        //Intent numberIntent = new Intent("ACTION_MAKE_NUMBER");
        views.setOnClickPendingIntent(R.id.tv_widget, pending);
        */

        /*
        manager.updateAppWidget(provider, views);
        */

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        ComponentName provider = new ComponentName(context,WidgetCourse.class);
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widgetcourse);
        PendingIntent pendingIntent = PendingIntent.getService(context,0,new Intent(context,AppWidgetService.class),0);
        views.setOnClickPendingIntent(R.id.tv_widget,pendingIntent);
        //context.startService();
        manager.updateAppWidget(provider, views);

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d("TAG","onDeleted方法调用了");
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d("TAG","onEnabled方法调用了");
        //启动MyService
        //Intent intent = new Intent(context,MyService.class);
        //context.startService(intent);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d("TAG","onDisabled方法调用了");
        //停止MyService
        //Intent intent = new Intent(context,MyService.class);
        //context.stopService(intent);
    }


    public String readSDFile(String fileName) throws IOException {

        File file = new File(fileName);

        FileInputStream fis = new FileInputStream(file);

        int length = fis.available();

        byte [] buffer = new byte[length];
        fis.read(buffer);

        String res = EncodingUtils.getString(buffer, "UTF-8");

        fis.close();
        return res;
    }

}
