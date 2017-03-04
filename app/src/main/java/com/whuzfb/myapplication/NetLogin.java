package com.whuzfb.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private EditText et_user;
    private EditText et_pwd;
    private Spinner spinner_studyweb;
    private TextView tv_result;
    private Button btn_post;
    //private Button btn_get;
    private Button btn_changecode;
    private Button btn_sourcecode;
    private ImageView img_checkcode;
    private Bitmap bm_checkCode;
    private String COOKIE;
    private String token;
    private String regex="&csrftoken=([\\S]{36})";
    private String url_webcode;
    private String[] url_score={"http://210.42.121.241/servlet/Svlt_QueryStuScore?csrftoken=","&year=0&term=&learnType=&scoreFlag=0&t=Sat%20Mar%2004%202017%2017:49:29%20GMT+0800%20(%D6%D0%B9%FA%B1%EA%D7%BC%CA%B1%BC%E4)"};
    private String url_course="http://210.42.121.241/servlet/Svlt_QueryStuLsn?action=queryStuLsn&csrftoken=";
    private String url_info="http://210.42.121.241/stu/student_information.jsp";
    private String url_login="http://210.42.121.241/servlet/Login";
    private String url_checkcode="http://210.42.121.241/servlet/GenImg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.netlogin);

        //不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        et_check = (EditText) findViewById(R.id.et_check);
        et_user=(EditText) findViewById(R.id.et_user);
        et_pwd=(EditText) findViewById(R.id.et_pwd);
        spinner_studyweb=(Spinner)findViewById(R.id.spinner_studyweb);
        btn_post = (Button) findViewById(R.id.btn_post);
        //btn_get = (Button) findViewById(R.id.btn_get);
        btn_changecode = (Button) findViewById(R.id.btn_web);
        btn_sourcecode = (Button) findViewById(R.id.btn_sourcecode);
        tv_result = (TextView) findViewById(R.id.tv_result);
        img_checkcode = (ImageView) findViewById(R.id.img);
        new Thread(checkcodeGet).start();

        //获取Shared Preference对象
        SharedPreferences setinfo=getPreferences(Activity.MODE_PRIVATE);
        //取出保存的用户名和密码分别赋给字符串String username,password
        String username=setinfo.getString("STUDYNUM","");
        String password=setinfo.getString("PASSWORD","");
        //将取出的信息写在对应的edittext
        //其中user=(EditText)findViewById(R.id.editText);
        //pwd同理
        et_user.setText(username);
        et_pwd.setText(password);

        btn_sourcecode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                htmlcode();
            }
        });

        btn_changecode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et_check.setText("");
                new Thread(checkcodeGet).start();
            }
        });

        /*
        btn_get.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_result.setText("正在加载中···\n若15S内无反应，请检查网络后重试");
                new Thread(everyGet_study).start();
            }
        });
        */

        btn_post.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(networkPost).start();
            }
        });

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.web,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_studyweb.setAdapter(adapter);
        //重载函数时自己写。
        spinner_studyweb.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                switch (arg2){
                    case 0:
                        tv_result.setText("正在加载中···\n若15S内无反应，请检查网络后重试");
                        new Thread(everyGet_study_course).start();
                        break;
                    case 1:
                        tv_result.setText("正在加载中···\n若15S内无反应，请检查网络后重试");
                        new Thread(everyGet_study_info).start();
                        break;
                    case 2:
                        tv_result.setText("正在加载中···\n若15S内无反应，请检查网络后重试");
                        new Thread(everyGet_study_score).start();
                        break;
                    default:
                        break;
                }
                //textview_thirdA.setText(adapter.getItem(arg2));
                //arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?>arg0){
                //textview_thirdA.setText("未选择");
                //arg0.setVisibility(View.VISIBLE);
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
            //tv_result.setText(val.replaceAll("\\n\\n\\n", ""));
            switch (data.getInt("flag")){
                case 0:
                    tv_result.setText(val.replaceAll("\\n\\n\\n", ""));
                    img_checkcode.setImageBitmap(bm_checkCode);
                    break;
                case 1:
                    tv_result.setText(val);
                    break;
                case 2:
                    new AlertDialog.Builder(NetLogin.this).setTitle("源代码")
                            .setMessage(val)
                            .setPositiveButton("确定",null).show();
                    break;
            }
        }
    };

    Runnable networkPost = new Runnable() {
        public void run() {
            HttpResponse httpResponse = null;
            HttpPost httpPost = new HttpPost(url_login);
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", et_user.getText().toString()));
            params.add(new BasicNameValuePair("pwd", getMd5Value(et_pwd.getText().toString())));
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
            data.putInt("flag", 1);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

    Runnable checkcodeGet = new Runnable() {
        public void run() {
            HttpResponse httpResponse = null;
            HttpGet httpGet = new HttpGet(url_checkcode);
            HttpClient client=new DefaultHttpClient();
            try {
                httpResponse = client.execute(httpGet);
                COOKIE = ((AbstractHttpClient) client).getCookieStore().getCookies().get(0).getValue();
            } catch(IOException e) {
                e.printStackTrace();
            }
            String resulta="本次请求的cookie：\n"+COOKIE;
            if(httpResponse.getStatusLine().getStatusCode() == 200) {
                try {
                    byte[] bytes = new byte[1024];
                    bytes = EntityUtils.toByteArray(httpResponse.getEntity());
                    bm_checkCode = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", resulta);
            data.putInt("flag", 0);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

    Runnable everyGet_study_score = new Runnable() {
        public void run() {everythingGet_study(url_score[0]+token+url_score[1]);
        }
    };

    Runnable everyGet_study_info = new Runnable() {
        public void run() {everythingGet_study(url_info);
        }
    };

    Runnable everyGet_study_course = new Runnable() {
        public void run() {everythingGet_study(url_course+token);
        }
    };

    Runnable webcodeGet=new Runnable() {
        public void run() {
            getWebCode(url_webcode);
        }
    };

    protected void htmlcode() {
        LayoutInflater flater = LayoutInflater.from(this);
        View linearview = flater.inflate(R.layout.packageshow, null);
        TextView tv=  (TextView) linearview.findViewById(R.id.textView_listitem_show);
        final EditText et = (EditText) linearview.findViewById(R.id.edittext_useless);
        tv.setTextSize(10.0F);
        tv.setText("注意：\n百度的网址是:https://www.baidu.com/\n不是http://www.baidu.com/");
        et.setText("http://www.whuzfb.cn");
        AlertDialog.Builder dg = new AlertDialog.Builder(NetLogin.this);
        dg.setTitle("显示HTML代码");
        dg.setIcon(android.R.drawable.ic_dialog_info);
        dg.setView(linearview);
        dg.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                url_webcode=et.getText().toString();
                new Thread(webcodeGet).start();
            }
        });
        dg.setNegativeButton("取消", null);
        dg.show();// 显示对话框
    }

    //获得某个网址的源代码
    public void getWebCode(String url){
        /*
        //下面这个也行
        HttpResponse httpResponse = null;
        HttpGet httpGet = new HttpGet(url);
        HttpClient client=new DefaultHttpClient();
        try {
            httpResponse = client.execute(httpGet);
        } catch(IOException e) {
            e.printStackTrace();
        }

        if(httpResponse.getStatusLine().getStatusCode() == 200) {
            try {
                resultc = EntityUtils.toString(httpResponse.getEntity());
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        //这两种获得resultc的方法都是可行的
        */
        String resultc="";
        try {
            resultc = getHtmlContent(url);
        } catch (Exception e) {
            resultc="程序出现异常："+e.toString();
        }
        Message msg = new Message();
        Bundle data = new Bundle();
        data.putString("value", resultc);
        data.putInt("flag", 2);
        msg.setData(data);
        handler.sendMessage(msg);
    }

    //以登录状态访问教务系统的任一网址
    public void everythingGet_study(String url){
        HttpResponse httpResponse = null;
        HttpGet httpGet = new HttpGet(url);
        HttpClient client=new DefaultHttpClient();
        //header在需要登陆的页面必须设置，其他页面则不必
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
        data.putInt("flag", 1);
        msg.setData(data);
        handler.sendMessage(msg);
    }

    //获得任意字符串的MD5值
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

    //获得html的内容，用UTF-8解码
    public  String getHtmlContent(String path) throws Exception {
        // 通过网络地址创建URL对象
        URL url = new URL(path);
        // 根据URL
        // 打开连接，URL.openConnection函数会根据URL的类型，返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设定URL的请求类别，有POST、GET 两类
        conn.setRequestMethod("GET");
        //设置从主机读取数据超时（单位：毫秒）
        conn.setConnectTimeout(5000);
        //设置连接主机超时（单位：毫秒）
        conn.setReadTimeout(5000);
        // 通过打开的连接读取的输入流,获取html数据
        InputStream inStream = conn.getInputStream();
        // 得到html的二进制数据
        byte[] data = readInputStream(inStream);
        // 是用指定的字符集解码指定的字节数组构造一个新的字符串
        String html = new String(data, "utf-8");
        return html;
    }

    //读取输入流，得到html的二进制数据
    public static byte[] readInputStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        byte[] bt = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(bt)) != -1) {
            outstream.write(bt, 0, length);
        }
        inputStream.close();
        return outstream.toByteArray();
    }

    @Override
    protected void onStop() {
        //获取Shared Preference对象
        SharedPreferences setinfo=getPreferences(Activity.MODE_PRIVATE);
        //保存用户名和密码
        setinfo.edit()
                .putString("STUDYNUM",et_user.getText().toString())
                .putString("PASSWORD",et_pwd.getText().toString())
                .commit();
        super.onStop();
    }
}

