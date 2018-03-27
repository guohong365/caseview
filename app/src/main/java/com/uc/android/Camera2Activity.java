package com.uc.android;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.uc.caseview.R;
import com.uc.caseview.fragment.CameraFragment;
import com.uc.caseview.fragment.GalleryFragment;

import static com.uc.caseview.entity.RequestParams.KEY_REQUEST;

public class Camera2Activity extends AppCompatActivity {
    Long mCaseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);

        Fragment fragment=CameraFragment.newInstance(mCaseId);
        Bundle bundle=new Bundle();
        bundle.putParcelable(KEY_REQUEST,  getIntent().getParcelableExtra(KEY_REQUEST));
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_frame, fragment)
                .commit();
        View imageView= findViewById(R.id.layout_frame);
    }
}
