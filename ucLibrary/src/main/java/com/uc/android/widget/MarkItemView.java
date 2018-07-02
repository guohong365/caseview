package com.uc.android.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.uc.android.model.MarkItem;

public class MarkItemView extends FrameLayout{
    private MarkItem markItem;
    private int markColor;
    private ImageView markView;
    public MarkItemView(@NonNull Context context) {
        super(context);
    }

    public MarkItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MarkItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MarkItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MarkItem getMarkItem() {
        return markItem;
    }

    public void setMarkItem(MarkItem markItem) {
        this.markItem = markItem;
        markView.setImageResource(markItem.getShapeRes());
        invalidate();
    }

    public int getMarkColor() {
        return markColor;
    }

    public void setMarkColor(int markColor) {
        this.markColor = markColor;
        markView.setColorFilter(markColor, PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        markView=(ImageView) getChildAt(0);
    }
}
