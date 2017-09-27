package com.uc.android.util;

import android.net.Uri;

import com.uc.android.Selectable;

public interface MediaItem extends Selectable {
    String getMimeType();
    long getDateModified();
    int getOrientation();
    Uri getUri();
    Type getType();
    long getDateTaken();
    enum Type {
        VIDEO,
        IMAGE,
    }
}
