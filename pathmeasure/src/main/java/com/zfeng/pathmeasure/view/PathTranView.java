package com.zfeng.pathmeasure.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by zhaofeng on 2017/2/13.
 */

public class PathTranView extends View implements View.OnClickListener
{
    private int mWidth,mHeight;

    private Path mPath;
    private Paint mPaint;
    private ValueAnimator mValueAnimator;
    private PathMeasure pathMeasure;

    private float[] mPos,mTran;
    private float mCurrentValue;


    public PathTranView(Context context) {
        super(context);
    }

    public PathTranView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);

        pathMeasure=new PathMeasure();

        mPath = new Path();

        setOnClickListener(this);

        mPath.addCircle(0,0,200, Path.Direction.CW);
        pathMeasure.setPath(mPath,false);

        mPos = new float[2];
        mTran = new float[2];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pathMeasure.getPosTan(mCurrentValue*pathMeasure.getLength(),mPos,mTran);
        float degree=(float)(Math.atan2(mTran[0],mTran[1])*180/Math.PI);
        canvas.save();
        canvas.translate(mWidth/2,mHeight*2/5);
        canvas.drawPath(mPath,mPaint);
        canvas.drawCircle(mPos[0],mPos[1],11,mPaint);
        canvas.drawLine(mPos[0],mPos[1],mPos[0],-200,mPaint);
        canvas.restore();
    }

    @Override
    public void onClick(View v) {
        mValueAnimator=ValueAnimator.ofFloat(0,1);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setDuration(4000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentValue=(float)animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();
    }
}
