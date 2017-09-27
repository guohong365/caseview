package com.uc.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class DataGroupHolder extends RecyclerViewHolderBase {
    public final TextView groupNameTextView;
    public final RecyclerView itemsRecyclerView;
    public DataGroupHolder(View itemView, int groupNameTextView, int itemsRecyclerViewId) {
        super(itemView);
        this.groupNameTextView =(TextView) itemView.findViewById(groupNameTextView);
        itemsRecyclerView=(RecyclerView)itemView.findViewById(itemsRecyclerViewId);
    }
}
