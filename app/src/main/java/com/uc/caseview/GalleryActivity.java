package com.uc.caseview;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.uc.android.Selectable;
import com.uc.android.view.OnItemClickListener;
import com.uc.caseview.adapter.ImageGroupListViewAdapter;
import com.uc.caseview.adapter.ImageItemAdapter;
import com.uc.caseview.adapter.holder.ImageItemViewViewHolderFactory;
import com.uc.caseview.entity.EntityUtils;
import com.uc.caseview.entity.ImageGroupItem;
import com.uc.caseview.entity.ImageItem;
import com.uc.caseview.entity.RequestParams;
import com.uc.caseview.utils.GlideApp;
import com.uc.caseview.utils.GlobalHolder;

import java.util.ArrayList;
import java.util.List;

import static com.uc.caseview.entity.RequestParams.KEY_REQUEST;

public class GalleryActivity extends ActivityBase {

    List<ImageGroupItem> imageGroups;
    ImageGroupListViewAdapter mAdapter;
    List<ImageItem> mImageItems;
    ImageItemAdapter imageAdapter;
    RequestParams request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        request=getIntent().getParcelableExtra(KEY_REQUEST);
        if(request==null || request.getCaseItem()==null){
            mImageItems = EntityUtils.loadAllImages();
        } else {
            mImageItems=EntityUtils.loadImagesByCase(request.getCaseItem().getId());
        }
        Log.v(TAG, mImageItems.size() + " images loaded.");
        imageAdapter = new ImageItemAdapter(this,
                new ArrayList<Selectable>(mImageItems),
                new ImageItemViewViewHolderFactory(this, GlobalHolder.gridColumns),
                GlideApp.with(this));
        final RecyclerView recyclerView= findView(R.id.gallery_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, GlobalHolder.gridColumns, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClicked(View view, Object tag, int position) {
                Log.v(TAG,  "clicked on view :" + view.getClass().toString());
                Log.v(TAG, " item at position: [" + recyclerView.getChildLayoutPosition(view) + "][" + position +"]");
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(imageAdapter.isSelectable()){
            imageAdapter.setSelectable(false);
            return;
        }
        super.onBackPressed();
    }
}
