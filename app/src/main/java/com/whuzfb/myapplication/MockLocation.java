package com.whuzfb.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import static android.content.pm.PackageManager.GET_ACTIVITIES;
import static android.content.pm.PackageManager.GET_CONFIGURATIONS;
import static android.content.pm.PackageManager.GET_GIDS;
import static android.content.pm.PackageManager.GET_INSTRUMENTATION;
import static android.content.pm.PackageManager.GET_INTENT_FILTERS;
import static android.content.pm.PackageManager.GET_META_DATA;
import static android.content.pm.PackageManager.GET_PERMISSIONS;
import static android.content.pm.PackageManager.GET_PROVIDERS;
import static android.content.pm.PackageManager.GET_RECEIVERS;
import static android.content.pm.PackageManager.GET_SERVICES;
import static android.content.pm.PackageManager.GET_SHARED_LIBRARY_FILES;
import static android.content.pm.PackageManager.GET_SIGNATURES;
import static android.content.pm.PackageManager.GET_UNINSTALLED_PACKAGES;
import static android.content.pm.PackageManager.GET_URI_PERMISSION_PATTERNS;
import static android.content.pm.PackageManager.MATCH_DISABLED_COMPONENTS;
import static android.content.pm.PackageManager.MATCH_DISABLED_UNTIL_USED_COMPONENTS;
import static android.content.pm.PackageManager.MATCH_UNINSTALLED_PACKAGES;

/**
 * Created by zfb15 on 2017/2/21.
 */

public class MockLocation extends Activity {

    private LocationManager mLocationManager;
    private Context mContext;
    //只针对网络定位进行模拟，不兼容6.0
    private String mMockProviderName = LocationManager.NETWORK_PROVIDER;
    private double mLongitude = 40.052462;
    private double mLatitude = 166.29064;
    public EditText editText_longitude;
    public EditText editText_latitude;


