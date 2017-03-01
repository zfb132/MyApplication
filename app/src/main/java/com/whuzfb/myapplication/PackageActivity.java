package com.whuzfb.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static android.content.pm.PackageManager.GET_PERMISSIONS;
import static android.content.pm.PackageManager.GET_RECEIVERS;
import static android.content.pm.PackageManager.GET_SERVICES;

/**
 * Created by zfb15 on 2017/2/22.
 */

public class PackageActivity extends Activity implements AbsListView.OnScrollListener{
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
    //以列表的形式获得所有用户应用
    public List<PackageInfo> app_user;
    //以列表的形式获得所有系统应用
    public List<PackageInfo> app_system;
    //以列表的形式获得所有用户应用（即app_user与app_system的集合）
    public List<PackageInfo> packagelist;
    public PackageManager pm;

    public ListView listView_packagename;

    // 声明数组链表，其装载的类型是ListItem(封装了一个Drawable和一个String的类)
    public ArrayList<ListItem> mList;
    public MainListViewAdapter adapter;
    public boolean flag_end=false;


    //
    public View loadmore_view;
    public TextView loadmore_text;
    public FloatingActionButton fab;
    //最后的可视项索引
    private int viewLastIndex = 0;
    // 当前窗口可见项总数
    private int viewItemCount;
    //等待
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packageactivity);
        getAllApps(this);

        listView_packagename = (ListView) findViewById(R.id.listview_packagename);
        mList = new ArrayList<PackageActivity.ListItem>();

        //
        loadmore_view = getLayoutInflater().inflate(R.layout.packagelistfooter, null);
        loadmore_text = (TextView)loadmore_view.findViewById(R.id.textView_showmore);
        listView_packagename.addFooterView(loadmore_view);   //设置列表底部视图
        fab = (FloatingActionButton)findViewById(R.id.fab_returntop);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //数据集变化后,通知adapter
                adapter.notifyDataSetChanged();
                //回到顶部
                //listView_packagename.setSelection(0);
                //平滑滑动到指定的适配器位置
                listView_packagename.smoothScrollToPosition(0);
            }
        });


        //初始化创建Item
        initListView(packagelist);

        // 获取MainListAdapter对象
        adapter = new MainListViewAdapter();
        // 将MainListAdapter对象传递给ListView视图
        listView_packagename.setAdapter(adapter);
        //listView_packagename.setDividerHeight(1);

        listView_packagename.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PackageInfo p=packagelist.get(position);
                Intent intent=new Intent();
                intent.setClass(PackageActivity.this,PackageShow.class);
                intent.putExtra("com.whuzfb.myapplication.packageInfo",getSingleAppInfo(p));
                startActivity(intent);
            }
        });

        //设置滑动监听器
        listView_packagename.setOnScrollListener(this);

        //fab.setVisibility(View.INVISIBLE);
    }

    //加载数据
    private void loadData(List<PackageInfo> appAll) {
        int count = adapter.getCount();
        ListItem item;

        if(adapter.getCount()==appAll.size())
            flag_end=true;

        for (int i = count; (i < count + 15)&&(i <appAll.size()); i++) {
            PackageInfo packageInfo = appAll.get(i);
            item = new ListItem();
            item.setImage(packageInfo.applicationInfo.loadIcon(pm));
            if(isSystemApp(packageInfo)){
                item.setTextcolor(Color.parseColor("#9A32CD"));
            }
            item.setTitle("" + packageInfo.applicationInfo.loadLabel(pm));
            mList.add(item);
        }

    }

    public boolean isSystemApp(PackageInfo p){
        boolean f=false;
        for(int i=0;i<app_system.size();i++){
            if(p==app_system.get(i)){
                f=true;
            }
        }
        return f;
    }

    public void initListView(List<PackageInfo> appAll){
        // 初始化data，装载八组数据到数组链表mList中
        ListItem item;
        int i = 0;
        for (; i < 15; i++) {
            PackageInfo packageInfo = appAll.get(i);
            //listall.add("" + packageInfo.applicationInfo.loadLabel(pm));
            item = new ListItem();
            item.setImage(packageInfo.applicationInfo.loadIcon(pm));
            if(isSystemApp(packageInfo)){
                item.setTextcolor(Color.parseColor("#9A32CD"));
            }
            item.setTitle("" + packageInfo.applicationInfo.loadLabel(pm));
            mList.add(item);
        }
    }

    //滑动时被调用
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.viewItemCount = visibleItemCount;
        viewLastIndex = firstVisibleItem + visibleItemCount - 1;

    }

    //滑动状态改变时被调用
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int itemsLastIndex = adapter.getCount() - 1;    //数据集最后一项的索引
        int lastIndex = itemsLastIndex + 1;             //加上底部的loadMoreView项
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && viewLastIndex == lastIndex) {
            loadmore_text.setText("共"+(viewLastIndex+1)+"个应用");
            //自动加载,可以在这里放置异步加载数据
            loadData(packagelist);
            //数据集变化后,通知adapter
            adapter.notifyDataSetChanged();
            //设置选中项，如果不加这个flag则会导致最后一个时footview显示不全
            if(!flag_end){
                if((viewLastIndex+1)==packagelist.size()){
                    Toast.makeText(PackageActivity.this,"sssss",Toast.LENGTH_SHORT);
                }else{

                    loadmore_text.setText("已加载"+packagelist.size()+"个应用");
                }
                listView_packagename.setSelection(viewLastIndex - viewItemCount + 1);
            }
        }
    }

    //获得所有App应用
    public void getAllApps(Context context) {
        app_system = new ArrayList<PackageInfo>();
        app_user = new ArrayList<PackageInfo>();
        pm = context.getPackageManager();
        //获取手机内所有应用
        // 这是曾经安装过但是卸载了的软件|MATCH_UNINSTALLED_PACKAGES
        packagelist = pm.getInstalledPackages(PackageManager.GET_ACTIVITIES|GET_PERMISSIONS | GET_RECEIVERS | GET_SERVICES);

        //获得应用的banner对象Drawable类型
        //pm.getApplicationBanner(packagename);
        //获得应用的设置int
        //pm.getApplicationEnabledSetting(packagename);
        //获得应用图标Drawable
        //pm.getApplicationIcon(packagename);
        //获得应用logo  Drawable
        //pm.getApplicationLogo(packagename);
        //获得应用程序名称CharSequence
        //pm.getApplicationLabel(applicationinfo);
        //获得已安装应用List<ApplicationInfo>
        //pm.getInstalledApplications(flag);
        //获得已安装应用包List<PackageInfo>
        //pm.getInstalledPackages(flag);
        //获得安装该应用的应用的名字String
        //pm.getInstallerPackageName(packagename);
        //获取某个特定的package的信息PackageInfo
        //pm.getPackageInfo(packagename,flag);
        //获取当前拥有某项权限的应用List<PackageInfo>
        //pm.getPackagesHoldingPermissions(strpermission,flag);
        //获取某个权限的所有详细信息PermissionInfo
        //pm.getPermissionInfo(strpermission,flag);
        //获得用户喜欢的应用，越靠前越喜欢List<PackageInfo>
        //pm.getPreferredPackages(flag);
        //获得系统可用的分享库String[]
        //pm.getSystemSharedLibraryNames();
        //某个应用的某项权限是否被政策禁用boolean
        //pm.isPermissionRevokedByPolicy(strpermission,packagename);
        //判断当前设备是否以安全模式启动boolean
        //pm.isSafeMode();


        for (int i = 0; i < packagelist.size(); i++) {
            PackageInfo packageInfo = packagelist.get(i);
            //判断是否为非系统预装的应用程序
            if ((packageInfo.applicationInfo.flags & packageInfo.applicationInfo.FLAG_SYSTEM) <= 0) {
                // customs applications
                app_user.add(packageInfo);
            } else {
                app_system.add(packageInfo);
            }
        }
    }

    public String getSingleAppInfo(PackageInfo packageInfo){
        String all="";
        all="包名："+packageInfo.packageName+"\n应用名称："+packageInfo.applicationInfo.loadLabel(pm)+"\n分享用户ID："+packageInfo.sharedUserId+"\n版本名："+packageInfo.versionName+"\n应用信息："+packageInfo.applicationInfo+"\n首次安装时间："+formatter.format(new Date(packageInfo.firstInstallTime))+"\n最新安装时间："+formatter.format(new Date(packageInfo.lastUpdateTime));
        try{
            all=all+"\n应用活动信息：\n";
            if(packageInfo.activities!=null){
                for(int t=0;t<packageInfo.activities.length;t++){
                    all=all+packageInfo.activities[t].toString()+"\n";
                }
            }else{
                all=all+"未获取到";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            all=all+"\n应用安装时要求的权限：\n";
            if(packageInfo.permissions!=null){
                for(int t=0;t<packageInfo.permissions.length;t++){
                    all=all+packageInfo.permissions[t].toString()+"\n";
                }
            }else{
                all=all+"未获取到\n";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            all=all+"应用接收的广播：\n";
            if(packageInfo.receivers!=null){
                for(int t=0;t<packageInfo.receivers.length;t++){
                    all=all+packageInfo.receivers[t]+"\n";
                }
            }else{
                all=all+"未获取到\n";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            all=all+"应用安装及运行时要求的权限：\n";
            if(packageInfo.requestedPermissions!=null){
                for(int t=0;t<packageInfo.requestedPermissions.length;t++){
                    all=all+packageInfo.requestedPermissions[t]+"\n";
                }
            }else{
                all=all+"未获取到\n";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            all=all+"应用注册的服务：\n";
            if(packageInfo.services!=null){
                for(int t=0;t<packageInfo.services.length;t++){
                    all=all+packageInfo.services[t]+"\n";
                }
            }else{
                all=all+"未获取到\n";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return all;
    }


    //定义ListView适配器MainListViewAdapter
    class MainListViewAdapter extends BaseAdapter {

        //返回item的个数
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mList.size();
        }

        //返回item的内容
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mList.get(position);
        }

        //返回item的id
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        //返回item的视图
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListItemView listItemView;

            // 初始化item view
            if (convertView == null) {
                // 通过LayoutInflater将xml中定义的视图实例化到一个View中
                convertView = LayoutInflater.from(PackageActivity.this).inflate(
                        R.layout.packagelistitem, null);

                // 实例化一个封装类ListItemView，并实例化它的两个域
                listItemView = new ListItemView();
                listItemView.imageView = (ImageView) convertView
                        .findViewById(R.id.imageview_item);
                listItemView.textView = (TextView) convertView
                        .findViewById(R.id.textview_item_title);

                // 将ListItemView对象传递给convertView
                convertView.setTag(listItemView);
            } else {
                // 从converView中获取ListItemView对象
                listItemView = (ListItemView) convertView.getTag();
            }

            // 获取到mList中指定索引位置的资源
            Drawable img = mList.get(position).getImage();
            String title = mList.get(position).getTitle();
            int color=mList.get(position).getTextcolor();

            // 将资源传递给ListItemView的两个域对象
            listItemView.imageView.setImageDrawable(img);
            listItemView.textView.setTextColor(color);
            listItemView.textView.setText(title);

            // 返回convertView对象
            return convertView;
        }
    }

    //封装两个视图组件的类
    class ListItemView {
        ImageView imageView;
        TextView textView;
    }

    //封装了两个资源的类
    //为了区别系统应用而加一个颜色
    class ListItem {
        private Drawable image;
        private String title;
        private int textcolor=Color.parseColor("#2F4F4F");

        public Drawable getImage() {
            return image;
        }

        public void setImage(Drawable image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTextcolor(){
            return textcolor;
        }

        public void setTextcolor(int color){
            this.textcolor=color;
        }
    }
}