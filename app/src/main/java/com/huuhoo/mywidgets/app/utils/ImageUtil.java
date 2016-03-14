package com.huuhoo.mywidgets.app.utils;

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
}
