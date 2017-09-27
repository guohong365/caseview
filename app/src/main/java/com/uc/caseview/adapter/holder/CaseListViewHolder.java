package com.uc.caseview.adapter.holder;

import android.app.Activity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uc.android.adapter.RecyclerViewHolderBase;
import com.uc.caseview.R;

public class CaseListViewHolder extends RecyclerViewHolderBase {

    public final ImageView previewImage;
    public final TextView caseDescription;
    public final TextView caseName;

    public CaseListViewHolder(final View itemView) {
        super(itemView);
        previewImage = (ImageView) itemView.findViewById(R.id.ctrl_image_view_case_preview);
        caseDescription = (TextView) itemView.findViewById(R.id.t_case_descript);
        caseName=(TextView)itemView.findViewById(R.id.t_case_name);
        itemView.setLongClickable(true);
        itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                Log.v("CaseListViewHolder: ", "on create context menu....");
                MenuInflater menuInflater = ((Activity) itemView.getContext()).getMenuInflater();
                menuInflater.inflate(R.menu.context_menu_case_list, menu);
            }
        });
    }


}