    //wifi显示
    public WifiManager wifiManager;
    public WifiInfo wifiInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mocklocation);
        //不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        new AlertDialog.Builder(MockLocation.this).setTitle("温馨提示")
                //设置显示的内容
                .setMessage("本部分仅适用于Android 6.0以下，而且以防非法用途，只对本应用模拟位置")
                //添加确定按钮
                .setPositiveButton("我知道了",null).show();
        boolean isMockLocation = Settings.Secure.getInt(getApplicationContext().getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION, 0) != 0;
        editText_latitude=(EditText)findViewById(R.id.edittext_latitude);
        editText_longitude=(EditText)findViewById(R.id.edittext_longitude);
        Button button_change_location=(Button)findViewById(R.id.button_change_location);
        Button button_close_mock=(Button)findViewById(R.id.button_close_mock);
        Button button_screenshot=(Button)findViewById(R.id.button_screenshot);
        Button button_show_wifi=(Button)findViewById(R.id.button_show_wifi);
        mContext=this;
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        enableTestProvider();
        //setLocation();//设置模拟位置信息，也可以建一个thread不断设置虚拟位置信息。

        if(!isMockLocation){
            Toast.makeText(this, "请进入开发者选项——>允许模拟位置", Toast.LENGTH_SHORT).show();
            button_change_location.setClickable(false);
            button_close_mock.setClickable(false);
        }
        button_change_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLongitude=Double.parseDouble(editText_longitude.getText().toString());
                mLatitude=Double.parseDouble(editText_latitude.getText().toString());
                setLocation();
            }
        });
        button_close_mock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unenableTestProvider();
                Toast.makeText(MockLocation.this, "已经模拟位置", Toast.LENGTH_SHORT).show();
            }
        });
        button_screenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveBitmap(captureScreen(MockLocation.this),"1.png");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        button_show_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MockLocation.this,PackageShow.class);
                intent.putExtra("com.whuzfb.myapplication.packageInfo",getWifiInfo());
                startActivity(intent);
            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
    }

    public String getWifiInfo(){
        //wifi
        wifiManager=(WifiManager)getSystemService(Context.WIFI_SERVICE);
        wifiInfo=wifiManager.getConnectionInfo();
        //获得wifi信息
        //      查看已经连接上的WIFI信息，在Android的SDK中为我们提供了一个叫做WifiInfo的对象，这个对象可以通过WifiManager.getConnectionInfo()来获取。WifiInfo中包含了当前连接中的相关信息。
//      getBSSID()  获取BSSID属性
//      getDetailedStateOf()  获取客户端的连通性
//      getHiddenSSID()  获取SSID 是否被隐藏
//      getIpAddress()  获取IP 地址
//      getLinkSpeed()  获取连接的速度
//      getMacAddress()  获取Mac 地址
//      getRssi()  获取802.11n 网络的信号
//      getSSID()  获取SSID
//      getSupplicanState()  获取具体客户端状态的信息
        String sb = "";
        sb=sb+"\nWiFi状态：" + wifiManager.getWifiState();
        sb=sb+"\n\nBSSID（WIFI设备MAC地址）：" + wifiInfo.getBSSID();
//      sb.append("getDetailedStateOf()  获取客户端的连通性：");
        sb=sb+"\n\nSSID 是否被隐藏："+ wifiInfo.getHiddenSSID();
        //sb=sb+"\n获取DHCP地址：" + wifiManager.getDhcpInfo();
        sb=sb+"\n\nIP 地址：" + ipIntToString(wifiInfo.getIpAddress());
        //sb=sb+"\n\n获取IP 地址：" + ipIntToString(wifiManager.getDhcpInfo().ipAddress);
        sb=sb+"\n\nDNS1：" + ipIntToString(wifiManager.getDhcpInfo().dns1);
        sb=sb+"\n\nDNS2：" + ipIntToString(wifiManager.getDhcpInfo().dns2);
        sb=sb+"\n\n网关地址：" + ipIntToString(wifiManager.getDhcpInfo().gateway);
        sb=sb+"\n\n服务器地址：" + ipIntToString(wifiManager.getDhcpInfo().serverAddress);
        //sb=sb+"\n获取BSSID属性（所连接的WIFI设备的MAC地址）：" + wifiManager.getConfiguredNetworks();
        sb=sb+"\n当前可用WIFI：";
        try{
            for(int i=0;i<wifiManager.getScanResults().size();i++){
                sb=sb+"\n"+wifiManager.getScanResults().get(i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        sb=sb+"\n\n连接的速度：" + wifiInfo.getLinkSpeed();
        sb=sb+"\n\n手机Mac 地址:" + wifiInfo.getMacAddress();
        try{
            sb=sb+"\n\n频率：" + wifiInfo.getFrequency();
        }catch (Exception e){
            e.printStackTrace();
        }

        sb=sb+"\n\n网络ID：" + wifiInfo.getNetworkId();
        sb=sb+"\n\n信号质量(满格为10)：" + wifiManager.calculateSignalLevel(wifiInfo.getRssi(),10);
        sb=sb+"\n\nWIFI的网络SSID：" + wifiInfo.getSSID();
        sb=sb+"\n\n客户端状态：" + wifiInfo.getSupplicantState();
        return sb;
    }

    // 将int类型的IP转换成字符串形式的IP
    public String ipIntToString(int ip) {
        try {
            byte[] bytes = new byte[4];
            bytes[0] = (byte) (0xff & ip);
            bytes[1] = (byte) ((0xff00 & ip) >> 8);
            bytes[2] = (byte) ((0xff0000 & ip) >> 16);
            bytes[3] = (byte) ((0xff000000 & ip) >> 24);
            return Inet4Address.getByAddress(bytes).getHostAddress();
        } catch (Exception e) {
            return "";
        }
    }

    public void enableTestProvider(){
        ContentResolver res = mContext.getContentResolver();
        //获取gps的状态，false为关闭，true为开启。
        boolean gps_enable = Settings.Secure.isLocationProviderEnabled(
                res, android.location.LocationManager.GPS_PROVIDER);
        //获取“允许模拟地点”的状态，0为不允许，1为允许。
        int mock_enable = Settings.Secure.getInt(
                res, Settings.Secure.ALLOW_MOCK_LOCATION, 0);
        if(mock_enable==1){
            mLocationManager.addTestProvider(mMockProviderName,
                    "requiresNetwork" == "", "requiresSatellite" == "",
                    "requiresCell" == "", "hasMonetaryCost" == "",
                    "supportsAltitude" == "", "supportsSpeed" == "",
                    "supportsBearing" == "supportsBearing",
                    Criteria.POWER_LOW,
                    Criteria.ACCURACY_FINE);
            mLocationManager.setTestProviderEnabled(mMockProviderName, true);
        }

    }

    @SuppressLint("NewApi")
    private void setLocation(){
        Location loc = new Location(mMockProviderName);//这里是模拟的gps位置信息，当然也可以设置network位置信息了。
        loc.setAccuracy(Criteria.ACCURACY_FINE);
        loc.setTime(System.currentTimeMillis());//设置当前时间
        loc.setLatitude(mLongitude);           //设置纬度
        loc.setLongitude(mLatitude);           //设置经度
        loc.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        mLocationManager.setTestProviderLocation(mMockProviderName, loc);
        Toast.makeText(this, "开启模拟位置("+mLatitude+","+mLongitude+")成功", Toast.LENGTH_LONG).show();
    }

    public void unenableTestProvider(){
        int mock_enable = Settings.Secure.getInt(
                mContext.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION, 0);
        if(mock_enable == 0)
            return;
        try {
            mLocationManager.clearTestProviderEnabled(mMockProviderName);
            mLocationManager.removeTestProvider(mMockProviderName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //unenableTestProvider();
    }

    public Bitmap captureScreen(Activity activity) {
        //对Activity进行截屏
        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap bmp=getWindow().getDecorView().getDrawingCache();
        return bmp;
    }

    public void saveBitmap(Bitmap bm,String picName) {
        File f = new File("/sdcard/myImageRecords/", picName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(MockLocation.this, "成功保存截屏到:\n"+f.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
