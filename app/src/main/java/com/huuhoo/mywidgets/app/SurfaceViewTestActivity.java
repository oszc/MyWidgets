package com.huuhoo.mywidgets.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.huuhoo.mywidgets.app.widgets.LuckyPanView;

/**
 * 7/26/16  4:23 PM
 * Created by JustinZhang.
 */
public class SurfaceViewTestActivity extends Activity{
    public LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout.LayoutParams parentParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        RelativeLayout rootView = new RelativeLayout(this);
        rootView.setLayoutParams(parentParams);
        rootView.setBackgroundColor(Color.BLUE);

        RelativeLayout.LayoutParams luckyPanParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LuckyPanView luckyPanView = new LuckyPanView(this);
        luckyPanView.setZOrderOnTop(true);
        luckyPanView.setLayoutParams(luckyPanParams);
        rootView.addView(luckyPanView);

        RelativeLayout.LayoutParams menuLayoutParams= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View menu = mInflater.inflate(R.layout.layout_menu,rootView,false);
        menu.setBackgroundColor(Color.WHITE);
        menu.setLayoutParams(menuLayoutParams);
 //     rootView.addView(menu);
        setContentView(rootView);
        Dialog dialog = new Dialog(this);
    }
}
