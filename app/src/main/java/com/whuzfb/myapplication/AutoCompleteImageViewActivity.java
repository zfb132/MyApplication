package com.whuzfb.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by zfb15 on 2017/1/24.
 */

public class AutoCompleteImageViewActivity extends Activity {

    private AutoCompleteTextView autotextview;
    private ImageButton imagebuttonA;
    private ImageButton imageButtonB;
    private ImageView imageview;
    /*
    private  static final String[] text=new String[]{
            "我们一起走","我们的大学","我们去哪呀","","hello","hello world","helloandroid","hellochina"
    };

    //final ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.subject,android.R.layout.simple_spinner_item);
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocompleteimageview);

        autotextview=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        imagebuttonA=(ImageButton)findViewById(R.id.imagebuttonA);
        imageButtonB=(ImageButton)findViewById(R.id.imageButtonB);
        imageview=(ImageView)findViewById(R.id.imageViewA);

        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.subject,android.R.layout.simple_dropdown_item_1line);
        //ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,text);
        autotextview.setAdapter(adapter);

        imagebuttonA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params=imageview.getLayoutParams();
                params.height+=5;
                params.width+=5;
                imageview.setLayoutParams(params);
            }
        });
        imageButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = imageview.getLayoutParams();
                params.height -= 5;
                params.width -= 5;
                imageview.setLayoutParams(params);
            }

        });
    }
}
