package com.huuhoo.mywidgets.app.widgets;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.huuhoo.mywidgets.app.R;

/**
 * 3/25/16  2:00 PM
 * Created by JustinZhang.
 */
public class LuckyPanView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private static final String TAG = LuckyPanView.class.getSimpleName();
    private SurfaceHolder mSurfaceHolder;
    private Thread t;
    private boolean isRunning;


    /**
     * 抽奖的文字
     */
    private String[] mStrs = new String[]{"单反相机", "IPAD", "恭喜发财", "IPHONE",
            "妹子一只", "恭喜发财"};
    /**
     * 每个盘块的颜色
     */
    private int[] mColors = new int[]{0xFFFFC300, 0xFFF17E01, 0xFFFFC300,
            0xFFF17E01, 0xFFFFC300, 0xFFF17E01};
    /**
     * 与文字对应的图片
     */
    private int[] mImgs = new int[]{R.drawable.danfan, R.drawable.ipad,
            R.drawable.f040, R.drawable.iphone, R.drawable.meizi,
            R.drawable.f040};

    /**
     * 与文字对应图片的bitmap数组
     */
    private Bitmap[] mImgsBitmap;
    /**
     * 盘块的个数
     */
    private int mItemCount = 6;

    /**
     * 绘制盘块的范围
     */
    private RectF mRange = new RectF();
    /**
     * 圆的直径
     */
    private int mDiameter;

    private int mRadius;
    /**
     * 绘制盘快的画笔
     */
    private Paint mArcPaint;

    /**
     * 绘制文字的画笔
     */
    private Paint mTextPaint;

    /**
     * 滚动的速度
     */
    private double mSpeed = 1;
    private volatile float mStartAngle = 0;
    /**
     * 是否点击了停止
     */
    private boolean isShouldEnd;

    /**
     * 控件的中心位置
     */
    private int mCenter;
    /**
     * 控件的padding，这里我们认为4个padding的值一致，以paddingleft为标准
     */
    private int mPadding;

    /**
     * 背景图的bitmap
     */
    private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(),
            R.drawable.bg2);
    /**
     * 文字的大小
     */
    private float mTextSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());


    public LuckyPanView(Context context) {
        this(context, null);
    }

    public LuckyPanView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckyPanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        // 初始化绘制圆弧的画笔
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);

        // 初始化绘制文字的画笔
        mTextPaint = new Paint();
        mTextPaint.setColor(0xFFffffff);
        mTextPaint.setTextSize(mTextSize);

        // 圆弧的绘制范围
        mRange = new RectF(getPaddingLeft(), getPaddingLeft(), mDiameter
                + getPaddingLeft(), mDiameter + getPaddingLeft());

        // 初始化图片
        mImgsBitmap = new Bitmap[mItemCount];
        for (int i = 0; i < mItemCount; i++) {
            mImgsBitmap[i] = BitmapFactory.decodeResource(getResources(),
                    mImgs[i]);
        }


        isRunning = true;
        t = new Thread(this);
        t.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        isRunning = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        Log.e(TAG, "onMeasure: width " + width);

        mDiameter = width - getPaddingLeft() - getPaddingRight();

        mRadius = (int) Math.floor(mDiameter / 2.0f);
        mCenter = mDiameter / 2;
        mPadding = getPaddingLeft();
        setMeasuredDimension(width, width);
    }

    @Override
    public void run() {
        while (isRunning) {
            draw();
        }
    }

    private Canvas mCanvas;

    private void draw() {

        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            float sweepAngle = 360.0f / mItemCount;
            float tempAngle = (mStartAngle += (mSpeed));
            //tempAngle%=360;
            if (mCanvas != null) {
                drawBackGround();
                //   drawPanel(sweepAngle);
                for (int i = 0; i < mItemCount; i++) {
                    mArcPaint.setColor(mColors[i]);
                    mCanvas.drawArc(mRange, tempAngle, sweepAngle, true, mArcPaint);
                    float top = 0, left = 0;
                    Bitmap bmp = mImgsBitmap[i];
                    float angle = tempAngle + sweepAngle / 2.0f;
                    top = (float) (mRadius + mRadius / 2.0f * Math.cos(Math.toRadians(angle))) - bmp.getHeight() / 2.0f;
                    left = (float) (mRadius + mRadius / 2.0f * Math.sin(Math.toRadians(angle))) - bmp.getWidth() / 2.0f;
                    mCanvas.drawBitmap(mImgsBitmap[i], top, left, mTextPaint);
                    tempAngle += sweepAngle;
                }
            }
        } catch (Exception e) {

        } finally {

            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void drawPanel(float sweepAngle) {

    }

    private void drawBackGround() {

    }
}
