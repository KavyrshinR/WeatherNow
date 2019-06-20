package ru.kavyrshin.weathernow.ui.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import androidx.annotation.IntDef;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MarginItemDecoration extends RecyclerView.ItemDecoration {

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {}

    public static final int HORIZONTAL = RecyclerView.HORIZONTAL;
    public static final int VERTICAL = RecyclerView.VERTICAL;

    private int margin;
    private int marginTop;
    private int marginLeft;
    private int marginRight;
    private int marginBottom;

    @Orientation
    private int orientation = VERTICAL;

    public MarginItemDecoration(int margin, @Orientation int orientation) {
        this(margin, margin, margin, margin, orientation);
    }

    public MarginItemDecoration(int marginTop, int marginLeft, int marginRight, int marginBottom,
                                @Orientation int orientation) {
        this.marginTop = marginTop;
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        this.marginBottom = marginBottom;
        this.orientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (orientation == VERTICAL) {
            getVerticalItemOffsets(outRect, view, parent, state);
        } else {
            getHorizontalItemOffsets(outRect, view, parent, state);
        }
    }

    private void getVerticalItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = marginTop;
        }

        outRect.left = marginLeft;
        outRect.right = marginRight;
        outRect.bottom = marginBottom;
    }

    private void getHorizontalItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = marginTop;

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = marginLeft;
        }

        outRect.right = marginRight;
        outRect.bottom = marginBottom;
    }
}
