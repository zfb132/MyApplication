package com.whuzfb.myapplication;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.hardware.Sensor;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by zfb15 on 2017/2/19.
 */

public class SensorActivity extends Activity implements SensorEventListener{

    public TextView textview_show_sensors;
    public TextView textview_orientation;
    public TextView textview_accelerometer;
    public TextView textview_accelerometer_linear;
    public TextView textview_magnetic_field;
    public TextView textview_gyroscope;
    public TextView textview_light;
    public TextView textview_gravity;
    public TextView textview_proximity;
    public TextView textview_step_counter;
    public TextView textview_vector;
    public int totalstep;

    public ImageView imageview_compass;
    public SensorManager sm;
    public String[] tempString=new String[13];
    public float currentdegree=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor);

        totalstep=0;

        Toast.makeText(this,"Y轴与音量键平行向外\nX轴与Y轴垂直向右\nZ轴与屏幕垂直向上",Toast.LENGTH_LONG).show();

        textview_show_sensors=(TextView)findViewById(R.id.textview_show_sensor);
        textview_orientation=(TextView)findViewById(R.id.textview_orientation);
        textview_accelerometer=(TextView)findViewById(R.id.textview_accelerometer);
        textview_accelerometer_linear=(TextView)findViewById(R.id.textview_accelerometer_linear);
        textview_magnetic_field=(TextView)findViewById(R.id.textview_magnetic_field);
        textview_gyroscope=(TextView)findViewById(R.id.textview_gyroscope);
        textview_light=(TextView)findViewById(R.id.textview_light);
        textview_gravity=(TextView)findViewById(R.id.textview_gravity);
        textview_proximity=(TextView)findViewById(R.id.textview_proximity);
        textview_step_counter=(TextView)findViewById(R.id.textview_step_counter);
        textview_vector=(TextView)findViewById(R.id.textview_vector);
        imageview_compass=(ImageView)findViewById(R.id.imageview_compass);

        //从系统服务中获得传感器管理器
        //SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //注册方向传感器，监听器、传感器类型、间隔时间
        //sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_FASTEST);

        //从系统服务中获得传感器管理器
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //从传感器管理器中获得全部的传感器列表
        List<Sensor> allSensors = sm.getSensorList(Sensor.TYPE_ALL);
        //显示有多少个传感器
        textview_show_sensors.setText("该手机有" + allSensors.size() + "个传感器：\n\n");
        //获得并显示传感器的信息
        getSensorInfo(allSensors);



        //每隔两秒更新一次界面
        Timer m_timer = new Timer("timer");
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                invalidateUI();
            }
        }, 0, 800);

    }
    public void invalidateUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textview_orientation.setText(tempString[5]);
                textview_accelerometer.setText(tempString[0]);
                textview_magnetic_field.setText(tempString[4]);
                textview_gyroscope.setText(tempString[2]);
                textview_accelerometer_linear.setText(tempString[1]);
                textview_light.setText(tempString[3]);
                textview_gravity.setText(tempString[9]);
                textview_proximity.setText(tempString[7]);
                textview_step_counter.setText(tempString[10]);
                textview_vector.setText(tempString[11]);
            }
        });
    }

    @Override
    protected void onResume() {
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE),SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_LIGHT),SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_GRAVITY),SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_PROXIMITY),SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onStop() {
        sm.unregisterListener(this);
        super.onStop();
    }

    public void getSensorInfo(List<Sensor> all){
        //显示每个传感器的具体信息
        for (Sensor s : all) {

            String tempString = "\n" + "设备名称：" + s.getName() + "\n" + "设备版本：" + s.getVersion() + "\n" + "供应商："
                    + s.getVendor() + "\n\n";
            switch (s.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    textview_show_sensors.setText(textview_show_sensors.getText().toString() + "加速度传感器accelerometer" + tempString);
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    textview_show_sensors.setText(textview_show_sensors.getText().toString() + "线性加速度传感器linear accelerometer" + tempString);
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    textview_show_sensors.setText(textview_show_sensors.getText().toString() + "陀螺仪传感器gyroscope" + tempString);
                    break;
                case Sensor.TYPE_LIGHT:
                    textview_show_sensors.setText(textview_show_sensors.getText().toString() + "光敏传感器light" + tempString);
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    textview_show_sensors.setText(textview_show_sensors.getText().toString() + "磁场传感器magnetic field" + tempString);
                    break;
                case Sensor.TYPE_ORIENTATION:
                    textview_show_sensors.setText(textview_show_sensors.getText().toString() + "方向传感器orientation" + tempString);
                    break;
                case Sensor.TYPE_PRESSURE:
                    textview_show_sensors.setText(textview_show_sensors.getText().toString() + "压力传感器pressure" + tempString);
                    break;
                case Sensor.TYPE_PROXIMITY:
                    textview_show_sensors.setText(textview_show_sensors.getText().toString() + "距离传感器proximity" + tempString);
                    break;
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    textview_show_sensors.setText(textview_show_sensors.getText().toString() + "温度传感器temperature" + tempString);
                    break;
                case Sensor.TYPE_GRAVITY:
                    textview_show_sensors.setText(textview_show_sensors.getText().toString() + "重力传感器gravity" + tempString);
                    break;
                case Sensor.TYPE_STEP_COUNTER:
                    textview_show_sensors.setText(textview_show_sensors.getText().toString() + "计步器step counter" + tempString);
                    break;
                case Sensor.TYPE_ROTATION_VECTOR:
                    textview_show_sensors.setText(textview_show_sensors.getText().toString() + "旋转矢量传感器rotation vector" + tempString);
                    break;
                case Sensor.TYPE_STEP_DETECTOR:
                    textview_show_sensors.setText(textview_show_sensors.getText().toString() + "步行检测传感器step detector" + tempString);
                    break;
                default:
                    textview_show_sensors.setText(textview_show_sensors.getText().toString() + "未知传感器" + tempString);
                    break;
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch(event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                /*
                //单位是加速度m/s2。如果手机平放好，x，y在位置为0，而z轴方向加速度=当前z方向加速度-g。由于g（重力加速度）垂直向下，则g=-9.81m/s2，即z轴 a=0-(-9.81)=9.81m/s2。也就是自由落体是为0。x、y、z的测量的加速度分别位于value[0]、value[1]、value[2]
                */
                tempString[0]="加速度传感器m/(s^2):\nX轴加速度: "+event.values[0]+"\nY轴加速度: "+event.values[1]+"\nZ轴加速度: "+event.values[2];
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                tempString[1]="线性加速度传感器m/(s^2):\nX轴线性加速度: "+event.values[0]+"\nY轴线性加速度: "+event.values[1]+"\nZ轴线性加速度: "+event.values[2];
                break;
            case Sensor.TYPE_GYROSCOPE:
                /*
                //测量的是x、y、z三个轴向的角速度，分别从values[0]、values[1]、values[2]中读取，单位为弧度/秒
                */
                tempString[2]="陀螺仪传感器rad/s:\nX轴角速度: "+event.values[0]+"\nY轴角速度: "+event.values[1]+"\nZ轴角速度: "+event.values[2];
                break;
            case Sensor.TYPE_LIGHT:
                tempString[3]="光敏传感器lux:\n精度: "+event.accuracy+"\n光照强度: "+event.values[0];
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                tempString[4]="磁场传感器uT:\nX轴分量: "+event.values[0]+"\nY轴分量: "+event.values[1]+"\nZ轴分量: "+event.values[2];
                break;
            case Sensor.TYPE_ORIENTATION:
                tempString[5]="方向传感器degree:\nX轴夹角: "+event.values[0]+"\nY轴夹角: "+event.values[1]+"\nZ轴夹角: "+event.values[2];
                float degree=event.values[0];
                //以指南针图像中心为轴逆时针旋转degree度
                RotateAnimation ra=new RotateAnimation(currentdegree,-degree, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                //在200ms内完成旋转操作
                ra.setDuration(200);
                //开始旋转图像
                imageview_compass.startAnimation(ra);
                //保存旋转之后的度数
                currentdegree=-degree;
                break;
            case Sensor.TYPE_PRESSURE:
                //tempString[6]="压力传感器hPa:\n精度: "+event.accuracy+"\n压力: "+event.values[0];
                break;
            case Sensor.TYPE_PROXIMITY:
                //不准,只有远5和近0两个状态（工程测试正常）
                tempString[7]="距离传感器cm:\n精度: "+event.accuracy+"\n距离: "+event.values[0];
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                //tempString[8]="温度传感器:\n精度: "+event.accuracy+"\n温度: "+event.values[0];
                break;
            case Sensor.TYPE_GRAVITY:
                tempString[9]="重力传感器m/(s^2):\nX轴加速度: "+event.values[0]+"\nY轴加速度: "+event.values[1]+"\nZ轴加速度: "+event.values[2];
                break;
            case Sensor.TYPE_STEP_COUNTER:
                //totalstep++;
                tempString[10]="计步器step:\n步数: "+event.values[0];
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                /*
                values[0]，沿x轴方向的旋转矢量正弦分量x*sin(θ/2)
                values[1]，沿y轴方向的旋转矢量正弦分量y*sin(θ/2)
                values[2]，沿z轴方向的旋转矢量正弦分量z*sin(θ/2)
                values[3]，旋转矢量余弦分量cos(θ/2)
                values[4]，评估朝向精度，单位为弧度（int），无法获得时为-1
                */
                tempString[11]="旋转矢量传感器:\nX轴正弦分量: "+event.values[0]+"\nY轴正弦分量: "+event.values[1]+"\nZ轴正弦分量: "+event.values[2]+"\n余弦值: "+event.values[3]+"\n精度: "+event.values[4];
                break;
            case Sensor.TYPE_STEP_DETECTOR:
                //tempString[12]="步行检测器cm:\n精度: "+event.accuracy+"\n距离: "+event.values[0];
                break;
            default:
                //textview_change_sensors.setText(textview_change_sensors.getText().toString() + "未知传感器" + tempString);
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}