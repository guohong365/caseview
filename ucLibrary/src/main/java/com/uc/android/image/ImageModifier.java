package com.uc.android.image;

import android.graphics.drawable.BitmapDrawable;
import com.uc.android.model.ImageModifierOptions;

public interface ImageModifier{
    void applyForPreview(BitmapDrawable drawable, ImageModifierOptions options);
    void applyTo(BitmapDrawable drawable, ImageModifierOptions options);
}
