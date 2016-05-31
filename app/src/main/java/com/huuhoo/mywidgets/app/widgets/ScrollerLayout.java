package com.huuhoo.mywidgets.app.widgets;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import javax.security.auth.login.LoginException;

/**
 * 4/1/16  5:43 PM
 * Created by JustinZhang.
 */
public class ScrollerLayout extends ViewGroup{


    private static final String TAG = ScrollerLayout.class.getSimpleName();
    private Scroller mScroller;
    private int mTouchslop;
    private int mLeftBound = 0; //左边界
    private int mRightBound = 0;//右边界
    private int mChildWidth = 0;
    private int mChildHeight = 0;

    public ScrollerLayout(Context context) {
        this(context,null);
    }

    public ScrollerLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext(),null);
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchslop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed){
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                mChildWidth = child.getMeasuredWidth();
                mChildHeight = child.getMeasuredHeight();
                child.layout(i*mChildWidth,0,(i+1)*mChildWidth,mChildHeight);
                if(i==0){
                    mLeftBound = 0;
                }
                if( i == (childCount -1 )){

                    mRightBound = i * mChildWidth;

                }
            }
            Log.e(TAG, "onLayout --> right bound:" + mRightBound);
        }
    }




    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }




    float mLastX = 0;
    float mLastY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float curX = event.getX();
        float curY = event.getY();

        Log.e(TAG, "onTouchEvent");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = curX;
                mLastY = curY;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = mLastX - curX;
                Log.e(TAG, "onTouchEvent dx-->"+dx);
                mLastX = curX;

                /*
                if(getChildAt(0).getLeft()+dx < 0 ){
                    return false;
                }
                */


                int scrollX = getScrollX();
                Log.e(TAG, "onTouchEvent scrollX:"+scrollX);
                Log.e(TAG, "onTouchEvent first child left:" + getChildAt(0).getLeft());
                Log.e(TAG, "onTouchEvent last child left:" + getChildAt(getChildCount()-1).getLeft());
                if(scrollX+dx < mLeftBound || scrollX+dx > mRightBound){
                    return false;
                }

                scrollBy((int)dx,0);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                float deltaX = mLastX - curX;
                int n = (getScrollX() / mChildWidth + 1);
                int left = n * mChildWidth;

                Log.e(TAG, "onTouchEvent n --> "+n+"   left --> "+ left+"   childwidth:"+mChildWidth+"   getScrollX:"+getScrollX());

                mScroller.startScroll(getScrollX(),0,left-getScrollX(),0);
                // mScroller.startScroll(getScrollX(),0,(int)deltaX,0);
                invalidate();


                break;
        }
        return true;
    }
}
