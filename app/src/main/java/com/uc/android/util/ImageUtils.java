package com.uc.android.util;

import android.graphics.BitmapFactory;
import android.media.ExifInterface;

import com.uc.caseview.entity.ImageItem;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by guoho on 2017/9/27.
 */

public class ImageUtils {
    public static ImageItem getPhotoExif(File imageFile) {
        if(!imageFile.exists()) return null;
        try {
            ImageItem imageInfo=new ImageItem();
            imageInfo.setName(imageFile.getName());
            imageInfo.setSize(imageFile.length());

            FileInputStream stream=new FileInputStream(imageFile);
            ExifInterface exifInterface = new ExifInterface(stream);
            float[] latLong=new float[2];
            if(exifInterface.getLatLong(latLong)){
                imageInfo.setLatitude(latLong[0]);
                imageInfo.setLongitude(latLong[1]);
            }
            String datetime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);// 拍摄时间
            imageInfo.setHeight(exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0));
            imageInfo.setWidth(exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH,0));
            imageInfo.setOrientation(exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0));
            byte[] bytes=exifInterface.getThumbnail();
            imageInfo.setThumbnail(bytes==null?null:BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            stream.close();
            return imageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static double convertRationalLatLonToFloat(
            String rationalString, String ref) {

        String[] parts = rationalString.split(",");

        String[] pair;
        pair = parts[0].split("/");
        double degrees = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[1].split("/");
        double minutes = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[2].split("/");
        double seconds = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
        if ((ref.equals("S") || ref.equals("W"))) {
            return -result;
        }
        return result;
    }

    /**
     * 经纬度格式  转换为  度分秒格式 ,如果需要的话可以调用该方法进行转换
     *
     * @param point 坐标点
     * @return
     */
    public static String pointToLatlong(String point) {
        Double du = Double.parseDouble(point.substring(0, point.indexOf("°")).trim());
        Double fen = Double.parseDouble(point.substring(point.indexOf("°") + 1, point.indexOf("'")).trim());
        Double miao = Double.parseDouble(point.substring(point.indexOf("'") + 1, point.indexOf("\"")).trim());
        Double duStr = du + fen / 60 + miao / 60 / 60;
        return duStr.toString();
    }
}
