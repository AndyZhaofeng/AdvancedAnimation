package com.zfeng.bazier.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhaofeng on 2017/2/13.
 */

public class BazierAnimatorView extends View implements View.OnClickListener
{
    private float mStartPointX,mStartPointY;
    private float mEndPointX,mEndPointY;
    private float mFlagPointX,mFlagPointY;

    private Path mPath;
    private Paint mPaintBezier;

    private ValueAnimator mValueAnimator;


    public BazierAnimatorView(Context context) {
        super(context);
    }

    public BazierAnimatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaintBezier=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.STROKE);

        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2 - 200;

        mEndPointX = w * 3 / 4;
        mEndPointY = h / 2 - 200;

        mFlagPointX = w / 2;
        mFlagPointY = h / 2 - 300;

        mPath = new Path();

        mValueAnimator=ValueAnimator.ofFloat(mStartPointY,h);
        mValueAnimator.setDuration(5000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFlagPointY=(float)animation.getAnimatedValue();
                invalidate();
            }
        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartPointX,mStartPointY);
        mPath.quadTo(mFlagPointX,mFlagPointY,mEndPointX,mEndPointY);
        canvas.drawPath(mPath,mPaintBezier);

    }

    @Override
    public void onClick(View v) {
        mValueAnimator.start();
    }
}
