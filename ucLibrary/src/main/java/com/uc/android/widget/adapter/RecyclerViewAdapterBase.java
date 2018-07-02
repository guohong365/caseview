package com.uc.android.widget.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.uc.android.model.Selectable;
import com.uc.android.widget.OnRecyclerViewItemClickListener;
import com.uc.android.widget.OnRecyclerViewItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewAdapterBase<ViewHolderType extends RecyclerViewHolderBase> extends RecyclerView.Adapter<ViewHolderType> {
    protected final String TAG;
    protected final Context context;
    protected final List<Selectable> items;
    private final ViewHolderFactory<ViewHolderType> factory;
    protected boolean selectable;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener;
    public RecyclerViewAdapterBase(Context context, List<Selectable> items, ViewHolderFactory<ViewHolderType> factory){
        TAG ="["+getClass().getSimpleName() + "]";
        this.context=context;
        this.items=items;
        this.factory=factory;
    }

    @Override
    public ViewHolderType onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderType holder= factory.create(parent, viewType);
        Log.w(TAG, "view holder created....[" + holder.getClass().getName() + "]");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderType holder, final int position) {
        final Selectable item=items.get(position);
        Log.w(TAG, "bind data at position [" + position + "]");
        holder.setSelectable(item.isSelectable());
        holder.setSelected(item.isSelected());
        if(onRecyclerViewItemClickListener !=null) {
            holder.itemView.setClickable(true);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(TAG, "item clicked at [" + position + "]");
                    onRecyclerViewItemClickListener.onClicked(v, item, position);
                }
            });
        }
        if(onRecyclerViewItemLongClickListener !=null) {
            holder.itemView.setLongClickable(true);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.v(TAG, "item long clicked at [" + position + "]");
                    return onRecyclerViewItemLongClickListener.onLongClicked(v, item, position);
                }
            });
        }
        onAssigned(holder, item);
        item.setTag(holder);
    }
    protected abstract void onAssigned(ViewHolderType holder, Selectable item);

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    public Selectable getItem(int position){
        return items.get(position);
    }
    public List<Selectable> getItems(){
        return items;
    }

    protected void onInsertItem(Selectable item){}

    public void insertItem(Selectable item, int position){
        onInsertItem(item);
        if(position<0 || position >= items.size()){
            items.add(item);
            notifyItemInserted(items.size()-1);
        } else {
            items.add(position, item);
            notifyItemInserted(position);
            notifyItemRangeChanged(position, items.size());
        }
    }
    protected void onUpdateItem(Selectable oldItem, Selectable newItem){}
    public void updateItems(Selectable item, int position){
        onUpdateItem(getItem(position), item);
        items.set(position, item);
        notifyItemChanged(position);
    }
    protected void onDeleteItem(Selectable item){}
    public void deleteItem(int position){
        Selectable item=getItem(position);
        onDeleteItem(item);
        items.remove(position);
        notifyItemRemoved(position);
        if(position!=getItemCount()){
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setOnRecyclerViewItemLongClickListener(OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener) {
        this.onRecyclerViewItemLongClickListener = onRecyclerViewItemLongClickListener;
    }


    public void setSelectable(boolean selectable) {
        if(this.selectable!=selectable){
            this.selectable = selectable;
            for (Selectable item: items){
                item.setSelectable(selectable);
            }
            notifyDataSetChanged();
        }
    }

    public boolean isSelectable() {
        return selectable;
    }
    public int getSelectedCount(){
        int count=0;
        for(Selectable item: items){
            if(item.isSelected()) count ++;
        }
        return count;
    }
    @SuppressWarnings("unchecked")
    public <T extends Selectable> List<T> getSelectedItems(){
        List<T> results=new ArrayList<>();
        for(Selectable item : items){
            if(item.isSelected()) results.add((T)item);
        }
        return results;
    }
}
