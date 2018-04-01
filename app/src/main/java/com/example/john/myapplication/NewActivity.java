package com.example.john.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import contentbar.MainUI;

public class NewActivity extends AppCompatActivity {

    MainUI contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = new MainUI(this);
        setContentView(contentView);
    }
}
