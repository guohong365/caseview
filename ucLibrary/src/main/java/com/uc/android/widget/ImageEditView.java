package com.uc.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import com.uc.android.image.ImageFilter;
import com.yalantis.ucrop.util.FastBitmapDrawable;
import com.yalantis.ucrop.view.GestureCropImageView;

public class ImageEditView extends GestureCropImageView {

    private float brightness;
    private ColorMatrix brightnessMatrix=new ColorMatrix();
    private float contrast;
    private ColorMatrix contrastMatrix=new ColorMatrix();
    private float saturation;
    private ColorMatrix saturationMatrix=new ColorMatrix();

    public ImageEditView(Context context) {
        super(context);
    }

    public ImageEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageEditView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void processStyledAttributes(@NonNull TypedArray a) {
        super.processStyledAttributes(a);
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
        invalidate();
    }

    public void setContrast(float contrast) {
        this.contrast = contrast;
        contrastMatrix.reset();
        contrastMatrix.setScale(contrast, contrast, contrast, 1);
        invalidate();
    }

    public float getContrast() {
        return contrast;
    }

    public void setSaturation(float saturation) {
        if(saturation >1 ) saturation=1;
        else if(saturation<0) saturation=0;
        this.saturation = saturation;
        saturationMatrix.reset();
        saturationMatrix.setSaturation(saturation);
        invalidate();
    }

    public float getSaturation() {
        return saturation;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(getDrawable()!=null) {
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.postConcat(brightnessMatrix);
            colorMatrix.postConcat(contrastMatrix);
            colorMatrix.postConcat(saturationMatrix);
            getDrawable().setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        }
        super.onDraw(canvas);
    }

    public void applyColorFilter(ColorMatrix colorMatrix){
        FastBitmapDrawable drawable=(FastBitmapDrawable)getDrawable();
        Bitmap source=drawable.getBitmap();
        Bitmap target=Bitmap.createBitmap(source.getWidth(), source.getHeight(), source.getConfig());
        Canvas canvas=new Canvas(target);
        Paint paint=new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(drawable.getColorFilter());
        canvas.drawBitmap(source, 0, 0, paint);
        drawable.setColorFilter(null);
        drawable.setBitmap(target);
    }

    public void applyFilter(@NonNull ImageFilter filter){
        FastBitmapDrawable drawable=(FastBitmapDrawable)getDrawable();
        Bitmap source=drawable.getBitmap();
        Bitmap target=filter.apply(source);
        drawable.setBitmap(target);
    }
}
