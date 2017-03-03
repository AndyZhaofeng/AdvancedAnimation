package com.zfeng.bazier.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.zfeng.bazier.animator.MoveEvalutor;

/**
 * Created by zhaofeng on 2017/2/13.
 */

public class MoveOnBezierView extends View implements View.OnClickListener
{
    private float mPointY;
    private int waveLength,waveCount;
    private int offset=0;
    private float mCircleX,mCircleY;

    private Path mPath,mReversePath,mMovePath;
    private Paint mPaintBezier,mPaintReverseBezier,mPaintCircle;

    private ValueAnimator mValueAnimator,mValueReverseAnimator;

    private float mStartPointX,mStartPointY;
    private float mEndPointX,mEndPointY;
    private float mFlagPointX,mFlagPointY;


    public MoveOnBezierView(Context context) {
        super(context);
    }

    public MoveOnBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaintBezier=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setColor(Color.BLUE);

        mPaintReverseBezier=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintReverseBezier.setStrokeWidth(8);
        mPaintReverseBezier.setStyle(Paint.Style.STROKE);
        mPaintReverseBezier.setColor(Color.GREEN);

        mPaintCircle=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle.setStrokeWidth(8);
        mPaintCircle.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintCircle.setColor(Color.BLUE);

        waveLength=200;
        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        waveCount=(int)Math.round(w/waveLength+1.5);

        mPointY=h/2;

        mStartPointX = w / 4;
        mStartPointY = h / 2 - 200;

        mEndPointX = w * 3 / 4;
        mEndPointY = h / 2 - 200;

        mFlagPointX = w / 2;
        mFlagPointY = h / 2 - 400;

        mCircleX=mStartPointX;
        mCircleY=mStartPointY;

        mMovePath=new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath = new Path();
        mPath.reset();
        mPath.moveTo(-waveLength+offset,mPointY);
        for(int i=0;i<waveCount;++i){
            mPath.quadTo(-waveLength*3/4+i*waveLength+offset,mPointY+57,-waveLength/2+i*waveLength+offset,mPointY);
            mPath.quadTo(-waveLength/4+i*waveLength+offset,mPointY-57,i*waveLength+offset,mPointY);
        }

        mReversePath=new Path();
        mReversePath.reset();
        mReversePath.moveTo(-waveLength+offset,mPointY);
        for(int i=0;i<waveCount;++i){
            mReversePath.quadTo(-waveLength*3/4+i*waveLength+offset,mPointY-57,-waveLength/2+i*waveLength+offset,mPointY);
            mReversePath.quadTo(-waveLength/4+i*waveLength+offset,mPointY+57,i*waveLength+offset,mPointY);
        }
        canvas.drawPath(mReversePath,mPaintReverseBezier);
        canvas.drawPath(mPath,mPaintBezier);

        canvas.drawCircle(mCircleX,mCircleY,30,mPaintCircle);
        mMovePath.reset();
        mMovePath.moveTo(mStartPointX,mStartPointY);
        mMovePath.quadTo(mFlagPointX,mFlagPointY,mEndPointX,mEndPointY);
        canvas.drawPath(mMovePath,mPaintBezier);

    }

    @Override
    public void onClick(View v) {
        PointF flagPoint1=new PointF(mFlagPointX,mFlagPointY);

        PointF startPoint=new PointF(mStartPointX,mStartPointY);
        PointF endPoint=new PointF(mEndPointX,mEndPointY);

        /**
         *
         */
        mValueAnimator=ValueAnimator.ofObject(new MoveEvalutor(flagPoint1),
                startPoint,endPoint);
        mValueAnimator.setDuration(4000);
//        mValueAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point=(PointF) animation.getAnimatedValue();
                mCircleX=point.x;
                mCircleY=point.y;
                invalidate();
            }
        });
        mValueAnimator.start();
    }
}