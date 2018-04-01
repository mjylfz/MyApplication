package recodeline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LFZ on 2017/8/13.
 * 标记的view
 */

public class recodeLine extends View {

    Paint paint;
    Path path;
    private int currentColor = Color.RED;
    private List<Path> nextList;
    private List<Path> historyList;
    private List<Path> line;
    private Canvas canvas;

    public recodeLine(Context context) {
        this(context ,null, 0);
    }

    public recodeLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public recodeLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        this.setBackgroundColor(Color.parseColor("#eeeeee"));

        paint = new Paint();
        paint.setColor(currentColor);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();

        nextList = new ArrayList<>();
        historyList = new ArrayList<>();
        line = new ArrayList<>();
        historyList.add(path);
        line.add(path);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i = 0 ;i<line.size();i++){
            canvas.drawPath(line.get(i),paint);
        }
    }

    /**
     * 上一步
     */
    public void doPrevious(){
        if(line.size()!=0){
            Path path=line.get(line.size() - 1);
            historyList.add(path);
            line.remove(path);
        }
        invalidate();
    }

    /**
     * 下一步
     */
    public void doNext(){
        if(historyList.size()!=0){
            line.add(historyList.get(historyList.size() - 1));
            historyList.remove(historyList.size() - 1);
        }
        invalidate();
    }

    public void setStrokeWidth(int width){
        paint.setStrokeWidth(width);
    }

    public void setColor(int color){
        this.currentColor = color;
        paint.setColor(color);
    }

    public void setCurrentColor(int currentColor) {
        this.currentColor = currentColor;
    }

    public void setWidth(int width){
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
            case MotionEvent.ACTION_UP:
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                line.add(path);
                path.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(x,y);
                historyList.clear();
                break;
        }
        invalidate();
        return super.onTouchEvent(event);
    }
}
