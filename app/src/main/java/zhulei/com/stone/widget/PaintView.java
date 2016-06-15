package zhulei.com.stone.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhulei on 16/6/15.
 */
public class PaintView extends View {

    private Path mPath;
    private Paint mPaint;

    private float mControlX;
    private float mControlY;

    public PaintView(Context context) {
        super(context);
        init();
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                mControlX = event.getX();
                mControlY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = (mControlX + event.getX()) / 2;
                float endY = (mControlY + event.getY()) / 2;
                mPath.quadTo(mControlX, mControlY, endX, endY);
                mControlX = event.getX();
                mControlY = event.getY();
                invalidate();
                break;
        }
        return true;
    }

    private void init(){
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    public void reset(){
        mPath.reset();
        invalidate();
    }
}
