package com.uc.android.adapter;

import android.content.Context;

/**
 * Created by guoho on 2017/9/25.
 */

public abstract class ViewViewHolderFactoryBase<ViewHolderType> implements ViewHolderFactory<ViewHolderType> {
    protected final Context context;
    public ViewViewHolderFactoryBase(Context context){
        this.context=context;
    }
}
