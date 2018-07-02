package com.uc.android.widget;

import android.content.Context;
import android.util.AttributeSet;

public class HorizontalProgressWheelView extends ProgressWheelView{
    public HorizontalProgressWheelView(Context context) {
        super(context);
    }

    public HorizontalProgressWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalProgressWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HorizontalProgressWheelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        orientation = ORIENTATION_HORIZONTAL;
        super.init(context, attrs);
    }
}
