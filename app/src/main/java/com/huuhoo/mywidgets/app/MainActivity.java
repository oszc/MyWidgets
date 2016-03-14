package com.huuhoo.mywidgets.app;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import com.huuhoo.mywidgets.app.utils.ImageUtil;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Point p = ImageUtil.calculateImageSize(new Point(100,100),new Point(200,100));
        System.out.println("width:"+p.x + " height:"+p.y);
    }
}
