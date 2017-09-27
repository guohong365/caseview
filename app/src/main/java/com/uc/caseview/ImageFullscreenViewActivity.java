package com.uc.caseview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.uc.caseview.fragment.GalleryFragment;

import static com.uc.caseview.entity.RequestParams.KEY_REQUEST;

public class ImageFullscreenViewActivity extends ActivityBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fullscreen_view);
        Fragment fragment=new GalleryFragment();
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
