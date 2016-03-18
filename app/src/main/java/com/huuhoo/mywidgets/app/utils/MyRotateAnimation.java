package com.huuhoo.mywidgets.app.utils;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 3/15/16  1:56 PM
 * Created by JustinZhang.
 */
public class MyRotateAnimation extends Animation{

    private Camera camera;

    private int mPivotX;
    private int mPivotY;

    public MyRotateAnimation(int pivotX,int pivotY) {
        camera = new Camera();
        mPivotX = pivotX;
        mPivotY = pivotY;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        System.out.println("interpolatedTime:"+interpolatedTime);
        t.setTransformationType(Transformation.TYPE_MATRIX);
        Matrix matrix = t.getMatrix();
        camera.save();
        camera.rotate(.0f,interpolatedTime,.0f);
        camera.setLocation(0,0,-mPivotY);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-mPivotX,-mPivotY);
        matrix.postTranslate(mPivotX,mPivotY);
    //    matrix.preTranslate(,-100f);
    //    matrix.postTranslate(100f,100f);

    }
}
