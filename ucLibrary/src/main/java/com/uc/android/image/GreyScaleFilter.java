package com.uc.android.image;

public class GreyScaleFilter extends ColorMatrixImageFilter {
    public static final String FILTER_GREY_SCALE="filter.greyScale";
    private static final float[] array=new float[]{
        0.299f, 0.587f, 0.114f, 0, 0,
        0.299f, 0.587f, 0.114f, 0, 0,
        0.299f, 0.587f, 0.114f, 0, 0,
             0,      0,      0, 1, 0
    };
    public GreyScaleFilter(){
        super(0L, FILTER_GREY_SCALE);
        setMatrix(array);
    }
}
