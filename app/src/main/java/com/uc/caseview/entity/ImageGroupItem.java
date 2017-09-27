package com.uc.caseview.entity;

import com.uc.android.AbstractSelectable;
import com.uc.android.Selectable;
import com.uc.android.model.DataGroup;

import java.util.ArrayList;
import java.util.List;

public class ImageGroupItem extends AbstractSelectable implements DataGroup {
    private final CaseItem caseItem;
    private final String groupName;
    private final List<Selectable> items;
    public ImageGroupItem(String groupName, List<ImageItem> imageItems, CaseItem caseItem) {
        this.groupName = groupName;
        this.items =new ArrayList<>();
        this.items.addAll(imageItems);
        this.caseItem=caseItem;
    }
    @Override
    public String getGroupName() {
        return groupName;
    }
    @Override
    public List<Selectable> getItems() {
        return items;
    }
    @Override
    public void selectedAllItems(boolean selected) {
        for(Selectable item:items){
            item.setSelected(selected);
        }
    }
    public void selectedItem(int position, boolean selected){
        items.get(position).setSelected(selected);
    }
    @Override
    public int getSelectedCount() {
        int count=0;
        for(Selectable item:  items){
            if(item.isSelected()) count++;
        }
        return count;
    }

    @Override
    public void setSelectable(boolean selectable) {
        if(isSelectable() != selectable){
            super.setSelectable(selectable);
            for (Selectable item:items){
                item.setSelectable(selectable);
            }
        }
    }

    public void reload(){
        List<ImageItem> imageItems=EntityUtils.loadImagesByDateAndCaseId(groupName, caseItem==null ? null : caseItem.getId());
        items.clear();
        items.addAll(imageItems);
    }
    @Override
    public Long getId() {
        return Long.valueOf(hashCode());
    }

    public CaseItem getCaseItem() {
        return caseItem;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("--------").append(getClass().getSimpleName()).append("-------------\n")
                .append(super.toString())
                .append("name=[").append(groupName).append(']').append('\n')
                .append("----Case Item:-----\n").append(caseItem)
                .append("selectedCount=[").append(getSelectedCount()).append(']').append('\n')
                .append("----Image Items:----\n");
        for(Selectable item:items){
            builder.append(item);
        }
        builder.append("========").append(getClass().getSimpleName()).append("=========");
        return builder.toString();
    }
}
