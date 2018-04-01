package view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by LFZ on 2017/7/25.
 */

public class FCTButtonWidget extends TextView {

    private static final int NONE = -1;
    private static final int DEFAULT_BACKGROUND = Color.rgb(116,168,220);
    private int clickBackgroundColor = NONE;
    private int unClickBackgroundColor = DEFAULT_BACKGROUND;

    public FCTButtonWidget(Context context) {
        this(context,null , 0);
    }

    public FCTButtonWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FCTButtonWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initRoundCorner();
    }

    private void initRoundCorner(){
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(getMeasuredHeight()/8);
        gradientDrawable.setColor(Color.rgb(116,168,220));
        setBackground(gradientDrawable);
    }

    public void setUnClickBackground(int color){
        this.unClickBackgroundColor = color;
    }

    public void setClickBackground(int color){
        this.clickBackgroundColor = color;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("onTouchEvent", String.valueOf(unClickBackgroundColor));
        if(clickBackgroundColor == NONE){
            return super.onTouchEvent(event);
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(clickBackgroundColor);
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(unClickBackgroundColor);
                break;
        }
        return super.onTouchEvent(event);
    }
}
