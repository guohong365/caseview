package com.uc.android;

public interface Selectable extends WithId {
    boolean isSelectable();
    void setSelectable(boolean selectable);
    boolean isSelected();
    void setSelected(boolean selected);
    //void setOnSelectedChangedListener(OnSelectedChangedListener listener);
}
