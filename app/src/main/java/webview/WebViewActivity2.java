package webview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.john.myapplication.R;

/**
 * Created by LFZ on 2018/3/28.
 */

public class WebViewActivity2 extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 0;

    private WebView webView;
    private ValueCallback<Uri> mUploadMessage;//回调图片选择，4.4以下
    private ValueCallback<Uri[]> mUploadCallbackAboveL;//回调图片选择，5.0以上

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_file);
        initWebView();

    }

    private void initWebView(){
        WebView webView = (WebView) findViewById(R.id.webview);
        //允许JavaScript执行
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setLoadsImagesAutomatically(true);
        //运行webview通过URI获取安卓文件
        String url = "file:///android_asset/data.html";
//        String url = "file:///sdcard/data.html";
        // 禁止 file 协议加载 JavaScript
//        if (url.startsWith("file://")) {
//            webView.getSettings().setJavaScriptEnabled(false);
//        } else {
//            webView.getSettings().setJavaScriptEnabled(true);
//        }

        //false可以防止读取本地的html，或者htm使用本地图片作为背景
        webView.getSettings().setAllowFileAccess(true);

        // 设置是否允许通过 file url 加载的 Js代码读取其他的本地文件,默认是false。
        webView.getSettings().setAllowFileAccessFromFileURLs(false);
        // 设置是否允许通过 file url 加载的 Javascript 可以访问其他的源(包括http、https等源)，默认是false。
        webView.getSettings().setAllowUniversalAccessFromFileURLs(false);



        webView.setWebViewClient(new WebViewClient() {
            // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }





}
