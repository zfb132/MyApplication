package com.whuzfb.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;


/**
 * Created by zfb15 on 2017/2/7.
 */

public class MyAudioService extends Service {


    private MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String path="/sdcard/Music/test.mp3";
        mediaPlayer=new MediaPlayer();
        try{
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }
}
