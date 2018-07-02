package com.uc.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.uc.android.R;

public final class MarkOptionsLayout extends LinearLayout implements OptionControl {
    public MarkOptionsLayout(Context context) {
        super(context);
    }

    public MarkOptionsLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MarkOptionsLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MarkOptionsLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.OptionBarLayout);

    }

    @Override
    public void setOptionControlListener(OptionControlListener optionControlListener) {

    }

    @Override
    public void setOption(String name, Object value) {

    }

    @Override
    public void commit() {

    }

    @Override
    public void cancel() {

    }
}
