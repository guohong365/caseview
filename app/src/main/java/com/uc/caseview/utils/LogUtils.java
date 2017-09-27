package com.uc.caseview.utils;

import android.util.Log;

import java.util.Collection;

public class LogUtils {
    public static void logItem(String tag, Object item) {
        if (item == null) Log.v(tag, "item is null.....");
        else Log.v(tag,item.toString());
    }
    public static void logItems(String tag, Collection<?> items) {
        if(items == null ) Log.v(tag, "collection is null.");
        else if(items.isEmpty()) Log.v(tag, "collection is empty.");
        else {
            StringBuilder builder = new StringBuilder();
            builder.append("TOTAL [" + items.size() + "]").append('\n');
            for (Object item : items) {
                builder.append('\t').append(item.toString()).append('\n');
            }
            builder.append("-----------------------------");
            Log.v(tag, builder.toString());
        }
    }
}
