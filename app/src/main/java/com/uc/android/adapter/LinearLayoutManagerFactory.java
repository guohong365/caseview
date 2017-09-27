package com.uc.android.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by guoho on 2017/9/25.
 */

public class LinearLayoutManagerFactory implements LayoutManagerFactory {
    Context context;

    public LinearLayoutManagerFactory(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.LayoutManager create() {
        return new LinearLayoutManager(context);
    }
}
