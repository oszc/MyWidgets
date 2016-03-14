package com.huuhoo.mywidgets.app.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.huuhoo.mywidgets.app.R;

import java.util.Timer;


/**
 * 3/8/16  9:34 PM
 * Created by JustinZhang.
 */
public class CountDownWidget extends LinearLayout implements View.OnClickListener {

    private EditText mEtContent;
    private TextView mViewCountDown;

    public interface OnCountDownClickListener{
        void onClick();
    }

    private View mRoot;
    private Drawable bg;
    private Drawable mBtBg;

    private long time = 1;

    public boolean stop = false;

    private OnCountDownClickListener mListener;


    public CountDownWidget(Context context) {
        this(context,null);
    }

    public CountDownWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot = inflater.inflate(R.layout.layout_countdown, this);

        mEtContent = (EditText) mRoot.findViewById(R.id.et_content);
        mViewCountDown = (TextView) mRoot.findViewById(R.id.bt_countdown);
        mViewCountDown.setOnClickListener(this);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CountDownWidget);
            String hint = a.getString(R.styleable.CountDownWidget_countdown_et_hint);
            setEtHint(hint);
            String text = a.getString(R.styleable.CountDownWidget_countdown_et_text);
            setEtText(text);
            mBtBg = a.getDrawable(R.styleable.CountDownWidget_countdown_bt_bg);
            setBtBg(mBtBg);
            String btText = a.getString(R.styleable.CountDownWidget_countdown_bt_text);
            setBtText(btText);
            Drawable bg = a.getDrawable(R.styleable.CountDownWidget_countdown_bg);
            setBg(bg);
            int last  = a.getInt(R.styleable.CountDownWidget_last,2);
            setTime(last);
            a.recycle();
        }
    }

    public void setEtHint(String hint){
         if(!TextUtils.isEmpty(hint)){
             mEtContent.setHint(hint);
          }
    }


    public void setBtText(String text){
         if(!TextUtils.isEmpty(text)){

             mViewCountDown.setText(text);

          }
    }


    public void setEtText(String etTExt) {
         if(!TextUtils.isEmpty(etTExt)){
             mEtContent.setText(etTExt);

          }
    }

    public void setBtBg(Drawable d){
        if (d != null) {
            mViewCountDown.setBackgroundDrawable(d);
        }else {
            mViewCountDown.setBackgroundColor(Color.TRANSPARENT);
        }
    }


    public void setBg(Drawable bg) {
        if (bg != null) {
            mRoot.setBackgroundDrawable(bg);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_countdown:
                startCountDown();
                mViewCountDown.setBackgroundColor(Color.TRANSPARENT);
                break;
        }
    }

    private boolean isCountDown = false;

    private void startCountDown() {
        if(isCountDown){
            return;
        }
        isCountDown = true;
        if(mListener!=null){
            mListener.onClick();
        }

        CountDownTimer timer = new CountDownTimer(time*1000*60,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(!stop) {
                    setBtText(millisUntilFinished / 1000 + "s后重新发送");
                }


            }

            @Override
            public void onFinish() {
                if(!stop) {
                    isCountDown = false;
                    setBtText("重新发送");
                    setBtBg(mBtBg);
                }

            }
        };
        timer.start();

    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getText(){
        return mEtContent.getText().toString();
    }

    public void setListener(OnCountDownClickListener l) {
        this.mListener = l;
    }
}
