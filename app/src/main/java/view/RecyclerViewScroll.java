package view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 内层的rv
 * Created by LFZ on 2017/7/21.
 */

public class RecyclerViewScroll extends RecyclerView {
    public RecyclerViewScroll(Context context) {
        super(context);
    }

    public RecyclerViewScroll(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewScroll(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

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
}
