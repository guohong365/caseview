package com.uc.android.image;

import android.graphics.*;

public class ColorMatrixImageFilter extends AbstractImageFilter {
    public static final String CATEGORY_COLOR_MATRIX="filter.category.color";
    private ColorMatrix colorMatrix=new ColorMatrix();
    protected ColorMatrixImageFilter(Long id, String title) {
        super(CATEGORY_COLOR_MATRIX, id, title);
    }

    public void setMatrix(float[] colorMatrix) {
        this.colorMatrix.set(colorMatrix);
    }

    public ColorMatrix getColorMatrix() {
        return colorMatrix;
    }

    @Override
    public Bitmap apply(Bitmap input) {
        Bitmap result=Bitmap.createBitmap(input.getWidth(), input.getHeight(), input.getConfig());
        Canvas canvas=new Canvas(result);
        Paint paint=new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(new ColorMatrixColorFilter(getColorMatrix()));
        canvas.drawBitmap(result, 0,0, paint);
        return result;
    }
}
