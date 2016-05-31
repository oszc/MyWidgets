package com.huuhoo.mywidgets.app.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * 4/21/16  5:57 PM
 * Created by JustinZhang.
 */
public class PageGridLayoutManager extends RecyclerView.LayoutManager {

    private static final String TAG = PageGridLayoutManager.class.getSimpleName();

    private static final int DEFAULT_COUNT = 1;

    /* View Removal Constants */
    private static final int REMOVE_VISIBLE = 0;
    private static final int REMOVE_INVISIBLE = 1;

    /* Fill Direction Constants */
    private static final int DIRECTION_NONE = -1;
    private static final int DIRECTION_START = 0;
    private static final int DIRECTION_END = 1;
    private static final int DIRECTION_UP = 2;
    private static final int DIRECTION_DOWN = 3;

    /* First (top-left) position visible at any point */
    private int mFirstVisiblePosition;
    /* Consistent size applied to all child views */
    private int mDecoratedChildWidth;
    private int mDecoratedChildHeight;
    /* Number of columns that exist in the grid */
    private int mTotalColumnCount = DEFAULT_COUNT;
    /* Metrics for the visible window of our data */
    private int mVisibleColumnCount;
    private int mVisibleRowCount;
    /* Flag to force current scroll offsets to be ignored on re-layout */
    private boolean mForceClearOffsets;

    /* Used for tracking off-screen change events */
    private int mFirstChangedPosition;
    private int mChangedPositionCount;


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //super.onLayoutChildren(recycler, state);

        View scrap = recycler.getViewForPosition(0);
        addView(scrap);
        measureChildWithMargins(scrap, 0, 0);

        mDecoratedChildWidth = getDecoratedMeasuredWidth(scrap);
        mDecoratedChildHeight = getDecoratedMeasuredHeight(scrap);

        detachAndScrapView(scrap, recycler);

        updateWindowSizing();

        int childLeft = 0;
        int childTop = 0;

        fillGrid(recycler, childLeft, childTop);


    }

    private void fillGrid(RecyclerView.Recycler recycler, int childLeft, int childTop) {


        mFirstVisiblePosition = 0;

        childLeft = childTop = 0;

        detachAndScrapAttachedViews(recycler);

        SparseArray<View> viewCache = new SparseArray<View>(getChildCount());

        int offSetItem = Math.abs(mDeltaY) / mDecoratedChildHeight;

        if(Math.abs(mDeltaY) % mDecoratedChildHeight >0  ){
            offSetItem ++;
        }
        mFirstVisiblePosition +=offSetItem;

        if (getChildCount() != 0) {

            for (int i = 0; i < getChildCount(); i++) {
                int position = positionOfIndex(i);
                View child = getChildAt(i);
                viewCache.put(position, child);
            }

            for (int i = 0; i < viewCache.size(); i++) {
                detachView(viewCache.valueAt(i));
            }
        }

        int startLeftOffset = getPaddingLeft();
        int startTopOffset = getPaddingTop();
        int leftOffset = startLeftOffset;
        int topOffset = startTopOffset;

        for (int i = 0; i < getVisibleChildCount(); i++) {

            int nextPosition = positionOfIndex(i);
            View view = viewCache.get(nextPosition);
            if (view == null) {

                leftOffset = i % mTotalColumnCount * mDecoratedChildWidth;
                topOffset = i / mTotalColumnCount * mDecoratedChildHeight;

                view = recycler.getViewForPosition(nextPosition+offSetItem);

                addView(view);
                measureChildWithMargins(view, 0, 0);
                layoutDecorated(view, leftOffset, topOffset, leftOffset + mDecoratedChildWidth, topOffset + mDecoratedChildHeight);
            //    Log.e(TAG, "onLayoutChildren add view(" + i + "): " + view);
            } else {
                Log.e(TAG, "onLayoutChildren attachView");
                attachView(view);
                viewCache.remove(nextPosition);
            }
        }

        for (int i = 0; i < viewCache.size(); i++) {
            recycler.recycleView(viewCache.valueAt(i));
        }
    }


    /*
    * Mapping between child view indices and adapter data
    * positions helps fill the proper views during scrolling.
    */
    private int positionOfIndex(int childIndex) {
        int row = childIndex / mVisibleColumnCount;
        int column = childIndex % mVisibleColumnCount;

        return mFirstVisiblePosition + (row * getTotalColumnCount()) + column;
    }

    private void updateWindowSizing() {

        mVisibleColumnCount = (getHorizontalSpace() / mDecoratedChildWidth) + 1;

        if (getHorizontalSpace() % mDecoratedChildWidth > 0) {
            mVisibleColumnCount++;
        }

        if (mVisibleColumnCount > getTotalColumnCount()) {
            mVisibleColumnCount = getTotalColumnCount();
        }

        mVisibleRowCount = (getVerticalSpace() / mDecoratedChildHeight) + 1;

        if (getVerticalSpace() % mDecoratedChildHeight > 0) {
            mVisibleRowCount++;
        }

        if (mVisibleRowCount > getTotalRowCount()) {
            mVisibleRowCount = getTotalRowCount();
        }
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingRight() - getPaddingLeft();
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    private int mDeltaY = 0;

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
      //  Log.e(TAG, "scrollVerticallyBy :" + dy);
        mDeltaY -= dy;

        fillGrid(recycler,0,0);
        offsetChildrenVertical(-dy);
        return dy;
    }

    private int getTotalColumnCount() {
        if (getItemCount() < mTotalColumnCount) {
            return getItemCount();
        }

        return mTotalColumnCount;
    }

    private int getTotalRowCount() {

        if (mTotalColumnCount == 0 || getItemCount() == 0) {
            return 0;
        }

        int maxRow = getItemCount() / mTotalColumnCount;

        if (getItemCount() % mTotalColumnCount > 0) {
            maxRow++;
        }
        return maxRow;
    }

    private int getVisibleChildCount() {
        return mVisibleColumnCount * mVisibleRowCount;
    }

}
