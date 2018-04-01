package contentbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import recodeline.recodeLine;

/**
 * Created by LFZ on 2017/7/15.
 */

public class MainUI extends RelativeLayout{

    final String TAG = "MainUI";
    protected IFBottomToolBar bottom;
    protected recodeLine center;

    public MainUI(Context context) {
        super(context);
        initWidget(context);
    }

    public MainUI(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainUI(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initWidget(Context context){
        this.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));

        //头
        this.addView(top);
        //中部
        //底部
        bottom = new IFBottomToolBar(context);
        this.addView(bottom);

    }
    protected  IFTopToolBar top = new IFTopToolBar(getContext()) {
        @Override
        public void enterEdit() {
            Log.e(TAG,"enterEdit");
        }

        @Override
        public void exitEdit() {
            Log.e(TAG,"exitEdit");
        }

        @Override
        public void fData(String data) {
            Log.e(TAG,"fData" + data);
        }
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                return false;
            case MotionEvent.ACTION_MOVE:
                return false;
            case MotionEvent.ACTION_UP:
        }
        return super.onInterceptTouchEvent(ev);
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
