package com.huuhoo.mywidgets.app.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.huuhoo.mywidgets.app.R;

/**
 * 3/18/16  2:47 PM
 * Created by JustinZhang.
 */
public class CircleMenuLayout extends ViewGroup{

    private interface OnItemClickListener{
        void itemCenterClick(View view);
        void itemClick(View view ,int index);

    }

    private OnItemClickListener mOnMenuItemClickListener;

    private int mRadius;
    /**
     * 该容器内child item的默认尺寸
     */
    private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;
    /**
     * 菜单的中心child的默认尺寸
     */
    private float RADIO_DEFAULT_CENTERITEM_DIMENSION = 1 / 3f;
    /**
     * 该容器的内边距,无视padding属性，如需边距请用该变量
     */
    private static final float RADIO_PADDING_LAYOUT = 1 / 12f;

    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private static final int FLINGABLE_VALUE = 300;

    /**
     * 如果移动角度达到该值，则屏蔽点击
     */
    private static final int NOCLICK_VALUE = 3;

    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private int mFlingableValue = FLINGABLE_VALUE;
    /**
     * 该容器的内边距,无视padding属性，如需边距请用该变量
     */
    private float mPadding;

    /**
     * 布局时的开始角度
     */
    private double mStartAngle = 0;
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

    /**
     * 检测按下到抬起时旋转的角度
     */
    private float mTmpAngle;
    /**
     * 检测按下到抬起时使用的时间
     */
    private long mDownTime;

    /**
     * 判断是否正在自动滚动
     */
    private boolean isFling;

    public CircleMenuLayout(Context context) {
        this(context,null);
    }

