package com.uc.caseview.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by guohog on 2018/2/7.
 */

public class CompareFrame extends FrameLayout {
    private final String TAG="CompareFrame";
    public CompareFrame(@NonNull Context context) {
        super(context);
    }

    public CompareFrame(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CompareFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CompareFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.v(TAG, String.format("onMeasure (width=%d, height=%d)", widthMeasureSpec, heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.v(TAG, String.format("onLayout(left=%d, top=%d, right=%d, bottom=%d)", left, top, right, bottom));
        super.onLayout(changed, left, top, right, bottom);
    }
}
