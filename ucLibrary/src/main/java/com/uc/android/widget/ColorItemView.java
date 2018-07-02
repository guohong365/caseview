package com.uc.android.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.uc.android.model.ColorItem;

public class ColorItemView extends FrameLayout {
    private ColorItem colorItem;

    public ColorItemView(Context context) {
        super(context);
    }

    public ColorItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ColorItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ColorItem getColorItem() {
        return colorItem;
    }

    public void setColorItem(ColorItem colorItem) {
        this.colorItem = colorItem;
        ImageView view=(ImageView)getChildAt(0);
        view.setImageDrawable(new ColorDrawable(colorItem.getColor()));
    }
}
