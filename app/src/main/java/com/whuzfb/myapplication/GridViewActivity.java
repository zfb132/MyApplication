package com.whuzfb.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by zfb15 on 2017/1/31.
 */

public class GridViewActivity extends Activity {
    private Integer[] mThumbIds={
            R.drawable.sample_0,R.drawable.sample_1,R.drawable.sample_2,
            R.drawable.sample_3,R.drawable.sample_4,R.drawable.sample_5,
            R.drawable.sample_0,R.drawable.sample_1,R.drawable.sample_2,
            R.drawable.sample_3,R.drawable.sample_4,R.drawable.sample_5,
            R.drawable.sample_0,R.drawable.sample_1,R.drawable.sample_2,
            R.drawable.sample_3,R.drawable.sample_4,R.drawable.sample_5
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);

        GridView gridView=(GridView)findViewById(R.id.gridviewA);
        gridView.setAdapter(new myImageAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showToast("这是第"+(position+1)+"幅图像",Toast.LENGTH_SHORT);

            }
        });
    }
    public void showToast(String str,int duration)
    {
        //Toast.makeText(this, str, duration).show();
        Toast toast = Toast.makeText(this, str, duration);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }

    public class myImageAdapter extends BaseAdapter{
        private Context mContext;
        public myImageAdapter(Context c){
            mContext=c;
        }
        @Override
        public int getCount() {
            return mThumbIds.length;
        }
        @Override
        public Object getItem(int position) {
            return position;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView==null){
                //实例化ImageView对象
                imageView=new ImageView(mContext);
                //设置ImageView对象布局，设置View的height和width
                imageView.setLayoutParams(new GridView.LayoutParams(300,300));
                //设置边界对齐
                imageView.setAdjustViewBounds(false);
                //按比例统一缩放图片
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                //设置间距
                imageView.setPadding(8,8,8,8);
            } else{
                imageView=(ImageView)convertView;
            }
            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }
    }
}