    public CircleMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPadding(0,0,0,0);


    }

    /**
     * 设置菜单条目的图标和文本
     *
     * @param resIds
     */
    public void setMenuItemIconsAndTexts(int[] resIds, String[] texts)
    {
        mItemImgs = resIds;
        mItemTexts = texts;

        // 参数检查
        if (resIds == null && texts == null)
        {
            throw new IllegalArgumentException("菜单项文本和图片至少设置其一");
        }

        // 初始化mMenuCount
        mMenuItemCount = resIds == null ? texts.length : resIds.length;

        if (resIds != null && texts != null)
        {
            mMenuItemCount = Math.min(resIds.length, texts.length);
        }

        addMenuItems();

    }

    /**
     * 添加菜单项
     */
    private void addMenuItems()
    {
        LayoutInflater mInflater = LayoutInflater.from(getContext());

        /**
         * 根据用户设置的参数，初始化view
         */
        for (int i = 0; i < mMenuItemCount; i++)
        {
            final int j = i;
            View view = mInflater.inflate(R.layout.circle_menu_item, this,
                    false);
            ImageView iv = (ImageView) view
                    .findViewById(R.id.id_circle_menu_item_image);
            TextView tv = (TextView) view
                    .findViewById(R.id.id_circle_menu_item_text);

            if (iv != null)
            {
                iv.setVisibility(View.VISIBLE);
                iv.setImageResource(mItemImgs[i]);
                iv.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {


                        if (mOnMenuItemClickListener != null)
                        {
                            mOnMenuItemClickListener.itemClick(v, j);
                        }

                    }
                });
            }
            if (tv != null)
            {
                tv.setVisibility(View.VISIBLE);
                tv.setText(mItemTexts[i]);
            }
            // 添加view到容器中
            addView(view);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int resWidth = 0 ;
        int resHeight = 0;

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY){

            resWidth = getSuggestedMinimumWidth();
            resWidth = resWidth == 0 ? getDefaultWidth():resWidth;

            resHeight = getSuggestedMinimumHeight();
            resHeight = resHeight == 0? getDefaultWidth():resHeight;

        }else{
            resWidth = resHeight = Math.min(width,height);
        }

        setMeasuredDimension(resWidth,resHeight);

        mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());

        final int count = getChildCount();

        int childSize = (int)(mRadius * RADIO_DEFAULT_CHILD_DIMENSION);

        int childMode = MeasureSpec.EXACTLY;

        for (int i = 0; i < count; i++) {

            final View child = getChildAt(i);

            if(child.getVisibility() == GONE){
                continue;
            }

            int makeMeasureSpec = -1 ;

            if(child.getId() == R.id.id_circle_menu_item_center){
                makeMeasureSpec = MeasureSpec.makeMeasureSpec((int)(mRadius * RADIO_DEFAULT_CENTERITEM_DIMENSION),childMode);
            }else{
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,childMode);
            }

            child.measure(makeMeasureSpec, makeMeasureSpec);
        }

        mPadding = RADIO_PADDING_LAYOUT * mRadius;
    }

    private int getDefaultWidth() {
        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int layoutRadius = mRadius;

        final int childCount = getChildCount();

        int left, top;
        int cWidth = (int) (layoutRadius * RADIO_DEFAULT_CHILD_DIMENSION);

        float angleDelay = 360 /(childCount - 1);

        for (int i = 0; i < childCount; i++) {

            final View child  = getChildAt(i);

            if(child.getId() == R.id.id_circle_menu_item_center){
                continue;
            }

            if(child.getVisibility() == GONE){
                continue;
            }

            mStartAngle %= 360;

            float tmp = layoutRadius / 2f - cWidth /2 -mPadding;

            left = layoutRadius / 2 + (int)Math.round(tmp*Math.cos(Math.toRadians(mStartAngle))-1/2f*cWidth);

            top = layoutRadius / 2 + (int)Math.round(tmp*Math.sin(Math.toRadians(mStartAngle))-1/2f*cWidth);

            child.layout(left,top,left+cWidth,top+cWidth);

            mStartAngle += angleDelay;

        }


        /*
        View cView = findViewById(R.id.id_circle_menu_item_center);
        if (cView != null)
        {

            cView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    if (mOnMenuItemClickListener != null)
                    {
                        mOnMenuItemClickListener.itemCenterClick(v);
                    }
                }
            });

            // 设置center item位置
            int cl = layoutRadius / 2 - cView.getMeasuredWidth() / 2;
            int cr = cl + cView.getMeasuredWidth();
            cView.layout(cl, cl, cr, cr);
        }
        */
    }


    private float mLastX;
    private float mLastY;

    private AutoFlingRunnable mFlingRunnable;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        float x = ev.getX();
        float y = ev.getY();

        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:

                mLastX = x;
                mLastY = y;

                mDownTime = System.currentTimeMillis();
                mTmpAngle = 0;

                if(isFling){
                    removeCallbacks(mFlingRunnable);
                    isFling = false;
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float start = getAngle(mLastX,mLastY);

                float end = getAngle(x,y);

                int quadrant = getQuadrant(x,y);
                if(quadrant == 1 || quadrant == 4){
                    mStartAngle +=end -start;
                    mTmpAngle += end- start;
                }else{
                    mStartAngle += start -end;
                    mTmpAngle += start-end;
                }
                requestLayout();
                mLastY = y;
                mLastX = x;
                break;

            case MotionEvent.ACTION_UP:

                // 计算，每秒移动的角度
                float anglePerSecond = mTmpAngle * 1000
                        / (System.currentTimeMillis() - mDownTime);

                // Log.e("TAG", anglePrMillionSecond + " , mTmpAngel = " +
                // mTmpAngle);

                // 如果达到该值认为是快速移动
                if (Math.abs(anglePerSecond) > mFlingableValue && !isFling)
                {
                    // post一个任务，去自动滚动
                    post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));

                    return true;
                }

                // 如果当前旋转角度超过NOCLICK_VALUE屏蔽点击
                if (Math.abs(mTmpAngle) > NOCLICK_VALUE)
                {
                    return true;
                }

                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 根据触摸的位置，计算角度
     *
     * @param xTouch
     * @param yTouch
     * @return
     */
    private float getAngle(float xTouch, float yTouch)
    {
        double x = xTouch - (mRadius / 2d);
        double y = yTouch - (mRadius / 2d);
        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
    }


    private int getQuadrant(float x, float y)
    {
        int tmpX = (int) (x - mRadius / 2);
        int tmpY = (int) (y - mRadius / 2);
        if (tmpX >= 0)
        {
            return tmpY >= 0 ? 4 : 1;
        } else
        {
            return tmpY >= 0 ? 3 : 2;
        }

    }

    private class AutoFlingRunnable implements Runnable
    {

        private float angelPerSecond;

        public AutoFlingRunnable(float velocity)
        {
            this.angelPerSecond = velocity;
        }

        public void run()
        {
            // 如果小于20,则停止
            if ((int) Math.abs(angelPerSecond) <13)
            {
                isFling = false;
                return;
            }
            isFling = true;
            // 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
          //  mStartAngle += (angelPerSecond / 16);
            mStartAngle += angelPerSecond/30;
            // 逐渐减小这个值
            angelPerSecond /= 1.0666F;
            postDelayed(this, 16);
            // 重新布局
            requestLayout();
        }
    }
}
