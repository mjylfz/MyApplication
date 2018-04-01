package view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by LFZ on 2017/7/21.
 * 内层scroll
 */

public class NeiNestScrollView extends NestedScrollView {
    public NeiNestScrollView(Context context) {
        super(context);
    }

    public NeiNestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NeiNestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * down和move请求上层不拦截，内层才能滑动
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("NeiNestScrollView","down");
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("NeiNestScrollView","move");
                getParent().requestDisallowInterceptTouchEvent(true);
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

}