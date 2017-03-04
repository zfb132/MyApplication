package com.whuzfb.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by zfb15 on 2017/3/2.
 */

public class FloatViewService extends Service {

    //悬浮窗
    private boolean isAdded = false; // 是否已增加悬浮窗
    private static WindowManager wm;
    private static WindowManager.LayoutParams params;
    //private Button btn_floatView;
    private ImageView img_floatView;
    public LinearLayout mFloatLayout;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //createFloatView();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        createFloatView();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void createFloatView() {
        //btn_floatView = new Button(getApplicationContext());
        //btn_floatView.setText("悬浮窗");

        wm = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();

        // 设置window type
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
       /*
        * 如果设置为params.type = WindowManager.LayoutParams.TYPE_PHONE;
        * 那么优先级会降低一些, 即拉下通知栏不可见
        */

        params.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明

        // 设置Window flag
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
       /*
        * 下面的flags属性的效果形同“锁定”。FLAG_NOT_FOCUSABLE
        * 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
       wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL
                              | LayoutParams.FLAG_NOT_FOCUSABLE
                              | LayoutParams.FLAG_NOT_TOUCHABLE;
        */

        // 设置悬浮窗的长得宽
        params.width = 200;
        params.height = 200;


        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.floatlayout, null);
        //添加mFloatLayout
        wm.addView(mFloatLayout, params);
        //浮动窗口按钮
        //btn_floatView = (Button)mFloatLayout.findViewById(R.id.button_float);

        img_floatView=(ImageView) mFloatLayout.findViewById(R.id.img_float);

        // 设置悬浮窗的Touch监听
        img_floatView.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY;
            int paramX, paramY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        paramX = params.x;
                        paramY = params.y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        params.x = paramX + dx;
                        params.y = paramY + dy;
                        // 更新悬浮窗位置
                        wm.updateViewLayout(mFloatLayout, params);
                        break;
                }
                //此处必须的返回false，否则监听不到点击事件
                return false;
            }
        });

        //绑定点击监听
        img_floatView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent intent_a=getPackageManager().getLaunchIntentForPackage("com.whuzfb.myapplication");
                startActivity(intent_a);
            }
        });

    }
}
