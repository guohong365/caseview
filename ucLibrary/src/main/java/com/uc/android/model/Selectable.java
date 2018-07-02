package com.uc.android.model;

public interface Selectable extends WithId, WithTag {
    boolean isSelectable();
    void setSelectable(boolean selectable);
    boolean isSelected();
    void setSelected(boolean selected);
    //void setOnSelectedChangedListener(OnSelectedChangedListener listener);
}
