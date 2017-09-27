package com.uc.android.view;

import android.view.View;

public interface OnItemLongClickListener {
    boolean onLongClicked(View view, Object tag, int position);
}
