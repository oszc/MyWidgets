package com.huuhoo.mywidgets.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

public class GravityTestActivity extends AppCompatActivity {

    @Bind(R.id.bt)
    Button bt;
    @Bind(R.id.ll)
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_gravity_test);
        ButterKnife.bind(this);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll.setGravity(Gravity.RIGHT);

            }
        });
    }

}
