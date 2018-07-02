package com.uc.android.image;

public class ContrastModifier extends ColorMatrixModifier {
    public static final String OPTION_CONTRAST="contrast";

    @Override
    public void onOptionChanged(Object sender, String key, Object newValue) {
        if(key.equals(OPTION_CONTRAST)){
            float scale=(float)newValue;
            getColorMatrix().reset();
            getColorMatrix().setScale(scale, scale,scale, 1);
        }
    }
}
