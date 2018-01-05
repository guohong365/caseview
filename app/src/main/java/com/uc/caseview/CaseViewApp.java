package com.uc.caseview;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;

import com.uc.android.camera.app.CameraApp;
import com.uc.android.camera.app.FirstRunDetector;
import com.uc.android.camera.stats.UsageStatistics;
import com.uc.android.camera.stats.profiler.Profile;
import com.uc.android.camera.stats.profiler.Profilers;
import com.uc.android.camera.util.AndroidContext;
import com.uc.android.camera.util.AndroidServices;
import com.uc.caseview.utils.FileUtils;
import com.uc.caseview.utils.GlideCacheUtil;
import com.uc.caseview.utils.GlobalHolder;

import java.io.File;
import java.io.IOException;

import static com.uc.caseview.utils.FileUtils.IMAGES_DIR;

public class CaseViewApp extends CameraApp {
    public static final String APP_DB = "case_view.db";
    public static String TAG;
    public static CaseViewApp App;

    public CaseViewApp() {
        GlobalHolder.debug=true;
        GlobalHolder.gridColumns=3;
        TAG=getClass().getSimpleName();
    }



    public String getImagePath() {
        return FileUtils.getImageDir(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App =this;
    }

    private void initPath() throws IOException {
        Log.v(TAG,
                "\nroot: " + Environment.getRootDirectory().getAbsolutePath() +
                "\ndata: " +Environment.getDataDirectory().getAbsolutePath() +
                "\nisExternalStorageEmulated: " + Environment.isExternalStorageEmulated() +
                "\ngetExternalStorageState: " + Environment.getExternalStorageState() +
                "\ngetDownloadCacheDirectory: " + Environment.getDownloadCacheDirectory() +
                "\ngetExternalStorageDirectory: " + (Environment.isExternalStorageEmulated() ?
                                Environment.getExternalStorageDirectory().getAbsolutePath() : "\n--"));

        File pictureDir=new File(getFilesDir(), IMAGES_DIR);
        if(!pictureDir.exists()){
            Log.v(TAG, "dir not exists...");
            if(pictureDir.mkdirs()){
                Log.v(TAG, "dir ["+pictureDir.getAbsolutePath()+"] created.");
            } else {
                Log.e(TAG, "dir ["+pictureDir.getAbsolutePath()+"] create failed.");
            }
        }
        Log.v(TAG, "picture path is " + pictureDir.getAbsolutePath());
    }
    public void init() throws IOException {
        initPath();
        GlideCacheUtil.getInstance().clearImageAllCache(this);
    }

}
