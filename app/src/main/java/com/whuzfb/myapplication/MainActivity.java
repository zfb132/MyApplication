package com.whuzfb.myapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static android.telephony.TelephonyManager.CALL_STATE_IDLE;
import static android.telephony.TelephonyManager.CALL_STATE_OFFHOOK;
import static android.telephony.TelephonyManager.CALL_STATE_RINGING;
import static android.telephony.TelephonyManager.DATA_ACTIVITY_DORMANT;
import static android.telephony.TelephonyManager.DATA_ACTIVITY_IN;
import static android.telephony.TelephonyManager.DATA_ACTIVITY_INOUT;
import static android.telephony.TelephonyManager.DATA_ACTIVITY_NONE;
import static android.telephony.TelephonyManager.DATA_ACTIVITY_OUT;
import static android.telephony.TelephonyManager.DATA_CONNECTED;
import static android.telephony.TelephonyManager.DATA_CONNECTING;
import static android.telephony.TelephonyManager.DATA_DISCONNECTED;
import static android.telephony.TelephonyManager.DATA_SUSPENDED;
import static android.telephony.TelephonyManager.NETWORK_TYPE_CDMA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EDGE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_0;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_A;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_B;
import static android.telephony.TelephonyManager.NETWORK_TYPE_GPRS;
import static android.telephony.TelephonyManager.NETWORK_TYPE_GSM;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSDPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSUPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_IWLAN;
import static android.telephony.TelephonyManager.NETWORK_TYPE_LTE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_TD_SCDMA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_UMTS;
import static android.telephony.TelephonyManager.PHONE_TYPE_CDMA;
import static android.telephony.TelephonyManager.PHONE_TYPE_GSM;
import static android.telephony.TelephonyManager.PHONE_TYPE_NONE;
import static android.telephony.TelephonyManager.PHONE_TYPE_SIP;
import static android.telephony.TelephonyManager.SIM_STATE_ABSENT;
import static android.telephony.TelephonyManager.SIM_STATE_NETWORK_LOCKED;
import static android.telephony.TelephonyManager.SIM_STATE_PIN_REQUIRED;
import static android.telephony.TelephonyManager.SIM_STATE_PUK_REQUIRED;
import static android.telephony.TelephonyManager.SIM_STATE_READY;
import static android.telephony.TelephonyManager.SIM_STATE_UNKNOWN;

//因为类TextView属于这个库
//By me:设置私有变量textview
//ctrl+shift+o快速导入所缺包
//linear layout:线性排列listview；网格排列gridview
//relative layout:一个控件以另一些控件作为参考
//线性排列：在xml布局文件写的控件顺序就是实际生成的控件的摆放位置，当android:orientation="vertical"时，第一个控件始终与第二个垂直对齐并在他的上面;如果水平对齐时，第一个控件占据第一列（如果第一个控件太宽（比如match_parent），第二个就被挤到屏幕外边去了。
//px：像素，一个就是会显示红绿蓝三种元素的点。
//dpi：每英寸上面的点的数目，描述像素排列的密集程度dpi=(width*width+height*height)^(1/2)/size，其中size为英寸px=dp*(dpi/160)，如果按照px布局的话，手机800X678与320x267的手机显示会不同
//sp:通常用于显示字体大小
//控件外边距margin(四个方向)   marginTop（上边距） ....
//内边距控件边缘与控件内容的距离padding

public class MainActivity extends AppCompatActivity {
    //为了实现两个Activity公用一个count
    public static MainActivity stata;

    private TextView textview;
    private TextView textview2;
    private TextView textview3;
    private Button button;
    private Button button2;
    private CheckBox box2;
    private CheckBox box3;
    private CheckBox box4;
    private ToggleButton tb;
    private RadioGroup rg1;
    private RadioButton rb1;
    private RadioButton rb2;
    private EditText user;
    private EditText pwd;
    private ProgressBar pb;
    int count=0;
    int dead=0;
    String m="已被选中的按钮：";
    String[] array={"",""};
    String n;

