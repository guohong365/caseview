package com.uc.android.model;

import com.uc.android.util.MediaItem;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by guoho on 2017/9/21.
 */

public class Int2TypeConverter implements PropertyConverter<MediaItem.Type, Integer> {
    @Override
    public MediaItem.Type convertToEntityProperty(Integer databaseValue) {
        return databaseValue == 0 ? MediaItem.Type.IMAGE : MediaItem.Type.VIDEO ;
    }

    @Override
    public Integer convertToDatabaseValue(MediaItem.Type entityProperty) {
        return entityProperty== MediaItem.Type.IMAGE ? 1 : 0;
    }
}
