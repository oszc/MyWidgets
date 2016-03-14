package com.huuhoo.mywidgets.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import com.huuhoo.mywidgets.app.utils.ImageUtil;
import com.huuhoo.mywidgets.app.utils.Screen;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    int res = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  Point p = ImageUtil.calculateImageSize(new Point(100,100),new Point(200,100));
      //  System.out.println("width:"+p.x + " height:"+p.y);
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        res = R.drawable.hello;

        BitmapFactory.decodeResource(getResources(), res,options);

        Point imageSize = new Point(options.outWidth,options.outHeight);


        Point targetSize= ImageUtil.calculateImageSize(new Point(Screen.getWidth(this), Screen.getHeight(this)), imageSize);

        int sampleSize = ImageUtil.calculateSampleSize(this,res,targetSize);

        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        Log.e(TAG,"sample size --> "+ sampleSize);

        ImageView iv = (ImageView) findViewById(R.id.iv);
        iv.setImageBitmap(BitmapFactory.decodeResource(getResources(),res,options));


    }
}
