package com.uc.caseview.adapter.holder;

import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;

import com.uc.android.adapter.RecyclerViewHolderBase;
import com.uc.caseview.R;

public class ImageItemViewHolder extends RecyclerViewHolderBase {
    public final ImageView imageView;
    public final ImageView selectedCheckBox;
    final View imageOverlay;
    public ImageItemViewHolder(View itemView) {
        super(itemView);
        imageView=(ImageView) itemView.findViewById(R.id.ctrl_image_view);
        selectedCheckBox=(ImageView) itemView.findViewById(R.id.ctrl_check_box);
        imageOverlay=itemView.findViewById(R.id.ctrl_image_overlay);
    }

    @Override
    protected void onSelectedChanged() {
        selectedCheckBox.setSelected(selected);
        imageView.setSelected(selected);
    }

    @Override
    protected void onSelectableChanged() {
        selectedCheckBox.setVisibility(selectable ? View.VISIBLE:View.GONE);
        ViewConfiguration.getPressedStateDuration();
    }
}
