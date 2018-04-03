package com.example.john.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import customview.CustomviewActivity;
import image.BitmapActivity;
import location.LocationActivity;
import recodeline.RecodeLineActivity;
import shortcut.ShortCutActivity;
import view.FCTButtonWidget;
import webview.WebViewActivity;
import webview.WebViewActivity2;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout linearLayout;
    Button button;
    Button btn;
    Button btn1;
    Button bt;
    Button customView;
    Button view;
    Button location;
    Button webView;
    Button compress;
    Button cameraview;
    Button start;
    Button navigatioButton;
    Button shortCutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        findById();
        setOnclickListener();
    }

    public void findById(){
        button = (Button)findViewById(R.id.recyclerView);
        btn = (Button)findViewById(R.id.btnOwn);
        btn1 = (Button)findViewById(R.id.btn_slide);
        bt=(Button)findViewById(R.id.topViewPager);
        customView = (Button)findViewById(R.id.customView);
        view = (Button)findViewById(R.id.draw);
        location = (Button)findViewById(R.id.location);
        webView = (Button)findViewById(R.id.webview);
        compress = (Button)findViewById(R.id.compress);
        cameraview = (Button)findViewById(R.id.cameraview);
        start = (Button)findViewById(R.id.start);
        navigatioButton = (Button)findViewById(R.id.navigation_button);
        shortCutButton = (Button)findViewById(R.id.shortcut_button);
    }

    private void setOnclickListener(){
        button.setOnClickListener(this);
        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);
        bt.setOnClickListener(this);
        customView.setOnClickListener(this);
        view.setOnClickListener(this);
        location.setOnClickListener(this);
        webView.setOnClickListener(this);
        compress.setOnClickListener(this);
        cameraview.setOnClickListener(this);
        start.setOnClickListener(this);
        navigatioButton.setOnClickListener(this);
        shortCutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recyclerView:
                startActivity(new Intent(MainActivity.this,RecyclerActivity.class));
                break;
            case R.id.btnOwn:
                startActivity(new Intent(MainActivity.this,NewActivity.class));
                break;
            case R.id.btn_slide:
                startActivity(new Intent(MainActivity.this,SlipdActivity.class));
                break;
            case R.id.topViewPager:
                startActivity(new Intent(MainActivity.this,ViewPagerScrollActivity.class));
                break;
            case R.id.customView:
                startActivity(new Intent(MainActivity.this, CustomviewActivity.class));
                break;
            case R.id.draw:
                startActivity(new Intent(MainActivity.this, RecodeLineActivity.class));
                break;
            case R.id.location:
                startActivity(new Intent(MainActivity.this, LocationActivity.class));
                break;
            case R.id.webview:
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
                break;
            case R.id.compress:
                startActivity(new Intent(MainActivity.this, BitmapActivity.class));
                break;

            case R.id.cameraview:
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
                break;
            case R.id.start:
                //url为"finereportv9://" 或 "com.fr.finereportv9://"
//                String url = "finereport://";
////                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                Intent intent = new Intent();
//                intent.setAction("com.finereact.main");
//                intent.putExtra("1","1");
//                startActivity(intent);

                startActivity(new Intent(this, WebViewActivity2.class));
                break;

            case R.id.navigation_button:
                startActivity(new Intent(MainActivity.this, NavigationActivity.class));
                break;
            case R.id.shortcut_button:
                startActivity(new Intent(MainActivity.this, ShortCutActivity.class));
                break;
        }
    }
}
