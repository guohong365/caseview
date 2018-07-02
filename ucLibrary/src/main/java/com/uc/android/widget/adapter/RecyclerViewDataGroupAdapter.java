package com.uc.android.widget.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.uc.android.model.Selectable;
import com.uc.android.model.DataGroup;
import com.uc.android.widget.OnRecyclerViewGroupedItemClickListener;
import com.uc.android.widget.OnRecyclerViewGroupedItemLongClickListener;
import com.uc.android.widget.OnRecyclerViewItemClickListener;
import com.uc.android.widget.OnRecyclerViewItemLongClickListener;

import java.util.List;

public abstract class RecyclerViewDataGroupAdapter<HolderType extends RecyclerViewDataGroupHolder, ItemHolderType extends RecyclerViewHolderBase> extends RecyclerViewAdapterBase<HolderType> {
    protected final ViewHolderFactory<ItemHolderType> itemVievViewHolderFactory;
    private OnRecyclerViewGroupedItemClickListener onRecyclerViewGroupedItemClickListener;
    private OnRecyclerViewGroupedItemLongClickListener onRecyclerViewGroupedItemLongClickListener;

    public RecyclerViewDataGroupAdapter(Context context, List<Selectable> items,
                                        ViewHolderFactory<HolderType> viewViewHolderFactory,
                                        ViewHolderFactory<ItemHolderType> itemViewViewHolderFactory){
        super(context, items, viewViewHolderFactory);
        this.itemVievViewHolderFactory = itemViewViewHolderFactory;
    }
    @Override
    public DataGroup getItem(int position){
        return (DataGroup) super.getItem(position);
    }

    protected abstract RecyclerViewAdapterBase<ItemHolderType> createItemAdapter(Context context, List<Selectable> items);

    @Override
    protected void onAssigned(HolderType holder, Selectable item) {
        Log.w(TAG, "assigning data to view....");
        final DataGroup group =(DataGroup)item;
        holder.groupNameTextView.setText(group.getGroupName());
    }

    @Override
    public void onBindViewHolder(final HolderType holder, final int groupPosition) {
        super.onBindViewHolder(holder, groupPosition);
        final DataGroup group=getItem(groupPosition);
        RecyclerViewAdapterBase<ItemHolderType> itemAdapter=createItemAdapter(context, group.getItems());
        holder.itemsRecyclerView.setAdapter(itemAdapter);

        if(onRecyclerViewGroupedItemClickListener !=null) {
            itemAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onClicked(View view, Object tag, int position) {
                    Log.v(TAG, "item clicked at group["+ groupPosition +"] item[" + position +"]");
                    onRecyclerViewGroupedItemClickListener.onClicked(holder.itemView, view, group, groupPosition, tag, position);
                }
            });
        }
        if(onRecyclerViewGroupedItemLongClickListener !=null) {
            itemAdapter.setOnRecyclerViewItemLongClickListener(new OnRecyclerViewItemLongClickListener() {
                @Override
                public boolean onLongClicked(View view, Object tag, int position) {
                    Log.v(TAG, "item long clicked at group["+ groupPosition + "] item[" + position +"]");
                    return onRecyclerViewGroupedItemLongClickListener
                            .onLongClicked(holder.itemView, view, group, groupPosition, tag, position);
                }
            });
        }
    }

    public DataGroup findGroupByName(@NonNull String name){
        for(Selectable item : getItems()){
            if(name.equals(((DataGroup)item).getGroupName())){
                return (DataGroup) item;
            }
        }
        return null;
    }

    public int findGroupIndexByName(@NonNull String name){
        for (Selectable item : getItems()){
            if(name.equals(((DataGroup)item).getGroupName())){
                return getItems().indexOf(item);
            }
        }
        return -1;
    }

    public void setOnRecyclerViewGroupedItemClickListener(OnRecyclerViewGroupedItemClickListener onRecyclerViewGroupedItemClickListener) {
        this.onRecyclerViewGroupedItemClickListener = onRecyclerViewGroupedItemClickListener;
    }

    public void setOnRecyclerViewGroupedItemLongClickListener(OnRecyclerViewGroupedItemLongClickListener onRecyclerViewGroupedItemLongClickListener) {
        this.onRecyclerViewGroupedItemLongClickListener = onRecyclerViewGroupedItemLongClickListener;
    }
}
