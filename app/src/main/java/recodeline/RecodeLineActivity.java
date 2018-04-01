package recodeline;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.john.myapplication.R;

/**
 * Created by LFZ on 2017/8/13.
 */

public class RecodeLineActivity extends AppCompatActivity implements View.OnClickListener{

    Button pre;
    Button next;

    Button btn_green;
    Button btn_blue;
    Button btn_red;

    Button btn_width3;
    Button btn_width5;
    recodeLine reline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordli);

        pre = (Button)findViewById(R.id.previos);
        next = (Button)findViewById(R.id.next);
        btn_green = (Button)findViewById(R.id.lw);
        btn_blue = (Button)findViewById(R.id.lan);
        btn_red = (Button)findViewById(R.id.hong);
        btn_width3 = (Button)findViewById(R.id.width3);
        btn_width5 = (Button)findViewById(R.id.width5);
        reline = (recodeLine)findViewById(R.id.line);
        pre.setOnClickListener(this);
        next.setOnClickListener(this);
        btn_green.setOnClickListener(this);
        btn_blue.setOnClickListener(this);
        btn_red.setOnClickListener(this);
        btn_width3.setOnClickListener(this);
        btn_width5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.previos:
                reline.doPrevious();
                break;
            case R.id.next:
                reline.doNext();
                break;

            case R.id.width3:
                reline.setStrokeWidth(3);
                break;
            case R.id.width5:
                reline.setStrokeWidth(5);
                break;

            case R.id.hong:
                reline.setColor(Color.RED);
                break;
            case R.id.lw:
                reline.setColor(Color.GREEN);
                break;

            case R.id.lan:
                reline.setColor(Color.BLUE);
                break;
        }
    }
}
