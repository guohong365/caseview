package com.uc.android.drawing;

public interface Scalable {
    boolean isScalable();
    void setScalable(boolean scalable);
    void scale(float scaleX, float scaleY);
    void scaleAt(float scaleX, float scaleY, float centerX, float centerY);
}
