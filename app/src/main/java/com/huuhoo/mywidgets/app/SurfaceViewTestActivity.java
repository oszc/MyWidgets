package com.huuhoo.mywidgets.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * 7/26/16  4:23 PM
 * Created by JustinZhang.
 */
public class SurfaceViewTestActivity extends Activity{
    private static class MySurfaceView extends SurfaceView{

        public MySurfaceView(Context context) {
            super(context);
        }

        public MySurfaceView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
