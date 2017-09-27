package com.uc.caseview.utils;

import android.content.Context;

import java.io.File;
import java.util.UUID;

/**
 * Created by guoho on 2017/6/18.
 */

public class FileUtils {
    public static final String IMAGES_DIR="images";
    public static final String AUTHORITIES="com.uc.caseview.fileprovider";
    static File imageDirFile;
    public  static File getPictureDirFile(Context context) {
        synchronized (FileUtils.class){
            if(imageDirFile==null){
                imageDirFile=new File(context.getFilesDir(), IMAGES_DIR);
            }
        }
        return imageDirFile;
    }
    public static String getImageDir(Context context){
        return getPictureDirFile(context).getAbsolutePath();
    }
    public static String getImagePathName(Context context,String fileName){
        return new File(getPictureDirFile(context), fileName).getAbsolutePath();
    }
    public static File getImageFile(Context context, String fileName){
        return new File(getPictureDirFile(context), fileName);
    }

    public static File getRandomImageFile(Context context){
        return new File(getPictureDirFile(context) ,  UUID.randomUUID().toString() + ".jpeg");
    }
}
