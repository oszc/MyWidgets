package com.huuhoo.mywidgets.app.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static android.R.attr.orientation;

/**
 * 4/20/16  3:16 PM
 * Created by JustinZhang.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private static final String TAG = DividerItemDecoration.class.getSimpleName();
    private Drawable mDivider;
    private int mOrientation;

    private Drawable mLeftDivider;
    private Drawable mTopDivider;
    private Drawable mRightDivider;
    private Drawable mBottomDivider;

    public DividerItemDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    private void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation must be HORIZONTAL_LIST || VERTICAL_LIST");
        }
        this.mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        /*
        if (mOrientation == VERTICAL_LIST) {
            drawVertically(c, parent, state);
        } else {
            drawHorizontally(c, parent, state);
        }
        */
        drawLeft(c, parent, state);
        drawTop(c, parent, state);
        drawRight(c, parent, state);
        drawBottom(c, parent, state);




    }

    private int divideHeight = 15;

    private void drawLeft(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int left = parent.getPaddingLeft()-divideHeight;
        int right =  parent.getPaddingLeft()  ;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop()-divideHeight ;
            int bottom = child.getBottom()+divideHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }


    }

    private void drawTop(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int left = parent.getPaddingLeft();
        int right = parent.getWidth()-parent.getPaddingRight()   ;

        int childCount = parent.getChildCount();
    //    for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(0);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop()-divideHeight ;
            int bottom = child.getTop();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
     //   }


    }
    private void drawRight(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int left = parent.getWidth() - parent.getPaddingRight() ;
        int right =left + divideHeight;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop()-divideHeight;
            int bottom = child.getBottom()+divideHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }


    }

    private void drawBottom(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int left = parent.getPaddingLeft();
        int right = parent.getWidth()-parent.getPaddingRight() ;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() ;
            int bottom = child.getBottom()+divideHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }


    }

    private void drawVertically(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        //int left = 0;
        //int right = parent.getWidth();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + 15;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontally(Canvas c, RecyclerView parent, RecyclerView.State state) {

        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }

    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        /*
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, 15);
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
        */
        outRect.set(divideHeight,divideHeight,divideHeight,divideHeight);

       // outRect.set(-15, -15, -15, -15);

    }


}
