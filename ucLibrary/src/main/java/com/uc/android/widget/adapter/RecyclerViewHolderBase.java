package com.uc.android.widget.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.uc.android.model.Selectable;

public class RecyclerViewHolderBase extends RecyclerView.ViewHolder implements Selectable {
    protected boolean selectable;
    protected boolean selected;
    protected Object tag;
    public RecyclerViewHolderBase(View itemView) {
        super(itemView);
        selectable=false;
    }
    @Override
    public final boolean isSelectable() {
        return selectable;
    }

    @Override
    public final void setSelectable(boolean selectable) {
        this.selectable=selectable;
        onSelectableChanged();
    }
    protected void onSelectableChanged(){}
    @Override
    public final boolean isSelected() {
        return selected;
    }
    @Override
    public final void setSelected(boolean selected) {
        this.selected=selected;
        onSelectedChanged();
    }
    protected void onSelectedChanged(){}
    @Override
    public Long getId() {
        return Long.valueOf(getLayoutPosition());
    }

    @Override
    public Object getTag() {
        return tag;
    }

    @Override
    public void setTag(Object tag) {
        this.tag = tag;
    }
}
