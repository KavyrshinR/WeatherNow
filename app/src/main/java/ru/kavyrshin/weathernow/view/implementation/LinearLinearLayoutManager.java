package ru.kavyrshin.weathernow.view.implementation;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class LinearLinearLayoutManager extends RecyclerView.LayoutManager {

    private int firstVisiblePosition = 0;

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        final int parentBottom = getHeight() - getPaddingBottom();
        final View oldTopView = getChildCount() > 0 ? getChildAt(0) : null;

        int oldTop = 0;

        if (oldTopView != null) {
            oldTop = oldTopView.getPaddingTop();
        }

        detachAndScrapAttachedViews(recycler);

        int top = oldTop;
        int bottom = 0;
        int left = 0;
        int right = getWidth();


        int countItem = getItemCount();
        for (int i = 0; firstVisiblePosition + i < countItem && top < parentBottom; i++, top = bottom) {
            View view = recycler.getViewForPosition(firstVisiblePosition + i);
            addView(view, i);
            measureChildWithMargins(view, 0, 0);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            bottom = top + getDecoratedMeasuredHeight(view) + layoutParams.bottomMargin + layoutParams.topMargin;
            layoutDecoratedWithMargins(view, left, top, right, bottom);
        }

    }


    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }
}