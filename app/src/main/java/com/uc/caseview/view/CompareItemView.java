package com.uc.caseview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by guohog on 2018/2/5.
 */

public class CompareItemView extends View {
    private static final String TAG="CompareItemView";

    public CompareItemView(Context context) {
        super(context);
    }

    public CompareItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CompareItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CompareItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Bitmap mImage;
    void setImage(Bitmap image){
        this.mImage=image;
    }
    Bitmap getImage(){
        return mImage;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.v(TAG, String.format(" w=%d, h=%d", widthMeasureSpec, heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }
}
