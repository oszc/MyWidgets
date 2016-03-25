package com.huuhoo.mywidgets.app.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * 3/24/16  11:10 AM
 * Created by JustinZhang.
 */
public class HorizontalProgressBarWithNumber extends ProgressBar{
    public HorizontalProgressBarWithNumber(Context context) {
        this(context,null);
    }

    public HorizontalProgressBarWithNumber(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalProgressBarWithNumber(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);




    }


}
