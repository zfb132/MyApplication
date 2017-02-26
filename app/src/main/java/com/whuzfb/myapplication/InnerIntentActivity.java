package com.whuzfb.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
//import android.graphics.Camera;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zfb15 on 2017/2/5.
 */

public class InnerIntentActivity extends Activity {

    public Button button_dial;
    public Button button_call;
    public Button button_music_filesystem;
    public Button button_music_local;
    public Button button_music_web;
    public Button button_music_service;
    public ToggleButton toggleButton_battery;
    public EditText editText;
    public TextView textView_showbattery;
    public TextView textView_playmusic;
    String telnum;
    String path;
    //public boolean flag_play;
    public AudioManager audioManager;
    public MediaPlayer mediaPlayer;
    public Button button_sendsms;
    public EditText editText_receiver;
    public EditText editText_smscontent;

    //音频录制
    public Button button_recorder_start;
    public Button button_recorder_stop;
    public TextView textView_recorder;
    public MediaRecorder mediaRecorder;
    public File recordPath;
    public File recordFile;
    public boolean flag_record;
    public String filename="";

    //照相机
    public Button button_camera_open;
    public Button button_camera_close;
    public Button button_camera_take;
    public SurfaceView surfaceView_camera;
    public SurfaceHolder surfaceHolder_camera;
    public Camera camera;
    public int previewWidth = 300;
    public int previewHeight = 300;
    public String imagepath = "";
    public boolean flag_camera;
    //待添加
    public boolean permission_camera;


