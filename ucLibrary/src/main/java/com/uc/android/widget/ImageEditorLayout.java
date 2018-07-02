package com.uc.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.uc.android.R;
import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.callback.CropBoundsChangeListener;
import com.yalantis.ucrop.callback.OverlayViewChangeListener;
import com.yalantis.ucrop.view.TransformImageView;

public class ImageEditorLayout extends FrameLayout  {
    private String TAG="ImageEditorLayout";
    public static final int MODE_VIEW = -1;
    public static final int MODE_GEOMETRY = 0;
    public static final int MODE_CROP = 1;
    public static final int MODE_FILTER = 2;
    public static final int MODE_TUNE = 3;
    public static final int MODE_MARK = 4;
    private TransformImageView.TransformImageListener transformImageListener;

    public ImageEditorLayout(@NonNull Context context) {
        this(context, null);
    }

    public ImageEditorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageEditorLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ImageEditorLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    ImageEditView imageEditView;
    ImageEditViewOverlay imageEditViewOverlay;
    int mode;

    public void setMode(int mode) {
       this.mode = mode;
       updateViewState();
    }
    public int getMode() {
        return mode;
    }
    void updateViewState(){
        switch (mode){
            case MODE_VIEW:
                imageEditViewOverlay.setShowCropRect(false);
                imageEditView.setRotateEnabled(true);
                imageEditView.setScaleEnabled(true);
                Log.d(TAG, "updateViewState: mode=["+mode+"] MODE_VIEW");

                break;
            case MODE_GEOMETRY:
                imageEditViewOverlay.setShowCropRect(false);
                imageEditView.setRotateEnabled(true);
                imageEditView.setScaleEnabled(true);
                Log.d(TAG, "updateViewState: mode=["+mode+"] MODE_GEOMETRY");

                break;
            case MODE_CROP:
                imageEditViewOverlay.setShowCropRect(true);
                imageEditView.setRotateEnabled(true);
                imageEditView.setScaleEnabled(true);
                Log.d(TAG, "updateViewState: mode=["+mode+"] MODE_CROP");
                break;
            case MODE_FILTER:
            case MODE_TUNE:
            case MODE_MARK:
                imageEditViewOverlay.setShowCropRect(false);
                imageEditView.setRotateEnabled(false);
                imageEditView.setScaleEnabled(true);
                Log.d(TAG, "updateViewState: mode=["+mode+"] ");
                break;
        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ImageEditorLayout);
        LayoutInflater.from(context).inflate(R.layout.layout_image_editor, this, true);
        imageEditView = findViewById(R.id.image_edit_view);
        imageEditViewOverlay = findViewById(R.id.image_edit_overlay);
        setListenersToViews();
        array.recycle();
    }

    private void setListenersToViews() {
        imageEditView.setCropBoundsChangeListener(new CropBoundsChangeListener() {
            @Override
            public void onCropAspectRatioChanged(float cropRatio) {
                imageEditViewOverlay.setTargetAspectRatio(cropRatio);
            }
        });
        imageEditViewOverlay.setOverlayViewChangeListener(new OverlayViewChangeListener() {
            @Override
            public void onCropRectUpdated(RectF cropRect) {
                imageEditView.setCropRect(cropRect);
            }
        });
        imageEditView.setTransformImageListener(new TransformImageView.TransformImageListener() {
            @Override
            public void onLoadComplete() {
                if(transformImageListener!=null){
                    transformImageListener.onLoadComplete();
                }
            }

            @Override
            public void onLoadFailure(@NonNull Exception e) {
                if(transformImageListener!=null){
                    transformImageListener.onLoadFailure(e);
                }
            }

            @Override
            public void onRotate(float currentAngle) {
                if(transformImageListener!=null){
                    transformImageListener.onRotate(currentAngle);
                }
            }

            @Override
            public void onScale(float currentScale) {
                if(transformImageListener!=null){
                    transformImageListener.onScale(currentScale);
                }
            }
        });
    }
    public void setImageUri(Uri source, Uri target) throws Exception {
        imageEditView.setImageUri(source, target);
    }

    public void cancelAllAnimations(){
        imageEditView.cancelAllAnimations();
    }
    public void setImageToWrapCropBounds(){
        imageEditView.setImageToWrapCropBounds();
    }
    public void zoom(float deltaScale){
        float scale=imageEditView.getCurrentScale() + deltaScale*(imageEditView.getMaxScale()-imageEditView.getMinScale());
        if(deltaScale>0){
            imageEditView.zoomInImage(scale);
        } else {
            imageEditView.zoomOutImage(scale);
        }
    }
    public void rotate(float deltaAngle){
        imageEditView.postRotate(deltaAngle);
    }

    public ImageEditView getImageEditView() {
        return imageEditView;
    }

    public void setTargetAspectRatio(float ratio){
        imageEditView.setTargetAspectRatio(ratio);
        imageEditView.setImageToWrapCropBounds();
    }

    public ImageEditViewOverlay getImageEditViewOverlay() {
        return imageEditViewOverlay;
    }

    public void setBrightness(float v) {
        imageEditView.setBrightness(v);
    }
    public float getBrightness(){
        return imageEditView.getBrightness();
    }
    public void setSaturation(float saturation){
        imageEditView.setSaturation(saturation);
    }
    public float getSaturation(){
        return imageEditView.getSaturation();
    }
    public void setContrast(float contrast){
        imageEditView.setContrast(contrast);
    }
    public float getContrast(){
        return imageEditView.getContrast();
    }
    public float getTargetAspectRatio(){
        return imageEditView.getTargetAspectRatio();
    }
    public void setTransformImageListener(TransformImageView.TransformImageListener transformImageListener) {
        this.transformImageListener = transformImageListener;
    }
    public void saveImage(Bitmap.CompressFormat format, int quality, BitmapCropCallback callback){
        imageEditView.cropAndSaveImage(format, quality, callback);
    }
}