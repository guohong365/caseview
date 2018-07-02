package com.uc.android.widget;

import android.view.View;

public interface OnRecyclerViewItemLongClickListener {
    boolean onLongClicked(View view, Object tag, int position);
}
