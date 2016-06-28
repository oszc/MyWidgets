package com.huuhoo.mywidgets.app.utils;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 5/31/16  3:47 PM
 * Created by JustinZhang.
 */
public class MyLinearLayoutManager2 extends RecyclerView.LayoutManager{

    private int mFirstPositionView;
    private int mLayoutHeight;
    private int mLayoutWidth;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        
    }
}
