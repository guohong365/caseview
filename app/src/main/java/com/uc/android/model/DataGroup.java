package com.uc.android.model;

import com.uc.android.Selectable;

import java.util.List;

public interface DataGroup extends Selectable{
    String getGroupName();
    List<Selectable> getItems();
    void selectedAllItems(boolean selected);
    int getSelectedCount();
}
