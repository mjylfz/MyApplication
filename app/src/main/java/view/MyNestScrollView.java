package view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by LFZ on 2017/7/21.
 * 外层Scroll
 */

public class MyNestScrollView extends ScrollView {
    public MyNestScrollView(Context context) {
        super(context);
    }

    public MyNestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * down和move都请求refresh不拦截，这样move会被下层滚动收到，下层下滑不会进行刷新
     * @param ev
     * @return
     */
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
            }
            return super.dispatchTouchEvent(ev);
    }

    float x = 0;
    float y = 0;
    float lastx = 0;
    float lasty = 0;
    /**
     * down不拦截，move虽然拦截，下层request(true)，所以不拦截
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastx = ev.getX();
                lasty = ev.getY();
                //为什么在这里直接return super
                //虽然super也返回了false，但super中执行了滑动的一些代码，如果直接返回false不调用super则无法滑动了。
                //ViewGroup的onInterceptTouchEvent应该默认返回false，使用super即可
                return super.onInterceptTouchEvent(ev);
            case MotionEvent.ACTION_MOVE:
                x = ev.getX();
                y = ev.getY();
//                Log.e("外层","jj");
                if(Math.abs(x - lastx) < Math.abs(y - lasty)&&Math.abs(y-lasty)-Math.abs(x-lastx)>10){
                    return true;
                }else{
                    return false;
                }
            case MotionEvent.ACTION_UP:
                return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