    //录制视频
    public TextView textView_video;
    public Button button_video_open;
    public Button button_video_record;
    public Button button_video_close;
    public SurfaceView surfaceView_video;
    public SurfaceHolder surfaceHolder_video;
    public Camera camera1;
    public String videopath = "";
    public boolean flag_video;
    public MediaRecorder videoRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.innerintent);

        //不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        //拍照程序
        flag_camera=false;
        button_camera_open = (Button) findViewById(R.id.button_camera_open);
        button_camera_take = (Button) findViewById(R.id.button_camera_take);
        button_camera_close = (Button) findViewById(R.id.button_camera_close);
        surfaceView_camera = (SurfaceView) findViewById(R.id.surfaceview_camera);
        surfaceHolder_camera = surfaceView_camera.getHolder();
        surfaceHolder_camera.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //不想显示了
                /*
                Toast toast = Toast.makeText(InnerIntentActivity.this, "创建SurfaceView", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 200);
                toast.show();
                */
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                //showToast("更改",Toast.LENGTH_SHORT);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //不想显示了
                /*
                Toast toast = Toast.makeText(InnerIntentActivity.this, "销毁SurfaceView", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 200);
                toast.show();
                */
            }
        });
        button_camera_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag_camera){
                    openCamera();
                }else{
                    showToast("已经打开了摄像头",Toast.LENGTH_SHORT);
                }
            }
        });
        button_camera_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_camera){
                    takePicture();
                }else{
                    showToast("请先打开摄像头",Toast.LENGTH_SHORT);
                }
            }
        });
        button_camera_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_camera){
                    closeCamera();
                    //surfaceView_camera.setBackgroundColor(Color.rgb(0,0xf,0));
                }else{
                    showToast("还没有打开摄像头呢",Toast.LENGTH_SHORT);
                }
                flag_camera=false;
            }
        });


        //录制视频
        flag_video=false;
        textView_video=(TextView)findViewById(R.id.textview_video);
        button_video_open=(Button)findViewById(R.id.button_video_open);
        button_video_record=(Button)findViewById(R.id.button_video_record);
        button_video_close=(Button)findViewById(R.id.button_video_close);
        surfaceView_video=(SurfaceView)findViewById(R.id.surfaceview_video);
        surfaceHolder_video=surfaceView_video.getHolder();

        surfaceHolder_video.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                surfaceHolder_video=holder;
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                surfaceHolder_video=holder;
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                surfaceHolder_video=null;
                stopVideo();
                releaseCamera();
            }
        });
        button_video_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag_video){
                    //openVideo();
                    showToast("不好意思！\n" + "功能未实现",Toast.LENGTH_SHORT);
                }else{
                    showToast("已经打开了摄像头",Toast.LENGTH_SHORT);
                }
                flag_video=true;
            }
        });
        button_video_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_video){
                    //startVideo();
                    showToast("不好意思！\n" + "功能未实现",Toast.LENGTH_SHORT);
                    //button_video_record.setText("结束");
                }else{
                    showToast("请先打开摄像头",Toast.LENGTH_SHORT);
                }
            }
        });
        button_video_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_video){
                    showToast("不好意思！\n功能未实现",Toast.LENGTH_SHORT);
                    //stopVideo();
                    //button_video_record.setText("录制");
                }else{
                    showToast("还没有打开摄像头呢",Toast.LENGTH_SHORT);
                }
                flag_video=false;
            }
        });


        showToast("如果不需要直接拨打电话时，则不需要申请拨打电话的权限", Gravity.CENTER);
        button_dial = (Button) findViewById(R.id.button_dial);
        button_call = (Button) findViewById(R.id.button_call);
        button_sendsms = (Button) findViewById(R.id.button_sendsms);
        button_music_filesystem = (Button) findViewById(R.id.button_music_filesystem);
        button_music_local = (Button) findViewById(R.id.button_music_local);
        button_music_web = (Button) findViewById(R.id.button_music_web);
        button_music_service = (Button) findViewById(R.id.button_music_service);
        toggleButton_battery = (ToggleButton) findViewById(R.id.toggleButton_battery);

        //音频录制
        flag_record = false;
        button_recorder_start = (Button) findViewById(R.id.button_recorder_start);
        button_recorder_stop = (Button) findViewById(R.id.button_recorder_stop);
        textView_recorder = (TextView) findViewById(R.id.textview_recorder);

        textView_showbattery = (TextView) findViewById(R.id.textView_showbattery);
        textView_playmusic = (TextView) findViewById(R.id.textview_playmusic);
        editText_receiver = (EditText) findViewById(R.id.editText_receiver);
        editText_smscontent = (EditText) findViewById(R.id.editText_smscontent);
        editText = (EditText) findViewById(R.id.editText_dialorcall);

        //获取Shared Preference对象
        SharedPreferences setinfo=getPreferences(Activity.MODE_PRIVATE);
        //取出保存的用户名和密码  将取出的信息写在对应的edittext
        editText.setText(setinfo.getString("Telephone",""));
        editText_receiver.setText(setinfo.getString("Receiver",""));

        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                    //获取当前手机电量
                    int current = intent.getExtras().getInt("level");
                    //获取总电量
                    int total = intent.getExtras().getInt("scale");
                    textView_showbattery.setText("当前电量是：" + current * 100 / total + "%");
                }
            }
        };
        toggleButton_battery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                    registerReceiver(broadcastReceiver, intentFilter);
                    getLocalMessage();
                } else {
                    unregisterReceiver(broadcastReceiver);
                    textView_showbattery.setText("获取手机电量");
                }
            }
        });


        button_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telnum = editText.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telnum));
                startActivity(intent);
            }
        });

        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //以下内容是为了检测应用是否有拨打电话的权限
                if (ActivityCompat.checkSelfPermission(InnerIntentActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                telnum = editText.getText().toString();
                if (!telnum.equals("")) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telnum));
                    startActivity(intent);
                } else {
                    showToast("请输入手机号码", Toast.LENGTH_SHORT);
                }
            }
        });

        button_sendsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String receiver = editText_receiver.getText().toString();
                String content = editText_smscontent.getText().toString();
                if (receiver.equals("") || content.equals("")) {
                    showToast("输入有误，请重新输入", Toast.LENGTH_SHORT);
                } else {
                    SmsManager message = SmsManager.getDefault();
                    message.sendTextMessage(receiver, null, content, null, null);
                    showToast("测试所用短信已发送", Toast.LENGTH_SHORT);
                }
            }
        });

        myButtonListener ls = new myButtonListener();
        button_music_filesystem.setOnClickListener(ls);
        button_music_local.setOnClickListener(ls);
        button_music_web.setOnClickListener(ls);
        button_music_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_playmusic.setText("正在以服务形式播放音乐/sdcard/Music/test.mp3");
                Intent mIntent = new Intent();
                mIntent.setClass(InnerIntentActivity.this, MyAudioService.class);
                startService(mIntent);
            }
        });

        //音频测试
        myRecorderButtonListener my = new myRecorderButtonListener();
        button_recorder_start.setOnClickListener(my);
        button_recorder_stop.setOnClickListener(my);

    }

    public boolean checkSDCard() {
        //检测SD卡是否插入手机
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public void recordStart() {
        if (checkSDCard()) {
            recordPath = Environment.getExternalStorageDirectory();
            File path = new File(recordPath.getPath() + File.separator + "myAudioRecords");
            if (!path.exists()) {
                if (!path.mkdirs()) {
                    showToast("创建目录失败", Toast.LENGTH_LONG);
                    return;
                }
            }
            recordPath = path;
        } else {
            showToast("SD卡未连接", Toast.LENGTH_LONG);
            return;
        }
        //创建文件
        try {
            recordFile = File.createTempFile(String.valueOf("myRecord_"), ".amr", recordPath);
        } catch (IOException e) {
            showToast("文件创建失败", Toast.LENGTH_LONG);
            e.printStackTrace();
        }
        //创建对象
        mediaRecorder = new MediaRecorder();
        //设置麦克风
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //输出文件格式
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        //音频文件编码
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        //输出文件路径
        mediaRecorder.setOutputFile(recordFile.getAbsolutePath());
        filename=recordFile.getAbsolutePath();
        //showToast(recordFile.getPath(), Toast.LENGTH_LONG);
        //textView_recorder.setText(recordFile.getAbsolutePath()+"~~~~"+recordFile.getPath());

        //开始录音
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        handler.post(runnable);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        int minutes = 0;
        int seconds = 0;
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                if (seconds < 60) {
                    seconds++;
                } else {
                    seconds = 0;
                    minutes++;
                }
                //因为更新view必须写在主线程内，所以此处需要使用Handler而不能直接使用Thread
                textView_recorder.setText(minutes + ":" + seconds);
                handler.postDelayed(this, 1000);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("exception...");
            }
        }
    };

    public void recordStop() {
        handler.removeCallbacks(runnable);
        try {
            if (mediaRecorder != null) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        showToast("音频文件已保存\n"+filename, Toast.LENGTH_LONG);
    }


    //拍照程序
    public void openCamera() {
        try {
            //不传参数为后置摄像头
            camera = Camera.open(1);
            //camera=Camera.open();
        }catch (Exception e){
            showToast("打开相机失败", Toast.LENGTH_SHORT);
            e.printStackTrace();
            return;
        }
        Camera.Parameters parameters=camera.getParameters();
        parameters.setPreviewSize(previewWidth,previewHeight);
        parameters.setPictureFormat(PixelFormat.JPEG);
        parameters.setFocusMode("auto");
        parameters.setPictureSize(2448,3264);
        camera.setParameters(parameters);
        camera.setDisplayOrientation(90);
        try{
            camera.setPreviewDisplay(surfaceHolder_camera);
        } catch (IOException e) {
            showToast("预览失败", Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
        camera.startPreview();
        flag_camera=true;
    }

    public void takePicture(){
        if(checkSDCard()){
            camera.takePicture(null,null,jpeg);
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            camera.startPreview();
        }else{
            showToast("没有找到SD卡", Toast.LENGTH_SHORT);
            return;
        }
    }

    public void closeCamera(){
        if(camera!=null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    public Camera.PictureCallback jpeg=new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
            Matrix matrix=new Matrix();
            matrix.setRotate(270);
            bitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            if (checkSDCard()) {
                recordPath = Environment.getExternalStorageDirectory();
                File path = new File(recordPath.getPath() + File.separator + "myImageRecords");
                if (!path.exists()) {
                    if (!path.mkdirs()) {
                        showToast("创建目录失败", Toast.LENGTH_LONG);
                        return;
                    }
                }
                recordPath = path;
            } else {
                showToast("SD卡未连接", Toast.LENGTH_LONG);
                return;
            }
            //创建文件
            try {
                recordFile = File.createTempFile(String.valueOf("myImage_"), ".jpg", recordPath);
            } catch (IOException e) {
                showToast("文件创建失败", Toast.LENGTH_LONG);
                e.printStackTrace();
            }
            imagepath=recordFile.getAbsolutePath();
            File pictureFile=new File(imagepath);
            /*
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
            Date curDate=new Date(System.currentTimeMillis());
            File pictureFile=new File(recordPath.getAbsolutePath()+"/"+formatter.format(curDate)+".jpg","");
            */
            if(pictureFile==null){
                showToast("创建目录失败", Toast.LENGTH_SHORT);
                return;
            }
            try{
                FileOutputStream fs=new FileOutputStream(pictureFile);
                BufferedOutputStream bs=new BufferedOutputStream(fs);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bs);
                bs.flush();
                bs.close();
                fs.close();
                showToast("图片已保存\n"+pictureFile.getAbsolutePath(), Toast.LENGTH_LONG);
            } catch (FileNotFoundException e) {
                showToast("未找到文件"+e.getMessage(), Toast.LENGTH_SHORT);
                e.printStackTrace();
            } catch (IOException e) {
                showToast("访问文件失败"+e.getMessage(), Toast.LENGTH_SHORT);
                e.printStackTrace();
            }
        }
    };

    //录制视频
    public void openVideo(){
        try {
            //不传参数为后置摄像头
            camera1 = Camera.open();
            //camera=Camera.open();
        }catch (Exception e){
            showToast("打开相机失败", Toast.LENGTH_SHORT);
            e.printStackTrace();
            return;
        }
        /*
        Camera.Parameters parameters=camera.getParameters();
        parameters.setPreviewSize(previewWidth,previewHeight);
        parameters.setPictureFormat(PixelFormat.JPEG);
        parameters.setFocusMode("auto");
        parameters.setPictureSize(2448,3264);
        camera.setParameters(parameters);
        camera.setDisplayOrientation(90);
        */
        try{
            camera1.setPreviewDisplay(surfaceHolder_video);
        } catch (IOException e) {
            showToast("预览失败", Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
        camera1.startPreview();
    }

    public void startVideo(){
        //给摄像头解锁
        showToast("ceshi",Toast.LENGTH_SHORT);
        camera1.unlock();
        //MediaRecorder获取访问摄像头权限
        videoRecorder.setCamera(camera1);
/*
        //设置录制视频过程中所录制音频来自手机的麦克风
        videoRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        //设置视频源为摄像头
        videoRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //设置视频录制的输出文件格式为3gp
        videoRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //设置音频编码方式为AAC
        videoRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //设置录制的视频的编码方式为H.264
        videoRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        //设置视频录制的分辨率，必须在设置编码和格式的后面
        videoRecorder.setVideoSize(176,144);
        //设置录制的视频帧率，必须在设置编码和格式的后面
        videoRecorder.setVideoFrameRate(20);

        if (checkSDCard()) {
            recordPath = Environment.getExternalStorageDirectory();
            File path = new File(recordPath.getPath() + File.separator + "myVideoRecords");
            if (!path.exists()) {
                if (!path.mkdirs()) {
                    showToast("创建目录失败", Toast.LENGTH_LONG);
                    return;
                }
            }
            recordPath = path;
        } else {
            showToast("SD卡未连接", Toast.LENGTH_LONG);
            return;
        }
        //创建文件
        try {
            recordFile = File.createTempFile(String.valueOf("myVideo_"), ".3gp", recordPath);
        } catch (IOException e) {
            showToast("文件创建失败", Toast.LENGTH_LONG);
            e.printStackTrace();
        }
        videopath=recordFile.getAbsolutePath();
        //设置输出文件路径
        videoRecorder.setOutputFile(videopath);
        //设置预览
        videoRecorder.setPreviewDisplay(surfaceHolder_video.getSurface());
        try{
            videoRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
        videoRecorder.start();
        */
        //handler.post(runnable_video);
    }

    public void stopVideo(){
        handler.removeCallbacks(runnable_video);
        if(videoRecorder!=null){
            videoRecorder.stop();
            videoRecorder.reset();
            videoRecorder.release();
            videoRecorder=null;
            camera1.lock();
            releaseCamera();
        }
    }

    public void releaseCamera(){
        if(camera1!=null){
            camera1.release();
            camera1=null;
        }
    }

    Runnable runnable_video = new Runnable() {
        int minutes = 0;
        int seconds = 0;
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                if (seconds < 60) {
                    seconds++;
                } else {
                    seconds = 0;
                    minutes++;
                }
                //因为更新view必须写在主线程内，所以此处需要使用Handler而不能直接使用Thread
                textView_video.setText(minutes + ":" + seconds);
                handler.postDelayed(this, 1000);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("exception...");
            }
        }
    };

    @Override
    protected void onStop() {
        //获取Shared Preference对象
        SharedPreferences setinfo=getPreferences(Activity.MODE_PRIVATE);
        //保存用户名和密码
        setinfo.edit()
                .putString("Telephone",editText.getText().toString())
                .putString("Receiver",editText_receiver.getText().toString())
                .commit();
        super.onStop();
    }

    class myRecorderButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button_recorder_start) {
                textView_recorder.setText("正在进行录音");
                if(!flag_record){
                    InnerIntentActivity.this.recordStart();
                }
                flag_record = true;
            }
            if (v.getId() == R.id.button_recorder_stop) {
                textView_recorder.setText("已经停止录音");
                if(flag_record){
                    InnerIntentActivity.this.recordStop();
                }
                flag_record = false;
            }
        }
    }

    class myButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_music_filesystem:
                    playAudio(1);
                    break;
                case R.id.button_music_local:
                    playAudio(2);
                    break;
                case R.id.button_music_web:
                    playAudio(3);
                    break;
                default:
                    break;
            }
        }
    }

    private void playAudio(int type) {
        try {
            switch (type) {
                case 1:
                    //path= Environment.getDataDirectory().getPath();
                    path = "/sdcard/Music/test.mp3";
                    if (!(new File(path)).exists()) {
                        showToast("SD卡中未找到/sdcard/Music/test.mp3", Toast.LENGTH_SHORT);
                    }
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    textView_playmusic.setText("播放/sdcard/Music/test.mp3");
                    createProgress("音量：", mediaPlayer);
                    createProgress("进度：", mediaPlayer);
                    break;
                case 2:
                    mediaPlayer = MediaPlayer.create(this, R.raw.testa);
                    mediaPlayer.start();
                    textView_playmusic.setText("播放raw/testa");
                    createProgress("音量：", mediaPlayer);
                    createProgress("进度：", mediaPlayer);
                    break;
                case 3:
                    path = "http://music.163.com/m/song?id=19144617";
                    if (path == "") {
                        showToast("未找到http://music.163.com/m/song?id=19144617", Toast.LENGTH_SHORT);
                    }
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.start();
                    textView_playmusic.setText("播放I only want to be with you");
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            textView_playmusic.setText(e.toString());
            showToast("播放异常", Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
    }

    public void createProgress(final String mystr, final MediaPlayer mediaPlayer) {
        final SeekBar seekBar = new SeekBar(InnerIntentActivity.this);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        //seekBar.setProgress(100);
        seekBar.setProgress(audioManager.getStreamVolume(audioManager.STREAM_MUSIC));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (mystr.equals("音量：")) {
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (progress / 100.0 * audioManager.getStreamMaxVolume(audioManager.STREAM_MUSIC)), AudioManager.FLAG_SHOW_UI);
                    } else {
                        mediaPlayer.seekTo((int) (progress * mediaPlayer.getDuration() / 100.0));
                    }

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //flag_play=false;

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                //flag_play=true;
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        seekBar.setPadding(20, 0, 20, 0);
        seekBar.setLayoutParams(params);
        TextView textView = new TextView(InnerIntentActivity.this);
        textView.setText(mystr);
        textView.setTextSize(30);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout_A);
        linearLayout.addView(textView);
        linearLayout.addView(seekBar);
        if (mystr.equals("进度：")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //while (flag_play){
                    while (true) {
                        seekBar.setProgress((int) (mediaPlayer.getCurrentPosition() * 100.0 / mediaPlayer.getDuration()));
                        try {
                            Thread.sleep(300);

                        } catch (InterruptedException e) {
                            showToast("线程异常", Toast.LENGTH_SHORT);
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            showToast(e.toString(), Toast.LENGTH_SHORT);
                        }
                    }
                    //if (seekBar.getProgress()<=103){
                    //  seekBar.setProgress(100);
                    // }
                }//}
            }).start();
        }
    }

    @Override
    protected void onDestroy() {
        //showToast("显示异常",Toast.LENGTH_SHORT);
        //flag_play=false;
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            //此处无法释放，待解决。
            //mediaPlayer.release();
            mediaPlayer = null;
        }
        if(camera!=null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        super.onDestroy();
    }

    public void showToast(String str, int duration) {
        //Toast.makeText(this, str, duration.show();
        Toast toast = Toast.makeText(this, str, duration);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }

    public void getLocalMessage(){
        String[] projection=new String[]{"address","person","date","body"};
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d=null;
        try{
            d=dateFormat.parse("1970-01-11 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Cursor cursor=this.managedQuery(Uri.parse("content://sms/inbox"),projection,"date>?",new String[]{d.getTime()+""},"date desc");
        if(cursor!=null){
            int addressColumn=cursor.getColumnIndex("address");
            int personColumn=cursor.getColumnIndex("person");
            int bodyColumn=cursor.getColumnIndex("body");
            int dateColumn=cursor.getColumnIndex("date");

            String total="";
            while (cursor.moveToNext()){
                String address=cursor.getString(addressColumn);
                //总是获取到null
                int person=cursor.getInt(personColumn);
                String body=cursor.getString(bodyColumn);
                String date=dateTostring(cursor.getString(dateColumn));

                if(!(address.startsWith("9",0)||address.startsWith("10",0)||address.length()>14)){
                    total=total+"\n"+address+"("+getName(address)+")"+"\n-----"+date+"-----\n"+body+"\n\n";
                }
            }
            if(total.isEmpty()){
                total="请允许本应用获得联系人，读取短信权限";
            }
            //Android SDK version>14以后，不需要自己关闭
            //cursor.close();
            writeData("/sdcard/sms_inbox.txt",total);
        }
    }

    public String dateTostring(String  time){
        Date d=new Date(Long.parseLong(time));
        return (new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒")).format(d);
    }

    public void writeData(String filename,String string){
        try{
            //文件输出流，如果目标文件不存在，新建一个；如果已存在，默认覆盖
            FileOutputStream fileOutputStream=new FileOutputStream(filename);
            byte[] bytes=string.getBytes();
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName(String num){
        ContentResolver resolver=this.getContentResolver();
        Uri uri=Uri.parse("content://com.android.contacts/data/phones/filter/"+num);
        Cursor c=resolver.query(uri,new String[]{"display_name"},null,null,null);
        if(c!=null){
            while (c.moveToNext()){
                num=c.getString(0);
            }
            if(num.isEmpty()){
                //Android SDK version>14以后，不需要自己关闭
                //c.close();
                return null;
            }
            //Android SDK version>14以后，不需要自己关闭
            //c.close();
        }
        return num;
    }
}

