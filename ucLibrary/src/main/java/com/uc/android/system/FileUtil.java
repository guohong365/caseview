package com.uc.android.system;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

public class FileUtil {
    static String TAG="FILE_UTIL";
    public static Uri getExternalFileUri(Context context, String fileName){
        File file=context.getExternalFilesDir(null);
        file = new File(file , fileName) ;
        Log.d(TAG, "external dir :" + file.getPath());
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        Uri uri=FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
        Log.d(TAG, "URI:" + uri.toString());
        return uri;
    }
    public static long getFileSize(String filePathName){
        File file=new File(filePathName);
        return  file.length();
    }
}
