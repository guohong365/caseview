package com.uc.android.model;

/**
 * Created by guoho on 2017/9/19.
 */

public interface OnSelectedCollectionChangedListener {
    void onSelectedCountChanged(SelectableCollection sender, Selectable changedItem, int count);
}
