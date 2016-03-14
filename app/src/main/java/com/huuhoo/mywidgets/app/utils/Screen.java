package com.huuhoo.mywidgets.app.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;

/**
 * 3/14/16  2:28 PM
 * Created by JustinZhang.
 */
public class Screen {

    public static int getWidth(Activity context){
        Display display = context.getWindowManager().
                getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getHeight(Activity context){

        Display display = context.getWindowManager().
                getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }




}
