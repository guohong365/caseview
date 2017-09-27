package com.uc.caseview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.uc.android.adapter.GridLayoutManagerFactory;
import com.uc.android.model.DataGroup;
import com.uc.android.view.OnGroupedItemClickListener;
import com.uc.caseview.adapter.ImageGroupListViewAdapter;
import com.uc.caseview.adapter.holder.ImageGroupViewViewHolderFactory;
import com.uc.caseview.adapter.holder.ImageItemViewViewHolderFactory;
import com.uc.caseview.entity.CaseItem;
import com.uc.caseview.entity.CompareParams;
import com.uc.caseview.entity.EntityUtils;
import com.uc.caseview.entity.ImageGroupItem;
import com.uc.caseview.entity.ImageItem;
import com.uc.caseview.entity.RequestParams;
import com.uc.caseview.utils.GlideApp;
import com.uc.caseview.utils.GlobalHolder;
import com.uc.caseview.utils.LogUtils;
import com.uc.caseview.view.Action;

import java.io.File;
import java.util.List;
import java.util.SortedMap;

import static com.uc.caseview.entity.EntityUtils.loadImagesByCase;
import static com.uc.caseview.entity.RequestParams.KEY_REQUEST;
import static com.uc.caseview.view.Action.CHOOSE_PHOTOS;
import static com.uc.caseview.view.Action.COMPARE_SPLIT;
import static com.uc.caseview.view.Action.COMPARE_STACK_UP;

