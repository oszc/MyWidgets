package com.huuhoo.mywidgets.app;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 6/14/16  9:59 AM
 * Created by JustinZhang.
 */
public class AnimPanelActivity extends Activity implements View.OnClickListener {

    private static final String TAG = AnimPanelActivity.class.getSimpleName();
    @Bind(R.id.menu_ll)
    Button mMenu;
    @Bind(R.id.root)
    LinearLayout mRoot;

    private int[] effects = new int[]{R.drawable.record_video_aqua, R.drawable.record_video_blackboard,
            R.drawable.record_video_mono, R.drawable.record_video_negative,
            R.drawable.record_video_none, R.drawable.record_video_posterize,
            R.drawable.record_video_sepia, R.drawable.record_video_solarize};

    private RelativeLayout mRlEffectPanel;
    private Point mMenuSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_panel_layout);
        ButterKnife.bind(this);
        mMenu.setOnClickListener(this);
        initPopUp();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_ll:
                if (mRlEffectPanel.getVisibility() == View.VISIBLE) {
                    startDisappearAnim();
                } else {
                    mRlEffectPanel.setVisibility(View.VISIBLE);
                    startDisplayAnim();
                }
                break;
        }
    }

    int margin = 10;
    int itemWidth, itemHeight;

    private void initPopUp() {
        mMenu.post(new Runnable() {
            @Override
            public void run() {
                Point screenSize = getScreenSize();
                mRlEffectPanel = new RelativeLayout(AnimPanelActivity.this);
                mRlEffectPanel.setBackgroundColor(Color.GREEN);
                int rowCount = 0;
                rowCount = effects.length / 2;
                if (effects.length % 2 != 0) {
                    rowCount++;
                }
                int columnCount = 2;
                itemWidth = itemHeight = (int) convertDpToPixel(AnimPanelActivity.this, 80);
                int menuWidth = itemWidth * columnCount + margin * (columnCount + 1);
                int menuHeight = rowCount * itemHeight + margin * (rowCount + 1);
                Log.e(TAG, "run menu size:" + menuWidth + "   " + menuHeight + "   item width:" + itemWidth);
                mMenuSize = new Point(menuWidth, menuHeight);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mMenuSize.x, mMenuSize.y);
                params.addRule(RelativeLayout.RIGHT_OF, R.id.menu_ll);
                params.addRule(RelativeLayout.ABOVE, R.id.menu_ll);

                for (int i = 0; i < effects.length; i++) {
                    ImageView iv = generateImageView(i, effects[i], itemWidth, itemHeight);
                    mRlEffectPanel.addView(iv);
                }
                mRoot.addView(mRlEffectPanel, params);

                mRlEffectPanel.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int x = (int) event.getX();
                        int y = (int) event.getY();

                        int childCount = mRlEffectPanel.getChildCount();
                        Point curTouch = new Point(x, y);
                        Rect childRect = new Rect();

                        for (int i = 0; i < childCount; i++) {
                            View child = mRlEffectPanel.getChildAt(i);
                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) child.getLayoutParams();
                            childRect.left = layoutParams.leftMargin;
                            childRect.top = layoutParams.topMargin;
                            childRect.right = layoutParams.leftMargin + layoutParams.width;
                            childRect.bottom = layoutParams.topMargin + layoutParams.height;

                            if (childRect.contains(x, y)) {
                                String tag = (String) child.getTag();
                                Toast.makeText(AnimPanelActivity.this, tag, Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onTouch child -->" + i + " tag:" + tag);
                                break;
                            }
                        }
                        return false;
                    }
                });
            }
        });
    }

    private ImageView generateImageView(int index, int res, int width, int height) {
        ImageView iv = new ImageView(this);
        iv.setTag("No." + index);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);

        int rowIndex = index / 2;
        int columnIndex = index % 2;

        if (columnIndex == 0) {
            layoutParams.leftMargin = margin;
        } else {
            layoutParams.leftMargin = margin * 2 + itemWidth;
        }

        layoutParams.topMargin = rowIndex * itemHeight + (rowIndex + 1) * margin;

        Log.e(TAG, "generateImageView index:" + index + "   -->  " + layoutParams.leftMargin + ":" + layoutParams.topMargin);
        iv.setLayoutParams(layoutParams);
        iv.setImageResource(res);
        /*
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDisappearAnim();
            }
        });
        */

        return iv;
    }

    private TextView generateTextView(String text) {
        TextView tv = new TextView(AnimPanelActivity.this);
        tv.setText("Hello World ~ " + text);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return tv;
    }

    private void startDisplayAnim() {
        Log.e(TAG, "startDisplayAnim ");

        mRlEffectPanel.setPivotX(0);
        mRlEffectPanel.setPivotY(mMenuSize.y);
        ObjectAnimator xAnim = ObjectAnimator.ofFloat(mRlEffectPanel, View.SCALE_X, 0, 1);
        xAnim.setDuration(250);

        ObjectAnimator yAnim = ObjectAnimator.ofFloat(mRlEffectPanel, View.SCALE_Y, 0, 1);
        yAnim.setDuration(350);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(xAnim, yAnim);
        set.start();
    }


    private void startDisappearAnim() {

        Log.e(TAG, "startDisappearAnim ");
        mRlEffectPanel.setPivotX(0);
        mRlEffectPanel.setPivotY(mMenuSize.y);
        ObjectAnimator xAnim = ObjectAnimator.ofFloat(mRlEffectPanel, View.SCALE_X, mRlEffectPanel.getScaleX(), 0f);
        xAnim.setDuration(350);

        ObjectAnimator yAnim = ObjectAnimator.ofFloat(mRlEffectPanel, View.SCALE_Y, mRlEffectPanel.getScaleY(), 0f);
        yAnim.setDuration(250);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(xAnim, yAnim);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                mRlEffectPanel.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
    }

    private Point getScreenSize() {
        Point p = new Point();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        p.y = metrics.heightPixels;
        p.x = metrics.widthPixels;
        Log.e(TAG, "getScreenSize :" + p);
        return p;
    }

    public static float convertDpToPixel(Context context, int dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


}
