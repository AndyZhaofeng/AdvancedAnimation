package com.zfeng.bazier.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;

/**
 * Created by zhaofeng on 2017/2/10.
 */

public class BazerView extends View
{
    private float mStartPointX,mStartPointY;
    private float mEndPointX,mEndPointY;
    private float mFlagPointX,mFlagPointY;

    private Path mPath;
    private Paint mPaintBezier;
    private Paint mPaintFlag;
    private Paint mPaintFlagText;

    public BazerView(Context context) {
        super(context);
    }

    public BazerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaintBezier=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.STROKE);

        mPaintFlag=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFlag.setStrokeWidth(3);
        mPaintFlag.setStyle(Paint.Style.STROKE);

        mPaintFlagText=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFlagText.setStyle(Paint.Style.STROKE);
        mPaintFlagText.setTextSize(20);
    }

    public BazerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mFlagPointY=event.getX();
                mFlagPointY=event.getY();
                invalidate();
                break;
        }
        return true;
    }
}
