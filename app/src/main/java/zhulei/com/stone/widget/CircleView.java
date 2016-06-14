package zhulei.com.stone.widget;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import zhulei.com.stone.entity.custom.Circle;

/**
 * Created by zhulei on 16/6/14.
 */
public class CircleView extends View {

    private Circle mCurCircle;

    private ValueAnimator mAnimator;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurCircle != null){
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(mCurCircle.getCorlor());
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, mCurCircle.getRadius(), paint);
        }
    }

    public void startAnim(Circle startCircle, Circle endCircle){
        mAnimator = ValueAnimator.ofObject(new CircleEvaluate(), startCircle, endCircle);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurCircle = (Circle) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimator.setDuration(1000);
        mAnimator.setInterpolator(new BounceInterpolator());
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }

    public void cancleAnim(){
        if (mAnimator != null && mAnimator.isRunning()){
            mAnimator.cancel();
        }
    }

    public static class CircleEvaluate implements TypeEvaluator<Circle> {

        @Override
        public Circle evaluate(float fraction, Circle startValue, Circle endValue) {
            int startRadius = startValue.getRadius();
            int startColor = startValue.getCorlor();
            int endRadius = endValue.getRadius();
            int endColor = endValue.getCorlor();
            int curRadius = (int) (startRadius + (endRadius - startRadius) * fraction);

            int startA = (startColor >> 24) & 0xff;
            int startR = (startColor >> 16) & 0xff;
            int startG = (startColor >> 8) & 0xff;
            int startB = startColor & 0xff;

            int endA = (endColor >> 24) & 0xff;
            int endR = (endColor >> 16) & 0xff;
            int endG = (endColor >> 8) & 0xff;
            int endB = endColor & 0xff;

            int curColor =  (int)((startA + (int)(fraction * (endA - startA))) << 24) |
                    (int)((startR + (int)(fraction * (endR - startR))) << 16) |
                    (int)((startG + (int)(fraction * (endG - startG))) << 8) |
                    (int)((startB + (int)(fraction * (endB - startB))));

            return new Circle(curRadius, curColor);
        }

    }
}
