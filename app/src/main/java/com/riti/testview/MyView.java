package com.riti.testview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by YZBbanban on 2018/2/26.
 */

public class MyView extends View {
    private static final String TAG = "MyView";
    private Paint mPaint;
    private RectF rectF;
    private int backgroundColor = Color.GRAY;
    private int progressColor = Color.BLUE;
    private float radius;
    private int progress;
    private float centerX = 0;
    private float centerY = 0;
    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int CENTER = 2;
    public static final int RIGHT = 3;
    public static final int BOTTOM = 4;
    private int gravity = CENTER;

    public MyView(Context context) {
        super(context);
        init();

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//防抖动
        rectF = new RectF();

    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.MyView);
        backgroundColor=typedArray.getColor(R.styleable.MyView_percent_background_color,Color.GRAY);
        gravity=typedArray.getInt(R.styleable.MyView_percent_circle_gravity,CENTER);
        progress=typedArray.getInt(R.styleable.MyView_percent_circle_progress,0);
        progressColor=typedArray.getColor(R.styleable.MyView_percent_progress_color,Color.YELLOW);
        radius=typedArray.getDimension(R.styleable.MyView_percent_circle_radius,0.0f);




    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:

                break;
            case MeasureSpec.AT_MOST:

                break;
            case MeasureSpec.UNSPECIFIED:

                break;
        }

        Log.e(TAG, "onMeasure--widthSize-->" + widthSize);
        Log.e(TAG, "onMeasure--heightMode-->" + heightMode);
        Log.e(TAG, "onMeasure--heightSize-->" + heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @SuppressLint("Range")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        int width = getWidth();
        int height = getHeight();
        float radius = width / 4;
        canvas.drawCircle(width / 2, height / 2, radius, mPaint);
        mPaint.setColor(Color.BLUE);
        rectF.set(width / 2-radius, height/2-radius, width/2+radius, height/2+radius);
        canvas.drawArc(rectF, startAng, endAng, true, mPaint);

        rectF.set(width / 2-radius, height/2-radius, width/2+radius, height/2+radius);

        mPaint.setColor(Color.parseColor("#89ffb6cf"));
        canvas.drawRect(rectF,mPaint);
        float[] pts=new float[]{1.1f,20.2f,30.3f};

        mPaint.setColor(Color.BLACK);
        canvas.drawLines(pts,mPaint);

    }


    private int startAng;
    private int endAng;


    public void setDate(int startAng,int endAng){
        this.startAng=startAng;
        this.endAng=endAng;

    }
}
