package com.example.john.myapplication;

import android.database.ContentObserver;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import utils.IFHelper;

public class NavigationActivity extends AppCompatActivity implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener{

    private Button hasNavigation;
    private Button navigationOpen;
    private Button navigationHeight;

    FrameLayout content;
    private boolean mLayoutComplete = false;

    private ContentObserver mNavigationBarObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            int navigationBarIsMin = 0;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                navigationBarIsMin = Settings.System.getInt(getContentResolver(),
                        "navigationbar_is_min", 0);
            } else {
                navigationBarIsMin = Settings.Global.getInt(getContentResolver(),
                        "navigationbar_is_min", 0);
            }
            if (navigationBarIsMin == 1) {
                //导航键隐藏了
//                Toast.makeText(NavigationActivity.this,"虚拟键隐藏了" + IFHelper.getNavigationHeight(NavigationActivity.this) + "",Toast.LENGTH_SHORT).show();
            } else {
                //导航键显示了
//                Toast.makeText(NavigationActivity.this,"虚拟键打开了" + IFHelper.getNavigationHeight(NavigationActivity.this) + "",Toast.LENGTH_SHORT).show();
            }
        }
    };


    private ContentObserver mNavigationStatusObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            int navigationBarIsMin = Settings.System.getInt(getContentResolver(),
                    "navigationbar_is_min", 0);
            if (navigationBarIsMin == 1) {
                //导航键隐藏了
//                Toast.makeText(NavigationActivity.this,"虚拟键隐藏了" + IFHelper.getNavigationHeight(NavigationActivity.this) + "",Toast.LENGTH_SHORT).show();
            } else {
                //导航键显示了
//                Toast.makeText(NavigationActivity.this,"虚拟键打开了" + IFHelper.getNavigationHeight(NavigationActivity.this) + "",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        hasNavigation = (Button)findViewById(R.id.hasNavigation);
        navigationOpen = (Button)findViewById(R.id.navigationOpen);
        navigationHeight = (Button)findViewById(R.id.navigationHeight);
        content = (FrameLayout) findViewById(android.R.id.content);

        hasNavigation.setOnClickListener(this);
        navigationOpen.setOnClickListener(this);
        navigationHeight.setOnClickListener(this);

        content.post(new Runnable() {
            @Override
            public void run() {
                mLayoutComplete = true;
                Toast.makeText(NavigationActivity.this,"布局完成",Toast.LENGTH_SHORT).show();
            }
        });
        content.getViewTreeObserver().addOnGlobalLayoutListener(this);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            getContentResolver().registerContentObserver(Settings.System.getUriFor
                    ("navigationbar_is_min"), true, mNavigationBarObserver);
        } else {
            getContentResolver().registerContentObserver(Settings.Global.getUriFor
                    ("navigationbar_is_min"), true, mNavigationBarObserver);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hasNavigation:
                Toast.makeText(this, IFHelper.getContentHeight(this) + " "+ IFHelper.getContentWidth(this) + "",Toast.LENGTH_LONG).show();
                break;
            case R.id.navigationOpen:
                Toast.makeText(this, IFHelper.isNavigationBarShow(NavigationActivity.this) + "",Toast.LENGTH_LONG).show();
                break;
            case R.id.navigationHeight:
//                IFHelper.getNavigationBarHeight(NavigationActivity.this);
                Toast.makeText(this, IFHelper.getNavigationHeight(NavigationActivity.this) + "",Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onGlobalLayout() {
        if (!mLayoutComplete)
            return;

//        Toast.makeText(NavigationActivity.this,"虚拟键" + IFHelper.getNavigationHeight(NavigationActivity.this) + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }
}
