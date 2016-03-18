package com.huuhoo.mywidgets.app.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * 3/14/16  3:08 PM
 * Created by JustinZhang.
 */
public class ImageUtil {

    public static Point calculateImageSize(Point canvas, Point image){

        if(canvas == null || image == null){
            return null;
        }

        if(image.x < canvas.x && image.y < canvas.y){
            return image;
        }

        Point imageSize = new Point();
        if(image.y/(float)canvas.y >= image.x/(float)canvas.x){
            imageSize.y = canvas.y;
            imageSize.x = (int)(image.x * canvas.y /(float) image.y);
        }else{
            imageSize.x = canvas.x;
            imageSize.y =(int) (image.y * canvas.x /(float)image.x);
        }

        return imageSize;
    }

    public static int calculateSampleSize(String bmpPath , Point target){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(bmpPath, options);
        int height = options.outHeight;
        int width = options.outWidth;

        int looptime = 1;
        while (height > target.y || width > target.x){

            options.inSampleSize = (int)Math.pow(2,looptime++);
            BitmapFactory.decodeFile(bmpPath,options);
            height = options.outHeight;
            width = options.outWidth;

        }
        return options.inSampleSize;


    }

    public static int calculateSampleSize(Context context ,int bmpPath , Point target){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
         BitmapFactory.decodeResource(context.getResources(),bmpPath, options);
        int height =options.outHeight;
        int width = options.outWidth;
        int looptime = 1;
        while (height > target.y || width > target.x){
            options.inSampleSize = (int)Math.pow(2,looptime++);
            BitmapFactory.decodeResource(context.getResources(),bmpPath,options);
            height = options.outHeight;
            width = options.outWidth;
        }
        if(looptime > 2){
            return (int)Math.pow(2,looptime-2);
        }
        return 1;
    }


}
