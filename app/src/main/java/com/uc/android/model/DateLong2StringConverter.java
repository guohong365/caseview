package com.uc.android.model;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by guoho on 2017/9/21.
 */

public class DateLong2StringConverter implements PropertyConverter<String, Long> {
    final SimpleDateFormat formatter=new SimpleDateFormat("yyyy年MM月dd日");
    @Override
    public String convertToEntityProperty(Long databaseValue) {
        return databaseValue==null ? null : formatter.format(new Date(databaseValue));
    }

    @Override
    public Long convertToDatabaseValue(String entityProperty) {
        try {
            return formatter.parse(entityProperty).getTime();
        }catch (Exception e){
            return null;
        }
    }
}
