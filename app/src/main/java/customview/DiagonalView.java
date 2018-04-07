package customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LFZ on 2018/4/3.
 * 用来支持多表头的View，画多个斜线，绘制多个文字
 */

public class DiagonalView extends View {

    private int mWidth = 0;
    private int mHeight = 0;
    private Paint paint;

    public DiagonalView(Context context) {
        this(context, null);
    }

    public DiagonalView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiagonalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //wrap
        if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpec;
        } else {
            mWidth = widthSpec;
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            mHeight = heightSpec;
        } else {
            mHeight = heightSpec;
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String s[] = {"1", "1521", "3222", "4561","131531"};
        drawMutiDiagonal(canvas, s, 30, 0);
    }

    /**
     *
     * @param canvas
     * @param content
     * @param textSize
     * @param direction 斜线的方向，0代表左上到右下，1代表左下到右上
     */
    private void drawMutiDiagonal(Canvas canvas, String[] content, int textSize, int direction) {
        if(direction == 1){
            canvas.rotate(-90,getWidth()/2,getHeight()/2);
        }

        canvas.save();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);

        int angle = 90 / content.length;
        int length = getMaxLineLength();
        //先画斜线
        for (int i = 0; i < content.length - 1; i++) {
            canvas.rotate(angle);
            canvas.drawLine(0, 0, length, 0, paint);
        }

        //恢复画布角度，然后旋转二分之一的角度开始画文字
        canvas.restore();
        canvas.save();
        canvas.rotate(angle / 2);

        paint.setTextSize(textSize);

        //绘制文字
        for(int i = 0;i <content.length;i++){
            Rect textBound = new Rect();
            //计算文字大小
            paint.getTextBounds(content[i], 0, content[i].length(), textBound);

            //第一个值是绘制的文字恰好不接触到斜线的最小x距离，第二个值是故意再多出一块为了美观，取了宽高中较小值的五分之一
            float x = (float) ((textBound.height()/2)/(Math.tan((Math.PI*angle/2)/180))) + Math.min(getWidth(),getHeight()) / 5;
            canvas.drawText(content[i], x, textBound.height() / 2, paint);
            canvas.rotate(angle);
        }

        canvas.restore();

    }

    private int getMaxLineLength(){
        return (int) (Math.sqrt(getMeasuredHeight() * getMeasuredHeight() + getMeasuredWidth() * getMeasuredWidth()) + 1);
    }
}
