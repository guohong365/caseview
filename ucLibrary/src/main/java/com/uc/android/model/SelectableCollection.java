package com.uc.android.model;

import java.util.Collection;
import java.util.List;

public interface SelectableCollection extends Collection<Selectable>{
    void setOnSelectedCollectionChangedListener(OnSelectedCollectionChangedListener listener);
    int getSelectedCount();

    abstract <T extends Selectable> List<T> getSelectedItems();
}
