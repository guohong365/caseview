package com.uc.caseview.adapter.holder;

import android.view.View;
import android.widget.ImageView;

import com.uc.android.adapter.DataGroupHolder;
import com.uc.caseview.R;


public class ImageItemGroupHolder extends DataGroupHolder {
    public final ImageView selectedCheckBox;

    public ImageItemGroupHolder(View itemView) {
        super(itemView, R.id.t_group_date, R.id.ctrl_recycler_view);
        selectedCheckBox=(ImageView)itemView.findViewById(R.id.ctrl_checkbox_group_select);
    }

    @Override
    protected void onSelectableChanged() {
        selectedCheckBox.setVisibility(selectable ? View.VISIBLE :View.GONE);
    }

    @Override
    protected void onSelectedChanged() {
        selectedCheckBox.setSelected(selected);
    }
}
