package com.uc.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.uc.android.Selectable;
import com.uc.android.model.DataGroup;
import com.uc.android.view.OnGroupedItemClickListener;
import com.uc.android.view.OnGroupedItemLongClickListener;
import com.uc.android.view.OnItemClickListener;
import com.uc.android.view.OnItemLongClickListener;

import java.util.List;

public abstract class DataGroupAdapter<HolderType extends DataGroupHolder, ItemHolderType extends RecyclerViewHolderBase> extends RecyclerViewAdapterBase<HolderType>{
    protected final ViewHolderFactory<ItemHolderType> itemVievViewHolderFactory;
    private OnGroupedItemClickListener onGroupedItemClickListener;
    private OnGroupedItemLongClickListener onGroupedItemLongClickListener;

    public DataGroupAdapter(Context context,  List<Selectable> items,
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

        if(onGroupedItemClickListener!=null) {
            itemAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClicked(View view, Object tag, int position) {
                    Log.v(TAG, "item clicked at group["+ groupPosition +"] item[" + position +"]");
                    onGroupedItemClickListener.onClicked(holder.itemView, view, group, groupPosition, tag, position);
                }
            });
        }
        if(onGroupedItemLongClickListener!=null) {
            itemAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public boolean onLongClicked(View view, Object tag, int position) {
                    Log.v(TAG, "item long clicked at group["+ groupPosition + "] item[" + position +"]");
                    return onGroupedItemLongClickListener
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

    public void setOnGroupedItemClickListener(OnGroupedItemClickListener onGroupedItemClickListener) {
        this.onGroupedItemClickListener=onGroupedItemClickListener;
    }

    public void setOnGroupedItemLongClickListener(OnGroupedItemLongClickListener onGroupedItemLongClickListener) {
        this.onGroupedItemLongClickListener = onGroupedItemLongClickListener;
    }
}
