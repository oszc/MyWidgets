package com.huuhoo.mywidgets.app.widgets;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import com.huuhoo.mywidgets.app.R;

/**
 * 3/25/16  2:00 PM
 * Created by JustinZhang.
 */
public class LuckyPanView extends SurfaceView implements SurfaceHolder.Callback, Runnable, View.OnTouchListener {

    private static final String TAG = LuckyPanView.class.getSimpleName();
    private SurfaceHolder mSurfaceHolder;
    private Thread t;
    private boolean isRunning;
    private ImageView mIv;


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
    private double mSpeed = 0;
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

    private Bitmap mArrowBitmap;
    private int mArrowWidth;
    private int mArrowHeight;

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

        mArrowWidth = mRadius / 4;
        mArrowHeight = mArrowWidth * 2;
        mIv = new ImageView(getContext());
        mIv.layout(0, 0, mArrowWidth, mArrowHeight);
        mArrowBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
        mIv.setImageBitmap(mArrowBitmap);
        setOnTouchListener(this);

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

    long start = 0;
    long end = 0;

    @Override
    public void run() {
        while (isRunning) {
            start = System.currentTimeMillis();
            draw();
            end = System.currentTimeMillis();

            if (end - start < 16) {
                try {
                    Thread.sleep(16 - (end - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Canvas mCanvas;

    private void draw() {

        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            float sweepAngle = 360.0f / mItemCount;
            float tempAngle = (mStartAngle += (mSpeed));
            int imageWidth = mDiameter / 8;
            //tempAngle%=360;

            if (mCanvas != null) {
                drawBackGround();
                //   drawPanel(sweepAngle);
                for (int i = 0; i < mItemCount; i++) {
                    mArcPaint.setColor(mColors[i]);
                    mCanvas.drawArc(mRange, tempAngle, sweepAngle, true, mArcPaint);
                    drawItem(tempAngle + sweepAngle / 2.0f, imageWidth, mImgsBitmap[i]);
                    drawText(tempAngle + sweepAngle / 2.0f, mStrs[i]);
                    tempAngle += sweepAngle;
                }
                mSpeed -= 1;
                if (mSpeed < 0) {
                    mSpeed = 0;
                }

                drawArrow();
            }
        } catch (Exception e) {

        } finally {

            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void drawItem(float angle, int imageWidth, Bitmap bitmap) {
        float top = 0, left = 0;
        top = (float) (mRadius + mRadius * 2 / 4.0f * Math.sin(Math.toRadians(angle))) - imageWidth / 2.0f;
        left = (float) (mRadius + mRadius * 2 / 4.0f * Math.cos(Math.toRadians(angle))) - imageWidth / 2.0f;
        RectF rect = new RectF(left, top, left + imageWidth, top + imageWidth);
        mCanvas.save();
        mCanvas.rotate(angle, left + imageWidth / 2.0f, top + imageWidth / 2.0f);
        mCanvas.drawBitmap(bitmap, null, rect, mTextPaint);
        mCanvas.restore();
    }

    private void drawArrow() {
        int left = mRadius - mArrowWidth / 2;
        int top = mRadius - mArrowHeight / 2;
        mCanvas.save();
        mCanvas.translate(left, top);
        mIv.draw(mCanvas);
        mCanvas.restore();
    }

    private void drawText(float angle, String text) {

        float top = 0, left = 0;
        Rect bound = new Rect();

        mTextPaint.getTextBounds(text, 0, text.length(), bound);
        top = (float) (mRadius + mRadius * 3 / 4.0f * Math.sin(Math.toRadians(angle))) - bound.height() / 2.0f;
        left = (float) (mRadius + mRadius * 3 / 4.0f * Math.cos(Math.toRadians(angle))) - bound.width() / 2.0f;
        RectF rect = new RectF(left, top, left + (bound.width() / 2), top + bound.height() / 2);
        //mCanvas.drawBitmap(bitmap,null,rect,mTextPaint);
        mCanvas.save();
        mCanvas.rotate(angle + 90, left + bound.width() / 2.0f, top + bound.height() / 2.0f);
        mCanvas.drawText(text, left, top, mTextPaint);
        mCanvas.restore();

    }

    private void drawPanel(float sweepAngle) {

    }

    private void drawBackGround() {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        Rect r = new Rect();
        mIv.getDrawingRect(r);
        int dx= mRadius-r.centerX();
        int dy = mRadius - r.centerY();
        r.offset(dx,dy);

        if(r.contains((int)x,(int)y)){
            mSpeed += 100;
        }

        Log.e(TAG,"x->"+x +" y->"+y+ " rect:"+r);

        return false;
    }
}
