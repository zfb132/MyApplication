package com.whuzfb.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
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
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
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
    private boolean flag_checkcode;
    private int flag_search=0;
    private int flag_connect_checkcode=0;
    private int flag_connect_course=0;
    private int flag_connect_score=0;
    private int flag_connect_info=0;
    private int flag_connect_login=0;
    private String COOKIE;
    private String token;
    private String regex_info="<tr>\\s*<th[ \"0-9a-z=%]{0,20}>(.{0,200})</td>\\s*</tr>";
    private String regex_token="&csrftoken=([\\S]{36})";
    private String regex_score="<td>([0-9]{8,14})</td>\\s*<td>([ 0-9A-Z\\u4E00-\\u9FA5\\uff08\\uff09\\u0020]{4,20})</td>\\s*<td>([\\u4E00-\\u9FA5]{4})</td>\\s*<td>([0-9\\.]{3})</td>\\s*<td>([\\u4E00-\\u9FA5\\?]{2,3})</td>\\s*<td>([\\u4E00-\\u9FA5]{3,8})</td>\\s*<td>([\\u4E00-\\u9FA5]{2,3})</td>\\s*<td>([0-9]{4})</td>\\s*<td>([\\u4E00-\\u9FA5]{1})</td>\\s*<td>([0-9\\.]{4})</td>";
    private String regex_course="var lessonName = \"([0-9\\u4E00-\\u9FA5\\u002c\\uff08\\uff09\\u003b\\-]+)\";//[\\u4E00-\\u9FA5]{3}\\s*var day = \"([0-9]{1})\";//\\s*var.{49}\\s*var beginWeek = \"([0-9]{1,2})\";//[\\u4E00-\\u9FA5\\uff0c]{11}\\s*var endWeek = \"([0-9]{1,2})\";//[\\u4E00-\\u9FA5\\uff0c]{11}\\s*var classNote = \"([ 0-9\\-A-Z\\u4E00-\\u9FA5]{0,10})\";\\s*var beginTime = \"([0-9]{1,2})\";//[\\u4E00-\\u9FA5\\uff0c]{12}\\s*var endTime = \"([0-9]{1,2})\";//[\\u4E00-\\u9FA5\\uff0c]{12}\\s*var detail=\"([0-9\\u4E00-\\u9FA5\\u002c\\u003b\\-]+)\";//[\\u4E00-\\u9FA5]{7}\\s*var classRoom = \"([0-9\\-\\u4E00-\\u9FA5]{1,8})?\";//[\\u4E00-\\u9FA5]{2}\\s*var weekInterVal= \"([0-9]{1})\";//[\\u4E00-\\u9FA5]{5}\\s*var teacherName = \"([\u003f\\u4E00-\\u9FA5]{2,3})\";//[\\u4E00-\\u9FA5]{4}\\s*var professionName = \"([\\u4E00-\\u9FA5]{2,3})\";//[\\u4E00-\\u9FA5]{4}\\s*var planType = \"([\u003f\\u4E00-\\u9FA5]{4})\";\\s*var credit = \"([0-9\\.]{3})\";//[\\u4E00-\\u9FA5]{4}\\s*var areaName = \"([0-9\\u4E00-\\u9FA5]{2,3})?\";\\s*var.{20}\\s*var academicTeach = \"([\\u4E00-\\u9FA5]{3,10})\";\\s*var grade = \"([0-9]{4})?\";\\s*var classNote = \"[ 0-9\\-A-Z\\u4E00-\\u9FA5]{0,10}?\";\\s*var note = \"([ 0-9\\-A-Z\\u4E00-\\u9FA5]{1,20})?\";\\s*var state = \"([ 0-9]{1})\";";
    private String url_webcode;
    private String[] url_score={"http://210.42.121.241/servlet/Svlt_QueryStuScore?csrftoken=","&year=0&term=&learnType=&scoreFlag=0&t=","(%D6%D0%B9%FA%B1%EA%D7%BC%CA%B1%BC%E4)"};
    private String timestamp="";
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
        //new Thread(checkcodeGet).start();
        flag_checkcode=false;

        //btn_changecode.setText("");
        //获取Shared Preference对象
        SharedPreferences setinfo=getPreferences(Activity.MODE_PRIVATE);
        //取出保存的用户名和密码分别赋给字符串String username,password
        String username=setinfo.getString("STUDYNUM","");
        String password=setinfo.getString("PASSWORD","");
        COOKIE=setinfo.getString("COOKIE","");
        token=setinfo.getString("TOKEN","");
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
                if(netConnect()){
                    et_check.setText("");
                    if(flag_checkcode){
                        btn_changecode.setText("看不清");
                    }
                    new Thread(checkcodeGet).start();
                    flag_checkcode=true;
                }else{
                    Toast.makeText(NetLogin.this,"请连接网络",Toast.LENGTH_SHORT).show();
                }
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
                if(netConnect()){
                    flag_connect_login=0;
                    new Thread(networkPost).start();
                }else{
                    Toast.makeText(NetLogin.this,"请确保网络通畅",Toast.LENGTH_SHORT).show();
                }
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
                        break;
                    case 1:
                        if(netConnect()){
                            flag_connect_course=0;
                            flag_search=1;
                            tv_result.setText("正在加载中···\n若15S内无反应，请检查网络后重试");
                            new Thread(everyGet_study_course).start();
                        }else{
                            Toast.makeText(NetLogin.this,"请确保网络通畅",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if(netConnect()){
                            flag_connect_info=0;
                            flag_search=2;
                            tv_result.setText("正在加载中···\n若15S内无反应，请检查网络后重试");
                            new Thread(everyGet_study_info).start();
                        }else{
                            Toast.makeText(NetLogin.this,"请确保网络通畅",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        if(netConnect()){
                            flag_connect_score=0;
                            flag_search=3;
                            Calendar cd = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("EEE%20MMM%20dd%20yyyy%20HH:mm:ss%20'GMT'+0800%20", Locale.US);
                            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00")); // 设置时区为GMT
                            timestamp= sdf.format(cd.getTime());
                            tv_result.setText("正在加载中···\n若15S内无反应，请检查网络后重试");
                            new Thread(everyGet_study_score).start();
                        }else{
                            Toast.makeText(NetLogin.this,"请确保网络通畅",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        break;
                }
            }
            public void onNothingSelected(AdapterView<?>arg0){
            }
        });
    }

    //更新UI一般要放在handle里面
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");

            //正则表达式匹配出token
            Pattern patternName = Pattern.compile(regex_token);
            Matcher mName = patternName.matcher(val);
            if(mName.find()){
                token=mName.group();
                token=token.replaceAll("&csrftoken=","");
            }
            //以下代码用于判断在教务系统网站要进行的事情
            switch (data.getInt("flag")){
                case 0:
                    tv_result.setText(val.replaceAll("\\n\\n\\n", ""));
                    img_checkcode.setImageBitmap(bm_checkCode);
                    break;
                case 1:
                    flag_connect_login++;
                    if(val.equals("")){
                        if(flag_connect_login<=3){
                            new Thread(networkPost).start();
                            System.out.println("登录"+flag_connect_login+"次");
                        }
                    }
                    tv_result.setText(val);
                    break;
                case 2:
                    new AlertDialog.Builder(NetLogin.this).setTitle("源代码")
                            .setMessage(val)
                            .setPositiveButton("确定",null).show();
                    break;
                case 3:
                    switch (flag_search){
                        case 1:
                            flag_connect_course++;
                            if(val.equals("")){
                                if(flag_connect_course<=3){
                                    new Thread(everyGet_study_course).start();
                                    System.out.println("huoqu"+flag_connect_login+"次");
                                }
                            }
                            Pattern patterna = Pattern.compile(regex_course);
                            Matcher namea = patterna.matcher(val);
                            val="";
                            int ia=0;
                            while (namea.find()) {
                                ia++;
                                val=val+"\n课程名:"+namea.group(1)+"\n星期"+namea.group(2)+"\n上课时间，从第几周开始："+namea.group(3)+"\n结课时间，第几周结束："+namea.group(4)+"\n班级备注："+namea.group(5)+"\n上课时间，从第几节课开始："+namea.group(6)+"\n下课时间，第几节下课："+namea.group(7)+"\n详细信息："+namea.group(8)+"\n教室："+namea.group(9)+"\n隔几周一次："+namea.group(10)+"\n任课老师："+namea.group(11)+"\n教师职称："+namea.group(12)+"\n课程类型："+namea.group(13)+"\n课程学分："+namea.group(14)+"\n学部："+namea.group(15)+"\n授课学院："+namea.group(16)+"\n年级备注："+namea.group(17)+"\n课程备注："+namea.group(18)+"\n课程状态："+namea.group(19)+"\n\n\n";
                            }
                            tv_result.setText("大二下共"+ia+"门课程\n\n"+val);
                            break;
                        case 2:
                            flag_connect_info++;
                            if(val.equals("")){
                                if(flag_connect_info<=3){
                                    new Thread(everyGet_study_info).start();
                                    System.out.println("huoqu"+flag_connect_login+"次");
                                }
                            }
                            Pattern patternb = Pattern.compile(regex_info);
                            Matcher nameb = patternb.matcher(val);
                            val="";
                            int ib=0;
                            while (nameb.find()) {
                                ib++;
                                val=val+"\n"+nameb.group(1);
                                System.out.println(val);
                            }
                            val=val.replaceAll(" ","");
                            val=val.replaceAll("width=\"([0-9]{2}%)\"","");
                            val=val.replaceAll("colspan=\"([0-9]{1})\"","");
                            val=val.replaceAll("</th><td>",":");
                            val=val.replaceAll("</td><th>","\n");
                            tv_result.setText("共"+ib+"条个人信息\n\n"+val);
                            break;
                        case 3:
                            flag_connect_score++;
                            if(val.equals("")){
                                if(flag_connect_score<=3){
                                    new Thread(everyGet_study_score).start();
                                    System.out.println("huoqu"+flag_connect_login+"次");
                                }
                            }
                            Pattern pattern = Pattern.compile(regex_score);
                            Matcher name = pattern.matcher(val);
                            val="";
                            int i=0;
                            while (name.find()) {
                                i++;
                                val=val+"课程代号："+name.group(1)+"\n课程名："+name.group(2)+"\n课程类型："+name.group(3)+"\n课程学分："+name.group(4)+"\n授课教师："+name.group(5)+"\n授课学院："+name.group(6)+"\n类型："+name.group(7)+"\n年级："+name.group(8)+"\n学期："+name.group(9)+"\n得分："+name.group(10)+"\n\n";
                            }
                            tv_result.setText("共"+i+"门课程\n\n"+val);
                            break;
                        default:
                            break;
                    }
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
        public void run() {everythingGet_study(url_score[0]+token+url_score[1]+timestamp+url_score[2]);
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
        data.putInt("flag", 3);
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

    public boolean netConnect(){
        ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net=cm.getActiveNetworkInfo();
        if(net!=null&&net.isAvailable()){
            return true;
        }
        return false;
    }

    @Override
    protected void onStop() {
        //获取Shared Preference对象
        SharedPreferences setinfo=getPreferences(Activity.MODE_PRIVATE);
        //保存用户名和密码
        setinfo.edit()
                .putString("STUDYNUM",et_user.getText().toString())
                .putString("PASSWORD",et_pwd.getText().toString())
                .putString("COOKIE",COOKIE)
                .putString("TOKEN",token)
                .commit();
        super.onStop();
    }
}

