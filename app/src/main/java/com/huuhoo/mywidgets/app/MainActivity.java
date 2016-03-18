package com.huuhoo.mywidgets.app;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import com.huuhoo.mywidgets.app.utils.ImageUtil;
import com.huuhoo.mywidgets.app.utils.MyRotateAnimation;
import com.huuhoo.mywidgets.app.utils.RangeInterpolator;
import com.huuhoo.mywidgets.app.utils.Screen;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView iv;
    private EditText mEt;
    int res = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  RotateAnimation anim;
        //  Point p = ImageUtil.calculateImageSize(new Point(100,100),new Point(200,100));
        //  System.out.println("width:"+p.x + " height:"+p.y);
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        res = R.drawable.small;

        BitmapFactory.decodeResource(getResources(), res, options);

        Point imageSize = new Point(options.outWidth, options.outHeight);

        Point targetSize = ImageUtil.calculateImageSize(new Point((int) Screen.convertDpToPixel(100, this), (int) Screen.convertDpToPixel(100, this)), imageSize);

        int sampleSize = ImageUtil.calculateSampleSize(this, res, targetSize);

        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        Log.e(TAG, "sample size --> " + sampleSize);

        iv = (ImageView) findViewById(R.id.iv);
        iv.setImageBitmap(BitmapFactory.decodeResource(getResources(), res, options));

        mEt = (EditText) findViewById(R.id.et_time);



        // iv.setAnimation(anim);
        //  anim.start();


    }
    public void click(View view) {

        int time = Integer.parseInt(mEt.getText().toString());
        MyRotateAnimation anim = new MyRotateAnimation(iv.getWidth() / 2, iv.getHeight() / 2);
        anim.setInterpolator(new RangeInterpolator());
        anim.setDuration(time);
        //   anim.setRepeatCount(Integer.MAX_VALUE);
        //  anim.setRepeatMode(Animation.RESTART);
        iv.startAnimation(anim);


    }

}
