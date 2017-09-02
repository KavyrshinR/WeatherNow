package ru.kavyrshin.weathernow.view.implementation;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class LinearLinearLayoutManager extends RecyclerView.LayoutManager {

    public static final String TAG = "myLogs";
    private int firstVisiblePosition = 0;


    private int numberColumns = 0;


    public LinearLinearLayoutManager(int numberColumns) {
        this.numberColumns = numberColumns;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.d(TAG, "onLayoutChildren: start");

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
        for (int i = 0; firstVisiblePosition + i < countItem && top < parentBottom; i++) {
            View view = recycler.getViewForPosition(firstVisiblePosition + i);
            addView(view, i);
            measureChildWithMargins(view, 0, 0);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();


            if ((firstVisiblePosition + i) % numberColumns == 0) { // Todo: Рефакторинг хуякторинг
                left = 0;
                right = getWidth();
                bottom = top + getDecoratedMeasuredHeight(view) + layoutParams.bottomMargin + layoutParams.topMargin;
            } else {
                right = left + layoutParams.rightMargin + getDecoratedMeasuredWidth(view) + layoutParams.leftMargin;
            }

            layoutDecoratedWithMargins(view, left, top, right, bottom);

            if ((firstVisiblePosition + i + 1) % numberColumns == 0) { // Todo: Рефакторинг хуякторинг
                top = bottom;
            } else {
                left = right;
            }
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
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        Log.d(TAG, "scrollVerticallyBy: dy " + dy);
        int delta = scrollInternalVertically(dy);
        offsetChildrenVertical(-delta);
        return delta;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        Log.d(TAG, "scrollHorizontallyBy: dx " + dx);
        int delta = scrollInternalHorizontally(dx);
        offsetChildrenHorizontal(-delta);
        return delta;
    }


    private int scrollInternalVertically(int dy) {
        int itemCount = getItemCount();
        int childCount = getChildCount();

        if (childCount == 0) {
            return 0;
        }

        final View firstView = getChildAt(0);
        final View lastView = getChildAt(childCount - 1);

        int top = getDecoratedTop(firstView);
        int bottom = getDecoratedBottom(lastView);


        if (getHeight() >= bottom - top && childCount == itemCount) {
            return 0;
        }

        int delta = 0;

        if (dy < 0) { //контент приходит сверху, уходит вниз
            int firstViewPosition = getPosition(firstView);
            if (firstViewPosition < 0) {
                delta = dy;
            } else {
                delta = Math.max(top, dy);
            }

        } else {
            int lastViewPosition = getPosition(lastView);
            if (lastViewPosition < itemCount - 1) {
                delta = dy;
            } else {
                delta = Math.min(bottom - getHeight(), dy);
            }
        }

        return delta;
    }

    private int scrollInternalHorizontally(int dx) {

        return dx;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        Log.d(TAG, "generateDefaultLayoutParams: ");
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }
}