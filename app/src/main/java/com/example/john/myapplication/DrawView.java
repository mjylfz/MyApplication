package com.example.john.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LFZ on 2017/7/14.
 */

public class DrawView extends View {
    public DrawView(Context context) {
        this(context,null,0);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.RED);// 设置红色
        p.setAlpha(55);
        p.setStrokeWidth(2);
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);

        canvas.drawRect(10,10,1000,1000,p);

        Paint p1 = new Paint();
        p1.setColor(Color.BLACK);// 设置红色
        p1.setStrokeWidth(1);
        p1.setAntiAlias(true);
        p1.setStyle(Paint.Style.FILL);
        p1.setTextSize(100);
        p1.setTextAlign(Paint.Align.LEFT);
        float baseLine = Math.abs(p1.ascent());
        canvas.drawText("画矩形",10,10 + baseLine,p1);


        Paint.FontMetrics fontMetrics = p1.getFontMetrics();
        float height = fontMetrics.bottom;

        Paint p2 = new Paint();
        p2.setColor(Color.BLACK);// 设置红色
        p2.setStrokeWidth(1);
        p2.setAntiAlias(true);
        p2.setStyle(Paint.Style.FILL);
        p2.setTextSize(100);
        p2.setTextAlign(Paint.Align.LEFT);
        p2.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));

        canvas.skew(0.1f, 0);
//        canvas.translate(100, 0);
        canvas.drawText("画矩形sfsdfsfd",10,1000 - height,p2);

//        canvas.drawText("画圆：", 10, 20, p);// 画文本
    }
}
