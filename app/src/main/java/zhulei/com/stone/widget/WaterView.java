package zhulei.com.stone.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import zhulei.com.stone.R;

/**
 * Created by zhulei on 16/6/15.
 */
public class WaterView extends View {

    private Paint mPaint;
    private Path mPath;

    private static final int mItemWaveLength = 600;
    private int mMovePx;

    public WaterView(Context context) {
        super(context);
        init();
    }

    public WaterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.colorAccent));

        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, getResources().getDisplayMetrics().heightPixels);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(-mItemWaveLength + mMovePx, 50);
        for (int i = -mItemWaveLength; i < getMeasuredWidth()
                + mItemWaveLength; i += mItemWaveLength){
            mPath.rQuadTo(mItemWaveLength/4, -50, mItemWaveLength/2, 0);
            mPath.rQuadTo(mItemWaveLength/4, 50, mItemWaveLength/2, 0);
        }
        mPath.lineTo(getMeasuredWidth(), getMeasuredHeight());
        mPath.lineTo(0, getMeasuredHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    public void startAnimator(){
        ValueAnimator animator = ValueAnimator.ofInt(0, mItemWaveLength);
        animator.setDuration(3000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMovePx = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

}
