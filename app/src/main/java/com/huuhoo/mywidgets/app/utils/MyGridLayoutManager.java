package com.huuhoo.mywidgets.app.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 4/25/16  11:12 AM
 * Created by JustinZhang.
 */
public class MyGridLayoutManager extends RecyclerView.LayoutManager{

    TextView tv;
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        super.smoothScrollToPosition(recyclerView, state, position);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);


        //View viewForPosition = recycler.getViewForPosition(0);

    }
}
