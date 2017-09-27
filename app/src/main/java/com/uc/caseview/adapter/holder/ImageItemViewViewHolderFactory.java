package com.uc.caseview.adapter.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uc.android.adapter.ViewViewHolderFactoryBase;
import com.uc.caseview.R;
import com.uc.caseview.utils.SysUtils;

public class ImageItemViewViewHolderFactory extends ViewViewHolderFactoryBase<ImageItemViewHolder> {
    public int columns;
    public ImageItemViewViewHolderFactory(Context context, int columns) {
        super(context);
        this.columns=columns;
    }
    @Override
    public ImageItemViewHolder create(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context)
                .inflate(R.layout.item_image, parent, false);
        int width = SysUtils.getScreenWidth(context) / columns;
        view.setLayoutParams(new ViewGroup.LayoutParams(width,width));
        return new ImageItemViewHolder(view);
    }
}
