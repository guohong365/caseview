package com.uc.android.model;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.uc.android.image.callback.OnOptionChangeListener;
import com.uc.android.image.callback.OptionChangedObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ImageModifierOptionsImpl implements ImageModifierOptions, OptionChangedObserver {
    private Bundle options=new Bundle();
    private List<OnOptionChangeListener> observers=new ArrayList<>();

    public void addOnOptionChangeListener(@NonNull OnOptionChangeListener onOptionChangeListener) {
        observers.add(onOptionChangeListener);
    }

    private void notify(String key, Object value){
        for(OnOptionChangeListener observer:observers){
            observer.onOptionChanged(this, key, value);
        }
    }

    @Override
    public int size() {
        return options.size();
    }

    @Override
    public boolean isEmpty() {
        return options.isEmpty();
    }

    @Override
    public void clear() {
        options.clear();
    }

    @Override
    public boolean containsKey(String key) {
        return options.containsKey(key);
    }

    @Override
    public Object get(String key) {
        return options.get(key);
    }

    @Override
    public void remove(String key) {
        options.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return options.keySet();
    }

    @Override
    public void putBoolean(@Nullable String key, boolean value) {
        if(options.getBoolean(key, false)!=value) {
            options.putBoolean(key, value);
            notify(key, value);
        }
    }

    @Override
    public void putByte(@Nullable String key, byte value) {
        if(options.getByte(key, (byte)0)!=value){
            options.putByte(key, value);
            notify(key, value);
        }
    }

    @Override
    public void putChar(@Nullable String key, char value) {
        if(options.getChar(key, (char) 0)!=value){
            options.putChar(key, value);
            notify(key, value);
        }
    }

    @Override
    public void putShort(@Nullable String key, short value) {
        if(options.getShort(key, (short) 0)!=value){
            options.putShort(key, value);
            notify(key, value);
        }
    }

    @Override
    public void putInt(@Nullable String key, int value) {
        if(options.getInt(key, 0)!=value){
            options.putInt(key, value);
            notify(key, value);
        }
    }

    @Override
    public void putLong(@Nullable String key, long value) {
        if(options.getLong(key, 0)!=value){
            options.putLong(key, value);
            notify(key, value);
        }
    }

    @Override
    public void putFloat(@Nullable String key, float value) {
        if(options.getFloat(key, 0f)!=value){
            options.putFloat(key, value);
            notify(key, value);
        }
    }

    @Override
    public void putDouble(@Nullable String key, double value) {
        if(options.getDouble(key, 0)!=value){
            options.putDouble(key, value);
            notify(key, value);
        }
    }

    @Override
    public void putString(@Nullable String key, @Nullable String value) {
        if(!options.getString(key, "").equals(value)){
            options.putString(key, value);
            notify(key, value);
        }
    }

    @Override
    public void putBooleanArray(@Nullable String key, @Nullable boolean[] value) {
        options.putBooleanArray(key, value);
        notify(key, value);
    }

    @Override
    public void putByteArray(@Nullable String key, @Nullable byte[] value) {
        options.putByteArray(key, value);
        notify(key, value);
    }

    @Override
    public void putShortArray(@Nullable String key, @Nullable short[] value) {
        options.putShortArray(key, value);
        notify(key, value);
    }

    @Override
    public void putCharArray(@Nullable String key, @Nullable char[] value) {
        options.putCharArray(key, value);
        notify(key, value);
    }

    @Override
    public void putIntArray(@Nullable String key, @Nullable int[] value) {
        options.putIntArray(key, value);
        notify(key, value);
    }

    @Override
    public void putLongArray(@Nullable String key, @Nullable long[] value) {
        options.putLongArray(key, value);
        notify(key, value);
    }

    @Override
    public void putFloatArray(@Nullable String key, @Nullable float[] value) {
        options.putFloatArray(key, value);
        notify(key, value);
    }

    @Override
    public void putDoubleArray(@Nullable String key, @Nullable double[] value) {
        options.putDoubleArray(key, value);
        notify(key, value);
    }

    @Override
    public void putStringArray(@Nullable String key, @Nullable String[] value) {
        options.putStringArray(key, value);
        notify(key, value);
    }

    @Override
    public boolean getBoolean(String key) {
        return options.getBoolean(key);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return options.getBoolean(key, defaultValue);
    }

    @Override
    public byte getByte(String key) {
        return options.getByte(key);
    }

    public byte getByte(String key, byte defaultValue){
        return  options.getByte(key, defaultValue);
    }

    @Override
    public char getChar(String key) {
        return options.getChar(key);
    }

    @Override
    public char getChar(String key, char defaultValue) {
        return options.getChar(key, defaultValue);
    }

    @Override
    public short getShort(String key) {
        return options.getShort(key);
    }

    @Override
    public short getShort(String key, short defaultValue) {
        return options.getShort(key, defaultValue);
    }

    @Override
    public int getInt(String key) {
        return options.getInt(key);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return options.getInt(key, defaultValue);
    }

    @Override
    public long getLong(String key) {
        return options.getLong(key);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return options.getLong(key, defaultValue);
    }

    @Override
    public float getFloat(String key) {
        return options.getFloat(key);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return options.getFloat(key, defaultValue);
    }

    @Override
    public double getDouble(String key) {
        return options.getDouble(key);
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        return options.getDouble(key, defaultValue);
    }

    @Nullable
    @Override
    public String getString(@Nullable String key) {
        return options.getString(key);
    }

    @Override
    public String getString(@Nullable String key, String defaultValue) {
        return options.getString(key, defaultValue);
    }

    @Nullable
    @Override
    public boolean[] getBooleanArray(@Nullable String key) {
        return options.getBooleanArray(key);
    }

    @Nullable
    @Override
    public byte[] getByteArray(@Nullable String key) {
        return options.getByteArray(key);
    }

    @Nullable
    @Override
    public short[] getShortArray(@Nullable String key) {
        return options.getShortArray(key);
    }

    @Nullable
    @Override
    public char[] getCharArray(@Nullable String key) {
        return options.getCharArray(key);
    }

    @Nullable
    @Override
    public int[] getIntArray(@Nullable String key) {
        return options.getIntArray(key);
    }

    @Nullable
    @Override
    public long[] getLongArray(@Nullable String key) {
        return options.getLongArray(key);
    }

    @Nullable
    @Override
    public float[] getFloatArray(@Nullable String key) {
        return options.getFloatArray(key);
    }

    @Nullable
    @Override
    public double[] getDoubleArray(@Nullable String key) {
        return options.getDoubleArray(key);
    }

    @Nullable
    @Override
    public String[] getStringArray(@Nullable String key) {
        return options.getStringArray(key);
    }

}
