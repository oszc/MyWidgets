package com.huuhoo.mywidgets.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.root)
    RelativeLayout mRoot;
    public static final int mItemSize = 40;//dp
    @Bind(R.id.bt_bluetooth)
    Button mBtBluetooth;

    private String[] mItemTexts = new String[]{"安全中心 ", "特色服务", "投资理财",
            "转账汇款", "我的账户", "信用卡"};
    private int[] mItemImgs = new int[]{R.drawable.home_mbank_1_normal,
            R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
            R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
            R.drawable.home_mbank_6_normal};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBtBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent blueToothActivity = new Intent(MainActivity.this, BlueToothActivity.class);
                //startActivity(blueToothActivity);
                startActivity(new Intent(MainActivity.this,ArrangeSingerActivity.class));
            }
        });
    }


    @Override
    public void onClick(View v) {
    }


    private void go(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }


}
