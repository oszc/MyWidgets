package com.huuhoo.mywidgets.app.utils;

import android.animation.TimeInterpolator;
import android.view.animation.Interpolator;

/**
 * 3/15/16  5:29 PM
 * Created by JustinZhang.
 */
public class RangeInterpolator implements Interpolator{

    @Override
    public float getInterpolation(float input) {
        //0..2pi
       // System.out.println("input:"+input);
        /*
        if(input < 0.25){

        }else if(input<0.75){
            input = -4*input +2;
        }else if(input <= 1){
            input = -8/3*input+1;
        }
        */

        float y= (float)Math.sin(2*Math.PI*input);
        System.out.println("input:"+input+" y:"+y);
       // input = 2*input -1;
        return y*90f;
    }
}
