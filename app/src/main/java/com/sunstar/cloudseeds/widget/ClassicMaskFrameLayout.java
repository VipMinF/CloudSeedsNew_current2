package com.sunstar.cloudseeds.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import com.classichu.classichu.app.CLog;

/**
 * Created by louisgeek on 2017/3/17.
 */

public class ClassicMaskFrameLayout extends FrameLayout {
    private Paint mPaint;
    private boolean mIsMask;
    private int mMaskColor = Color.parseColor("#42000000");
    private float mMaskAlpha;
    private float mMaskAlphaEnd = (float) (0.35 * 255);
    private int mMaskDuration = 500;

    public ClassicMaskFrameLayout(Context context) {
        this(context, null);
    }

    public ClassicMaskFrameLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicMaskFrameLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(mMaskColor);
        mPaint.setAlpha((int) mMaskAlpha);
    }

    private void setIsMask(boolean isMask) {
        mIsMask = isMask;
        setAnimator();
        invalidate();
    }

    public void showMask() {
        setIsMask(true);
    }


    public boolean isMask() {
        return mIsMask;
    }

    public void hideMask() {
        setIsMask(false);
    }

    public void setMaskAlphaEnd(float maskAlphaEnd) {
        this.mMaskAlphaEnd = maskAlphaEnd;
    }


    public void setMaskColor(int mMaskColor) {
        this.mMaskColor = mMaskColor;
    }

    public void setMaskDuration(int mMaskDuration) {
        this.mMaskDuration = mMaskDuration;
    }

    private void setAnimator() {
        clearAnimation();
        //
        ValueAnimator animator = ValueAnimator.ofFloat(0f, mMaskAlphaEnd);
        animator.setDuration(mMaskDuration);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float maskAlpha = (float) animation.getAnimatedValue();
                CLog.d("onAnimationUpdate maskAlpha:" + maskAlpha);
                reloadView(maskAlpha);
            }
        });
        animator.start();
    }

    private void reloadView(float maskAlpha) {
        this.mMaskAlpha = maskAlpha;
        mPaint.setAlpha((int) mMaskAlpha);
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mIsMask) {
            canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        }
    }
}
