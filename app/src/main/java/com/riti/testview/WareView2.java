package com.riti.testview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by YZBbanban on 2018/2/28.
 */

public class WareView2 extends View {
    private Paint mPaint;
    private Path mPath;
    private float offset;//偏移量

    public WareView2(Context context) {
        super(context);
        init();
    }

    public WareView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WareView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//防锯齿
        mPaint.setDither(true);//防抖动
        mPath = new Path();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        xController();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float radius = 150.0f;
//        Log.i(TAG, "onDraw: "+getWidth());
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.FILL);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);//创建透明图层
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);


        //画笔颜色
        mPaint.setColor(Color.RED);
        //改变模式为填充 fill
        mPaint.setStyle(Paint.Style.FILL);
        //设置图层叠加样式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        //初始化，不然会重复绘制
        mPath.reset();
        //移动到左边屏幕中间
        mPath.moveTo(0.0f , getHeight()/2);
        //贝斯阿尔曲线
        mPath.quadTo(-7 * getWidth() / 8 + offset, getHeight()/2 - 100, -3*getWidth() / 4 + offset, getHeight()/2);
        mPath.quadTo(-5 * getWidth() / 8 + offset, getHeight()/2 + 100, -getWidth()/2+offset, getHeight()/2);
        mPath.quadTo(-3 * getWidth() / 8 + offset, getHeight()/2 - 100, -getWidth() / 4 + offset, getHeight()/2);
        mPath.quadTo(-1 * getWidth() / 8 + offset, getHeight()/2 + 100, offset, getHeight()/2);

        mPath.quadTo(1 * getWidth() / 8 + offset, getHeight()/2 - 100, getWidth() / 4 + offset, getHeight()/2);
        mPath.quadTo(3 * getWidth() / 8 + offset, getHeight()/2 + 100, getWidth()/2 + offset, getHeight()/2);
        mPath.quadTo(5 * getWidth() / 8 + offset, getHeight()/2 - 100, 3*getWidth() / 4 + offset, getHeight()/2);
        mPath.quadTo(7 * getWidth() / 8 + offset, getHeight()/2 + 100, getWidth() + offset, getHeight()/2);
        //贝塞尔曲线中点为屏幕右端竖直方向的中点，所以选取屏幕上方作为一条线的中点
        mPath.lineTo(getWidth(),0);
        //在移动到屏幕起始点，形成闭区间
        mPath.lineTo(0,0);

        //在画布上绘制
        canvas.drawPath(mPath,mPaint);
        //取消样式
        mPaint.setXfermode(null);
        //在 onDraw 之前保存画笔等的状态，便于下次直接使用画笔等工具
        canvas.restoreToCount(layerId);

        //绘制边界
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
                postInvalidate();

            }
        });
        mAnimator.setDuration(1000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }


}
