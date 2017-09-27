package com.uc.caseview.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.uc.android.AbstractSelectable;
import com.uc.android.model.Bitmap2Bytes;
import com.uc.android.model.DateConverter;
import com.uc.android.model.DateTimeConverter;
import com.uc.android.util.ImageUtils;
import com.uc.caseview.utils.SysUtils;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.io.File;
import java.util.Date;

@Entity(nameInDb = "T_IMAGE",
    indexes = {
            @Index(value = "categoryId"),
            @Index(value = "name", unique = true),
            @Index(value = "takeTime desc")
    })

public class ImageItem extends AbstractSelectable implements Parcelable
{
    @Id(autoincrement = true)
    public Long id;
    @NotNull
    private Long categoryId;
    @NotNull
    @Unique
    private String name;
    private int width;
    private int height;
    private float latitude;
    private float longitude;
    //Only degrees 0, 90, 180, 270 will work.
    private int orientation;
    @Convert(converter = Bitmap2Bytes.class, columnType = byte[].class)
    private Bitmap thumbnail;
    @NotNull
    @Convert(converter = DateTimeConverter.class, columnType = Date.class)
    private String takeTime;
    @NotNull
    private long dateTaken;
    @NotNull
    @Convert(converter = DateConverter.class, columnType = Date.class)
    private String date;
    @NotNull
    private long size;
    @Transient
    private boolean dirty;

    public static ImageItem fromFile(Long categoryId, File file){
        if(!file.exists()) return null;
        ImageItem imageItem=ImageUtils.getPhotoExif(file);
        long dateTaken=file.lastModified();
        String takeTime=SysUtils.dataTimeFormat.format(imageItem.dateTaken);
        String date=SysUtils.dateFormat.format(new Date(imageItem.dateTaken));
        //long size=file.length();
        //Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
        //int width= bitmap.getWidth();
        //int height=bitmap.getHeight();
        //Only degrees 0, 90, 180, 270 will work.
        //int orientation;
        //Bitmap thumbnail=getThumbnail(bitmap, maxW,maxH);
        return imageItem;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isDirty(){
        return dirty;
    }
    public void setDirty(boolean dirty){
        this.dirty=dirty;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTakeTime() {
        return this.takeTime;
    }
    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public long getSize() {
        return this.size;
    }
    public void setSize(long size) {
        this.size = size;
    }
    public long getDateTaken() {
        return this.dateTaken;
    }
    public void setDateTaken(long dateTaken) {
        this.dateTaken = dateTaken;
    }
    @Override
    public String toString() {
        return "------" + getClass().getSimpleName() + "------\n" +
                super.toString() +
                "caseId=[" + categoryId + "]\n" +
                "fileName=[" + name + "]\n" +
                "size=[" + size + "]\n" +
                "take time=[" + takeTime + "]\n" +
                "date taken=[" + dateTaken + "][" + SysUtils.dataTimeFormat.format(new Date(dateTaken)) + "]\n" +
                "take date=[" + date + "]\n" +
                "dirty=[" + dirty + "]\n" +
                "==========";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.categoryId);
        dest.writeString(this.name);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeFloat(this.latitude);
        dest.writeFloat(this.longitude);
        dest.writeInt(this.orientation);
        dest.writeParcelable(this.thumbnail, flags);
        dest.writeString(this.takeTime);
        dest.writeLong(this.dateTaken);
        dest.writeString(this.date);
        dest.writeLong(this.size);
        dest.writeByte(this.dirty ? (byte) 1 : (byte) 0);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.selectable ? (byte) 1 : (byte) 0);
    }

    public ImageItem() {
    }

    protected ImageItem(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.categoryId = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.latitude = in.readFloat();
        this.longitude = in.readFloat();
        this.orientation = in.readInt();
        this.thumbnail = in.readParcelable(Bitmap.class.getClassLoader());
        this.takeTime = in.readString();
        this.dateTaken = in.readLong();
        this.date = in.readString();
        this.size = in.readLong();
        this.dirty = in.readByte() != 0;
        this.selected = in.readByte() != 0;
        this.selectable = in.readByte() != 0;
    }

    @Generated(hash = 387377068)
    public ImageItem(Long id, @NotNull Long categoryId, @NotNull String name, int width, int height,
            float latitude, float longitude, int orientation, Bitmap thumbnail, @NotNull String takeTime,
            long dateTaken, @NotNull String date, long size) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.width = width;
        this.height = height;
        this.latitude = latitude;
        this.longitude = longitude;
        this.orientation = orientation;
        this.thumbnail = thumbnail;
        this.takeTime = takeTime;
        this.dateTaken = dateTaken;
        this.date = date;
        this.size = size;
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel source) {
            return new ImageItem(source);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };
}
