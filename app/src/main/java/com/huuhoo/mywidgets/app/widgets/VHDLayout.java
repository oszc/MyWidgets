package com.huuhoo.mywidgets.app.widgets;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.huuhoo.mywidgets.app.R;

/**
 * 4/1/16  2:19 PM
 * Created by JustinZhang.
 */
public class VHDLayout extends LinearLayout{
    private ViewDragHelper mViewDragHelper;

    private View mDragView, mAutoBackView, mEdgeTrackerView;

    private PointF mAutoBackPoint;
    public VHDLayout(Context context) {
        this(context,null);
    }

    public VHDLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VHDLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        mAutoBackPoint= new PointF();

        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mDragView||child == mAutoBackView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {

                int leftBound = getPaddingLeft();
                int rightBound = getWidth() - child.getWidth() - leftBound-getPaddingRight();
                int newLeft = Math.min(rightBound , Math.max(left,leftBound));

                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {

                int topBound = getPaddingTop();
                int bottomBound = getHeight()-child.getHeight()-getPaddingTop()-getPaddingBottom();
                int newTop = Math.min(bottomBound,Math.max(top,topBound));
                return newTop;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if(releasedChild == mAutoBackView ){
                    mViewDragHelper.settleCapturedViewAt((int)mAutoBackPoint.x,(int)mAutoBackPoint.y);
                    invalidate();
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                mViewDragHelper.captureChildView(mEdgeTrackerView,pointerId);
            }
        });

        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if(mViewDragHelper.continueSettling(true)){
            invalidate();
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(mAutoBackView!=null) {
            mAutoBackPoint.x = mAutoBackView.getLeft();
            mAutoBackPoint.y = mAutoBackView.getTop();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
}
