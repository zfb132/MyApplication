package com.whuzfb.myapplication;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zfb15 on 2017/2/4.
 */

public class EventListenerActivity extends Activity implements View.OnFocusChangeListener{

    public Button[] buttons=new Button[3];
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventlistener);

        buttons[0]=(Button)findViewById(R.id.button_eventA);
        buttons[1]=(Button)findViewById(R.id.button_eventB);
        buttons[2]=(Button)findViewById(R.id.button_eventC);
        textView=(TextView)findViewById(R.id.textView_eventA);


        for(Button button:buttons){
            button.setOnFocusChangeListener(this);
        }

    }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

                switch (v.getId()){
                    case R.id.button_eventA:
                        showToast("测试A获得了焦点",Gravity.CENTER);
                        break;
                    case R.id.button_eventB:
                        showToast("测试B获得了焦点",Gravity.CENTER);
                        break;
                    case R.id.button_eventC:
                        showToast("测试C获得了焦点",Gravity.CENTER);
                        break;
                    default:
                        showToast("测试按钮异常",Gravity.CENTER);
                        break;
                }

        }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_UP:
                showToast("按了上方向键，KEYCODE_DPAD_UP",Gravity.CENTER);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                showToast("按了下方向键，KEYCODE_DPAD_DOWN",Gravity.CENTER);
                break;
            default:
                showToast("有按键被按下",Gravity.CENTER);
                break;
        }
        //return false;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_UP:
                showToast("松开上方向键，KEYCODE_DPAD_UP",Gravity.CENTER);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                showToast("松开下方向键，KEYCODE_DPAD_DOWN",Gravity.CENTER);
                break;
            default:
                showToast("有按键被释放",Gravity.CENTER);
                break;
        }
        //return false;
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                textView.setTextColor(Color.RED);
                //showToast("手指正在往屏幕按下",Gravity.BOTTOM);
                break;
            case MotionEvent.ACTION_MOVE:
                textView.setTextColor(Color.GREEN);
                //showToast("手指正在屏幕上移动",Gravity.BOTTOM);
                break;
            case MotionEvent.ACTION_UP:
                textView.setTextColor(Color.BLUE);
                //showToast("手指正在从屏幕抬起",Gravity.BOTTOM);
                break;
            default:
                break;
        }
        //ereturn false;
        return super.onTouchEvent(event);
    }

    public void showToast(String str,int gravity)
    {
        //Toast.makeText(this, str, duration).show();
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, 0, 200);
        toast.show();
    }


}
