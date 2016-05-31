package com.huuhoo.mywidgets.app.utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import static android.content.ContentValues.TAG;

/**
 * 4/28/16  11:22 AM
 * Created by JustinZhang.
 */
public class MyLinearLayoutManager extends RecyclerView.LayoutManager {


    private int mDecoratedViewWidth = 0;
    private int mDecoratedViewHeight = 0;
    private int mVisibleChildCount = 0;
    private int mFirstVisibleItem = 0;
    private int mDeltaY = 0;

    private enum ScrollDirection {
        None, Down, Up
    }

    private ScrollDirection mScrollDirection = ScrollDirection.None;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //super.onLayoutChildren(recycler, state);
        View child = recycler.getViewForPosition(0);
        addView(child);
        measureChildWithMargins(child, 0, 0);
        mDecoratedViewWidth = getDecoratedMeasuredWidth(child);
        mDecoratedViewHeight = getDecoratedMeasuredHeight(child);
        detachAndScrapView(child, recycler);
        refreshUI(recycler);
    }

    SparseArray<View> viewCache = new SparseArray<View>();
    int left = getPaddingLeft();
    int top = 0;

    private void refreshUI(RecyclerView.Recycler recycler) {
        //    Log.e(TAG,"child count:"+getChildCount());
        mVisibleChildCount = getVerticalSpace() / mDecoratedViewHeight + 1;
        if (getVerticalSpace() % mDecoratedViewHeight > 0) {
            mVisibleChildCount++;
        }
        if (getChildCount() != 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View attached = getChildAt(i);
                //放入view的缓存中, 与position对应
                viewCache.put(positionOf(i), attached);
            }
            for (int i = 0; i < viewCache.size(); i++) {
                detachView(viewCache.valueAt(i));
            }
        }

        mFirstVisibleItem = -mDeltaY / mDecoratedViewHeight;
        for (int i = 0; i < mVisibleChildCount; i++) {
            View view = viewCache.get(positionOf(i));
            if (view == null) {
                View viewForPosition = recycler.getViewForPosition(positionOf(i));
                View lastView = getChildAt(getChildCount() - 1);

                addView(viewForPosition);
                measureChildWithMargins(viewForPosition, 0, 0);
                if (mDeltaY == 0) {
                    layoutDecorated(viewForPosition, left, i * mDecoratedViewHeight, mDecoratedViewWidth, (i + 1) * mDecoratedViewHeight);
                } else if (mScrollDirection == ScrollDirection.Down) {
                    layoutDecorated(viewForPosition, left, getDecoratedBottom(lastView), mDecoratedViewWidth, getDecoratedBottom(lastView) + mDecoratedViewHeight);
                } else if (mScrollDirection == ScrollDirection.Up) {
                    Log.e(TAG, "refreshUI up!!!");
                    layoutDecorated(viewForPosition, left, getDecoratedTop(firstView) - mDecoratedViewHeight, mDecoratedViewWidth, getDecoratedTop(firstView));
                }
            } else {
                attachView(view);
                viewCache.remove(positionOf(i));
            }
        }

        for (int i = 0; i < viewCache.size(); i++) {
            recycler.recycleView(viewCache.valueAt(i));
        }
        viewCache.clear();
        dy = 0;
    }

    public int positionOf(int index) {
        return mFirstVisibleItem + index;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    private int dy;
    private View firstView;

    private ScrollDirection mLastScrollDirection = ScrollDirection.None;
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrollDistance = dy;
        if(Math.abs(dy) > mDecoratedViewHeight){
            if(dy <  0){
                dy = mDecoratedViewHeight * -1;
            }else {
                dy = mDecoratedViewHeight;
            }
        }
        if (dy > 0) {
            mScrollDirection = ScrollDirection.Down;
        } else {
            mScrollDirection = ScrollDirection.Up;
            firstView = getChildAt(0);
        }
        mLastScrollDirection = mScrollDirection;
        mDeltaY -= dy;
        this.dy = -dy;
        offsetChildrenVertical(-dy);
        refreshUI(recycler);
        return scrollDistance;
    }


    private int getHorizontalSpace() {
        return getWidth() - getPaddingRight() - getPaddingLeft();
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }
}
