package com.uc.android.model;

import java.util.Date;

public class ImageInfo {
    private long dateTaken;
    private float latitude;
    private float longitude;
    private int orientation;
    private int width;
    private int height;
    private long size;

    public ImageInfo(){
        this(0,0);
    }
    public ImageInfo(int width, int height) {
        this(width,height,0);
    }
    public ImageInfo(int width, int height, long size) {
        this(width,height, size, new Date().getTime());
    }
    public ImageInfo(int width, int height, long size, long dateTaken) {
        this(width, height, size, dateTaken, 0);
    }
    public ImageInfo(int width, int height, long size, long dateTaken, int orientation) {
        this(width,height,size,dateTaken,orientation,0,0);
    }
    public ImageInfo(int width, int height, long size,long dateTaken, int orientation, float latitude, float longitude) {
        this.dateTaken = dateTaken;
        this.latitude = latitude;
        this.longitude = longitude;
        this.orientation = orientation;
        this.width = width;
        this.height = height;
        this.size = size;
    }

    public long getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(long dateTaken) {
        this.dateTaken = dateTaken;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
