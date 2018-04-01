package com.example.john.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import Adapter.RecycleVAdatper;

public class RecyclerActivity extends AppCompatActivity implements View.OnClickListener{

    Button button;
    RecyclerView recyclerView;
    RecycleVAdatper adatper;
    List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        initView();
    }

    public void initView(){
        button = (Button)findViewById(R.id.button);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        data = new ArrayList<>();
        for(int i='a';i<'z';i++){
            data.add(String.valueOf(i));
        }

        adatper = new RecycleVAdatper(this,data);
        recyclerView.setAdapter(adatper);
        button.setOnClickListener(this);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                adatper.getData().clear();
////                adatper.notifyDataSetChanged();
//                adatper.getData().add("fdsfsd");
//                adatper.notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        data.add("sdfsdfffd");
        adatper.notifyDataSetChanged();
    }
}
