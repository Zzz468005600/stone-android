package zhulei.com.stone.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zhulei on 16/6/19.
 */
public class BageTextView extends TextView {
    public BageTextView(Context context) {
        super(context);
    }

    public BageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, Math.min(getMeasuredHeight(), getMeasuredWidth())/2, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(getTextSize()*4/5);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.WHITE);
        canvas.drawText(getText().toString(), getMeasuredWidth()/2, getMeasuredHeight()/2 + getTextSize() *4/13, paint);
    }
}
