package com.uc.caseview.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.uc.android.AbstractSelectable;
import com.uc.caseview.CaseViewApp;
import com.uc.caseview.utils.FileUtils;
import com.uc.caseview.utils.SysUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.query.Query;

import java.util.Date;
import java.util.List;

@Entity(nameInDb = "T_CASE",
    indexes = {
             @Index(value = "title", unique = true),
            @Index(value = "createTime desc")
    })
public class CaseItem extends AbstractSelectable implements Parcelable {
    @Transient
    static String TAG=CaseItem.class.getSimpleName();
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String user;
    @NotNull
    @Unique
    private String title;

    private String comment;
    @NotNull
    private Long createTime;
    @Transient
    private String previewImage;
    @Transient
    private Long imageCount;
    @Transient
    private boolean dirty=false;
    @Transient
    private String createDate;

    @Override
    public CaseItem clone(){
        return new CaseItem(getId(), getUser(), getTitle(), getComment(), getCreateTime());
    }

    @Override
    public String toString() {
        return "----------" + getClass().getSimpleName() + "----------\n" +
                super.toString() +
                "title=[" + title + "]\n" +
                "user=[" + user + "]\n" +
                "preview image=[" + (id == null ? "null" : getPreviewImage()) + "]\n" +
                "imageCount=[" + (id == null ? "null" : getImageCount()) + "]\n" +
                "comment=[" + comment + "]\n" +
                "createTime=[" + createTime + "]\n" +
                "createDate=[" + getCreateDate() + "]\n" +
                "dirty=[" + dirty + "]\n" +
                "=============================";
    }
    public boolean isDirty(){
        return dirty;
    }
    public void setDirty(boolean dirty){
        this.dirty=dirty;
    }
    public void resetPreviewImage(){
        synchronized (this){
            previewImage=null;
        }
    }
    public String getCreateDate(){
        synchronized (this){
            if(createDate==null){
                if(createTime==null) createDate= "";
                else{
                    Date date=new Date(createTime);
                    createDate= SysUtils.dateFormat.format(date);
                }
            }
        }
        return createDate;
    }
    public String getPreviewImage(){
        if(getId()==null) {
            Log.w("[CASE]", "new case!");
            return null;
        }
        synchronized (this) {
            if (previewImage == null) {
                Log.v(TAG, "image name not loaded.....");
                Query<ImageItem> query= EntityUtils.getSession()
                        .getImageItemDao()
                        .queryBuilder()
                        .where(ImageItemDao.Properties.CategoryId.eq(id))
                        .orderDesc(ImageItemDao.Properties.TakeTime)
                        .limit(1).build();
                query.setLimit(1);
                List<ImageItem> list = query.list();
                if(!list.isEmpty()){
                    Log.v(TAG, "has one image at least....");
                    previewImage= FileUtils.getImagePathName(CaseViewApp.App, list.get(0).getName());
                    Log.v(TAG, "first file name assigned as ["+ previewImage+"]");
                }
                else{
                    Log.v(TAG, "case ["+ getId() + "] has no any images...");
                    previewImage="";
                }
            }
        }
        return previewImage;
    }
    public void reset(){
        synchronized (this){
            setSelected(false);
            setDirty(false);
            imageCount=null;
            previewImage=null;
            createDate=null;
        }
    }
    public void resetImageCount(){
        synchronized (this){
            imageCount=null;
        }
    }
    public long getImageCount(){
        synchronized (this){
            if(imageCount==null){
                Log.v(TAG, "image count not loaded...");
                imageCount=EntityUtils.getCaseImageCount(id);
                Log.v(TAG, "image count loaded as ["+ imageCount + "]");
            }
        }
        return imageCount;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


    @Generated(hash = 886960644)
    public CaseItem(Long id, @NotNull String user, @NotNull String title, String comment,
            @NotNull Long createTime) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.comment = comment;
        this.createTime = createTime;
    }

    @Generated(hash = 1958375695)
    public CaseItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.user);
        dest.writeString(this.title);
        dest.writeString(this.comment);
        dest.writeValue(this.createTime);
        dest.writeString(this.previewImage);
        dest.writeValue(this.imageCount);
        dest.writeByte(this.dirty ? (byte) 1 : (byte) 0);
        dest.writeString(this.createDate);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.selectable ? (byte) 1 : (byte) 0);
    }

    protected CaseItem(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.user = in.readString();
        this.title = in.readString();
        this.comment = in.readString();
        this.createTime = (Long) in.readValue(Long.class.getClassLoader());
        this.previewImage = in.readString();
        this.imageCount = (Long) in.readValue(Long.class.getClassLoader());
        this.dirty = in.readByte() != 0;
        this.createDate = in.readString();
        this.selected = in.readByte() != 0;
        this.selectable = in.readByte() != 0;
    }

    public static final Creator<CaseItem> CREATOR = new Creator<CaseItem>() {
        @Override
        public CaseItem createFromParcel(Parcel source) {
            return new CaseItem(source);
        }

        @Override
        public CaseItem[] newArray(int size) {
            return new CaseItem[size];
        }
    };
}
