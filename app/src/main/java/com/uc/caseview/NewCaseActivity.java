package com.uc.caseview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.uc.caseview.entity.CaseItem;
import com.uc.caseview.entity.EntityUtils;
import com.uc.caseview.entity.RequestParams;
import com.uc.caseview.utils.LogUtils;
import com.uc.caseview.view.Action;

import java.util.Date;

import static com.uc.caseview.entity.RequestParams.KEY_REQUEST;

public class NewCaseActivity extends ActivityBase {
    private EditText mUserName;
    private EditText mCaseName;
    private EditText mComment;
    private RequestParams request;
    View.OnClickListener commandButtonClicked=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.m_action_ok:
                    int retId = checkInput();
                    if (retId >= 0) {
                        findViewById(retId).requestFocus();
                        return;
                    }
                    Intent intent = new Intent();
                    CaseItem caseItem = new CaseItem();
                    caseItem.setTitle(mCaseName.getText().toString());
                    caseItem.setUser(mUserName.getText().toString());
                    caseItem.setComment(mComment.getText().toString());
                    caseItem.setCreateTime(new Date().getTime());
                    Log.i(TAG, "case item prepared....");
                    LogUtils.logItem(TAG, caseItem);
                    if(request== null){  //for create new case
                        request=new RequestParams(Action.CREATE_NEW_CASE,-1, caseItem);
                    } else {
                        request.setCaseItem(caseItem);
                    }
                    intent.putExtra(KEY_REQUEST, request);
                    setResult(RESULT_OK, intent);
                    break;
                case R.id.m_action_cancel:
                    setResult(RESULT_CANCELED);
                    break;
            }
            finish();
        }
    };

    private int checkInput(){
        if(mUserName.getText().toString().trim().length()==0){
            showToast(R.string.info_operator_empty);
            return R.id.ed_user_name;
        }
        if(mCaseName.getText().toString().trim().length()==0){
            showToast(R.string.info_case_name_empty);
            return R.id.ed_case_name;
        }
        if((request==null || request.getAction()==Action.CREATE_NEW_CASE) &&
                EntityUtils.isCaseExistsByTitle(mCaseName.getText().toString())) {
            showToast(R.string.info_case_name_aready_exists);
            return R.id.ed_case_name;
        }
        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case);
        ImageView btn = findView(R.id.m_action_ok);
        btn.setOnClickListener(commandButtonClicked);
        btn= findView(R.id.m_action_cancel);
        btn.setOnClickListener(commandButtonClicked);
        mUserName= findView(R.id.ed_user_name);
        mCaseName= findView(R.id.ed_case_name);
        mComment= findView(R.id.ed_case_comment);
        request= getIntent().getParcelableExtra(KEY_REQUEST);
        if(request!=null && request.getAction()==Action.MODIFY_CASE){
            mUserName.setText(request.getCaseItem().getUser());
            mCaseName.setText(request.getCaseItem().getTitle());
            mComment.setText(request.getCaseItem().getComment());
        }
    }
}
