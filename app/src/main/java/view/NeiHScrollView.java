package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by LFZ on 2017/7/21.
 * 内部横向滚动
 */

public class NeiHScrollView extends HorizontalScrollView {
    public NeiHScrollView(Context context) {
        this(context,null,0);
    }

    public NeiHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public NeiHScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    float x = 0;
    float y = 0;
    float lastx = 0;
    float lasty = 0;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastx = ev.getX();
                lasty = ev.getY();
                return super.onTouchEvent(ev);
            case MotionEvent.ACTION_MOVE:
                x = ev.getX();
                y = ev.getY();
                Log.e("NeiH","NeiH");
                if(Math.abs(x - lastx) > Math.abs(y - lasty) && Math.abs(x - lastx) - Math.abs(y - lasty)>10){
                    return super.onTouchEvent(ev);
                }else{
                    return false;
                }
        }
        return super.onTouchEvent(ev);
    }
}
