package com.whuzfb.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by zfb15 on 2017/2/3.
 */

public class BitmapActivity extends Activity implements Animation.AnimationListener{

    public ImageView imageView;
    public SeekBar seekBar;
    public Bitmap mybmp,newbmp;
    public int bmpwidth,bmpheight;

    public Animation bmp_to_large;
    public Animation bmp_to_small;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bitmap);

        imageView=(ImageView)findViewById(R.id.imageView_bitmap);

        bmp_to_large= AnimationUtils.loadAnimation(this,R.anim.bitmap_to_big);
        bmp_to_small= AnimationUtils.loadAnimation(this,R.anim.bitmap_to_small);
        bmp_to_small.setAnimationListener(this);
        bmp_to_large.setAnimationListener(this);
        imageView.startAnimation(bmp_to_small);


        seekBar=(SeekBar)findViewById(R.id.seekBar);
        //由Resource载入图片
        mybmp= BitmapFactory.decodeResource(getResources(),R.drawable.sample_0);
        bmpwidth=mybmp.getWidth();
        bmpheight=mybmp.getHeight();
        //实例化matrix矩阵
        Matrix matrix=new Matrix();
        //设定Matrix属性：x，y缩放比例为1.5
        matrix.postScale(1.5F,1.5F);
        //顺时针旋转45度
        matrix.postRotate(45.0F);

        newbmp=Bitmap.createBitmap(mybmp,0,0,bmpwidth,bmpheight,matrix,true);

        mySeekBarChange myseekbar=new mySeekBarChange();
        seekBar.setOnSeekBarChangeListener(myseekbar);


    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation.hashCode()==bmp_to_large.hashCode()){
            imageView.startAnimation(bmp_to_small);
        }else{
            imageView.startAnimation(bmp_to_large);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    class mySeekBarChange implements SeekBar.OnSeekBarChangeListener{
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Matrix matrix=new Matrix();
            if(progress>=50)
                matrix.postScale(3.0F,1.5F);
            matrix.postRotate((float)progress*3.6F);

            newbmp=Bitmap.createBitmap(mybmp,0,0,bmpwidth,bmpheight,matrix,true);
            imageView.setImageBitmap(newbmp);
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
