package com.uc.caseview;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.uc.android.Selectable;
import com.uc.caseview.adapter.ImageItemAdapter;
import com.uc.caseview.adapter.holder.ImageItemViewViewHolderFactory;
import com.uc.caseview.entity.EntityUtils;
import com.uc.caseview.entity.ImageItem;
import com.uc.caseview.entity.RequestParams;
import com.uc.caseview.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class CasePhotoSelectActivity extends ActivityBase {
    public static final String REQUEST_KEY="REQUEST";
    List<ImageItem> imageItems;
    RequestParams request;
    ImageItemAdapter imageAdapter;
    ImageView btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_photo_select);
        request=getIntent().getExtras().getParcelable(REQUEST_KEY);
        TextView textView= findViewById(R.id.t_message);
        if(request.getMessage()==null){
            textView.setVisibility(View.GONE);
        }
        else{
            textView.setText(request.getMessage());
            textView.setVisibility(View.VISIBLE);
        }
        if(!request.getImageItems().isEmpty()){
               imageItems= request.getImageItems();
        } else {
            if(request.getCaseItem()==null){
               imageItems=EntityUtils.loadAllImages();
            } else {
                imageItems=EntityUtils.loadImagesByCase(request.getCaseItem().getId());
                Log.v(TAG, "load ["+ imageItems.size() +"] images for case ["+ request.getCaseItem().getTitle() + "]");
            }
        }
        CheckBox checkBox=findView(R.id.ctrl_checkbox_select_all);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for(Selectable item: imageAdapter.getItems()){
                item.setSelected(isChecked);
            }
            imageAdapter.notifyDataSetChanged();
        });
        final RecyclerView recyclerView= findViewById(R.id.ctrl_recycler_view);
        List<Selectable> list=new ArrayList<>();
        list.addAll(imageItems);
        int orient=getRequestedOrientation();
        int columns=getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE ? 5:3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, columns, LinearLayoutManager.VERTICAL, false));
        imageAdapter=new ImageItemAdapter(
                this,list,
                new ImageItemViewViewHolderFactory(this, columns));
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.setSelectable(true);
        imageAdapter.setOnItemClickListener((view, tag, position) -> {
            Selectable item=imageAdapter.getItem(position);
            item.setSelected(!item.isSelected());
            imageAdapter.notifyItemChanged(position);
            int count= imageAdapter.getSelectedCount();
            Log.v(TAG, "selected item count=[" + count + "]");
            if(request.getRequestCount()!=null && request.getRequestCount() > 0){
                btnOk.setEnabled(count==request.getRequestCount());
            } else {
                btnOk.setEnabled(true);
            }
        });
        btnOk= findViewById(R.id.m_action_ok);
        if(request.getRequestCount()!=null && request.getRequestCount() >0){
            btnOk.setEnabled(false);
        }
        btnOk.setOnClickListener(commandButtonClicked);
        ImageView imageView= findViewById(R.id.m_action_cancel);
        imageView.setOnClickListener(commandButtonClicked);
    }
    View.OnClickListener commandButtonClicked=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.m_action_ok:
                    Log.v(TAG, "ok clicked.");
                    List<ImageItem> list = imageAdapter.getSelectedItems();
                    Log.v(TAG, "selected items:");
                    LogUtils.logItems(TAG,list);
                    request.setImageItems(list);
                    Intent intent=new Intent();
                    intent.putExtra(REQUEST_KEY, request);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case R.id.m_action_cancel:
                    setResult(RESULT_CANCELED);
                    finish();
                    break;
            }
        }
    };
 }
