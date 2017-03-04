package com.whuzfb.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by zfb15 on 2017/2/23.
 */

public class PackageShow extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packageshow);

        Intent intent=getIntent();
        String text=intent.getStringExtra("com.whuzfb.myapplication.packageInfo");
        if(text==null){
            text="未获取到任何信息";
        }
        TextView textView=(TextView)findViewById(R.id.textView_listitem_show);
        EditText editText=(EditText)findViewById(R.id.edittext_useless);
        editText.setText("");
        editText.setVisibility(View.INVISIBLE);
        textView.setText(text);

    }
}
