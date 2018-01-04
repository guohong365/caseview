package com.uc.caseview;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.uc.caseview.entity.CompareParams;
import com.uc.caseview.view.CompareView;

import static com.uc.caseview.entity.RequestParams.KEY_REQUEST;

public class CompareActivity extends ActivityBase {
    CompareParams params;
    CompareView compareView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        params = getIntent().getExtras().getParcelable(KEY_REQUEST);
        setContentView(R.layout.activity_compare);
        ViewGroup frame=(ViewGroup) findViewById(R.id.layout_frame);
        View view=getLayoutInflater().inflate(R.layout.item_compare_view, frame);
        compareView= (CompareView) frame.findViewById(R.id.ctrl_surface_view);
        compareView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                MenuInflater menuInflater=getMenuInflater();
                menuInflater.inflate(R.menu.compare_view, menu);
            }
        });
        setFullScreenMode(FULL_SCREEN_MODE.FULL_SCREEN_HIDE_ALL);
        compareView.setImages(params.images);
        compareView.init();
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.m_action_take_photo:
                //startTakePhoto();
                break;
            case R.id.m_action_replace_photo:
                break;

        }
        return true;
    }

}
