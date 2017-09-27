package com.uc.caseview.layout;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by guoho on 2017/9/21.
 */

public class FullScreenGalleryLayoutManager extends GridLayoutManager {
    public FullScreenGalleryLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FullScreenGalleryLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public FullScreenGalleryLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return null;
    }
}
