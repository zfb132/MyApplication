package com.whuzfb.myapplication;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zfb15 on 2017/3/9.
 */

public class AppWidgetService extends Service {
    //MyReceiver receiver;
    public int t=0;
    public int s=0;
    public String[] weekday;
    public String temp[]=new String[3];
    @Override
    public void onCreate() {
        super.onCreate();
        String m="请先登录保存信息";
        try {
            m = readSDFile("/sdcard/myWeb/course.txt");
            m=m.replaceAll("null","无");
            weekday = m.split("\n\n\n");

        }catch (Exception e){
            e.printStackTrace();
        }

        /*
        //动态注册广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("ACTION_CHANGE_TEXT");
        registerReceiver(receiver,filter);
        Log.d("TAG","建立service");
        */
        RemoteViews views=new RemoteViews(this.getPackageName(),R.layout.widgetcourse);//远程视图
        ComponentName provider=new ComponentName(this, WidgetCourse.class);//提供者
        AppWidgetManager manager=AppWidgetManager.getInstance(this);//小部件管理器
        manager.updateAppWidget(provider, views);//更新

        long time=System.currentTimeMillis();
        Date date=new Date(time);
        SimpleDateFormat format=new SimpleDateFormat("E");
        Log.d("tag", weekday.length+"");
        switch (format.format(date)){
            case "周一":
                for(int i=0;i<weekday.length;i++){
                    if(weekday[i].contains("星期1")){
                        temp[t]=weekday[i];
                        t++;
                    }
                }
                break;
            case "周二":
                for(int i=0;i<weekday.length;i++){
                    if(weekday[i].contains("星期2")){
                        temp[t]=weekday[i];
                        t++;
                    }
                }
                break;
            case "周三":
                for(int i=0;i<weekday.length;i++){
                    if(weekday[i].contains("星期3")){
                        temp[t]=weekday[i];
                        t++;
                    }
                }
                break;
            case "周四":
                for(int i=0;i<weekday.length;i++){
                    if(weekday[i].contains("星期4")){
                        temp[t]=weekday[i];
                        t++;
                    }
                }
                break;
            case "周五":
                for(int i=0;i<weekday.length;i++){
                    if(weekday[i].contains("星期5")){
                        temp[t]=weekday[i];
                        t++;
                    }
                }
                break;
        }
    }
    @Override
    public void onDestroy() {
        //unregisterReceiver(receiver);
        t=0;
        s=0;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        RemoteViews views=new RemoteViews(this.getPackageName(),R.layout.widgetcourse);//远程视图
        ComponentName provider=new ComponentName(this, WidgetCourse.class);//提供者
        AppWidgetManager manager=AppWidgetManager.getInstance(this);//小部件管理器
        manager.updateAppWidget(provider, views);//更新

        if(s>=3){
            s=0;
        }

        if(temp[s]==null){
            s=0;
        }
        views.setTextViewText(R.id.tv_widget, temp[s]);
        s++;
        manager.updateAppWidget(provider, views);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 广播接收器
     * 想法很美好，实际过程中，WidgetCourse
     * 发送的广播始终接收不到
     *
     AppWidgetManager manager = AppWidgetManager.getInstance(context);
     ComponentName provider = new ComponentName(context,WidgetCourse.class);
     RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widgetcourse);

     //intent对象
     Intent intent = new Intent(context,WidgetCourse.class);
     //设置动作
     intent.setAction("ACTION_CHANGE_TEXT");
     //pendingIntent对象是一个广播（可以是活动或服务）
     PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
     //设置视图上textRemoto控件的点击事件
     views.setOnClickPendingIntent(R.id.tv_widget, pendingIntent);
     manager.updateAppWidget(provider, views);
     */


    /*
    public class MyReceiver extends BroadcastReceiver {
        // 接收到Widget发送的广播
        int s=0;
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("ACTION_CHANGE_TEXT")) {
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                ComponentName provider = new ComponentName(context,WidgetCourse.class);
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widgetcourse);

                //Bundle bundle=intent.getExtras();
                //String[] tt=bundle.getStringArray("BUNDLE_STRING_COURSE");
                //String[] tt={"","",""};

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
                s++;
                if(s>=3){
                    s=0;
                }
                views.setTextViewText(R.id.tv_widget,temp[s]);
                manager.updateAppWidget(provider, views);
            }
        }

    }

    */
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
