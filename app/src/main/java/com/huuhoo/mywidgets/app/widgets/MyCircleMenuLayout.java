package com.huuhoo.mywidgets.app.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.huuhoo.mywidgets.app.R;

/**
 * 3/21/16  4:04 PM
 * Created by JustinZhang.
 */
public class MyCircleMenuLayout extends ViewGroup {

    private static final String TAG = MyCircleMenuLayout.class.getSimpleName();
    private static float RADIO_LAYOUT_PADDING = 1 / 12f;
    private static float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;
    private int mRadius;
    private int mDiameter;
    private int mChildWidth;
    private int mStartAngle = 0;

    private int mPadding;

    /**
     * 菜单项的文本
     */
    private String[] mItemTexts;
    /**
     * 菜单项的图标
     */
    private int[] mItemImgs;

    /**
     * 菜单的个数
     */
    private int mMenuItemCount;

    public MyCircleMenuLayout(Context context) {
        this(context, null);
    }

    public MyCircleMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCircleMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO: 3/21/16


    }


    /*****************************************************************************/
    /**
     * 设置菜单条目的图标和文本
     *
     * @param resIds
     */
    public void setMenuItemIconsAndTexts(int[] resIds, String[] texts) {
        mItemImgs = resIds;
        mItemTexts = texts;

        // 参数检查
        if (resIds == null && texts == null) {
            throw new IllegalArgumentException("菜单项文本和图片至少设置其一");
        }

        // 初始化mMenuCount
        mMenuItemCount = resIds == null ? texts.length : resIds.length;

        if (resIds != null && texts != null) {
            mMenuItemCount = Math.min(resIds.length, texts.length);
        }

        addMenuItems();

    }

    /**
     * 添加菜单项
     */
    private void addMenuItems() {
        LayoutInflater mInflater = LayoutInflater.from(getContext());

        /**
         * 根据用户设置的参数，初始化view
         */
        for (int i = 0; i < mMenuItemCount; i++) {
            final int j = i;
            View view = mInflater.inflate(R.layout.circle_menu_item, this,
                    false);
            ImageView iv = (ImageView) view
                    .findViewById(R.id.id_circle_menu_item_image);
            TextView tv = (TextView) view
                    .findViewById(R.id.id_circle_menu_item_text);

            if (iv != null) {
                iv.setVisibility(View.VISIBLE);
                iv.setImageResource(mItemImgs[i]);
                iv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        /*
                        if (mOnMenuItemClickListener != null)
                        {
                            mOnMenuItemClickListener.itemClick(v, j);
                        }
                        */

                    }
                });
            }
            if (tv != null) {
                tv.setVisibility(View.VISIBLE);
                tv.setText(mItemTexts[i]);
            }
            // 添加view到容器中
            addView(view);
        }
    }

    /*****************************************************************************/

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //   super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量,该控件多大,子控件多大

        int resWidth = 0;
        int resHeight = 0;

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {

            int suggestHeight = getSuggestedMinimumHeight();
            int suggestWidth = getSuggestedMinimumWidth();

            resWidth = suggestWidth == 0 ? getDefaultWidth() : suggestWidth;
            resHeight = suggestHeight == 0 ? getDefaultWidth() : suggestHeight;
        } else {
            resWidth = resHeight = Math.min(width, height);
        }

        setMeasuredDimension(resWidth, resHeight);
        int childCount = getChildCount();
        mDiameter = getMeasuredWidth();
        mRadius = mDiameter / 2;
        mChildWidth = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getId() == R.id.id_circle_menu_item_center) {

                int centerMeasureSpec = MeasureSpec.makeMeasureSpec((int) (11 / 24.0f * mDiameter), MeasureSpec.EXACTLY);
                child.measure(centerMeasureSpec, centerMeasureSpec);
            } else {
                //    Log.e(TAG,"child width"+mChildWidth);
                int measureSpec = MeasureSpec.makeMeasureSpec(mChildWidth, MeasureSpec.EXACTLY);
                child.measure(measureSpec, measureSpec);
            }

        }
        mPadding = (int) (mDiameter * RADIO_LAYOUT_PADDING);
    }

    private int getDefaultWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //排列子控件
        int childCount = getChildCount();
        int left = 0, top = 0;
        int increaseDegree = 360 / (childCount - 1);
        int length = (int) (mRadius - mPadding - mChildWidth / 2);
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getId() == R.id.id_circle_menu_item_center) {
                continue;
            } else if (child.getVisibility() == View.GONE) {
                continue;
            } else {
                mStartAngle %= 360;
                left = mRadius + (int) (length * Math.cos(Math.toRadians(mStartAngle))) - (int) (mChildWidth / 2.0f);
                top = mRadius + (int) (length * Math.sin(Math.toRadians(mStartAngle))) - (int) (mChildWidth / 2.0f);
                child.layout(left, top, left + mChildWidth, top + mChildWidth);
                mStartAngle += increaseDegree;
            }
            //   Log.e(TAG,"top:"+top+" left:"+left);
        }


        View centerView = findViewById(R.id.id_circle_menu_item_center);
        if (centerView != null) {
            left = (int) (mRadius - 11 / 24f * mDiameter / 2.0f);
            top = left;
            centerView.layout(left, top, (int) (left + 11 / 24f * mDiameter), (int) (top + 11 / 24f * mDiameter));
        }

    }


    private float mLastX;
    private float mLastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        float x = ev.getX();
        float y = ev.getY();
        long downTime = System.currentTimeMillis();

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                float end = getAngle(x, y);
                float start = getAngle(mLastX, mLastY);

                Log.e(TAG,"start angle:"+start+ "   end angle:"+end);

                int quadrant = getQuadrant(x, y);
                if (quadrant == 1 || quadrant == 4) {
                    mStartAngle += (end - start);
                    // mTmpAngle += end- start;
                } else {
                    mStartAngle += (start - end);
                    //  mTmpAngle += start-end;
                }
                requestLayout();
                mLastX = x;
                mLastY = y;

                break;
            case MotionEvent.ACTION_UP:


                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    private float getAngle(float touchX, float touchY) {
        float x = touchX - mRadius;
        float y = touchY - mRadius;
        return (float) Math.toDegrees(Math.asin(y / Math.hypot(x, y)));
    }

    private int getQuadrant(float x, float y) {
        int tmpX = (int) (x - mRadius );
        int tmpY = (int) (y - mRadius );
        if (tmpX >= 0) {
            return tmpY >= 0 ? 4 : 1;
        } else {
            return tmpY >= 0 ? 3 : 2;
        }

    }


}
