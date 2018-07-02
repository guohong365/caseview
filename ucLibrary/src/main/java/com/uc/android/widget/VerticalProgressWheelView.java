package com.uc.android.widget;

import android.content.Context;
import android.util.AttributeSet;

public class VerticalProgressWheelView extends ProgressWheelView {
    public VerticalProgressWheelView(Context context) {
        super(context);
    }

    public VerticalProgressWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalProgressWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VerticalProgressWheelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        orientation = ORIENTATION_VERTICAL;
        super.init(context, attrs);
    }
}
