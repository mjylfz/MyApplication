package com.example.john.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import topviewpagerwidget.TopViewPagerWidget;
import topviewpagerwidget.WidgetModel;

public class ViewPagerScrollActivity extends AppCompatActivity {

    TopViewPagerWidget widget;
    CirclePageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_pager_scroll);

        findByIds();
        initView();

    }

    private void findByIds(){
        widget = (TopViewPagerWidget)findViewById(R.id.viewPager);
        indicator = (CirclePageIndicator)findViewById(R.id.indicator);
    }

    private void initView(){
        List<String> data = new ArrayList<>();
        data.add("http://ww2.sinaimg.cn/large/7a8aed7bjw1exng5dd728j20go0m877n.jpg");
        data.add("http://ww4.sinaimg.cn/large/7a8aed7bgw1esfbgw6vc3j20gy0pedic.jpg");
        data.add("http://ww2.sinaimg.cn/large/7a8aed7bjw1exng5dd728j20go0m877n.jpg");
        data.add("http://ww4.sinaimg.cn/large/7a8aed7bgw1esfbgw6vc3j20gy0pedic.jpg");
        data.add("http://ww2.sinaimg.cn/large/7a8aed7bjw1exng5dd728j20go0m877n.jpg");
        data.add("http://ww4.sinaimg.cn/large/7a8aed7bgw1esfbgw6vc3j20gy0pedic.jpg");


        WidgetModel model = new WidgetModel();
        model.setData(data).setAuto(true).setTouchStop(true).setTime(800);
        widget.setWidgetModel(model);

        indicator.setViewPager(widget);
        indicator.setSnap(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        widget.stop();
    }
}
