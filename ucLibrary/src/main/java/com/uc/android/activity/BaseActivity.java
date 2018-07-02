package com.uc.android.activity;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.uc.android.R;
import com.uc.android.system.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

public class BaseActivity extends AppCompatActivity{

    private static final int REQUEST_PERMISSIONS_CODE = 0;
    protected final String TAG;

    public BaseActivity(){
        TAG=getClass().getSimpleName();
    }

    enum FULL_SCREEN_MODE{
        NORMAL,
        NO_ACTION_BAR,
        FULL_SCREEN_WITH_NAVIGATION,
        FULL_SCREEN_HIDE_ALL
    }
    protected FULL_SCREEN_MODE fullScreenMode=FULL_SCREEN_MODE.NORMAL;

    public void setFullScreenMode(FULL_SCREEN_MODE fullScreenMode) {
        this.fullScreenMode = fullScreenMode;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus){
            View decorView = getWindow().getDecorView();
            ActionBar bar=getSupportActionBar();
            int options;
            switch (fullScreenMode) {
                case NO_ACTION_BAR:
                    if (bar != null) bar.hide();
                    break;
                case FULL_SCREEN_WITH_NAVIGATION:
                    options = View.SYSTEM_UI_FLAG_FULLSCREEN;
                    decorView.setSystemUiVisibility(options);
                    if (bar != null) bar.hide();
                    break;
                case FULL_SCREEN_HIDE_ALL:
                    options = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                    decorView.setSystemUiVisibility(options);
                    getWindow().setStatusBarColor(Color.TRANSPARENT);
                    getWindow().setNavigationBarColor(Color.TRANSPARENT);
                    if(bar!=null)bar.hide();
                    break;
            }
        }
    }

    public void hideStatusBarAndActionBar(){
        int option=View.SYSTEM_UI_FLAG_FULLSCREEN;
        View decorView=getWindow().getDecorView();
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }
    public void setStatusBaseTransparent(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
    public void hideNavigationBar(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
    public void hideNavigationBarAndStatusBar(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    public void setNavigationBarStatusBarTranslucent(){
        View decorView=getWindow().getDecorView();
        int option= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }

    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    public void showToast(int resId){
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }
    public void showToast(String text, int duration){
        Toast.makeText(this, text, duration).show();
    }
    public void showToast(int resId, int duration){
        Toast.makeText(this, resId, duration).show();
    }

    void checkAndRequestPermission(@NonNull String[] permissions){
        if(permissions.length == 0) return;

        List<String> unAuthorized=new ArrayList<>();
        for(String permission:permissions){
            if(ContextCompat.checkSelfPermission(this, permission)!= PackageManager.PERMISSION_GRANTED){
                unAuthorized.add(permission);
            }
        }
        if(unAuthorized.size()>0){
            ActivityCompat.requestPermissions(this, (String[]) unAuthorized.toArray(), REQUEST_PERMISSIONS_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_PERMISSIONS_CODE){
            if(PermissionUtil.verifyPermissions(grantResults)){
                showToast(R.string.info_all_permissions_granted, Toast.LENGTH_LONG);
            } else {
                showToast(R.string.info_permission_not_satisfied, Toast.LENGTH_LONG);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
