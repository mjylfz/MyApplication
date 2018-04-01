package com.example.john.myapplication;

import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapter.RecycleVAdatper;
import view.NeiNestScrollView;
import view.RecyclerViewScroll;

public class SlipdActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerViewScroll recyclerView;
    NeiNestScrollView neiNestScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slipd);

        initView();
    }

    /**
     * 自定义前天滚动总结：
     * -最上层SwipRefreshLayout
     * -第二层NestScrollview
     * -第三层NestScrollView,RecyclerView,HorizontalScrollView
     *
     * -第一层暂时没有重写
     *
     *  第二层MyNestScrollView（重要），dispatchTouchEvent中Down和Move申请父组件不拦截，
     *  onInterceptTouchEvent中通常使用Down不拦截，否则子控件收不到其他（事实上使用了super.OnInterceptTouchEvent才能使HorizontalScrollView竖向滑动时拦截）,
     *  UP事件如果竖向返回true进行拦截，横向返回false拦截，Up不拦截
     *
     *  第三层NestScrollView，onDown和onMove时request(true)申请父控件不拦截
     *  第三层RecyclerView，onDown和onMove时reques(true)申请父控件不拦截
     *  第三层HorizontalScrollView，onDown申请不拦截，onMove申请拦截，不过在二层和横向滑动将事件分发下来了。
     *
     */
    private void initView(){
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.activity_slipd);
        neiNestScrollView = (NeiNestScrollView)findViewById(R.id.neiNestScrollView);


        recyclerView = (RecyclerViewScroll)findViewById(R.id.recyclerView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               swipeRefreshLayout.setRefreshing(true);
                timer.start();
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        List<String> data = new ArrayList<>();
        for(int i=0;i<100;i++){
            data.add(String.valueOf(i));
        }
        RecycleVAdatper adatper = new RecycleVAdatper(this,data);
        recyclerView.setAdapter(adatper);
    }

    CountDownTimer timer = new CountDownTimer(1000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            swipeRefreshLayout.setRefreshing(false);
        }
    };
}