    //为了能在second中修改
    public TimePicker tp;
    public boolean flag=true;
    public String username,password;
    public boolean firstRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过这个方法接收的参数是一个布局文件的id，功能是在MainActivity这个activity中显示R.layout.activity_main中定义的内容
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    //用xml文件声明了一个控件，在java代码中定义一个对象代表这个控件，可以通过修改对象的属性或调用对象的方法来控制空间。一个textview类型的对象就在布局文件中对应一个文本控件。activity中的所有控件都是view的继承

        user=(EditText)findViewById(R.id.editText);
        pwd=(EditText)findViewById(R.id.editText4);

        //获取Shared Preference对象
        SharedPreferences setinfo=getPreferences(Activity.MODE_PRIVATE);
        //取出保存的用户名和密码分别赋给字符串String username,password
        username=setinfo.getString("USER","");
        password=setinfo.getString("PWD","");
        //将取出的信息写在对应的edittext
        //其中user=(EditText)findViewById(R.id.editText);
        //pwd同理
        user.setText(username);
        pwd.setText(password);

        firstRun=setinfo.getBoolean("FIRST_RUN",true);
        if(firstRun){
            showToast("欢迎使用我的应用" ,Toast.LENGTH_SHORT);
            setinfo.edit().putBoolean("FIRST_RUN",false).commit();
        }
        stata=this;

        rg1=(RadioGroup)findViewById(R.id.radiogroup1);
        rb1=(RadioButton)findViewById(R.id.radioButton1);
        rb2=(RadioButton)findViewById(R.id.radioButton2);

        mycheckedchangedlisten mm=new mycheckedchangedlisten();
        rg1.setOnCheckedChangeListener(mm);

        //ima=(ImageView)findViewById(R.id.imageviewa);

        //By me:这里将find的返回值view对象向下转型为textview对象

        textview= (TextView)findViewById(R.id.textView);
        textview.setText("hello modifyed");
        textview.setBackgroundColor(Color.GREEN);
        //为该TextView注册上下文菜单
        registerForContextMenu(textview);
        //监听器是一种对象，用于监控其他控件的动作，并做出响应，一个控件可以绑定多个监听器
        //首先获取代表控件的对象；定义一个类实现监听器接口；生成监听器对象；为控件绑定监听器对象
        button=(Button)findViewById(R.id.button);
        myButtonListener mb=new myButtonListener();
        button.setOnClickListener(mb);

        button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(mb);


        //这是多选框的
        box2=(CheckBox)findViewById(R.id.checkBox2);
        box3=(CheckBox)findViewById(R.id.checkBox3);
        box4=(CheckBox)findViewById(R.id.checkBox);
        box4.setOnClickListener(mb);//此处调用button的点击响应函数
        mycheckbListener mc=new mycheckbListener();
        box2.setOnClickListener(mc);
        box3.setOnClickListener(mc);

        tb=(ToggleButton)findViewById(R.id.toggleButton) ;
        mytoggleButtonListener mt=new mytoggleButtonListener();
        tb.setOnClickListener(mt);

        tp=(TimePicker)findViewById(R.id.timePicker2) ;
        tp.setCurrentHour(6);tp.setCurrentMinute(06);
        mytimechangedlistener mtc=new mytimechangedlistener();
        tp.setOnTimeChangedListener(mtc);

        pb=(ProgressBar)findViewById(R.id.progressBar2);
        pb.setProgress(0);
        //判断进度条是否是转圈的（不确定的），告诉用户手机正在进行计算，但不知道什么时候结束
        pb.isIndeterminate();
        //在当前进度的基础上增加10
        pb.incrementProgressBy(10);
        //在当前进度的基础上增加10
        pb.incrementSecondaryProgressBy(10);

        //文本更新
        textview2= (TextView) findViewById(R.id.textView2);
        textview3= (TextView)findViewById(R.id.textView3);

        //多选框的状态更改
        //checkedlisten cl=new checkedlisten();
        //box3.setOnCheckedChangeListener(cl);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "没有处理函数", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    class mytimechangedlistener implements TimePicker.OnTimeChangedListener{

