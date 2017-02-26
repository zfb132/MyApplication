package com.whuzfb.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by zfb15 on 2017/1/23.
 */

public class SpinnerActivity extends Activity {

    private TextView textview_thirdA;
    private Spinner spinner_thirdA;
    private TextView textview_thirdB;
    private Spinner spinner_thirdB;
    private TextView textview_thirdC;
    private Spinner spinner_thirdC;
    private TextView textview_thirdD;
    private Spinner spinner_thirdD;
    private TextView textview_thirdE;
    private Spinner spinner_thirdE;
    private TextView textview_thirdF;
    private Spinner spinner_thirdF;
    private TextView textview_thirdG;
    private Spinner spinner_thirdG;
    private TextView textview_thirdH;
    private Spinner spinner_thirdH;
    private TextView textview_thirdI;
    private Spinner spinner_thirdI;
    private TextView textview_thirdJ;
    private Spinner spinner_thirdJ;
    private TextView textview_thirdK;
    private Spinner spinner_thirdK;
    private TextView textview_thirdL;
    private Spinner spinner_thirdL;
    private TextView textview_thirdM;
    private Spinner spinner_thirdM;
    private TextView textview_thirdN;
    private Spinner spinner_thirdN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner);

        spinner_thirdA=(Spinner)findViewById(R.id.spinnerA);
        textview_thirdA=(TextView)findViewById(R.id.textView6);
        spinner_thirdB=(Spinner)findViewById(R.id.spinnerB);
        textview_thirdB=(TextView)findViewById(R.id.textView7);
        spinner_thirdC=(Spinner)findViewById(R.id.spinnerC);
        textview_thirdC=(TextView)findViewById(R.id.textView8);
        spinner_thirdD=(Spinner)findViewById(R.id.spinnerD);
        textview_thirdD=(TextView)findViewById(R.id.textView9);
        spinner_thirdE=(Spinner)findViewById(R.id.spinnerE);
        textview_thirdE=(TextView)findViewById(R.id.textView10);
        spinner_thirdF=(Spinner)findViewById(R.id.spinnerF);
        textview_thirdF=(TextView)findViewById(R.id.textView11);
        spinner_thirdG=(Spinner)findViewById(R.id.spinnerG);
        textview_thirdG=(TextView)findViewById(R.id.textView12);
        spinner_thirdH=(Spinner)findViewById(R.id.spinnerH);
        textview_thirdH=(TextView)findViewById(R.id.textView13);
        spinner_thirdI=(Spinner)findViewById(R.id.spinnerI);
        textview_thirdI=(TextView)findViewById(R.id.textView14);
        spinner_thirdJ=(Spinner)findViewById(R.id.spinnerJ);
        textview_thirdJ=(TextView)findViewById(R.id.textView15);
        spinner_thirdK=(Spinner)findViewById(R.id.spinnerK);
        textview_thirdK=(TextView)findViewById(R.id.textView16);
        spinner_thirdL=(Spinner)findViewById(R.id.spinnerL);
        textview_thirdL=(TextView)findViewById(R.id.textView17);
        spinner_thirdM=(Spinner)findViewById(R.id.spinnerM);
        textview_thirdM=(TextView)findViewById(R.id.textView18);
        spinner_thirdN=(Spinner)findViewById(R.id.spinnerN);
        textview_thirdN=(TextView)findViewById(R.id.textView19);


        final ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.subject,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_thirdA.setAdapter(adapter);
        //重载函数时自己写。
        spinner_thirdA.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>arg0, View arg1,int arg2,long arg3){
                textview_thirdA.setText(adapter.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?>arg0){
                textview_thirdA.setText("未选择");
                arg0.setVisibility(View.VISIBLE);
            }

        });
        //这个是获得被选择的spinner的值
        //textview_thirdA.setText(spinner_thirdA.getSelectedItem().toString());

        spinner_thirdB.setAdapter(adapter);
        spinner_thirdB.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>arg0, View arg1,int arg2,long arg3){
                textview_thirdB.setText(adapter.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?>arg0){
                textview_thirdB.setText("未选择");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        spinner_thirdC.setAdapter(adapter);
        spinner_thirdC.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>arg0, View arg1,int arg2,long arg3){
                textview_thirdC.setText(adapter.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?>arg0){
                textview_thirdC.setText("未选择");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        spinner_thirdD.setAdapter(adapter);
        spinner_thirdD.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>arg0, View arg1,int arg2,long arg3){
                textview_thirdD.setText(adapter.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?>arg0){
                textview_thirdD.setText("未选择");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        spinner_thirdE.setAdapter(adapter);
        spinner_thirdE.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>arg0, View arg1,int arg2,long arg3){
                textview_thirdE.setText(adapter.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?>arg0){
                textview_thirdE.setText("未选择");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        spinner_thirdF.setAdapter(adapter);
        spinner_thirdF.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>arg0, View arg1,int arg2,long arg3){
                textview_thirdF.setText(adapter.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?>arg0){
                textview_thirdF.setText("未选择");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        spinner_thirdG.setAdapter(adapter);
        spinner_thirdG.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>arg0, View arg1,int arg2,long arg3){
                textview_thirdG.setText(adapter.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?>arg0){
                textview_thirdG.setText("未选择");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        spinner_thirdH.setAdapter(adapter);
        spinner_thirdH.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>arg0, View arg1,int arg2,long arg3){
                textview_thirdH.setText(adapter.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?>arg0){
                textview_thirdH.setText("未选择");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        spinner_thirdI.setAdapter(adapter);
        spinner_thirdI.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>arg0, View arg1,int arg2,long arg3){
                textview_thirdI.setText(adapter.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?>arg0){
                textview_thirdI.setText("未选择");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        spinner_thirdJ.setAdapter(adapter);
        spinner_thirdJ.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>arg0, View arg1,int arg2,long arg3){
                textview_thirdJ.setText(adapter.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?>arg0){
                textview_thirdJ.setText("未选择");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        spinner_thirdK.setAdapter(adapter);
        spinner_thirdK.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>arg0, View arg1,int arg2,long arg3){
                textview_thirdK.setText(adapter.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?>arg0){
                textview_thirdK.setText("未选择");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        spinner_thirdL.setAdapter(adapter);
        spinner_thirdL.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>arg0, View arg1,int arg2,long arg3){
                textview_thirdL.setText(adapter.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?>arg0){
                textview_thirdL.setText("未选择");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        spinner_thirdM.setAdapter(adapter);
        spinner_thirdM.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>arg0, View arg1,int arg2,long arg3){
                textview_thirdM.setText(adapter.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?>arg0){
                textview_thirdM.setText("未选择");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        spinner_thirdN.setAdapter(adapter);
        spinner_thirdN.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>arg0, View arg1,int arg2,long arg3){
                textview_thirdN.setText(adapter.getItem(arg2));
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?>arg0){
                textview_thirdN.setText("未选择");
                arg0.setVisibility(View.VISIBLE);
            }
        });
    }
}
