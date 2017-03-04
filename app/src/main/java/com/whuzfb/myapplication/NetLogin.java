package com.whuzfb.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
在buildToolsVersion下边
        defaultConfig上边

        添加如下代码

        useLibrary 'org.apache.http.legacy'
*/
public class NetLogin extends Activity {

    private EditText et_check;
    private EditText et_address;
    private TextView tv_result;
    private Button btn_post;
    private Button btn_get;
    private Button btn_web;
    private ImageView img_checkcode;
    private Bitmap checkCode;
    private String COOKIE;
    private String token;
    private String regex="&csrftoken=([\\S]{36})";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.netlogin);

        et_check = (EditText) findViewById(R.id.check);
        et_address=(EditText) findViewById(R.id.address);
        btn_post = (Button) findViewById(R.id.btn_post);
        btn_get = (Button) findViewById(R.id.btn_get);
        btn_web = (Button) findViewById(R.id.btn_web);
        tv_result = (TextView) findViewById(R.id.tv_result);
        img_checkcode = (ImageView) findViewById(R.id.img);

        btn_web.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(networkGet).start();
            }
        });
        btn_get.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(everyGet).start();
            }
        });
        btn_post.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(networkPost).start();
            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");

            //正则表达式匹配出token
            Pattern patternName = Pattern.compile(regex);
            Matcher mName = patternName.matcher(val);
            if(mName.find()){
                token=mName.group();
                token=token.replaceAll("&csrftoken=","");
            }

            tv_result.setText(val.replaceAll("\r", ""));
            if(data.getBoolean("flag")) {
                img_checkcode.setImageBitmap(checkCode);
            }
        }
    };

    Runnable networkPost = new Runnable() {
        public void run() {
            String url = "http://210.42.121.241/servlet/Login";
            HttpResponse httpResponse = null;
            HttpPost httpPost = new HttpPost(url);
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", "2015301200199"));
            params.add(new BasicNameValuePair("pwd", "a1815f56a3c3ea2035911ae0d95adf56"));
            params.add(new BasicNameValuePair("xdvfb", et_check.getText().toString()));
            httpPost.setHeader("Cookie", "JSESSIONID=" + COOKIE);
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            } catch(UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                httpResponse = new DefaultHttpClient().execute(httpPost);
            } catch(IOException e) {
                e.printStackTrace();
            }
            String resultb="";
            if(httpResponse.getStatusLine().getStatusCode() == 200) {
                try {
                    resultb = EntityUtils.toString(httpResponse.getEntity());
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", resultb);
            data.putBoolean("flag", false);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

    Runnable networkGet = new Runnable() {
        public void run() {
            String url = "http://210.42.121.241/servlet/GenImg";
            HttpResponse httpResponse = null;
            HttpGet httpGet = new HttpGet(url);
            HttpClient client=new DefaultHttpClient();
            try {
                httpResponse = client.execute(httpGet);
                COOKIE = ((AbstractHttpClient) client).getCookieStore().getCookies().get(0).getValue();
            } catch(IOException e) {
                e.printStackTrace();
            }
            String resulta=COOKIE;
            if(httpResponse.getStatusLine().getStatusCode() == 200) {
                try {
                    byte[] bytes = new byte[1024];
                    bytes = EntityUtils.toByteArray(httpResponse.getEntity());
                    checkCode = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", resulta);
            data.putBoolean("flag", true);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };


    Runnable everyGet = new Runnable() {
        public void run() {
            String url = "http://210.42.121.241/servlet/Svlt_QueryStuScore?csrftoken="+token+"&year=0&term=&learnType=&scoreFlag=0&t=Sat%20Mar%2004%202017%2017:49:29%20GMT+0800%20(%D6%D0%B9%FA%B1%EA%D7%BC%CA%B1%BC%E4)";
            HttpResponse httpResponse = null;
            //url=url+et_address.getText().toString();
            HttpGet httpGet = new HttpGet(url);
            HttpClient client=new DefaultHttpClient();
            httpGet.setHeader("Cookie", "JSESSIONID=" + COOKIE);
            try {
                httpResponse = client.execute(httpGet);
            } catch(IOException e) {
                e.printStackTrace();
            }
            String resultc="";
            if(httpResponse.getStatusLine().getStatusCode() == 200) {
                try {
                    resultc = EntityUtils.toString(httpResponse.getEntity());
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", resultc);
            data.putBoolean("flag", false);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

    public  String getMd5Value(String sSecret) {
        try {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            bmd5.update(sSecret.getBytes());
            int i;
            StringBuffer buf = new StringBuffer();
            byte[] b = bmd5.digest();// 加密
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}


