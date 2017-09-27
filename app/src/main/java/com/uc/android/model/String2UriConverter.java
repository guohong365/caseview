package com.uc.android.model;

import android.net.Uri;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by guoho on 2017/9/21.
 */

public class String2UriConverter implements PropertyConverter<Uri, String>{

    @Override
    public Uri convertToEntityProperty(String databaseValue) {
        return Uri.parse(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(Uri entityProperty) {
        return entityProperty.getPath();
    }
}
