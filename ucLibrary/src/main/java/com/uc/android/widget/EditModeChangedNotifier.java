package com.uc.android.widget;

public interface EditModeChangedNotifier {
    void addOnEditModeChangeListener(OnEditModeChangeListener onEditModeChangeListener);
    void notifyEditModeChanged(int mode);
}
