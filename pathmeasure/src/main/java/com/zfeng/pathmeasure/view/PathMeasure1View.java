package com.zfeng.pathmeasure.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zhaofeng on 2017/2/13.
 */

public class PathMeasure1View extends View implements View.OnClickListener
{
    private float mPointY;
    private int waveLength,waveCount;
    private int offset=0;

    private float mPathAnimator,mPathReverseAnimator;

    private Path mPath,mReversePath,mPathMove,mReversePathMove;
    private Paint mPaintBezier,mPaintReverseBezier;

    private ValueAnimator mValueAnimator,mValueReverseAnimator;

    private PathMeasure pathMeasure,pathReverseMeasure;


    public PathMeasure1View(Context context) {
        super(context);
    }

    public PathMeasure1View(Context context, AttributeSet attrs) {
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
        mPathMove=new Path();
        mReversePathMove=new Path();

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
        mPathMove.reset();
        mPathMove.lineTo(0,0);
        mReversePathMove.reset();
        mReversePathMove.lineTo(0,0);

        mPath.reset();
        mPath.moveTo(-waveLength+offset,mPointY);
        for(int i=0;i<waveCount;++i){
            mPath.quadTo(-waveLength*3/4+i*waveLength+offset,mPointY+57,-waveLength/2+i*waveLength+offset,mPointY);
            mPath.quadTo(-waveLength/4+i*waveLength+offset,mPointY-57,i*waveLength+offset,mPointY);
        }

        mReversePath.reset();
        mReversePath.moveTo(-waveLength+offset,mPointY);
        for(int i=0;i<waveCount;++i){
            mReversePath.quadTo(-waveLength*3/4+i*waveLength+offset,mPointY-57,-waveLength/2+i*waveLength+offset,mPointY);
            mReversePath.quadTo(-waveLength/4+i*waveLength+offset,mPointY+57,i*waveLength+offset,mPointY);
        }

        pathMeasure.setPath(mPath,false);
        pathReverseMeasure.setPath(mReversePath,false);

        float stop=pathMeasure.getLength()*mPathAnimator;
        float stopReverse=pathReverseMeasure.getLength()*mPathAnimator;
        pathMeasure.getSegment(0,stop,mPathMove,true);
        pathReverseMeasure.getSegment(0,stopReverse,mReversePathMove,true);
//        canvas.drawPath(mReversePath,mPaintReverseBezier);
        canvas.drawPath(mReversePathMove,mPaintReverseBezier);
        canvas.drawPath(mPathMove,mPaintBezier);

    }

    @Override
    public void onClick(View v) {
        mValueAnimator=ValueAnimator.ofFloat(0,1);
        mValueAnimator.setDuration(4000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPathAnimator=(float)animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();
    }
}
