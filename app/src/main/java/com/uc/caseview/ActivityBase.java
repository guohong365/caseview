package com.uc.caseview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.uc.caseview.common.DialogWaiting;
import com.uc.caseview.utils.FileUtils;
import com.uc.caseview.view.Action;
import com.uc.caseview.view.ResultProcessor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
}