        @Override
        //view成员变量代表是触发函数的控件；
        //hourofday表示用户选择的小时；同理下一个。
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            textview.setText(hourOfDay+"时"+minute+"分");
            if(hourOfDay==11&&minute==11){
                //生成意图对象
                Intent intenta=new Intent();
                //setClass()的第一个参数是packagecontext，第二个参数是一个class对象（在当前场景下应该传入需要被启动的Activity的对象）
                //packagecontext对象就是当前Activity的对象（context是个类，Activity是它的子类）
                //MainActivity.this表示使用的是外部类的对象，而不是这个类的对象
                intenta.setClass(MainActivity.this,LinearTestAActivity.class);
                intenta.putExtra("com.whuzfb.myapplication.count",count);
                intenta.putExtra("com.whuzfb.myapplication.time",new int[]{tp.getCurrentHour(),tp.getCurrentMinute()});
                startActivity(intenta);
            }else if(hourOfDay==9&&minute==9){
                Intent mIntent = new Intent();
                mIntent.setClass(MainActivity.this,FloatViewService.class);
                startService(mIntent);
            }
        }
    }

    class mycheckedchangedlisten implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group,int CheckedID){
            if(CheckedID==rb1.getId()){
                textview.setText("选中单选按钮1");
                Intent intent=new Intent();
                intent.setComponent(new ComponentName("com.android.settings","com.android.settings.DeviceAdminSettings"));
                startActivity(intent);
            }
            if(CheckedID==rb2.getId())
                textview.setText("选中单选按钮2");
        }
    }

    public void showToast(String str,int duration) {
        //Toast.makeText(this, str, duration).show();
        Toast toast = Toast.makeText(this, str, duration);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }

    //By me:监听事件的处理函数
    class mytoggleButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            //设置对话框标题
            new AlertDialog.Builder(MainActivity.this).setTitle("root:警告")
                    //设置显示的内容
                    .setMessage("不要搞事情！")
                    //添加确定按钮
                    .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                        @Override
                        //确定按钮的响应事件
                        public void onClick(DialogInterface dialog, int which){
                            // TODO Auto-generated method stub
                            finish();
                        }
                    })
                    .setNegativeButton("取消",null).show();
                    //在按键响应事件中显示此对话框
        }
    }

    class myButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            if (v.getId()==R.id.checkBox) {
                dead++;
                if (dead>=10)
                    finish();
                textview.setText("请不要点击此button");
                box2.setChecked(true);
                showToast("警告 " ,Toast.LENGTH_SHORT);
            }
            else if(v.getId()==R.id.button2){
                if(user.getText().toString().equals("admin")&&(pwd.getText().toString().equals("123456"))) {
                    showToast("恭喜你登陆成功\n不过并没有卵用", Toast.LENGTH_LONG);
                    try{
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//参数是包名，类全限定名，注意直接用类名不行
                        startActivityForResult(intent,1);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
                else
                    showToast("账号密码错误\n请查看菜单的新手引导",Toast.LENGTH_LONG);
            }
            else{
                count++;
                textview.setText("你点击了左侧按钮" + count + "次");
                pb.setProgress(count);
                if(count==100) {
                    //生成意图对象
                    Intent intenta=new Intent();
                    //setClass()的第一个参数是packagecontext，第二个参数是一个class对象（在当前场景下应该传入需要被启动的Activity的对象）
                    //packagecontext对象就是当前Activity的对象（context是个类，Activity是它的子类）
                    //MainActivity.this表示使用的是外部类的对象，而不是这个类的对象
                    intenta.setClass(MainActivity.this,LinearTestAActivity.class);
                    intenta.putExtra("com.whuzfb.myapplication.count",count);
                    intenta.putExtra("com.whuzfb.myapplication.time",new int[]{tp.getCurrentHour(),tp.getCurrentMinute()});
                    startActivity(intenta);
                }
            }
        }
    }

    class mycheckbListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            CheckBox b=(CheckBox)v;
            //view也有getID()方法
            n="";
            if(b.getId()==R.id.checkBox2) {
                if(b.isChecked())
                    array[0]="A";
                else
                    array[0]="";
                textview2.setText("你点击了复选框A");
            }
            if(b.getId()==R.id.checkBox3) {
                if (b.isChecked()){
                    array[1] = " B";
                    try{
                    /*
                    Intent intent = new Intent();
//参数是包名，类全限定名，注意直接用类名不行
                    ComponentName cn = new ComponentName("zq.whu.zswd.ui",
                            "zq.whu.zswd.ui.MainActivity");
                    intent.setComponent(cn);
                    startActivity(intent);
                    */
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        //intent.setComponent(new ComponentName("com.android.calendar","com.android.calendar.LaunchActivity"));
                        startActivity(intent);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                    array[1] = "";
                textview2.setText("你点击了复选框B");
            }
            for(int i=0;i<2;i++)
                n=n+array[i];
            textview3.setText(m+n);
        }
    }

    class mycheckedlisten implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonview,boolean isChecked){
            if(buttonview.getId()==R.id.checkBox2)
                textview.setText("这是checkchange");
            if(isChecked)
                textview.setText("on");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.add(0,1,Menu.NONE,"fgg");
        //这四个参数依次是：
        //groupID：此菜单项所属的ID，一般不设置，即为Menu.None(也就是0)
        //itemID：唯一的菜单项ID
        //order：此菜单项在菜单中的顺序，设置为Menu.None表示不关注顺序
        //title：此菜单项的标题
        //设置创建菜单，也可以在其中加上以下代码实现添加菜单项
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        //以下代码实现：为选项菜单添加子菜单（SubMenu）
        SubMenu subMenu=menu.addSubMenu("子菜单");
        subMenu.setIcon(R.drawable.sample_0);
        subMenu.add(0,1,0,"子菜单A");
        subMenu.add(0,2,0,"子菜单B");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //此处建议使用switch-case语句来实现对多个菜单项的监控
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings:
                //showToast("点击了设置，没有响应函数",Toast.LENGTH_SHORT);
                new AlertDialog.Builder(MainActivity.this).setTitle("手机信息")
                        //设置显示的内容
                        .setMessage(getPhoneInfo())
                        //添加确定按钮
                        .setPositiveButton("好的",null).show();
                //在按键响应事件中显示此对话框
                break;
            case R.id.action_about:
                //showToast("点击了关于",Toast.LENGTH_SHORT);
                new AlertDialog.Builder(MainActivity.this).setTitle("温馨提示")
                        //设置显示的内容
                        .setMessage("这是我自己测试的，不会对你的手机产生任何不良影响。如果有任何担忧，请立即卸载本软件！\n拨打电话及读取联系人、发送读取短信、使用相机等敏感权限都会在你同意的情况下使用。如有问题和疑惑，欢迎联系Email: zfb1529017@163.com")
                        //添加确定按钮
                        .setNegativeButton("卸载",new DialogInterface.OnClickListener(){
                            @Override
                            //确定按钮的响应事件
                            public void onClick(DialogInterface dialog, int which){
                                // TODO Auto-generated method stub
                                unInstall(MainActivity.this.getPackageName());
                                //finish();
                            }
                        })
                        .setPositiveButton("我知道了",null).show();
                //在按键响应事件中显示此对话框
                break;
            case R.id.action_x1:
                //showToast("点击了待定1",Toast.LENGTH_SHORT);
                Intent intent_qq=new Intent(MainActivity.this,TabViewActivity.class);
                startActivity(intent_qq);
                break;
            case R.id.action_x2:
                //showToast("点击了待定2",Toast.LENGTH_SHORT);
                if(isInstalled(MainActivity.this,"com.tencent.mobileqq")){
                    String url_qq="mqqwpa://im/chat?chat_type=wpa&uin=1529017133";
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url_qq)));
                }else{
                    showToast("请安装QQ",Toast.LENGTH_SHORT);
                }
                break;
            case R.id.action_x3:
                //showToast("点击了待定3",Toast.LENGTH_SHORT);
                /*打开微信方式之一
                String url_weixin="weixin://";
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url_weixin)));
                */
                //打开微信方式之二
                if(isInstalled(MainActivity.this,"com.tencent.mm")){
                    Intent intent_weixin=getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                    startActivity(intent_weixin);
                    textToClipBoard("zfb132");
                }else{
                    showToast("请安装微信",Toast.LENGTH_SHORT);
                }
                break;
            case R.id.action_x4:
                showToast("点击了待定4",Toast.LENGTH_SHORT);
                break;
            case R.id.action_x5:
                showToast("点击了待定5",Toast.LENGTH_SHORT);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0,R.id.action_x4,0,"一键99");
        menu.add(0,R.id.action_x5,0,"一键归1");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_x4:
                count=1;
                showToast("点击了上下文菜单A，强制设置为1",Toast.LENGTH_SHORT);
                textview.setText("你点击了左侧按钮" + count + "次");
                pb.setProgress(count);
                break;
            case R.id.action_x5:
                count=99;
                showToast("点击了上下文菜单B，强制设置为99",Toast.LENGTH_SHORT);
                textview.setText("你点击了左侧按钮" + count + "次");
                pb.setProgress(count);
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void textToClipBoard(String str){
        ClipboardManager cm=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        //setText()然后getText()感觉很方便，不过被弃用了
        //cm.setText("");
        ClipData cp;
        cp=ClipData.newPlainText("text",str);
        cm.setPrimaryClip(cp);
        showToast("作者的微信号已被复制到剪切板",Toast.LENGTH_SHORT);
    }

    public boolean isInstalled(Context context,String name){
        /*
        //两种思路其一：直接获得所有已安装应用，然后循环比对；
        PackageManager pm=context.getPackageManager();
        //参数0表示不接受任何flag，只返回一些简单的包的信息
        List<PackageInfo> packageInfoList=pm.getInstalledPackages(0);
        for(int i=0;i<packageInfoList.size();i++){
            if(packageInfoList.get(i).packageName.equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
        */
        // 两种思路其二：直接尝试获得该name的应用信息，如果失败说明未安装
        PackageInfo packageInfo;
        try {
            packageInfo=context.getPackageManager().getPackageInfo(name,0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo=null;
            e.printStackTrace();
        }
        if(packageInfo==null){
            return false;
        }else{
            return true;
        }
    }

    public void unInstall(String packageName){
        Uri uri=Uri.parse("package:"+packageName);
        Intent intent=new Intent(Intent.ACTION_DELETE,uri);
        startActivity(intent);
    }

    public String wifiConnect(){
        ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net=cm.getActiveNetworkInfo();
        if(net!=null&&net.getType()==ConnectivityManager.TYPE_WIFI){
            return "已连接";
        }
        return "未连接";
    }

    public String netConnect(){
        ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net=cm.getActiveNetworkInfo();
        if(net!=null&&net.isAvailable()){
            return "可以上网";
        }
        return "无法上网";
    }

    public StringBuilder getPhoneInfo(){
        StringBuilder info=new StringBuilder();
        info.append("产品："+Build.PRODUCT+ System.getProperty("line.separator"));
        info.append("CPU："+ Build.CPU_ABI+System.getProperty("line.separator"));
        info.append("标签："+ Build.TAGS+System.getProperty("line.separator"));
        info.append("版本："+Build.VERSION_CODES.BASE+System.getProperty("line.separator"));
        info.append("型号："+Build.MODEL+System.getProperty("line.separator"));
        info.append("SDK版本："+Build.VERSION.SDK+System.getProperty("line.separator"));
        info.append("Android版本："+Build.VERSION.RELEASE+System.getProperty("line.separator"));
        info.append("设备："+Build.DEVICE+System.getProperty("line.separator"));
        info.append("显示："+Build.DISPLAY+System.getProperty("line.separator"));
        info.append("公司："+Build.BRAND+System.getProperty("line.separator"));
        info.append("商标："+Build.BOARD+System.getProperty("line.separator"));
        info.append("指纹："+Build.FINGERPRINT+System.getProperty("line.separator"));
        info.append("ID："+Build.ID+System.getProperty("line.separator"));
        info.append("制造商："+Build.MANUFACTURER+System.getProperty("line.separator"));
        info.append("用户："+Build.USER+System.getProperty("line.separator"));

        TelephonyManager tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        info.append("设备IMEI："+tm.getDeviceId()+System.getProperty("line.separator"));
        info.append("设备软件版本："+tm.getDeviceSoftwareVersion()+System.getProperty("line.separator"));
        info.append("手机号："+tm.getLine1Number()+System.getProperty("line.separator"));
        info.append("国际长途区号（MCC）："+tm.getNetworkCountryIso()+System.getProperty("line.separator"));
        info.append("网络运营码（MCC+MNC）："+tm.getNetworkOperator()+System.getProperty("line.separator"));
        info.append("网络运营商（SPN）："+tm.getNetworkOperatorName()+System.getProperty("line.separator"));
        info.append("当前使用的网络类型："+judge_net_type(tm.getNetworkType())+System.getProperty("line.separator"));
        info.append("手机制式类型："+judge_phone_type(tm.getPhoneType())+System.getProperty("line.separator"));
        info.append("SIM卡的国家码："+tm.getSimCountryIso()+System.getProperty("line.separator"));
        info.append("SIM卡提供的移动国家码和移动网络码："+tm.getSimOperator()+System.getProperty("line.separator"));
        info.append("服务商名："+tm.getSimOperatorName()+System.getProperty("line.separator"));
        info.append("SIM卡的序列号："+tm.getSimSerialNumber()+System.getProperty("line.separator"));
        info.append("SIM卡的状态信息："+judge_sim_state(tm.getSimState())+System.getProperty("line.separator"));
        info.append("国际移动用户识别码（IMSI）："+tm.getSubscriberId()+System.getProperty("line.separator"));
        info.append("语音邮件相关的标签（识别码）："+tm.getVoiceMailAlphaTag()+System.getProperty("line.separator"));
        info.append("电话状态："+judge_call_state(tm.getCallState())+System.getProperty("line.separator"));
        info.append("ICC卡是否存在："+tm.hasIccCard()+System.getProperty("line.separator"));
        info.append("是否漫游："+tm.isNetworkRoaming()+System.getProperty("line.separator"));
        info.append("数据活动状态："+judge_net_activity(tm.getDataActivity())+System.getProperty("line.separator"));
        info.append("数据连接状态："+judge_net_state(tm.getDataState())+System.getProperty("line.separator"));
        info.append("WIFI状态："+wifiConnect()+System.getProperty("line.separator"));
        info.append("网络连通状态："+netConnect()+System.getProperty("line.separator"));

        //这种方式在service中无法使用，
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;              //宽
        int height = dm.heightPixels;           //高
        /*在service中也能得到高和宽
        WindowManager mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        width = mWindowManager.getDefaultDisplay().getWidth();
        height = mWindowManager.getDefaultDisplay().getHeight();
        */
        info.append("手机宽度（像素）："+width+System.getProperty("line.separator"));
        info.append("手机高度（像素）："+height+System.getProperty("line.separator"));
        info.append("手机总内存："+getTotalMemory()[0]+System.getProperty("line.separator"));
        info.append("手机已用内存："+getTotalMemory()[1]+System.getProperty("line.separator"));
        info.append("\n\n\n");
        info.append("相关知识：\nIMSI国际移动用户识别码(International Mobile Subscriber Identify)，共有15位：MCC移动国家码（Mobile Country Code）+MNC移动网络码（Mobile Network Code）+MIN。其中，MCC共3位，中国为460；在中国，移动的代码为00和02，联通为01，电信为03\n\nIMEI国际移动设备标识(International Mobile Equipment Identify)，共有15位：TAC（型号核准代码）+FAC（最后装配号）+SNR（串号）+SP（校验码）。其中，TAC共6位，一般代表机型；FAC共2位，一般代表产地；SNR共6位，一般代表生产顺序号。");
        return info;
    }

    private String[] getTotalMemory() {
        String[] result = {"",""};  //1-total 2-avail
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager mActivityManager=(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        mActivityManager.getMemoryInfo(mi);
        long mTotalMem = 0;
        long mAvailMem = mi.availMem;
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            mTotalMem = Integer.valueOf(arrayOfString[1]).intValue() * 1024;
            localBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        result[0] = Formatter.formatFileSize(this, mTotalMem);
        result[1] = Formatter.formatFileSize(this, mAvailMem);
        //Log.i(TAG, "meminfo total:" + result[0] + " used:" + result[1]);
        return result;
    }

    public String judge_net_state(int num) {
        String str = null;
        switch (num) {
            case DATA_CONNECTED:
                str="已连接";
                break;
            case DATA_CONNECTING:
                str="正在连接";
                break;
            case DATA_DISCONNECTED:
                str="断开";
                break;
            case DATA_SUSPENDED:
                str="暂停";
                break;
            default:
                break;
        }
        return str;
    }

    public String judge_net_activity(int num) {
        String str = null;
        switch (num) {
            case DATA_ACTIVITY_IN:
                str="正在接收数据";
                break;
            case DATA_ACTIVITY_OUT:
                str="正在发送数据";
                break;
            case DATA_ACTIVITY_INOUT:
                str="正在接收和发送数据";
                break;
            case DATA_ACTIVITY_NONE:
                str="无数据交换";
                break;
            case DATA_ACTIVITY_DORMANT:
                str="睡眠模式";
                break;
            default:
                break;
        }
        return str;
    }

    public String judge_call_state(int num) {
        String str = null;
        switch (num) {
            case CALL_STATE_IDLE:
                str="无活动";
                break;
            case CALL_STATE_RINGING:
                str="响铃";
                break;
            case CALL_STATE_OFFHOOK:
                str="通话中";
                break;
            default:
                break;
        }
        return str;
    }

    public String judge_sim_state(int num) {
        String str = null;
        switch (num) {
            case SIM_STATE_ABSENT:
                str="SIM卡未找到";
                break;
            case SIM_STATE_NETWORK_LOCKED:
                str="SIM卡需要网络PIN解锁";
                break;
            case SIM_STATE_PIN_REQUIRED:
                str="SIM卡需要用户PIN解锁";
                break;
            case SIM_STATE_PUK_REQUIRED:
                str="SIM卡需要用户PUK解锁";
                break;
            case SIM_STATE_READY:
                str="SIM卡可用";
                break;
            case SIM_STATE_UNKNOWN:
                str="SIM卡未知";
                break;
            default:
                break;
        }
        return str;
    }

    public String judge_phone_type(int num) {
        String str = null;
        switch (num) {
            case PHONE_TYPE_CDMA:
                str="电信";
                break;
            case PHONE_TYPE_GSM:
                str="移动和联通";
                break;
            case PHONE_TYPE_SIP:
                str="SIP网络电话";
                break;
            case PHONE_TYPE_NONE:
                str="未知";
                break;
            default:
                break;
        }
        return str;
    }

    public String judge_net_type(int num){
        /*
        //IMSI国际移动用户识别码(International Mobile Subscriber Identify)
        //共有15位：MCC移动国家码（Mobile Country Code）+MNC移动网络码（Mobile Network Code）+MIN
        //MCC共3位，中国为460；在中国，移动的代码为00和02，联通为01，电信为03
        */
        /*
        //IMEI国际移动设备标识(International Mobile Equipment Identify)
        //共有15位：TAC（型号核准代码）+FAC（最后装配号）+SNR（串号）+SP（校验码）
        //TAC共6位，一般代表机型；FAC共2位，一般代表产地；SNR共6位，一般代表生产顺序号。
        */
        String str=null;
        switch (num){
            case NETWORK_TYPE_CDMA:
                str="CDMA(电信2G)";
                break;
            case NETWORK_TYPE_EDGE:
                str="EDGE(移动或联通2G)";
                break;
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EVDO_A:
            case NETWORK_TYPE_EVDO_0:
                str="EVDO(电信3G)";
                break;
            case NETWORK_TYPE_GPRS:
                str="GPRS(移动或联通2G)";
                break;
            case NETWORK_TYPE_HSDPA:
            case NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_HSUPA:
                str="HSDPA(联通3G)";
                break;
            case NETWORK_TYPE_UMTS:
                str="UMTS(联通3G)";
                break;
            case NETWORK_TYPE_LTE:
                str="LTE(4G)";
                break;
            case NETWORK_TYPE_TD_SCDMA:
                str="TD_SCDMA(移动3G)";
                break;
            case NETWORK_TYPE_GSM:
                str="GSM(移动或联通2G)";
                break;
            case NETWORK_TYPE_IWLAN:
                str="工业WLAN";
                break;
            default:
                str="未知";
                break;
        }
        return str;
    }

    @Override
    protected void onStop() {
        //获取Shared Preference对象
        SharedPreferences setinfo=getPreferences(Activity.MODE_PRIVATE);
        //保存用户名和密码
        setinfo.edit()
                .putString("USER",user.getText().toString())
                .putString("PWD",pwd.getText().toString())
                .commit();
        super.onStop();
    }



}
