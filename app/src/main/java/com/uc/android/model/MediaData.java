package com.uc.android.model;

import android.net.Uri;

import com.uc.android.AbstractSelectable;
import com.uc.android.util.MediaItem;

public class MediaData extends AbstractSelectable implements MediaItem  {
    private Long id;
    private Uri uri;
    private String mimeType;
    private long dateModified;
    private int orientation;
    private MediaItem.Type type;
    private long dateTaken;

    public MediaData(Long id, Uri uri, String mimeType, long dateModified, int orientation,
            MediaItem.Type type, long dateTaken) {
        this.id = id;
        this.uri = uri;
        this.mimeType = mimeType;
        this.dateModified = dateModified;
        this.orientation = orientation;
        this.type = type;
        this.dateTaken = dateTaken;
    }
    public MediaData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Uri getUri() {
        return this.uri;
    }
    public void setUri(Uri uri) {
        this.uri = uri;
    }
    public String getMimeType() {
        return this.mimeType;
    }
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    public long getDateModified() {
        return this.dateModified;
    }
    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }
    public int getOrientation() {
        return this.orientation;
    }
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
    public MediaItem.Type getType() {
        return this.type;
    }
    public void setType(MediaItem.Type type) {
        this.type = type;
    }
    public long getDateTaken() {
        return this.dateTaken;
    }
    public void setDateTaken(long dateTaken) {
        this.dateTaken = dateTaken;
    }
}