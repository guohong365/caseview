package com.uc.caseview;

import android.Manifest.permission;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.uc.android.camera.CameraActivity;
import com.uc.caseview.entity.CompareParams;
import com.uc.caseview.entity.EntityUtils;
import com.uc.caseview.entity.ImageItem;
import com.uc.caseview.utils.GlobalHolder;
import com.uc.caseview.utils.SysUtils;
import com.uc.caseview.view.Action;

import java.util.ArrayList;
import java.util.List;

import static com.uc.caseview.entity.RequestParams.KEY_REQUEST;
import static com.uc.caseview.view.Action.COMPARE_SPLIT;


public class MainActivity extends ActivityBase implements View.OnClickListener{
    private static final int REQUEST_PERMISSION = 0;
    private static String[] neededPermissions=new String[]{
            permission.READ_EXTERNAL_STORAGE,
            permission.WRITE_EXTERNAL_STORAGE,
            permission.CAMERA
    };
    void startActionActivity(Class<?> clazz){
        Intent intent=new Intent();
        intent.setClass(this, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GlobalHolder.ColumnWidth = SysUtils.Dp2Px(this, 98);
        GlobalHolder.ImageWidth = SysUtils.Dp2Px(this, 96);
        GlobalHolder.Spacing = SysUtils.Dp2Px(this, 2);
        GlobalHolder.GridViewCutOff = SysUtils.Dp2Px(this, 24);
        GlobalHolder.dynanicColumn = false;
        View imageView = findViewById(R.id.action_case);
        imageView.setOnClickListener(this);
        imageView = findViewById(R.id.action_search);
        imageView.setOnClickListener(this);
        imageView = findViewById(R.id.action_settings);
        imageView.setOnClickListener(this);
        imageView = findViewById(R.id.action_gallery);
        imageView.setOnClickListener(this);
        Button button = (Button) findViewById(R.id.button_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ImageItem> items= EntityUtils.getSession().getImageItemDao().queryBuilder().limit(2).build().list();
                Intent intent=new Intent();
                intent.setClass(getBaseContext(), CompareActivity.class);
                CompareParams params=new CompareParams(
                        Action.COMPARE_SPLIT,
                        null,
                        new String[]{items.get(0).getName(), items.get(1).getName()});
                intent.putExtra(KEY_REQUEST,params);
                startActivityForResult(intent, COMPARE_SPLIT.getCode());
            }
        });
        button =findView(R.id.m_action_take_photo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), CameraActivity.class);
                startActivity(intent);
            }
        });

        checkAndRequestPermission();
    }
    void checkAndRequestPermission(){
        List<String> unAuthorized=new ArrayList<>();
        for(String item : neededPermissions){
            if(ContextCompat.checkSelfPermission(this, item) != PackageManager.PERMISSION_GRANTED) {
                unAuthorized.add(item);
            }
        }
        if(unAuthorized.size() > 0){
            requestPermissions(unAuthorized);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_case:
                startActionActivity(CaseListViewActivity.class);
                break;
            case R.id.action_gallery:
                startActionActivity(GalleryActivity.class);
                break;
            case R.id.action_search:
                startActionActivity(SearchActivity.class);
                break;
            case R.id.action_settings:
                startActionActivity(SettingsActivity.class);
                break;
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void requestPermissions(List<String> permissions) {
        String[] array=new String[permissions.size()];
        permissions.toArray(array);
        ActivityCompat.requestPermissions(
                this,
                array,
                REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean error=false;
        switch (requestCode) {
            case REQUEST_PERMISSION: {  // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0){
                    for(int i= 0;i<grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "all permissions is not satisfied", Toast.LENGTH_LONG)
                                    .show();
                            finish();
                            return;
                        }
                    }
                } else {
                    Toast.makeText(this, "permissions requirement was canceled.", Toast.LENGTH_LONG)
                                .show();
                    finish();
                }
            }
        }
    }
}
