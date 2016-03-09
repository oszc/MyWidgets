package com.huuhoo.mywidgets.app.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.huuhoo.mywidgets.app.R;

/**
 * 3/8/16  9:34 PM
 * Created by JustinZhang.
 */
public class CountDownWidget extends LinearLayout{

    private EditText mEtContent;
    private Button mBtCountDown;

    private View mRoot;


    public CountDownWidget(Context context) {
        this(context,null);
    }

    public CountDownWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot = inflater.inflate(R.layout.layout_countdown,this);

        mEtContent = (EditText) mRoot.findViewById(R.id.et_content);
        mBtCountDown = (Button) mRoot.findViewById(R.id.bt_countdown);

        if(attrs!=null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CountDownWidget);

            a.recycle();
        }


    }



}
