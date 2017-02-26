package com.whuzfb.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

/**
 * Created by zfb15 on 2017/1/31.
 */

public class GalleryImageSwitcherActivity extends Activity {

    private Gallery gallery;
    private ImageSwitcher imageSwitcher;
    private int[] resids=new int[] {
            R.drawable.sample_0,R.drawable.sample_1,
            R.drawable.sample_2,R.drawable.sample_3,
            R.drawable.sample_4,R.drawable.sample_5
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.galleryimageswitcher);

        gallery=(Gallery)findViewById(R.id.gallery);
        imageSwitcher=(ImageSwitcher)findViewById(R.id.imageswitcher);

        //创建用于描述图像数据的ImageAdapter对象
        final myImageAdapter imageAdapter=new myImageAdapter(this);
        //设置gallery的adapter对象
        gallery.setAdapter(imageAdapter);
        //添加gallery监听器
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //当选取gallery上的图片时，在ImageSwitcher上显示该图像
                imageSwitcher.setImageResource(resids[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }) ;

        //设置ImageSwitcher的工厂对象
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            //ImageSwitcher用这个方法创建一个对象来显示图片
            @Override
            public View makeView() {
                ImageView imageview=new ImageView(GalleryImageSwitcherActivity.this);
                //setScaleType可以设置当图片大小和容器大小不匹配时的剪辑模式
                imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageview.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
                return imageview;
            }
        });

        //设置ImageSwitcher组件显示图像的动画效果
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_out));

    }


    public class myImageAdapter extends BaseAdapter{
        //定义context
        private Context mContext;
        //声明ImageAdapter
        public myImageAdapter(Context context){
            mContext=context;
        }

        @Override
        //获得图片的个数
        public int getCount() {
            return resids.length;
        }

        @Override
        //获取图片在图库中的位置
        public Object getItem(int position) {
            return position;
        }

        @Override
        //获取图片ID
        public long getItemId(int position) {
            return position;
        }

        @Override
        //返回具体位置的ImageView对象
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView=new ImageView(mContext);
            //给ImageView设置资源
            imageView.setImageResource(resids[position]);
            //设置图片布局大小为100X100
            imageView.setLayoutParams(new Gallery.LayoutParams(100,100));
            //设置显示比例类型
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }
    }
}
