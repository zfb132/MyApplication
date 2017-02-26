package com.whuzfb.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * Created by zfb15 on 2017/2/3.
 */

public class BitmapActivity extends Activity {

    public ImageView imageView;
    public SeekBar seekBar;
    public Bitmap mybmp,newbmp;
    public int bmpwidth,bmpheight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bitmap);

        imageView=(ImageView)findViewById(R.id.imageView_bitmap);
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
