package com.uc.caseview.adapter.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.uc.android.adapter.ViewViewHolderFactoryBase;
import com.uc.caseview.R;

public class CaseListViewViewHolderFactory extends ViewViewHolderFactoryBase<CaseListViewHolder> {
    public CaseListViewViewHolderFactory(Context context){
        super(context);
    }
    @Override
    public CaseListViewHolder create(ViewGroup parent, int viewType) {
        return new CaseListViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_case_list, parent, false));
    }
}
