package com.huuhoo.mywidgets.app.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.huuhoo.mywidgets.app.R;

/**
 * 6/28/16  5:27 PM
 * Created by JustinZhang.
 */
public class BlueToothItem extends FrameLayout {
    private LayoutInflater mInflater;
    private String mContent = "";
    private String mOn = "";
    private String mOff = "";
    private TextView mTvContent = null;
    private Switch mSwitch = null;

    public BlueToothItem(Context context) {
        this(context, null);
    }

    public BlueToothItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mInflater.inflate(R.layout.bluttooth_item, this, true);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mSwitch = (Switch) findViewById(R.id.sw_open_bluetooth);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BlueToothItem,
                0, 0);
        try {
            mContent = a.getString(R.styleable.BlueToothItem_content);
            mOn = a.getString(R.styleable.BlueToothItem_on);
            mOff = a.getString(R.styleable.BlueToothItem_off);
            setContentText(mContent);
            setOnText(mOn);
            setOffText(mOff);
        } finally {
            a.recycle();
        }
    }

    public void setContentText(String contentText) {
        if (!TextUtils.isEmpty(contentText)) {
            mContent = contentText;
        }
    }

    public void setSwitchStatus(boolean checked) {
        mSwitch.setChecked(checked);
    }

    public void setSwitchListener(CompoundButton.OnCheckedChangeListener checkListener) {
        if (checkListener != null) {
            mSwitch.setOnCheckedChangeListener(checkListener);
        }
    }

    public void setOnText(String onText) {
        if (!TextUtils.isEmpty(onText)) {

            this.mOn = onText;
            mSwitch.setTextOn(onText);
        }
    }

    public void setOffText(String offText) {
        if (!TextUtils.isEmpty(offText)) {
            this.mOff = offText;
            mSwitch.setTextOff(offText);
        }
    }
}
