package topviewpagerwidget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by LFZ on 2017/7/23.
 */

public class BaseViewPager extends ViewPager {

//    Timer timer = new Timer();
//
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if(msg.what == 0){
//                if(getAdapter().getCount() != 0){
//                    int index = (getCurrentItem() + 1) % getAdapter().getCount();
//                    setCurrentItem(index);
//                }
//            }
//        }
//    };

    public BaseViewPager(Context context) {
        this(context,null);
    }

    public BaseViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    float startX;
    float startY;
    float endX;
    float endY;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = ev.getRawX();
                startY = ev.getRawY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                endX = ev.getRawX();
                endY = ev.getRawY();
                //左右滑动
                if(Math.abs(startX - endX) > Math.abs(startY - endY)) {
                    //右滑在第一页，上层拦截
                    if (endX - startX > 0 && getCurrentItem() == 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        //左滑在尾页，上层拦截
                    } else if (startX - endX > 0 && getCurrentItem() == getAdapter().getCount() - 1) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                //上下滑动上层需要拦截
                else{
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}
