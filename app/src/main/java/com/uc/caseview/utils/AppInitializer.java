package com.uc.caseview.utils;

import android.os.Environment;
import android.util.LruCache;

import java.io.File;
import java.io.IOException;

/**
 * Created by guoho on 2017/6/8.
 */

public class AppInitializer {
    private AppInitializer(){};
    public static final String IMAGE_FOLDER="/com.uc.CaseView/images";
    public String RootPath;
    public String ImagePath;
    public LruCache ImageCache;

    public static AppInitializer instance=new AppInitializer();
    public void init() throws IOException {
        RootPath = Environment.getExternalStorageDirectory().getCanonicalPath();
        ImagePath=RootPath + IMAGE_FOLDER;
        File file=new File(ImagePath);
        if(!file.exists() || !file.isDirectory()){
            file.delete();
            file.mkdirs();
        }
    }
}
