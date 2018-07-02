package com.uc.android.widget;

import android.view.View;

import com.uc.android.model.DataGroup;

public interface OnRecyclerViewGroupedItemClickListener {
    void onClicked(View groupView, View view, DataGroup group, int groupPosition, Object tag, int position);
}
