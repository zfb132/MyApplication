package com.whuzfb.myapplication;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.Toast;

import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

/**
 * Created by zfb15 on 2017/1/3.
 */

public class FirstLocalService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //这个函数必须复写，不用就NULL
        return null;
    }
    private static final String TAG ="FirstLocalService";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        /*
        mytm=new Timer();
        mytm.schedule(task,5*1000);
        */
        showToast(getTaskPackname() ,Toast.LENGTH_SHORT);
        //SystemClock.sleep(5*1000);
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        showToast("销毁服务",Toast.LENGTH_SHORT);
        super.onDestroy();
    }
    @Override
    public void onCreate() {
        showToast("创建服务",Toast.LENGTH_SHORT);
        super.onCreate();
    }
    /*
    private Timer mytm;

    TimerTask task = new TimerTask() {
        public void run() {
            //每次需要执行的代码放到这里面。
            showToast("dingshiqi",Toast.LENGTH_SHORT);
            //mytm.cancel();

        }
    };
    */
    public void showToast(String str,int duration)
    {
        //Toast.makeText(this, str, duration).show();
        Toast toast = Toast.makeText(this, str, duration);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }

    private String getTaskPackname() {
        String currentApp = "CurrentNULL";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            if (appList != null && appList.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }
        //Log.e("TAG", "Current App in foreground is: " + currentApp);
        return currentApp;
    }
}
