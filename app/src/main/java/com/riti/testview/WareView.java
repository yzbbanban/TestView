package com.riti.testview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import static android.graphics.Path.Op.INTERSECT;

/**
 * Created by YZBbanban on 2018/2/28.
 */

public class WareView extends View{
    private Paint mPaint;
    private Path mPath;
    private float offset;//偏移量
    private float baseLine;//基线


    public WareView(Context context) {
        super(context);
        init();
    }

    public WareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPath=new Path();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        baseLine=MeasureSpec.getSize(heightMeasureSpec)/2;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        xController();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        float x1=getWidth()/2;
        float y1=getHeight()/2;
        float x2=getWidth()/2;
        float y2=getHeight();
        mPath.reset();
        mPath.moveTo(-getWidth()+offset,baseLine);
        mPath.quadTo(-3*getWidth()/4+offset,baseLine-100,-getWidth()/2+offset,baseLine);
        mPath.quadTo(-1*getWidth()/4+offset,baseLine+100,offset,baseLine);
        mPath.quadTo(1*getWidth()/4+offset,baseLine-100,getWidth()/2+offset,baseLine);
        mPath.quadTo(3*getWidth()/4+offset,baseLine+100,getWidth()+offset,baseLine);


        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(-getWidth()+offset,getHeight());
        mPath.close();

        float radius=100.0f;
        Path pathCircle=new Path();
        pathCircle.addCircle(getWidth()/2,getHeight()/2,radius, Path.Direction.CCW);
        mPath.op(mPath,pathCircle,INTERSECT);
        canvas.drawPath(mPath,mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(getWidth()/2,getHeight()/2,radius,mPaint);
    }

    private void xController(){
        ValueAnimator mAnimator = ValueAnimator.ofFloat(0,getWidth());
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatorValue = (float)valueAnimator.getAnimatedValue() ;
                offset =animatorValue;//不断的设置偏移量，并重画
                postInvalidate();

            }
        });
        mAnimator.setDuration(1000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }
}
