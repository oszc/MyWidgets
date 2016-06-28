package com.huuhoo.mywidgets.app.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huuhoo.mywidgets.app.R;
import com.huuhoo.mywidgets.app.utils.Screen;

/**
 * 6/21/16  5:17 PM
 * Created by JustinZhang.
 */
public class DragLayout extends RelativeLayout{

    private static final String TAG = DragLayout.class.getSimpleName();
    private final ViewDragHelper mDragHelper;

    private TextView mTvHeader;
    private TextView mTvContent;
    private LayoutInflater mLayoutInflater;

    private int mScreenWidth = 0;
    private int mScreenHeight = 0;

    private Point mHeaderSize = new Point();
    private DrawerLayout dl;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutInflater.inflate(R.layout.layout_drag, this, true);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());

        mTvHeader = (TextView) findViewById(R.id.tv_header);
        mTvContent = (TextView) findViewById(R.id.tv_content);

        mScreenWidth = Screen.getWidth(context);
        mScreenHeight = Screen.getHeight(context);

        RelativeLayout.LayoutParams headerParams = new LayoutParams(mScreenWidth, mScreenHeight / 3);
        mTvHeader.setLayoutParams(headerParams);
        mTvHeader.setBackgroundColor(Color.YELLOW);

        mTvHeader.post(new Runnable() {
            @Override
            public void run() {
                mHeaderSize.x = mTvHeader.getWidth();
                mHeaderSize.y =mTvHeader.getHeight();
            }
        });
        RelativeLayout.LayoutParams contentParams = new LayoutParams(mScreenWidth, mScreenHeight * 2 / 3);
      //  contentParams.addRule(BELOW, R.id.tv_header);
        mTvContent.setLayoutParams(contentParams);
        mTvContent.setBackgroundColor(Color.BLUE);
        mTvContent.setVisibility(View.GONE);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent child count:" + getChildCount() );
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            Log.e(TAG, "onInterceptTouchEvent: ACTION_CANCEL    ACTION_UP");
            mDragHelper.cancel();
            return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragHelper.processTouchEvent(ev);
        return true;
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.e(TAG, "tryCaptureView: "+ (child==mTvHeader )+"   "+child );
            return child == mTvHeader;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        /*
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.e(TAG, "clampViewPositionHorizontal:");
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - mTvHeader.getWidth();
            return Math.min(Math.max(left, leftBound), rightBound); //new left
        }
         */

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - mTvHeader.getHeight();
            int newTop = Math.min(Math.max(top, topBound), bottomBound); //new top
            float percentage = (newTop - topBound) *1.0f / (bottomBound - topBound);


            mTvHeader.setPivotX(child.getWidth());
            mTvHeader.setPivotY(0);
            mTvHeader.setScaleX(0.6f*(1-percentage));
            mTvHeader.setScaleY(0.6f*(1-percentage));

 //           ViewGroup.LayoutParams layoutParams = mTvHeader.getLayoutParams();
 //           layoutParams.width = (int)(mHeaderSize.x * (1-percentage));
            //          layoutParams.height = (int)(mHeaderSize.y*(1-percentage));

       //     Log.e(TAG, "clampViewPositionVertical: new top --> " + newTop+ " topBound-->"+topBound+"   bottomBound-->"+bottomBound+ "   percentage:"+percentage);
            return newTop;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
          //  requestLayout();
            if(releasedChild == mTvHeader) {
             //   releasedChild.requestLayout();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) releasedChild.getLayoutParams();
                int w = layoutParams.width;
                int h = layoutParams.height;
                int lm = layoutParams.leftMargin;
                int rm = layoutParams.rightMargin;
                Log.e(TAG, "onViewReleased:" + w + " " + h + " left margin:" + lm + " right margin:" + rm);
            }
            super.onViewReleased(releasedChild, xvel, yvel);
        }
    }
}
