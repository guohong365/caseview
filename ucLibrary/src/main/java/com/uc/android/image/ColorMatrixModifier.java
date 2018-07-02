package com.uc.android.image;

import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import com.uc.android.image.callback.OnOptionChangeListener;
import com.uc.android.model.ImageModifierOptions;

public class ColorMatrixModifier implements ImageModifier, OnOptionChangeListener{
    private ColorMatrix colorMatrix=new ColorMatrix();

    public ColorMatrix getColorMatrix() {
        return colorMatrix;
    }

    @Override
    public void applyForPreview(BitmapDrawable drawable, ImageModifierOptions options) {
        drawable.setColorFilter(new ColorMatrixColorFilter(getColorMatrix()));
    }

    @Override
    public void applyTo(BitmapDrawable drawable, ImageModifierOptions options) {
        Bitmap original=drawable.getBitmap();
        if(original==null) return;
        Bitmap result=Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight());
        Canvas canvas=new Canvas(original);
        Paint paint=new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(new ColorMatrixColorFilter(getColorMatrix()));
        canvas.drawBitmap(result, 0, 0, paint);
        result.recycle();
    }

    @Override
    public void onOptionChanged(Object sender, String key, Object newValue) {
    }
}
