package com.uc.caseview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.uc.android.camera.CameraActivity;
import com.uc.caseview.common.DialogWaiting;
import com.uc.caseview.utils.FileUtils;
import com.uc.caseview.view.Action;
import com.uc.caseview.view.ResultProcessor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static com.uc.caseview.utils.FileUtils.AUTHORITIES;

/**
 * Created by guoho on 2017/6/12.
 */

public abstract class ActivityBase extends AppCompatActivity implements ResultProcessor {
    protected CaseViewApp mApp;
    protected DialogWaiting mWaitDialog;
    protected Toast mToast;
    protected String TAG;
    protected File takePhotoFile;
    private final Map<Integer, ResultProcessor> processors;

    protected void startTakePhoto(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoFile= FileUtils.getRandomImageFile(this);
        Log.v(TAG, "generate file " + takePhotoFile.getAbsolutePath());
        Uri uri = FileProvider.getUriForFile(this, AUTHORITIES, takePhotoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra(MediaStore.EXTRA_FULL_SCREEN, true);
        intent.setClass(getApplicationContext(), CameraActivity.class);
        startActivityForResult(intent, Action.TAKE_PHOTO.getCode());
        Log.v(TAG, "system camera started...");
    }
    protected void acceptTackedPhoto(Intent data) {
        Log.i(TAG, "take photo returned....");
        File file=takePhotoFile.getAbsoluteFile();
        onPhotoTaken(file);
    }
    protected void onPhotoTaken(File photoFile){

    }
    protected void startTakeThumbnail(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Action.TAKE_THUMBNAIL.getCode());
        Log.v(TAG, "system camera started for take thumbnail ...");
    }
    protected void acceptTakeThumbnail(Intent intent){
        Bitmap bitmap=(Bitmap) intent.getExtras().get("data");
        onThumbnailTaken(bitmap);
    }
    void onThumbnailTaken(Bitmap bitmap){
    }

    @Override
    public boolean process(int requestCode, Intent intent) {
        switch (Action.fromCode(requestCode)){
            case TAKE_PHOTO:
                acceptTackedPhoto(intent);
                return true;
            case TAKE_THUMBNAIL:
                acceptTakeThumbnail(intent);
                return true;
        }
        return false;
    }

    public void addProcessor(int requestCode, ResultProcessor processor){
        processors.put(requestCode, processor);
    }
    protected Integer dispatchActionResponse(int action, Intent data){
        ResultProcessor processor=processors.get(action);
        if(processor!=null){
            return processor.process(action, data)? 0:-1;
        }
        return null;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            Integer result=dispatchActionResponse(requestCode, data);
            if(result == null) {
                Log.e(TAG, "processor not found for [" + requestCode + "][" + Action.fromCode(requestCode) + "]");
            }else if(result==0){
                Log.i(TAG, "process successfully.");
            } else {
                Log.w(TAG, "process failed.");
            }
        }
    }

    public ActivityBase(){
        TAG ="[" + getClass().getSimpleName() + "];";
        processors = new HashMap<>();
        processors.put(Action.TAKE_PHOTO.getCode(), this);
        processors.put(Action.TAKE_THUMBNAIL.getCode(),this);
        fullScreenMode=FULL_SCREEN_MODE.NORMAL;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (CaseViewApp)getApplication();
        Log.v(TAG, "onCreate");
    }


    protected DialogWaiting getWaitDialog(){
        if(mWaitDialog==null){
            mWaitDialog=new DialogWaiting(this);
        }
        return  mWaitDialog;
    }
    public void showWaiting(String text){
        getWaitDialog().show(text);
    }
    public void showWaiting(int resId){
        getWaitDialog().show(resId);
    }
    public void showWaiting(){
        getWaitDialog().show();
    }
    public void hideWaiting(){
        getWaitDialog().dismiss();
    }

    public void showToast(String text){
        if(mToast!=null){
            mToast.cancel();
        }
        mToast=Toast.makeText(this, text, Toast.LENGTH_SHORT);
        mToast.show();
    }
    public void showToast(int resId){
        if(mToast!=null){
            mToast.cancel();
        }
        mToast=Toast.makeText(this, resId, Toast.LENGTH_SHORT);
        mToast.show();
    }
    <T extends View> T findView(int id){
        return (T) findViewById(id);
    }
    enum FULL_SCREEN_MODE{
        NORMAL,
        NO_ACTION_BAR,
        FULL_SCREEN_WITH_NAVIGATION,
        FULL_SCREEN_HIDE_ALL
    }
    protected FULL_SCREEN_MODE fullScreenMode;

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
        int option=SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(option);
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
}
