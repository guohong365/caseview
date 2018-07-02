package com.uc.android.image;

public class ContrastFilter extends ColorMatrixImageFilter {
    public static final String FILTER_CONTRAST="filter.contrast";

    private float contrast;

    public ContrastFilter(float contrast){
        super( 0L, FILTER_CONTRAST);
        this.contrast=contrast;
    }

    public float getContrast() {
        return contrast;
    }

    public void setContrast(float contrast) {
        this.contrast = contrast;
        getColorMatrix().reset();
        getColorMatrix().setScale(contrast, contrast, contrast, 1.0f);
    }
}
