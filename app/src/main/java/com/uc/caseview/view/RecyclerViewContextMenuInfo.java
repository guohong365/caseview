package com.uc.caseview.view;

import android.view.ContextMenu;

public interface RecyclerViewContextMenuInfo extends ContextMenu.ContextMenuInfo {
    int getPosition();
    long getItemId();
}
