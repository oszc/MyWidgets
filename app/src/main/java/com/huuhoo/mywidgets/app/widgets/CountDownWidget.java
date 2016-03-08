package com.huuhoo.mywidgets.app.widgets;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * 3/8/16  9:34 PM
 * Created by JustinZhang.
 */
public class CountDownWidget extends TextView{

    private static final String TAG = CountDownWidget.class.getSimpleName();
    private Rect mRect;
    private Paint mPaint;


    public CountDownWidget(Context context) {
        this(context,null);
    }

    public CountDownWidget(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CountDownWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        Log.e(TAG,"measuredWidth:"+measuredWidth + " measuredHeight:"+measuredHeight);
        setMeasuredDimension(measuredWidth+100,measuredHeight);
    }
}
