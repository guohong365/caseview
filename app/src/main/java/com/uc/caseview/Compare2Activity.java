package com.uc.caseview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.uc.caseview.layout.CompareFrame;
import com.uc.caseview.layout.CompareItemLayout;

public class Compare2Activity extends AppCompatActivity {
    CompareFrame mFrame;
    CompareItemLayout mFirst;
    CompareItemLayout mSecond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare2);
        mFrame=findViewById(R.id.compare_root_view);
    }
}