public class CaseContentActivity extends ActivityBase {
    private RequestParams parentRequest;
   // private RequestParams request;
    private ImageGroupListViewAdapter groupAdapter;
    CaseItem caseItem;
    private OnClickListener commandButtonClicked = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.m_action_take_photo:
                    startTakePhoto();
                    break;
                case R.id.m_action_compare_split:
                    doRequestPhotos(COMPARE_SPLIT, 2, -1, R.string.message_request_to_compared);
                    break;
                case R.id.m_action_compare_stackup:
                    doRequestPhotos(Action.COMPARE_STACK_UP, 2, -1, R.string.message_request_to_compared);
                    break;
                case R.id.m_action_delete_photo:
                    doRequestPhotos(Action.DELETE_PHOTOS, -1, -1, R.string.message_request_to_deleted);
                    break;
                case R.id.m_action_export_case_photo:
                    doRequestPhotos(Action.EXPORT_PHOTOS, -1, -1, R.string.message_request_to_exported);
                    break;
            }
        }
    };
    void setupCommandButtons(){
        ImageView btnView= findView(R.id.m_action_take_photo);
        btnView.setOnClickListener(commandButtonClicked);
        btnView = findView(R.id.m_action_compare_split);
        btnView.setOnClickListener(commandButtonClicked);
        btnView= findView(R.id.m_action_compare_stackup);
        btnView.setOnClickListener(commandButtonClicked);
        btnView= findView(R.id.m_action_delete_photo);
        btnView.setOnClickListener(commandButtonClicked);
        btnView= findView(R.id.m_action_export_case_photo);
        btnView.setOnClickListener(commandButtonClicked);
    }
    void setupCaseInfo(CaseItem caseItem){
        TextView textView= findView(R.id.t_title);
        textView.setText(caseItem.getTitle());
        textView= findView(R.id.t_comment);
        textView.setText(caseItem.getComment());
        textView.setVisibility(View.GONE);
        textView= findView(R.id.t_user);
        textView.setText(caseItem.getUser());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentRequest = getIntent().getExtras().getParcelable(RequestParams.KEY_REQUEST);
        if(parentRequest==null || parentRequest.getPosition() < 0 || parentRequest.getCaseItem()==null){
            Log.v(TAG, "empty or invalid parameters.");
            showToast("empty or invalid parameters..");
            finish();
            return;
        }
        setContentView(R.layout.activity_case_content);
        setupCommandButtons();
        caseItem =parentRequest.getCaseItem();
        setupCaseInfo(caseItem);

        RecyclerView recyclerView= findView(R.id.ctrl_image_group);
        List<ImageItem> imageItems = loadImagesByCase(caseItem.getId());
        SortedMap<String, List<ImageItem>> map = EntityUtils.buildDateGroupedImageItem(imageItems);
        List<ImageGroupItem> groupList=EntityUtils.buildDateGroupedImageList(map, caseItem);
        groupAdapter=new ImageGroupListViewAdapter(
                this, groupList,
                new ImageGroupViewViewHolderFactory(this, new GridLayoutManagerFactory(this, GlobalHolder.gridColumns, LinearLayoutManager.VERTICAL)),
                new ImageItemViewViewHolderFactory(this, GlobalHolder.gridColumns),GlideApp.with(this));
        recyclerView.setAdapter(groupAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        groupAdapter.setOnGroupedItemClickListener(new OnGroupedItemClickListener() {
            @Override
            public void onClicked(View groupView, View view, DataGroup group, int groupPosition, Object tag, int position) {
                Log.v(TAG, "item [" + position + "] clicked in group [" + group.getGroupName() +"]");
                LogUtils.logItem(TAG, group);
                LogUtils.logItem(TAG, tag);
            }
        });
        addProcessor(CHOOSE_PHOTOS.getCode(), this);
    }

    @Override
    public boolean process(int requestCode, Intent intent) {
        switch (Action.fromCode(requestCode)){
            case CHOOSE_PHOTOS:
                RequestParams request=intent.getExtras().getParcelable(KEY_REQUEST);
                return dispatchResponse(request);

        }
        return super.process(requestCode, intent);
    }

    @Override
    protected void onPhotoTaken(File photoFile) {
        ImageItem imageItem=new ImageItem();
    }

    @Override
    public void onBackPressed() {
        Log.v(TAG, "back press.");
        if(groupAdapter.isSelectable()){
            groupAdapter.setSelectable(false);
            return;
        }
        if(caseItem.isDirty()) {
            Log.v(TAG, "case is dirty, to push back...");
            Intent intent = new Intent();
            parentRequest.setCaseItem(caseItem);
            intent.putExtra(KEY_REQUEST, parentRequest);
            setResult(RESULT_OK, intent);
        } else {
            Log.v(TAG, "case unchanged, just return");
            setResult(RESULT_CANCELED);
        }
        super.onBackPressed();
    }

    void doRequestPhotos(Action action, int count, int position, Integer message){
        Intent intent=new Intent();
        intent.setClass(this,CasePhotoSelectActivity.class);
        RequestParams request=new RequestParams(action, position, caseItem, message==null? null: getResources().getString(message), count);
        LogUtils.logItem(TAG,request);
        intent.putExtra(KEY_REQUEST, request);
        startActivityForResult(intent, Action.CHOOSE_PHOTOS.getCode());
    }

    void startCompareSplit(List<ImageItem> images){
        Log.i(TAG, "to start comparing in split mode.");
        LogUtils.logItems(TAG,images);
        Intent intent=new Intent();
        intent.setClass(this, CompareActivity.class);
        CompareParams params=new CompareParams(
                Action.COMPARE_SPLIT,
                caseItem.getId(),
                new String[]{images.get(0).getName(), images.get(1).getName()});
        intent.putExtra(KEY_REQUEST,params);
        startActivityForResult(intent, COMPARE_SPLIT.getCode());
    }
    void startCompareStackUp(List<ImageItem> images){
        CompareParams params=new CompareParams(
                Action.COMPARE_STACK_UP,
                caseItem.getId(),
                new String[]{images.get(0).getName(), images.get(1).getName()});
        Intent intent=new Intent();
        intent.setClass(this, CompareActivity.class);
        intent.putExtra(KEY_REQUEST, params);
        startActivityForResult(intent, COMPARE_STACK_UP.getCode());
    }
    boolean dispatchResponse(RequestParams request) {
        List<ImageItem> selectedItems=request.getSelectedItems();
        int error = 0;
        switch (request.getAction()) {
            case COMPARE_SPLIT:
                if (selectedItems.size() == 2) {
                    startCompareSplit(selectedItems);
                } else {
                    error = -2;
                }
                break;
            case COMPARE_STACK_UP:
                if (selectedItems.size() == 2) {
                    startCompareStackUp(selectedItems);
                } else {
                    error = -2;
                }
                break;
            case DELETE_PHOTOS:
                doDeletePhoto(selectedItems);
                break;
        }
        if (error < 0){
            showToast("errors [" + error + "]");
            return false;
        }
        return true;
    }

    void doDeletePhoto(List<ImageItem> imageItems){
        Log.v(TAG, "delete photos. count=[" + imageItems.size() + "]");
        groupAdapter.deleteImageItems(imageItems);
    }
    void doExportPhoto(){

    }

}
