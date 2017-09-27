package com.uc.android;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SelectableCollectionImpl extends ArrayList<Selectable>
        implements SelectableCollection, OnSelectedChangedListener{
    OnSelectedCollectionChangedListener onSelectedCollectionChangedListener;

    public SelectableCollectionImpl(){
    }

    public SelectableCollectionImpl(@NonNull Collection<? extends Selectable> c) {
        super(c);
        for(Selectable item : this){
            if(item instanceof SelectedObserved){
                SelectedObserved observed=(SelectedObserved)item;
                ((SelectedObserved) item).setOnSelectedChangedListener(this);
            }
        }
    }

    @Override
    public void setOnSelectedCollectionChangedListener(OnSelectedCollectionChangedListener listener) {
        onSelectedCollectionChangedListener=listener;
    }

    @Override
    public int getSelectedCount() {
        int count=0;
        for(Selectable item: this){
            if(item.isSelected()) count ++;
        }
        return count;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Selectable> List<T> getSelectedItems() {
        List<T> results=new ArrayList<>();
        for(Selectable item : this){
            if(item.isSelected()){
                results.add((T)item);
            }
        }
        return results;
    }

    @Override
    public void onSelectedChanged(Selectable sender, boolean selected) {
        if(onSelectedCollectionChangedListener!=null){
            onSelectedCollectionChangedListener.onSelectedCountChanged(this, sender, getSelectedCount());
        }
    }
}
