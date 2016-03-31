package com.huuhoo.mywidgets.app.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 3/31/16  5:43 PM
 * Created by JustinZhang.
 */
public class PaintTestView extends View{
    private Paint mRedPaint;
    private Paint mGreenPaint;
    public PaintTestView(Context context) {
        this(context,null);
    }

    public PaintTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        mRedPaint = new Paint();
        mRedPaint.setAntiAlias(true);
        mRedPaint.setColor(Color.RED);
        mRedPaint.setStyle(Paint.Style.STROKE);

        mGreenPaint = new Paint();
        mGreenPaint.setAntiAlias(true);
        mGreenPaint.setColor(Color.GREEN);
        mGreenPaint.setStyle(Paint.Style.STROKE);
    }

    int angle = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        RectF rect1 = new RectF(0,0,100,100);
        canvas.drawArc(rect1,90,angle++%360,false,mRedPaint);
        canvas.drawRect(rect1,mGreenPaint);
        invalidate();

    }
}
