package com.sunstar.cloudseeds.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

/**
 * 进度 LinearLayout
 * Created by louisgeek on 2017/3/17.
 */

public class ProgressOverLinearLayout extends LinearLayout {
    private float mProgress; // 当前进度
    private Paint mPaint; // 背景色画笔
    private long mDuration = 1500; // 动画持续时间

    private final int MAX_ALPHA = 150; // 最大透明度
    private final int MAX_PROGRESS = 100; // 最大进度值

    public ProgressOverLinearLayout(Context context) {
        this(context, null);
    }

    public ProgressOverLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressOverLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();

    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public void setProgress(int progress, int color) {
        setProgress(progress, color, false);
    }

    public void setProgress(int progress, int color, boolean animated) {
        mPaint.setColor(color);
        if (animated) {
            ValueAnimator animator = ValueAnimator.ofFloat(0f, progress);
            animator.setDuration(mDuration);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    reloadView((float) animation.getAnimatedValue());
                }
            });
            animator.start();
        } else {
            reloadView(progress);
        }
    }


    private void reloadView(float progress) {
        if (progress > MAX_PROGRESS) {
            progress = MAX_PROGRESS;
        }
        mProgress = progress;

        int alpha = (int) ((mProgress / MAX_PROGRESS) * MAX_ALPHA);
        mPaint.setAlpha(alpha);
        invalidate();
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawRect(0, 0, getMeasuredWidth() * mProgress / MAX_PROGRESS, getMeasuredHeight(), mPaint);
    }
}
