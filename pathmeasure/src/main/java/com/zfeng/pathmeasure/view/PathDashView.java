package com.zfeng.pathmeasure.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhaofeng on 2017/2/13.
 */

public class PathDashView extends View implements View.OnClickListener
{
    private float mPointY;
    private int waveLength,waveCount;

    private float mPathLength,mReverserPathLength;

    private Path mPath,mReversePath;
    private Paint mPaintBezier,mPaintReverseBezier;
    private PathEffect mPathEffect,mReversePathEffect;

    private ValueAnimator mValueAnimator,mValueReverseAnimator;

    private PathMeasure pathMeasure,pathReverseMeasure;


    public PathDashView(Context context) {
        super(context);
    }

    public PathDashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaintBezier=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setColor(Color.BLUE);

        mPaintReverseBezier=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintReverseBezier.setStrokeWidth(8);
        mPaintReverseBezier.setStyle(Paint.Style.STROKE);
        mPaintReverseBezier.setColor(Color.GREEN);

        pathMeasure=new PathMeasure();
        pathReverseMeasure=new PathMeasure();

        mPath = new Path();
        mReversePath=new Path();

        waveLength=200;
        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        waveCount=(int)Math.round(w/waveLength+1.5);

        mPointY=h/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        mPath.moveTo(0,mPointY);
        for(int i=1;i<waveCount;++i){
            mPath.quadTo(-waveLength*3/4+i*waveLength,mPointY+57,-waveLength/2+i*waveLength,mPointY);
            mPath.quadTo(-waveLength/4+i*waveLength,mPointY-57,i*waveLength,mPointY);
        }

        mReversePath.reset();
        mReversePath.moveTo(0,mPointY);
        for(int i=1;i<waveCount;++i){
            mReversePath.quadTo(-waveLength*3/4+i*waveLength,mPointY-57,-waveLength/2+i*waveLength,mPointY);
            mReversePath.quadTo(-waveLength/4+i*waveLength,mPointY+57,i*waveLength,mPointY);
        }


        pathMeasure.setPath(mPath,false);
        pathReverseMeasure.setPath(mReversePath,false);

        mPathLength=pathMeasure.getLength();
        mReverserPathLength=pathReverseMeasure.getLength();

//        canvas.drawPath(mReversePath,mPaintReverseBezier);
        canvas.drawPath(mPath,mPaintBezier);
        canvas.drawPath(mReversePath,mPaintReverseBezier);

    }

    @Override
    public void onClick(View v) {
        mValueAnimator=ValueAnimator.ofFloat(0,1);
        mValueAnimator.setDuration(4000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float mPathAnimator=(float)animation.getAnimatedValue();
                mPathEffect=new DashPathEffect(new float[]{mPathLength,mPathLength},mPathLength*mPathAnimator);
                mPaintBezier.setPathEffect(mPathEffect);
                invalidate();
            }
        });
        mValueAnimator.start();

        mValueReverseAnimator=ValueAnimator.ofFloat(1,0);
        mValueReverseAnimator.setDuration(4000);
        mValueReverseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float mPathAnimator=(float)animation.getAnimatedValue();
                mReversePathEffect=new DashPathEffect(new float[]{mReverserPathLength,mReverserPathLength},mReverserPathLength*mPathAnimator);
                mPaintReverseBezier.setPathEffect(mReversePathEffect);
                invalidate();
            }
        });
        mValueReverseAnimator.start();
    }
}
