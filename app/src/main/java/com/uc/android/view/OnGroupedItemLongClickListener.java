package com.uc.android.view;

import android.view.View;

import com.uc.android.model.DataGroup;

public interface OnGroupedItemLongClickListener {
    boolean onLongClicked(View groupView, View view, DataGroup group, int groupPosition, Object tag, int position);
}
