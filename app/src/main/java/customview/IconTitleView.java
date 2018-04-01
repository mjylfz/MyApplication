package customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.example.john.myapplication.R;

/**
 * Created by LFZ on 2017/8/12.
 * 简单的自定义view
 */

public class IconTitleView extends View {

    private String titleText;
    private int titleSize;
    private int titleColor;
    private Bitmap img;
    private int imageScaleType;

    private Rect rect;
    private Paint paint;
    private Rect textBound;

    private int mWidth = 0;
    private int mHeight = 0;


    public IconTitleView(Context context) {
        this(context , null , 0);
    }

    public IconTitleView(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public IconTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView);
        titleColor = a.getColor(R.styleable.CustomImageView_titleTextColor, Color.BLUE);
        titleSize = a.getDimensionPixelSize(R.styleable.CustomImageView_titleTextSize,16);
        titleText = a.getString(R.styleable.CustomImageView_titleText);
        img = BitmapFactory.decodeResource(getResources(),a.getResourceId(R.styleable.CustomImageView_image,0));
        imageScaleType = a.getInt(R.styleable.CustomImageView_imageScaleType,0);

        a.recycle();

        rect = new Rect();
        paint = new Paint();
        textBound = new Rect();
        paint.setTextSize(titleSize);
        //计算文字的范围
        paint.getTextBounds(titleText,0,titleText.length(),textBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //wrap
        if(widthMode == MeasureSpec.AT_MOST){
            mWidth = Math.min(Math.max(img.getWidth(),textBound.width()) +getPaddingRight() + getPaddingLeft(),widthSpec);
        }else {
            mWidth = widthSpec;
        }

        if(heightMode == MeasureSpec.AT_MOST){
            mHeight = Math.min(getPaddingTop() + getPaddingBottom() + textBound.height() + img.getHeight(),heightSpec);
        }else{
            mHeight = heightSpec;
        }

        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //画边框
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint);

        //设置边框
        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = mHeight - getPaddingBottom();

        //画文字
        paint.setColor(titleColor);
        if(textBound.width() > mWidth){
            TextPaint paints = new TextPaint(paint);
            String msg = TextUtils.ellipsize(titleText, paints, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), paint);
        }else{
            canvas.drawText(titleText,mWidth/2 - textBound.width()/2,mHeight - getPaddingBottom(),paint);
        }

        //画图片
        rect.bottom = rect.bottom - textBound.height();

        if(imageScaleType == 0){
            canvas.drawBitmap(img,null,rect,paint);
        }else{
            rect.top = (mHeight - textBound.height())/2 - img.getHeight()/2;
            rect.bottom = (mHeight - textBound.height())/2 + img.getHeight()/2;
            rect.left = mWidth/2 - img.getWidth()/2;
            rect.right = mWidth/2 + img.getWidth()/2;
            canvas.drawBitmap(img,null,rect,paint);
        }
    }

}
