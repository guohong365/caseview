package com.uc.caseview.adapter.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uc.android.adapter.LayoutManagerFactory;
import com.uc.android.adapter.ViewViewHolderFactoryBase;
import com.uc.caseview.R;

public class ImageGroupViewViewHolderFactory extends ViewViewHolderFactoryBase<ImageItemGroupHolder> {
    LayoutManagerFactory itemLayoutManagerFactory;
    public ImageGroupViewViewHolderFactory(Context context, LayoutManagerFactory itemLayoutManagerFactory) {
        super(context);
        this.itemLayoutManagerFactory=itemLayoutManagerFactory;
    }

    @Override
    public ImageItemGroupHolder create(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context)
                .inflate(R.layout.item_date_group_view, parent, false);
        ImageItemGroupHolder holder= new ImageItemGroupHolder(view);
        holder.itemsRecyclerView.setLayoutManager(itemLayoutManagerFactory.create());
        return holder;
    }
}
