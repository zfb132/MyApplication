package com.whuzfb.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;

/**
 * Created by zfb15 on 2017/2/20.
 */

public class GpsActivity extends Activity {

    private TextView textview_curpos;
    private TextView textview_gps;
    private LocationManager lm;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
    private Date curDate;
    private String strtime = "";
    private String strgps = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps);
        textview_gps = (TextView) findViewById(R.id.textview_gps);
        textview_curpos = (TextView) findViewById(R.id.textview_curpos);

        //首先获得服务
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 判断GPS是否正常启动
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请开启GPS卫星", Toast.LENGTH_SHORT).show();
            // 返回开启GPS导航设置界面
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
            //openGPS(this);
        }
        // 为获取地理位置信息时设置查询条件
        String bestProvider = lm.getBestProvider(getCriteria(), true);
        // 获取位置信息
        // 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
        try {
            //如果获取不到GPS再用网络(室外的话获取GPS的可能性较大，否则就只有看网速等待network定位了)
            Location location = lm.getLastKnownLocation(bestProvider);
            if (location == null)
                location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            updateView(location);
            // 监听状态
            lm.addGpsStatusListener(listener);
            // 绑定监听，有4个参数
            // 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
            // 参数2，位置信息更新周期，单位毫秒
            // 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
            // 参数4，监听器
            // 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
            // 1秒更新一次，或最小位移变化超过1米更新一次；
            // 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
            //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30 * 1000, 1, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    //强制帮用户打开GPS(无法正常打开)
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        try{
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15 * 1000, 1, locationListener);
    } catch (SecurityException e) {
        e.printStackTrace();
    }
        super.onResume();
    }

    // 位置监听
    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            curDate = new Date(System.currentTimeMillis());//获取当前时间
            strtime = formatter.format(curDate);
            strgps = strgps + "\n\n" + strtime + "位置改变：\n时间：" + location.getTime() + "\n经度：" + location.getLongitude() + "\n纬度：" + location.getLatitude() + "\n海拔：" + location.getAltitude();
            updateView(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                // GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    curDate = new Date(System.currentTimeMillis());//获取当前时间
                    strtime = formatter.format(curDate);
                    strgps = strgps + "\n\n" + strtime + "\n当前GPS状态为可见状态";
                    textview_gps.setText(strgps);
                    break;
                // GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    curDate = new Date(System.currentTimeMillis());//获取当前时间
                    strtime = formatter.format(curDate);
                    strgps = strgps + "\n\n" + strtime + "\n当前GPS状态为服务区外状态";
                    textview_gps.setText(strgps);
                    break;
                // GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    curDate = new Date(System.currentTimeMillis());//获取当前时间
                    strtime = formatter.format(curDate);
                    strgps = strgps + "\n\n" + strtime + "\n当前GPS状态为暂停服务状态";
                    textview_gps.setText(strgps);
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            try {
                Location location = lm.getLastKnownLocation(provider);
                updateView(location);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            updateView(null);
        }

    };

    // 状态监听
    GpsStatus.Listener listener = new GpsStatus.Listener() {
        @Override
        public void onGpsStatusChanged(int event) {
            switch (event) {
                // 第一次定位
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    curDate = new Date(System.currentTimeMillis());//获取当前时间
                    strtime = formatter.format(curDate);
                    strgps = strgps + "\n\n\n" + strtime + "\n第一次定位";
                    textview_gps.setText(strgps);
                    break;
                // 卫星状态改变
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    curDate = new Date(System.currentTimeMillis());//获取当前时间
                    strtime = formatter.format(curDate);
                    strgps = strgps + "\n\n\n" + strtime + "\n卫星状态改变";
                    textview_gps.setText(strgps);
                    GpsStatus gpsStatus = null;
                    // 获取当前状态
                    try {
                        gpsStatus = lm.getGpsStatus(null);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                    if (gpsStatus != null) {
                        // 获取卫星颗数的默认最大值
                        int maxSatellites = gpsStatus.getMaxSatellites();
                        // 创建一个迭代器保存所有卫星
                        Iterator<GpsSatellite> iters = gpsStatus.getSatellites()
                                .iterator();
                        /*
                        * satellite.getElevation(); //卫星仰角
                        * satellite.getAzimuth();   //卫星方位角
                        * satellite.getSnr();       //信噪比
                        * satellite.getPrn();       //伪随机数
                        * satellite.hasAlmanac();   //GPS引擎有近似轨道信息则返回true
                        * satellite.hasEphemeris();//GPS引擎有卫星星历则返回true
                        * satellite.usedInFix();//当卫星被GPS引擎用于计算最近位置时，返回true
                        */
                        int count = 0;
                        curDate = new Date(System.currentTimeMillis());//获取当前时间
                        strtime = formatter.format(curDate);
                        strgps = strgps + "\n\n" + strtime;
                        while (iters.hasNext() && count <= maxSatellites) {
                            GpsSatellite s = iters.next();
                            count++;
                            strgps = strgps + "\n伪噪声随机码：" + s.getPrn() + "\n卫星方位角：" + s.getAzimuth() + "\n卫星仰角：" + s.getElevation() + "\n信噪比：" + s.getSnr() + "\n是否有星历：" + s.hasEphemeris() + "\n是否有近似轨道信息：" + s.hasAlmanac() + "\n是否被用于计算最近位置：" + s.usedInFix()+"\n";
                        }
                        strgps = strgps + "\n以上共搜索到：" + count + "颗卫星";
                        try {
                            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location == null)
                                location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location == null)
                                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location == null)
                                location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            updateView(location);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                        // 定位启动
                case GpsStatus.GPS_EVENT_STARTED:
                    curDate = new Date(System.currentTimeMillis());//获取当前时间
                    strtime = formatter.format(curDate);
                    strgps = strgps + "\n\n\n" + strtime + "\n定位启动";
                    textview_gps.setText(strgps);
                    break;
                        // 定位结束
                case GpsStatus.GPS_EVENT_STOPPED:
                    curDate = new Date(System.currentTimeMillis());//获取当前时间
                    strtime = formatter.format(curDate);
                    strgps = strgps + "\n\n\n" + strtime + "\n定位结束";
                    textview_gps.setText(strgps);
                    break;
            }
        }
    };

    private void updateView(Location location) {
        if (location != null) {
            textview_curpos.setText("设备位置信息\n经度：" + String.valueOf(location.getLongitude()) + "\n纬度：" + String.valueOf(location.getLatitude()) + "\n提供者：" + String.valueOf(location.getProvider()) + "\n精度：" + String.valueOf(location.getAccuracy()) + "\n海拔：" + String.valueOf(location.getAltitude()) + "\n方位角（正北偏东）：" + String.valueOf(location.getBearing()) + "\n速度m/s：" + String.valueOf(location.getSpeed()) + "\n卫星时间：" + String.valueOf(formatter.format(location.getTime())));
            textview_gps.setText(strgps);
        } else {
                Toast.makeText(this, "无法获取位置", Toast.LENGTH_SHORT).show();
        }
    }

    // 返回查询条件
    private Criteria getCriteria() {
            Criteria criteria = new Criteria();
            // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            // 设置是否要求速度
            criteria.setSpeedRequired(true);
            // 设置是否允许运营商收费
            criteria.setCostAllowed(false);
            // 设置是否需要方位信息
            criteria.setBearingRequired(true);
            // 设置是否需要海拔信息
            criteria.setAltitudeRequired(true);
            // 设置对电源的需求
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            return criteria;
        }

    @Override
    protected void onStart() {
        try {
            //如果获取不到GPS再用网络
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null)
                location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            updateView(location);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        try {
            lm.removeUpdates(locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
