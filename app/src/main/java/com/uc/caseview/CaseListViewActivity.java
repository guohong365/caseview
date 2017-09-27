package com.uc.caseview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.uc.android.view.OnItemClickListener;
import com.uc.android.view.OnItemLongClickListener;
import com.uc.caseview.adapter.CaseListAdapter;
import com.uc.caseview.entity.CaseItem;
import com.uc.caseview.entity.EntityUtils;
import com.uc.caseview.entity.RequestParams;
import com.uc.caseview.utils.GlideApp;
import com.uc.caseview.utils.LogUtils;
import com.uc.caseview.view.Action;

import java.util.List;

import static com.uc.caseview.entity.RequestParams.KEY_REQUEST;

public class CaseListViewActivity extends ActivityBase {
    CaseListAdapter mAdapter;
    RequestParams requestParams;
    private void initButtonsListener(){
        View.OnClickListener commandButtonClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.m_action_new_case:
                        onStartCreateNewCase();
                        break;
                }
            }
        };
        ImageView imageView = (ImageView) findViewById(R.id.m_action_new_case);
        imageView.setOnClickListener(commandButtonClicked);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "enter CaseListView.....");
        setContentView(R.layout.activity_case_list_view);
        initButtonsListener();
        //加载数据
        List<CaseItem> caseList = EntityUtils.loadAllCases();
        Log.v(TAG, "load case items [" + caseList.size() + "]");
        for (CaseItem item : caseList) {
            LogUtils.logItem(TAG, item);
        }
        //设置列表
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ctrl_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CaseListAdapter(this, caseList, GlideApp.with(this));
        recyclerView.setAdapter(mAdapter);
        // set GONE for this edition because not implement
        SearchView searchView = (SearchView) findViewById(R.id.ctrl_search_view);
        if (mAdapter.getItemCount() == 0) {
            searchView.setEnabled(false);
        }
        searchView.setVisibility(View.GONE);
        Log.i(TAG, "activity created....");

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClicked(View view, Object tag, int position) {
                onStartCaseDetailActivity(position);
            }
        });
        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onLongClicked(View view, Object tag, int position) {
                mAdapter.setCurrent(position);
                return false;
            }
        });
     }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.v(TAG, "menu item [" + item.getTitle() + "] selected....");
        int position=mAdapter.getCurrent();
        CaseItem caseItem=mAdapter.getItem(position);
        LogUtils.logItem(TAG, caseItem);
        switch (item.getItemId()){
            case R.id.m_action_delete_case:
                doDeleteCase(position, caseItem);
                break;
            case R.id.m_action_edit_case:
                onStartModifyCase(position, caseItem);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK) return;
        RequestParams request=data.getParcelableExtra(KEY_REQUEST);
        LogUtils.logItem(TAG,request.getCaseItem());
        switch (Action.fromCode(requestCode)) {
            case CREATE_NEW_CASE:
                mAdapter.insertItem(request.getCaseItem(),0);
                break;
            case MODIFY_CASE:
                if(request.getCaseItem().isDirty()){
                    mAdapter.updateItems(request.getCaseItem(),request.getPosition());
                }
                break;
            case OPERATE_ON_DETAIL:
                if(request.getCaseItem().isDirty()){
                    mAdapter.updateView(request.getPosition());
                }
                break;
        }
    }

    void onStartCaseDetailActivity(int position) {
        Log.d(TAG, String.format("item [%d] clicked...", position));
        Intent intent = new Intent();
        CaseItem item = mAdapter.getItem(position);
        RequestParams request=new RequestParams(Action.OPERATE_ON_DETAIL, position, item);
        intent.putExtra(KEY_REQUEST, request);
        intent.setClass(this, CaseContentActivity.class);
        startActivityForResult(intent, Action.OPERATE_ON_DETAIL.getCode());
        Log.d(TAG, "start case content view....");
    }
    void onStartCreateNewCase() {
        Intent intent = new Intent();
        intent.setClass(this, NewCaseActivity.class);
        startActivityForResult(intent, Action.CREATE_NEW_CASE.getCode());
    }
    private void onStartModifyCase(int position, CaseItem caseItem){
        Intent intent=new Intent();
        RequestParams request=new RequestParams(Action.MODIFY_CASE, position, caseItem);
        intent.putExtra(KEY_REQUEST, request);
        intent.setClass(this, NewCaseActivity.class);
        startActivityForResult(intent, Action.MODIFY_CASE.getCode());
    }
    private void doDeleteCase(final int position, CaseItem caseItem) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.system_dialog_title))
                .setMessage(getString(R.string.message_if_delete_case))
                .setPositiveButton(R.string.action_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doRealDeleteCase(position);
                    }
                })
                .setNegativeButton(R.string.action_cancel, null)
                .show();
    }
    private void doRealDeleteCase(int position){
        mAdapter.deleteItem(position);
    }
}

