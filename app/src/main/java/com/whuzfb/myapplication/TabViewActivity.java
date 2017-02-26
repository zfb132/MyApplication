package com.whuzfb.myapplication;

import android.app.Activity;

import android.os.Bundle;

import android.widget.TabHost;
import android.widget.TextView;

/**
 * Created by zfb15 on 2017/2/2.
 */

public class TabViewActivity extends Activity {

    public TextView textViewA;
    public TextView textViewB;
    public TextView textViewC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabview);

        //首先，获得TabHost对象，并进行初始化
        TabHost tabs=(TabHost)findViewById(R.id.tabhost);
        textViewA=(TextView)findViewById(R.id.textviewA);
        textViewB=(TextView)findViewById(R.id.textviewB1);
        textViewC=(TextView)findViewById(R.id.textviewC);

        textViewA.setText(R.string.app_about_main);
        textViewB.setText(R.string.app_about_test);
        textViewC.setText(R.string.app_about_private);
        tabs.setup();
        //然后，通过TabHost.TabSpec增加tab的一页，通过setContent()增加内容，通过setIndicator()增加页的标签

        //增加第一个Tab
        TabHost.TabSpec spec=tabs.newTabSpec("Tag1");
        //单击Tab要显示的内容
        spec.setContent(R.id.scrollView_A);
        //显示Tab1内容
        spec.setIndicator("启动页");
        tabs.addTab(spec);

        //增加第二个Tab
        spec=tabs.newTabSpec("Tag2");
        //单击Tab要显示的内容
        spec.setContent(R.id.scrollView_B);
        //显示Tab2内容
        spec.setIndicator("测试页");
        tabs.addTab(spec);

        //增加第三个Tab
        spec=tabs.newTabSpec("Tag3");
        //单击Tab要显示的内容
        spec.setContent(R.id.scrollView_C);
        //显示Tab2内容
        spec.setIndicator("敏感页");
        tabs.addTab(spec);

        //最后，通过setCurrentTab(index)指定显示的页，从0开始计算
        tabs.setCurrentTab(0);
        /*
        //此方法将一个Activity的内容作为这个Tab页面的内容
        tabs.addTab(tabs.newTabSpec("ss").setIndicator("list").setContent(new Intent(this,GalleryImageSwitcherActivity.class)));
        */
        /*
        //此方法将一个Activity的内容作为这个Tab页面的内容
        Intent intent=new Intent().setClass(this,GalleryImageSwitcherActivity.class);
        spec=tabs.newTabSpec("spinner");
        spec.setContent(intent);
        spec.setIndicator("spinner");
        tabs.addTab(spec);
        */

        /*
    public String strFirst="这是应用的第一个界面\n  1.本程序首次运行会提示“欢迎使用我的应用”，另外用户每次在编辑框中输入的字符会在下次打开程序时自动填充。\n  2.每次打开程序第一条文字显示“hello modified”，在滑动选择时会显示当前选中的时间（当时间为11:11时会有奇妙的事情发生），也可以显示单选按钮被选中，还可以显示左侧按钮被点击（当次数达到100时也会有奇妙的事情发生），还可以显示中间的多选按钮被点击。另外，长按该文本会弹出上下文菜单。\n第二条文字显示多选框的点击状态。\n第三条文字显示多选框的选中状态。\n  3.右上的开关点击后会弹出对话框，如果选择“确定”直接退出软件。\n右上角是菜单项（按菜单键也可以），其中“待定4”、“待定5”点击后只有提示，“子菜单”点击后进入子菜单，点击后没有任何反应。“手机信息”、“关于软件”、“新手引导”这几个顾名思义不再解释，“QQ联系作者”自动打开与作者的聊天窗口（如果没有安装QQ会有提示），“微信联系作者”会自动打开微信并将作者微信号复制到系统剪切板（如果没有安装微信会有提示）\n  4.中间的多选按钮被选中时会自动选中测试按钮A，提示“警告”，并且当被点击10次时会退出本软件。\n右侧多选按钮B选中会打开辅助功能。\n进度条显示的是左侧按钮被点击的次数。\n左下方的单选按钮A被选中后会打开设备管理器列表\n浮动的红色邮件按钮点击后也是只会有提示。\n  5.用户名和密码输入错误也会有提示，输入正确时（admin   123456）可以打开相机，但不存储拍的照片。";
    public String strSecond="这是程序的主要测试界面\n  1.最上面的文字主要是为了测试英文字母的对齐方式，没有其他功能；下方是一个时间的控件，没有为它设置什么东西，只是单纯觉得它比较好看而已；再下面的两条文字显示的是界面跳转到该页面时已经在主页面点击的按钮的次数和选择的时间。\n  2.多选框“执行程序锁”选中后会以服务的形式每隔5秒钟检测一下显示在前台的应用并把包名显示出来。\n  3.“下拉列表”就是最常见的一类，点击后会进入界面，有许多的列表，不过选择之后也没有任何响应；“开启服务”和“结束服务”是“执行程序锁”的子功能，就是只执行检测一次；“提示”点击之后的界面中上面是一个会自动提示的编辑框（尝试输入hello、我们），下面是两个按钮，当点击“大”时，图片会变大，当点击“小”时，图片会变小（可以一直变小，直到······）\n  4.“图库”点击后会进入一个左右滑动来切换图片的界面；“网格视图”点击后会进入一个类似相册的界面，点击图片之后会显示提示；“网络视图”点击之后会进入我的网站\n\t\thttp://www.whuzfb.cn/\n“标签”点击之后进入新手引导的界面\n  5.“位图”点击之后的界面会有一个滑稽，在拖动进度条之后滑稽会消失，取而代之的是一个随滑动条而变化的图片；“对话框”点击之后会弹出一个具有三个按钮的对话框，点击任意一个都会有提示；“进度对话”点击后会显示一个带进度的对话框，每隔1S增加3%，在此过程中可以随时点击一下屏幕任意位置来使对话框消失；“通知”点击之后会以系统的默认提示音在通知栏显示一个通知，你可以在任意位置通过点击本通知来跳转到本应用程序的主界面（注意要允许本应用发送通知）\n  5.“事件响应”点击之后会进入一个界面，当手指按在屏幕上时，文字为红色，当手指在屏幕上移动时，文字为绿色，当手指从屏幕抬起以后，文字保持蓝色，三个按钮没有任何作用，在这个页面点击返回键会有提示；“内置程序”点击之后进入隐私界面，下一个页面会有详细介绍；“设备管理器”点击之后进入激活设备管理器界面等待用户激活。如果激活设备管理器以后，那么这个按钮将会变为“锁屏”（立即关闭屏幕，不会更改锁屏密码）、而且后面的按钮也会由“测试”（点击无任何反应）变为“5S锁屏”（将自动锁屏时间改为无操作后5秒，也不会更改锁屏密码）";
    public String strThird="这是涉及到用户隐私的一个界面，应用申请的绝大多数权限都将在这个界面被用到。\n  1.打开界面会有提示，因为“呼叫”按钮需要拨打电话的权限，“发送”按钮需要有发送短信的权限，“开始录音”需要有录音的权限，“打开摄像头”需要有访问相机的权限。另外，打开该界面时会自动备份收件箱短信到/sdcard/sms_inbox.txt文件（不备份发件人号码以9或10开头的，也不备份发件人号码的位数大于14的），当然，如果应用没有这些权限，这个文件就只有一句提示了。\n  2.在第一个编辑框输入电话号码，按下“拨号”会跳转到拨号盘等待用户点击呼叫，按下“呼叫”可以直接呼叫该号码；在第二、三个编辑框分别输入电话和信息内容，点击“发送”可以将信息发送到指定收件人\n  3.点击下方按钮可以获取手机当前电量；点击“播放音乐”会自动播放/sdcard/Music/test.mp3，如果想去测试的话，需要手动在该目录下放置该文件，播放过程中可以在第一个进度条调节媒体音量，第二个进度条调节歌曲进度；点击“本地资源”会播放软件内置的音频文件，同样可以调节音量和进度；点击“网络资源”会播放\n  http://music.163.com/m/song?id=19144617\n不过具体能否成功还不能保证；点击“服务”会在后台播放/sdcard/Music/test.mp3（就是当离开该界面以后仍然可以继续播放）。需要注意的是，这四个按钮中，同一个按钮不要多次点击，这样会造成同时播放好几个音频，声音会比较混乱。\n  4.点击“开始录音”的按钮之后会有一个记录时间的（如果应用没有录音的权限会录制一个0.00B的空的音频文件），点击“结束录音”就可以停止本次录音并将音频文件存储在/sdcard/myAudioRecords/目录下。\n  5.在本应用有访问摄像头权限的情况下点击“打开摄像头”会在上分显示预览界面（前置摄像头），点击“拍照”会将照片存储在/sdcard/myImageRecords/目录下，点击“关闭摄像头”预览界面会停留在点击按钮时刻的摄像头的画面。\n  6.只要应用程序没有被系统关掉的情况下，应用会在收到短信时提示短信内容。";
    */
    }
}
