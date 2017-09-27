package com.uc.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.uc.android.Selectable;
import com.uc.android.view.OnItemClickListener;
import com.uc.android.view.OnItemLongClickListener;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewAdapterBase<ViewHolderType extends RecyclerViewHolderBase> extends RecyclerView.Adapter<ViewHolderType> {
    final protected String TAG;
    final protected Context context;
    final protected List<Selectable> items;
    final ViewHolderFactory<ViewHolderType> factory;
    protected boolean selectable;
    protected OnItemClickListener onItemClickListener;
    protected OnItemLongClickListener onItemLongClickListener;
    public RecyclerViewAdapterBase(Context context, List<Selectable> items, ViewHolderFactory<ViewHolderType> factory){
        TAG ="["+getClass().getSimpleName() + "]";
        this.context=context;
        this.items=items;
        this.factory=factory;
    }

    @Override
    public ViewHolderType onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolderType holder= factory.create(parent, viewType);
        Log.w(TAG, "view holder created....[" + holder.getClass().getName() + "]");
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderType holder, final int position) {
        final Selectable item=items.get(position);
        Log.w(TAG, "bind data at position [" + position + "]");
        holder.setSelectable(item.isSelectable());
        holder.setSelected(item.isSelected());
        if(onItemClickListener !=null) {
            holder.itemView.setClickable(true);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(TAG, "item clicked at [" + position + "]");
                    onItemClickListener.onClicked(v, item, position);
                }
            });
        }
        if(onItemLongClickListener!=null) {
            holder.itemView.setLongClickable(true);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.v(TAG, "item long clicked at [" + position + "]");
                    return onItemLongClickListener.onLongClicked(v, item, position);
                }
            });
        }
        onAssigned(holder, item);
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

    public void insertItem(Selectable item, int position){
        if(position<0 || position >= items.size()){
            items.add(item);
            notifyItemInserted(items.size()-1);
        } else {
            items.add(position, item);
            notifyItemInserted(position);
        }
    }
    public void updateItems(Selectable item, int position){
        items.set(position, item);
        notifyItemChanged(position);
    }
    public void deleteItem(int position){
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
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
