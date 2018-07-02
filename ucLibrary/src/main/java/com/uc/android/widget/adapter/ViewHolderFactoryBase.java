package com.uc.android.widget.adapter;

import android.content.Context;
import com.uc.android.widget.adapter.ViewHolderFactory;

/**
 * Created by guoho on 2017/9/25.
 */

public abstract class ViewHolderFactoryBase<ViewHolderType> implements ViewHolderFactory<ViewHolderType> {
    protected final Context context;
    public ViewHolderFactoryBase(Context context){
        this.context=context;
    }
}
