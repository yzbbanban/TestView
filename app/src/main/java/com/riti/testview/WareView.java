package com.riti.testview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import static android.graphics.Path.Op.INTERSECT;

/**
 * Created by YZBbanban on 2018/2/28.
 */

public class WareView extends View {
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
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPath = new Path();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        baseLine = MeasureSpec.getSize(heightMeasureSpec) / 2 + 150;
        Log.i(TAG, "onMeasure---->: " + baseLine);
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
        float radius = 150.0f;
//        Log.i(TAG, "onDraw: "+getWidth());
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.FILL);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);

        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mPath.reset();
        mPath.moveTo(-getWidth() + offset, baseLine);

        int wareStep = 8;
        for (int i = 0; i < wareStep; i++) {
            float base = baseLine;
            if (i % 2 == 0) {
                base = base + 30f;
            } else {
                base = base - 30f;
            }
            mPath.quadTo(
                    (2 * i - wareStep + 1) * getWidth() / wareStep + offset,
                    base,
                    2 * (i - 3) * getWidth() / wareStep + offset,
                    baseLine);
        }

//        mPath.quadTo(-7 * getWidth() / 8 + offset, baseLine - 30, -3*getWidth() / 4 + offset, baseLine);
//        mPath.quadTo(-5 * getWidth() / 8 + offset, baseLine + 30, -getWidth()/2+offset, baseLine);
//        mPath.quadTo(-3 * getWidth() / 8 + offset, baseLine - 30, -getWidth() / 4 + offset, baseLine);
//        mPath.quadTo(-1 * getWidth() / 8 + offset, baseLine + 30, offset, baseLine);
//
//        mPath.quadTo(1 * getWidth() / 8 + offset, baseLine - 30, getWidth() / 4 + offset, baseLine);
//        mPath.quadTo(3 * getWidth() / 8 + offset, baseLine + 30, getWidth()/2 + offset, baseLine);
//        mPath.quadTo(5 * getWidth() / 8 + offset, baseLine - 30, 3*getWidth() / 4 + offset, baseLine);
//        mPath.quadTo(7 * getWidth() / 8 + offset, baseLine + 30, getWidth() + offset, baseLine);

        mPath.lineTo(getWidth(), 0.0f);
        mPath.lineTo(-getWidth() + offset, 0.0f);
        mPath.close();


        canvas.drawPath(mPath, mPaint);

        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);

        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
    }

    private void xController() {
        ValueAnimator mAnimator = ValueAnimator.ofFloat(0, getWidth());
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatorValue = (float) valueAnimator.getAnimatedValue();
                offset = animatorValue;//不断的设置偏移量，并重画
//                baseLine =animatorValue;//不断的设置偏移量，并重画
                postInvalidate();

            }
        });
        mAnimator.setDuration(1000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }

    private static final String TAG = "WareView";

    public void setProcess(int process) {
        Log.i(TAG, "setProcess: " + process);
        //[-150,150]
        float step = process * 3f;

        float pro = getHeight() / 2 + (150f - step);
        Log.i(TAG, "setProcess getWidth: " + getWidth());
        Log.i(TAG, "pro: " + pro);

        this.baseLine = pro;
    }
}
