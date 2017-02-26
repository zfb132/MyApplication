package com.whuzfb.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.view.Gravity;


import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zfb15 on 2017/1/1.
 */

public class LinearTestAActivity extends Activity {

    public TextView t;
    public CheckBox b;
    public Button b1;
    public Button b2;
    public Button b3;
    public Button b4;
    public Button b5;
    public Button b6;
    public Button b7;
    public Button b8;
    public Button b9;
    public Button b10;
    public Button b11;
    public Button b12;
    public Button b13;
    public Button b14;
    public Button b15;
    public Button b16;
    public Button b17;
    public Button b18;
    public Button b19;
    public Button b20;
    public Button b21;
    public Button b22;
    public Button b23;
    public Button b24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lineartesta);

        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button7);
        b5=(Button)findViewById(R.id.button8);
        b6=(Button)findViewById(R.id.button9);
        b7=(Button)findViewById(R.id.button10);
        b8=(Button)findViewById(R.id.button11);
        b9=(Button)findViewById(R.id.button12);
        b10=(Button)findViewById(R.id.button13);
        b11=(Button)findViewById(R.id.button14);
        b12=(Button)findViewById(R.id.button15);
        b13=(Button)findViewById(R.id.button16);
        b14=(Button)findViewById(R.id.button17);
        b15=(Button)findViewById(R.id.button18);
        b16=(Button)findViewById(R.id.button19);
        b17=(Button)findViewById(R.id.button20);
        b18=(Button)findViewById(R.id.button21);
        b19=(Button)findViewById(R.id.button22);
        b20=(Button)findViewById(R.id.button23);
        b21=(Button)findViewById(R.id.button24);
        b22=(Button)findViewById(R.id.button25);
        b23=(Button)findViewById(R.id.button26);
        b24=(Button)findViewById(R.id.button27);


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LinearTestAActivity.this,SpinnerActivity.class);
                startActivity(intent);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LinearTestAActivity.this,AutoCompleteImageViewActivity.class);
                startActivity(intent);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LinearTestAActivity.this,GalleryImageSwitcherActivity.class);
                startActivity(intent);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LinearTestAActivity.this,GridViewActivity.class);
                startActivity(intent);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LinearTestAActivity.this,WebViewActivity.class);
                startActivity(intent);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LinearTestAActivity.this,TabViewActivity.class);
                startActivity(intent);
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LinearTestAActivity.this,BitmapActivity.class);
                startActivity(intent);
            }
        });
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(2);
            }
        });
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence tickertext="这是一个显示在状态了的通知！";
                CharSequence contexttitle="我的通知";
                CharSequence contexttext="通知的内容：点击这个通知进入Activity";
                long time=System.currentTimeMillis();

                Context context=getApplicationContext();
                Intent notificationintent=new Intent(context,MainActivity.class);
                //PendingIntent对象指定了当用户单击扩展的Notification时应用程序如何跳转
                PendingIntent pendingIntent=PendingIntent.getActivity(context,0,notificationintent,0);

                Notification.Builder builder=new Notification.Builder(context);
                //设置通知栏左边的大图标
                //builder.setLargeIcon();
                //设置通知栏右边的小图标
                builder.setSmallIcon(R.drawable.sample_0);
                //通知首次出现在通知栏，带上升动画效果的
                builder.setTicker(tickertext);
                //通知产生的时间，会在通知信息里显示
                builder.setWhen(time);
                //设置通知的内容
                builder.setContentText(contexttext);
                //设置通知的标题
                builder.setContentTitle(contexttitle);
                //设置通知的优先级
                //builder.setPriority(Notification.PRIORITY_MAX);
                //设置这个标志当用户单击面板就可以让通知自动取消
                //builder.setAutoCancel(true);
                //设置它为一个正在进行的通知。（通常用来表示一个后台任务，用户积极参与或以某种方式正在等待）
                //builder.setOngoing(true);
                //向通知添加声音、闪灯和震动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性
                //builder.setDefaults(Notification.DEFAULT_VIBRATE|Notification.DEFAULT_SOUND);
                builder.setContentIntent(pendingIntent);
                Notification notification=builder.build();
                notification.defaults=notification.DEFAULT_SOUND;

                String ns=Context.NOTIFICATION_SERVICE;
                NotificationManager mNotificationManager=(NotificationManager)getSystemService(ns);
                //发起通知
                mNotificationManager.notify(1,notification);
                //取消显示通知
                //mNotificationManager.cancel(1);
            }
        });
        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LinearTestAActivity.this,EventListenerActivity.class);
                startActivity(intent);
            }
        });
        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LinearTestAActivity.this,InnerIntentActivity.class);
                startActivity(intent);
            }
        });
        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DevicePolicyManager manager=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
                //启动第三方组件
                ComponentName componentName=new ComponentName(LinearTestAActivity.this,MyDeviceAdmin.class);
                //判断MyAdmin.class是否为设备管理器
                if(!manager.isAdminActive(componentName)){
                    Intent intent=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
                    //"android.app.extra.ADD_EXPLANATION"  下面这句话不知道为何无法执行
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"这是我自己测试的，不会对你的手机产生任何不良影响。如果有任何担忧，请立即卸载本软件。");
                    startActivity(intent);
                }else{
                    try{
                        manager.lockNow();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //防卸载功能
                            //manager.setUninstallBlocked(componentName,"com.whuzfb.myapplication",true);
                        }
                    }catch (SecurityException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        b16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DevicePolicyManager manager=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
                //启动第三方组件
                ComponentName componentName=new ComponentName(LinearTestAActivity.this,MyDeviceAdmin.class);
                //判断MyAdmin.class是否为设备管理器
                if(manager.isAdminActive(componentName)){
                    manager.setMaximumTimeToLock(componentName,5000L);
                }
            }
        });
        b17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LinearTestAActivity.this,FlashLight.class);
                startActivity(intent);
            }
        });
        b18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LinearTestAActivity.this,SensorActivity.class);
                startActivity(intent);
            }
        });
        b19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LinearTestAActivity.this,GpsActivity.class);
                startActivity(intent);
            }
        });
        b20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LinearTestAActivity.this,MockLocation.class);
                startActivity(intent);
            }
        });
        b21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LinearTestAActivity.this,PackageActivity.class);
                startActivity(intent);
            }
        });





        if(needPermissionForBlocking(getApplicationContext())) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }
        MainActivity.stata.flag=false;

        b=(CheckBox)findViewById(R.id.checkBox4) ;
        b.setChecked(false);
        mybuttonlistener mm=new mybuttonlistener();
        b.setOnClickListener(mm);

        b1=(Button)findViewById(R.id.button5);
        b2=(Button)findViewById(R.id.button6);

        myservicelistener ms=new myservicelistener();
        b1.setOnClickListener(ms);b2.setOnClickListener(ms);

        Intent intenb=getIntent();
        int a=intenb.getIntExtra("com.whuzfb.myapplication.count",10);
        int[] m=intenb.getIntArrayExtra("com.whuzfb.myapplication.time");
        t=(TextView)findViewById(R.id.textView5);
        t.setText("获得的点击次数:"+a+"\n获得的时间:"+m[0]+"时"+m[1]+"分");
        MainActivity.stata.count=0;
        MainActivity.stata.tp.setCurrentHour(7);
        MainActivity.stata.tp.setCurrentMinute(7);
    }

    @Override
    protected void onStart() {
        DevicePolicyManager manager=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        //启动第三方组件
        ComponentName componentName=new ComponentName(LinearTestAActivity.this,MyDeviceAdmin.class);
        if(manager.isAdminActive(componentName)){
            b15.setText("锁屏");
            b16.setText("5S锁屏");
        }
        super.onStart();
    }

    class myservicelistener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.button5){
                showToast("即将进入服务",Toast.LENGTH_SHORT);
                Intent mIntent = new Intent();
                mIntent.setClass(LinearTestAActivity.this,FirstLocalService.class);
                startService(mIntent);
            }
            else {
                showToast("即将结束服务",Toast.LENGTH_SHORT);
                Intent mIntent = new Intent();
                mIntent.setClass(LinearTestAActivity.this,FirstLocalService.class);
                stopService(mIntent);
            }
        }
    }
    class mybuttonlistener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            CheckBox b=(CheckBox)v;
            if(b.isChecked()) {
                showToast("即将开始程序锁",Toast.LENGTH_SHORT);
                handler.postDelayed(runnable, 5000);
            }else{
                showToast("即将关闭程序锁",Toast.LENGTH_SHORT);
                Intent mIntent = new Intent();
                mIntent.setClass(LinearTestAActivity.this,FirstLocalService.class);
                stopService(mIntent);
                handler.removeCallbacks(runnable);
            }
        }
    }
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, 5000);
                Intent mIntent = new Intent();
                mIntent.setClass(LinearTestAActivity.this,FirstLocalService.class);
                startService(mIntent);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("exception...");
            }
        }
    };
    public void showToast(String str,int duration)
    {
        //Toast.makeText(this, str, duration).show();
        Toast toast = Toast.makeText(this, str, duration);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }

    public static boolean needPermissionForBlocking(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return (mode != AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }

    protected Dialog onCreateDialog(int id){
        Dialog dialog=null;
        switch (id){
            case 1:
                AlertDialog.Builder builder=new AlertDialog.Builder(LinearTestAActivity.this);
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setTitle("对话框");
                builder.setMessage("这是我测试用的AlertDialog");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("AlertDialog的确定按键被点击",Toast.LENGTH_SHORT);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("AlertDialog的取消按键被点击",Toast.LENGTH_SHORT);
                    }
                });
                builder.setNeutralButton("中立", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("AlertDialog的中立按键被点击",Toast.LENGTH_SHORT);
                    }
                });
                dialog=builder.create();
                break;
            case 2:
                final ProgressDialog progressDialog=new ProgressDialog(this);
                //设置水平进度条
                progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
                //设置进度条最大值为100
                progressDialog.setMax(100);
                //设置进度条当前为0
                progressDialog.setProgress(0);
                new Thread(new Runnable() {
                    int count=0;
                    @Override
                    public void run() {
                        while (progressDialog.getProgress()<100){
                            count+=3;
                            progressDialog.setProgress(count);
                            try{
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                showToast("显示异常",Toast.LENGTH_SHORT);
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                //显示进度条对话框
                progressDialog.show();
                break;
            default:
                break;
        }
        return dialog;
    }
}
