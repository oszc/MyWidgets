package com.huuhoo.mywidgets.app;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import com.huuhoo.mywidgets.app.utils.ImageUtil;
import com.huuhoo.mywidgets.app.utils.MyRotateAnimation;
import com.huuhoo.mywidgets.app.utils.RangeInterpolator;
import com.huuhoo.mywidgets.app.utils.Screen;
import com.huuhoo.mywidgets.app.widgets.CircleMenuLayout;
import com.huuhoo.mywidgets.app.widgets.MyCircleMenuLayout;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CircleMenuLayout mCi;
    private MyCircleMenuLayout myCi;

    private String[] mItemTexts = new String[] { "安全中心 ", "特色服务", "投资理财",
            "转账汇款", "我的账户", "信用卡" };
    private int[] mItemImgs = new int[] { R.drawable.home_mbank_1_normal,
            R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
            R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
            R.drawable.home_mbank_6_normal };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
