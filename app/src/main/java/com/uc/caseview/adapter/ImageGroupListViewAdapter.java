package com.uc.caseview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.uc.android.Selectable;
import com.uc.android.adapter.DataGroupAdapter;
import com.uc.android.adapter.RecyclerViewAdapterBase;
import com.uc.caseview.adapter.holder.ImageGroupViewViewHolderFactory;
import com.uc.caseview.adapter.holder.ImageItemGroupHolder;
import com.uc.caseview.adapter.holder.ImageItemViewHolder;
import com.uc.caseview.adapter.holder.ImageItemViewViewHolderFactory;
import com.uc.caseview.entity.CaseItem;
import com.uc.caseview.entity.EntityUtils;
import com.uc.caseview.entity.ImageGroupItem;
import com.uc.caseview.entity.ImageItem;
import com.uc.caseview.utils.GlideRequests;

import java.util.ArrayList;
import java.util.List;

public class ImageGroupListViewAdapter extends DataGroupAdapter<ImageItemGroupHolder, ImageItemViewHolder> {
    private final GlideRequests requests;
    private final ImageItemViewViewHolderFactory itemViewHolderFactory;
    public ImageGroupListViewAdapter(Context context, List<ImageGroupItem> imageGroups,
                                     ImageGroupViewViewHolderFactory groupFactory,
                                     ImageItemViewViewHolderFactory itemViewHolderFactory,
                                     GlideRequests requests){
        super(context, new ArrayList<Selectable>(), groupFactory, itemViewHolderFactory);
        items.addAll(imageGroups);
        this.itemViewHolderFactory=itemViewHolderFactory;
        this.requests=requests;
    }

    @Override
    public ImageGroupItem getItem(int position) {
        return (ImageGroupItem) super.getItem(position);
    }

    @Override
    protected void onAssigned(ImageItemGroupHolder holder, Selectable item) {
        super.onAssigned(holder, item);
    }

    @Override
    protected RecyclerViewAdapterBase<ImageItemViewHolder> createItemAdapter(Context context,   List<Selectable> items) {
        return new ImageItemAdapter(context, items, itemViewHolderFactory, requests);
    }
    @Override
    public ImageGroupItem findGroupByName(@NonNull String name){
        return (ImageGroupItem) super.findGroupByName(name);
    }
    public void deleteImageItems(List<ImageItem> imageItems){
        EntityUtils.getSession().getImageItemDao().deleteInTx(imageItems);
        ImageGroupItem groupItem=getItem(0);
        List<ImageItem> loaded=EntityUtils.loadImagesByCase(groupItem.getCaseItem().getId());
        List<ImageGroupItem> groupItems= EntityUtils.buildDateGroupedImageList(EntityUtils.buildDateGroupedImageItem(loaded), groupItem.getCaseItem());
        items.clear();
        items.addAll(new ArrayList<Selectable>(groupItems));
        notifyDataSetChanged();
    }
    public boolean insertImageItem(ImageItem item) {
        try {
            EntityUtils.getSession().getImageItemDao().insert(item);
            Log.v(TAG, "item inseted to database......");
            ImageGroupItem groupItem = findGroupByName(item.getDate());
            if (groupItem != null) {
                groupItem.getItems().add(item);
                Log.v(TAG, "group [" + groupItem.getGroupName() + "] find, and item was added");
            } else {
                Log.v(TAG, "group not found, to create it.....");
                List<ImageItem> list = new ArrayList<>();
                list.add(item);
                CaseItem caseItem = EntityUtils.getSession().getCaseItemDao().load(item.getCategoryId());
                groupItem = new ImageGroupItem(item.getDate(), list, caseItem);
                groupItem.getItems().add(new ImageGroupItem(item.getDate(), list, caseItem));
                Log.v(TAG, "new group added....");
            }
            notifyDataSetChanged();
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

}
