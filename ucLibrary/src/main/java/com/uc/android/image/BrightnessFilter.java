package com.uc.android.image;

import android.graphics.Bitmap;
import android.graphics.ColorMatrix;

public class BrightnessFilter extends ColorMatrixImageFilter {
    public static final String FILTER_BRIGHTNESS="filter.brightness";
    private float brightness = 1.0f;

    public BrightnessFilter(){
        this(0);
    }
    public BrightnessFilter(float brightness) {
        super( 0L, FILTER_BRIGHTNESS);
    }

    public void setBrightness(float brightness){
        this.brightness=brightness;
        getColorMatrix().reset();
    }

    public float getBrightness() {
        return brightness;
    }
}
