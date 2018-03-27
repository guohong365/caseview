package com.uc.caseview.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.uc.android.Selectable;
import com.uc.android.adapter.RecyclerViewAdapterBase;
import com.uc.caseview.R;
import com.uc.caseview.adapter.holder.CaseListViewHolder;
import com.uc.caseview.adapter.holder.CaseListViewViewHolderFactory;
import com.uc.caseview.entity.CaseItem;
import com.uc.caseview.entity.EntityUtils;
import com.uc.caseview.utils.GlobalHolder;
import com.uc.caseview.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class CaseListAdapter extends RecyclerViewAdapterBase<CaseListViewHolder> {
    private int current;
    private TypedArray levels;

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getCurrent() {
        return current;
    }

    public CaseListAdapter(@NonNull Context context, @NonNull List<CaseItem> caseList) {
        super(context, new ArrayList<Selectable>(caseList), new CaseListViewViewHolderFactory(context));
        levels=context.getResources().obtainTypedArray(R.array.case_level_drawables);

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        levels.recycle();
    }

    @Override
    public CaseItem getItem(int position) {
        return (CaseItem) super.getItem(position);
    }

    @Override
    protected void onAssigned(CaseListViewHolder holder, Selectable item) {
        CaseItem caseItem = (CaseItem) item;
        holder.caseName.setText(
                String.format(context.getResources().getString(R.string.format_image_count),
                        caseItem.getTitle(), caseItem.getImageCount()));
        holder.caseDescription.setText(caseItem.getComment());
        Glide.with(context)
                .load(caseItem.getPreviewImage())
                .placeholder(R.drawable.main_action_gallery_48dp)
                .error(R.drawable.main_action_gallery_48dp)
                .into(holder.previewImage);
        holder.caseLevel.setImageDrawable(levels.getDrawable(((CaseItem) item).getLevel()));

    }

    @Override
    public void insertItem(Selectable item, int position) {
        CaseItem caseItem = (CaseItem) item;
        try {
            Log.i(TAG, "the new case [" + caseItem.getTitle() + "] inserted.");
            LogUtils.logItem(TAG, caseItem);
            EntityUtils.getSession().getCaseItemDao().insert(caseItem);
            Log.i(TAG, "the new case [" + caseItem.getTitle() + "] inserted.");
            LogUtils.logItem(TAG, caseItem);
            super.insertItem(item, position);
        } catch (Exception e) {
            Log.v(TAG, e.toString());
            Toast.makeText(context, R.string.info_insert_case_failed, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void deleteItem(int position) {
        try {
            final CaseItem item = getItem(position);
            EntityUtils.deleteCases(new Long[]{item.getId()});
            super.deleteItem(position);
            Log.i(TAG, "the case [" + item.getTitle() + "] at [" + position + "] deleted.");
            LogUtils.logItem(TAG, item);
        } catch (Exception e) {
            Log.v(TAG, e.toString());
            Toast.makeText(context, R.string.info_delete_case_failed, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void updateItems(Selectable item, int position) {
        try {
            CaseItem caseItem = (CaseItem) item;
            final CaseItem exists = getItem(position);
            exists.setComment(caseItem.getComment());
            exists.setTitle(caseItem.getTitle());
            exists.setUser(caseItem.getUser());
            exists.reset();
            EntityUtils.getSession().getCaseItemDao().update(exists);
            super.updateItems(item, position);
        } catch (Exception e) {
            Log.v(TAG, e.toString());
            Toast.makeText(context, R.string.info_update_case_failed, Toast.LENGTH_SHORT).show();
        }
    }
    public void updateView(int position) {
        final CaseItem item = getItem(position);
        item.reset();
        notifyItemChanged(position);
    }


}
