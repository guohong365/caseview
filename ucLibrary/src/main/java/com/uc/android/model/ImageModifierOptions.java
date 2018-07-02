package com.uc.android.model;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public interface ImageModifierOptions {
    int size();
    boolean isEmpty();
    void clear();
    boolean containsKey(String key);
    Object get(String key);
    void remove(String key);
    Set<String> keySet();
    void putBoolean(@Nullable String key, boolean value);
    void putByte(@Nullable String key, byte value);
    void putChar(@Nullable String key, char value);
    void putShort(@Nullable String key, short value);
    void putInt(@Nullable String key, int value);
    void putLong(@Nullable String key, long value);
    void putFloat(@Nullable String key, float value);
    void putDouble(@Nullable String key, double value);
    void putString(@Nullable String key, @Nullable String value);
    void putBooleanArray(@Nullable String key, @Nullable boolean[] value);
    void putByteArray(@Nullable String key, @Nullable byte[] value);
    void putShortArray(@Nullable String key, @Nullable short[] value);
    void putCharArray(@Nullable String key, @Nullable char[] value);
    void putIntArray(@Nullable String key, @Nullable int[] value);
    void putLongArray(@Nullable String key, @Nullable long[] value);
    void putFloatArray(@Nullable String key, @Nullable float[] value);
    void putDoubleArray(@Nullable String key, @Nullable double[] value);
    void putStringArray(@Nullable String key, @Nullable String[] value);

    boolean getBoolean(String key);

    boolean getBoolean(String key, boolean defaultValue);

    byte getByte(String key);

    byte getByte(String key, byte defaultValue);

    char getChar(String key);

    char getChar(String key, char defaultValue);

    short getShort(String key);

    short getShort(String key, short defaultValue);

    int getInt(String key);

    int getInt(String key, int defaultValue);

    long getLong(String key);

    long getLong(String key, long defaultValue);

    float getFloat(String key);

    float getFloat(String key, float defaultValue);

    double getDouble(String key);

    double getDouble(String key, double defaultValue);

    @Nullable
    String getString(@Nullable String key);

    String getString(@Nullable String key, String defaultValue);

    @Nullable
    boolean[] getBooleanArray(@Nullable String key);

    @Nullable
    byte[] getByteArray(@Nullable String key);

    @Nullable
    short[] getShortArray(@Nullable String key);

    @Nullable
    char[] getCharArray(@Nullable String key);

    @Nullable
    int[] getIntArray(@Nullable String key);

    @Nullable
    long[] getLongArray(@Nullable String key);

    @Nullable
    float[] getFloatArray(@Nullable String key);

    @Nullable
    double[] getDoubleArray(@Nullable String key);

    @Nullable
    String[] getStringArray(@Nullable String key);
}
