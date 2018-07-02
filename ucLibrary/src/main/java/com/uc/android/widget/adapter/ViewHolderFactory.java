package com.uc.android.widget.adapter;

import android.view.ViewGroup;

public interface ViewHolderFactory<ViewHolderType> {
    ViewHolderType create(ViewGroup parent, int viewType);
}
