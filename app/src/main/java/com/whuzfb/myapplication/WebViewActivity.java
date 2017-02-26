package com.whuzfb.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by zfb15 on 2017/1/31.
 */

public class WebViewActivity extends Activity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        webView=(WebView)findViewById(R.id.webview);
        //设置WebView属性，能够执行JavaScript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        //设置出现缩放工具
        //webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        //加在需要显示的网页
        webView.loadUrl("http://www.whuzfb.cn/");
        //设置Web视图
        webView.setWebViewClient(new myWebViewClient());

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //当在网页内点击后退时返回网页上一网页
        if(keyCode==KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            webView.goBack();
            return true;
        }
        //当网页已经不能再返回时，返回上一页面
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode,event);
        }
        return false;
    }

    private class myWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void onStop() {
        webView.getSettings().setJavaScriptEnabled(false);
        super.onStop();
    }
}
