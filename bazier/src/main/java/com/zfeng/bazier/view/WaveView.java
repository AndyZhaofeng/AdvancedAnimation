package com.zfeng.bazier.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by zhaofeng on 2017/2/13.
 */

public class WaveView extends View implements View.OnClickListener
{
    private float mPointY;
    private int waveLength,waveCount;
    private int offset=0;
    private int mScreenWidth,mScreenHeight;

    private Path mPath;
    private Paint mPaintBezier;

    private ValueAnimator mValueAnimator,mValueHeightAnimator;


    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaintBezier=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintBezier.setColor(Color.BLUE);

        waveLength=200;
        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mScreenWidth=w;
        mScreenHeight=h;
        waveCount=(int)Math.round(w/waveLength+1.5);

        mPointY=h;

        mValueAnimator=ValueAnimator.ofInt(0,waveLength);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset=(int)animation.getAnimatedValue();
                invalidate();
            }
        });

        mValueHeightAnimator=ValueAnimator.ofFloat(h,0);
        mValueHeightAnimator.setDuration(6000);
        mValueHeightAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueHeightAnimator.setInterpolator(new LinearInterpolator());
        mValueHeightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPointY=(float)animation.getAnimatedValue();
            }
        });

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
        mPath.lineTo(mScreenWidth,mScreenHeight);
        mPath.lineTo(0,mScreenHeight);
        mPath.close();
        canvas.drawPath(mPath,mPaintBezier);

    }

    @Override
    public void onClick(View v) {
        mValueAnimator.start();
        mValueHeightAnimator.start();
    }
}