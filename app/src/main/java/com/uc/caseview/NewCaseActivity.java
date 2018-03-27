package com.uc.caseview;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.uc.caseview.entity.CaseItem;
import com.uc.caseview.entity.EntityUtils;
import com.uc.caseview.entity.RequestParams;
import com.uc.caseview.utils.LogUtils;
import com.uc.caseview.view.Action;

import java.util.Date;

import static android.widget.Toast.*;
import static com.uc.caseview.entity.RequestParams.KEY_REQUEST;

public class NewCaseActivity extends ActivityBase {
    private EditText mUserName;
    private EditText mCaseName;
    private EditText mComment;
    private TextView mDate;
    private TextView mLocationHint;
    private TextView mTxtLongitude;
    private TextView mTxtLatitude;
    private Double mLongitude;
    private Double mLatitude;
    private int mLevel=0;
    private RequestParams mRequest;
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
                    CaseItem caseItem;
                    if(mRequest== null){  //for create new case
                        caseItem=new CaseItem();
                        mRequest=new RequestParams(Action.CREATE_NEW_CASE,-1, caseItem);
                    }
                    caseItem=mRequest.getCaseItem();
                    caseItem.setTitle(mCaseName.getText().toString());
                    caseItem.setUser(mUserName.getText().toString());
                    caseItem.setComment(mComment.getText().toString());
                    caseItem.setLevel(mLevel);
                    caseItem.setCreateTime(new Date().getTime());
                    Log.i(TAG, "case item prepared....");
                    LogUtils.logItem(TAG, caseItem);
                    intent.putExtra(KEY_REQUEST, mRequest);
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
        if(EntityUtils.isCaseExistsByTitle(mRequest==null ? null : mRequest.getCaseItem().getId(),mCaseName.getText().toString())) {
                showToast(R.string.info_case_name_aready_exists);
                return R.id.ed_case_name;
        }
        return -1;
    }
    ImageView[] mLevelBtns=new ImageView[5];
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
        mTxtLongitude=findViewById(R.id.txt_longitude);
        mTxtLatitude=findViewById(R.id.txt_latitude);
        mLocationHint=findViewById(R.id.hint_location);
        mRequest= getIntent().getParcelableExtra(KEY_REQUEST);
        if(mRequest!=null && mRequest.getAction()==Action.MODIFY_CASE){
            mUserName.setText(mRequest.getCaseItem().getUser());
            mCaseName.setText(mRequest.getCaseItem().getTitle());
            mComment.setText(mRequest.getCaseItem().getComment());
            mLongitude=mRequest.getCaseItem().getLongitude();
            mLatitude=mRequest.getCaseItem().getLatitude();
            mLevel=mRequest.getCaseItem().getLevel();
        }
        mLevelBtns[0] = findViewById(R.id.btn_level_0);
        mLevelBtns[1] = findViewById(R.id.btn_level_1);
        mLevelBtns[2] = findViewById(R.id.btn_level_2);
        mLevelBtns[3] = findViewById(R.id.btn_level_3);
        mLevelBtns[4] = findViewById(R.id.btn_level_4);
        for(int index=0; index<5; index++) {
            mLevelBtns[index].setTag(index);
            mLevelBtns[index].setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         mLevel=(int)v.getTag();
                                                         checkLevelFlag();
                                                     }});
        }
        btn=findViewById(R.id.btn_location);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location location=CaseViewApp.locationManager.getCurrentLocation();
                if(location!=null) {
                    mLongitude = location.getLongitude();
                    mLatitude = location.getLatitude();
                } else {
                    showToast(R.string.error_get_location);
                    mLongitude=null;
                    mLatitude=null;
                }
                setLocation();
            }
        });
        checkLevelFlag();
        setLocation();
    }
    private void setLocation(){
        if(mLongitude !=null && mLatitude!=null){
            mTxtLongitude.setText(String.format("%.2f", mLongitude));
            mTxtLatitude.setText(String.format("%.2f", mLatitude));
            mLocationHint.setVisibility(View.GONE);
        }else {
            mTxtLatitude.setText("");
            mTxtLongitude.setText("");
            mLocationHint.setVisibility(View.VISIBLE);
        }
    }
    private void checkLevelFlag(){
        for(int index=0; index<5; index++) {
            mLevelBtns[index].setSelected(mLevel==index);
        }
    }
}
