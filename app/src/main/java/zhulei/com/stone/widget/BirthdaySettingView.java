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
 * Created by zhulei on 16/6/16.
 */
public class BirthdaySettingView extends View {

    private Paint mPaint;
    private static final int GAP_WITH = 300;
    private static final int INDEX_LINE_HEIGHT = 30;
    private static final int INDEX_LINE_HEIGHT_SELECTED = 50;
    private static final int TEXT_SIZE = 80;
    private static final int TEXT_SIZE_SELECTED = 90;
    private static final int TEXT_PADDING_TOP = 20;
    private static final int TEXT_PADDING_BOTTOM = 40;

    private float mPreX;
    private int mPreDeltaX;
    private int mDeltaX;

    private String mText;
    private int mCenterYear;

    public BirthdaySettingView(Context context) {
        super(context);
        init();
    }

    public BirthdaySettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BirthdaySettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        mCenterYear = 1990;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);

        mPaint.setSubpixelText(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    //TODO
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPreX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                mDeltaX = (int) (event.getX() - mPreX);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(mDeltaX) != GAP_WITH){
                    mDeltaX = GAP_WITH * (mDeltaX / GAP_WITH);
                    mPreDeltaX += mDeltaX;
                    invalidate();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path topPath = new Path();
        topPath.moveTo(0, 0);
        topPath.lineTo(getMeasuredWidth(), 0);
        canvas.drawPath(topPath, mPaint);

        //右半部分
        int countR = 0;
        for (int i = getMeasuredWidth()/2 + mDeltaX + mPreDeltaX; i < getMeasuredWidth() * 1.5; i += GAP_WITH) {
            Path indexPathR = new Path();
            indexPathR.moveTo(i, 0);
            if (i == getMeasuredWidth()/2) {
                mText = mCenterYear + countR + "";
                indexPathR.lineTo(i, INDEX_LINE_HEIGHT_SELECTED);
                mPaint.setTextSize(TEXT_SIZE_SELECTED);
                mPaint.setColor(Color.BLUE);
            } else {
                indexPathR.lineTo(i, INDEX_LINE_HEIGHT);
                mPaint.setTextSize(TEXT_SIZE);
                mPaint.setColor(Color.BLACK);
            }
            canvas.drawPath(indexPathR, mPaint);

            canvas.drawText(mCenterYear + countR + "", i, INDEX_LINE_HEIGHT + TEXT_SIZE + TEXT_PADDING_TOP, mPaint);
            countR++;
        }
        //左半部分
        int countL = 1;
        for (int i = getMeasuredWidth() / 2 - GAP_WITH + mDeltaX + mPreDeltaX; i > -0.5 * getMeasuredWidth(); i -= GAP_WITH) {
            Path indexPathL = new Path();
            indexPathL.moveTo(i, 0);
            if (i == getMeasuredWidth()/2) {
                mText = mCenterYear + countR + "";
                indexPathL.lineTo(i, INDEX_LINE_HEIGHT_SELECTED);
                mPaint.setTextSize(TEXT_SIZE_SELECTED);
                mPaint.setColor(Color.BLUE);
            } else {
                indexPathL.lineTo(i, INDEX_LINE_HEIGHT);
                mPaint.setTextSize(TEXT_SIZE);
                mPaint.setColor(Color.BLACK);
            }
            canvas.drawPath(indexPathL, mPaint);

            canvas.drawText(mCenterYear - countL + "", i, INDEX_LINE_HEIGHT + TEXT_SIZE + TEXT_PADDING_TOP, mPaint);
            countL++;
        }

        Path bottomPath = new Path();
        bottomPath.moveTo(0, INDEX_LINE_HEIGHT + TEXT_SIZE + TEXT_PADDING_TOP + TEXT_PADDING_BOTTOM);
        bottomPath.lineTo(getMeasuredWidth(), INDEX_LINE_HEIGHT + TEXT_SIZE + TEXT_PADDING_TOP + TEXT_PADDING_BOTTOM);
        canvas.drawPath(bottomPath, mPaint);

    }

    public String getText(){
        return mText;
    }
}
