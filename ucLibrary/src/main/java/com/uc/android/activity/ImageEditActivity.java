package com.uc.android.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.*;
import android.view.animation.AccelerateInterpolator;
import android.widget.*;
import com.uc.android.R;
import com.uc.android.image.ImageFilter;
import com.uc.android.widget.*;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.callback.CropBoundsChangeListener;
import com.yalantis.ucrop.callback.OverlayViewChangeListener;
import com.yalantis.ucrop.view.CropImageView;
import com.yalantis.ucrop.view.OverlayView;
import com.yalantis.ucrop.view.TransformImageView;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import static com.uc.android.widget.ImageEditorLayout.*;

public class ImageEditActivity extends BaseActivity {

    public static final int IMAGE_PROCESS_RESULT_ERROR = -1;
    public static final String EXTRA_PROCESS_ERROR = "EXTRA_PROCESS_ERROR";

    private int activeColor;
    private boolean showLoader = true;
    private WrapperProgressWheel layoutRotationWheel;
    private WrapperProgressWheel layoutScaleWheel;
    private ViewGroup layoutCropOptions;
    private ViewGroup layoutFilterOptions;
    private ViewGroup layoutTuneOptions;
    private ViewGroup layoutMarkOptions;
    private EditModeSelectionLayout modeSelectionLayout;
    private ImageEditorLayout imageEditorLayout;
    private View blockingView;

    TransformImageView.TransformImageListener imageListener = new TransformImageView.TransformImageListener() {
        @Override
        public void onLoadComplete() {
            imageEditorLayout.animate().alpha(1).setDuration(300).setInterpolator(new AccelerateInterpolator());
            blockingView.setClickable(false);
            showLoader = false;
            supportInvalidateOptionsMenu();
        }

        @Override
        public void onLoadFailure(@NonNull Exception e) {
            setResultError(e);
            finish();
        }

        @Override
        public void onRotate(float currentAngle) {
            layoutRotationWheel.setLabel(String.format("%.1f°", currentAngle));
        }

        @Override
        public void onScale(float currentScale) {
            if(layoutScaleWheel!=null){
                layoutScaleWheel.setLabel(String.format("%d%%", (int)(currentScale*100)));
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_process_activity);
        activeColor = getColor(R.color.color_active_widget);
        final Intent intent = getIntent();
        setupViews( intent);
        setImageData(intent);
        setInitialState();
        addBlockingView();
        Log.d(TAG, "onCreate: created.......");
    }
    void setInitialState(){
        imageEditorLayout.setMode(MODE_VIEW);
    }
    void setImageData(Intent intent) {
        Uri inputUri = intent.getParcelableExtra(UCrop.EXTRA_INPUT_URI);
        Uri outputUri = intent.getParcelableExtra(UCrop.EXTRA_OUTPUT_URI);
        if (inputUri != null && outputUri != null) {
            try {
                imageEditorLayout.setImageUri(inputUri, outputUri);
            } catch (Exception e) {
                setResultError(e);
                finish();
            }
        } else {
            setResultError(new NullPointerException(getString(R.string.ucrop_error_input_data_is_absent)));
            finish();
        }
    }

    private void addBlockingView() {
        if (blockingView == null) {
            blockingView = new View(this);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.addRule(RelativeLayout.BELOW, R.id.tool_bar);
            blockingView.setLayoutParams(lp);
            blockingView.setClickable(true);
            RelativeLayout layout = findViewById(R.id.layout_root);
            layout.addView(blockingView);
        }
    }

    private void setupViews(Intent intent) {
        setupAppBar();
        initiateViews();
        setupAspectRatioWidget();
        bindWheel();
        setupTuneWidget();
        setupMarkWidget();
    }
    static final int ROTATE_WIDGET_SENSITIVITY_COEFFICIENT = 42;
    static final int SCALE_WIDGET_SENSITIVITY_COEFFICIENT = 15000;

    void bindWheel(){
        layoutScaleWheel.setOnScrollingListener(new ProgressWheelView.OnScrollingListener() {
            @Override
            public void onScrollStart() {
                imageEditorLayout.getImageEditView().cancelAllAnimations();
            }

            @Override
            public void onScroll(float delta, float totalDistance, float ticks) {
                imageEditorLayout.zoom( delta  / SCALE_WIDGET_SENSITIVITY_COEFFICIENT);
            }

            @Override
            public void onScrollEnd() {
                if(imageEditorLayout.getMode()==MODE_CROP){
                    imageEditorLayout.setImageToWrapCropBounds();
                }
            }
        });
        layoutRotationWheel.setOnScrollingListener(new ProgressWheelView.OnScrollingListener() {
            @Override
            public void onScrollStart() {
                imageEditorLayout.cancelAllAnimations();
            }

            @Override
            public void onScroll(float delta, float totalDistance, float ticks) {
                imageEditorLayout.rotate(delta / ROTATE_WIDGET_SENSITIVITY_COEFFICIENT);
            }

            @Override
            public void onScrollEnd() {
                if(imageEditorLayout.getMode()==MODE_CROP){
                    imageEditorLayout.setImageToWrapCropBounds();
                }
            }
        });
    }
    private void setupAspectRatioWidget() {
        OptionBarLayout layout=findViewById(R.id.layout_crop_options);
        layout.setOptionControlListener(new OptionControlListener() {
            @Override
            public void onOptionChanged(OptionControl sender, String name, Object value) {
                Log.d(TAG, "ratio changed : ["+name+"]=["+value+"]");
                float ratio=(float)value;
                imageEditorLayout.setTargetAspectRatio(ratio);
            }
            @Override
            public void onCommit(OptionControl sender, Bundle Options) {

            }
            @Override
            public void onCancel(OptionControl sender) {
            }
        });
    }

    ColorSelectionLayout.OnColorSelectedListener onColorSelectedListener=new ColorSelectionLayout.OnColorSelectedListener() {
        @Override
        public void onColorSelected(int selectedColor) {
            Toast.makeText(ImageEditActivity.this, String.format("clicked :[%d]" , selectedColor), Toast.LENGTH_SHORT).show();
        }
    };
    MarkSelectionLayout.OnMarkSelectedListener onMarkSelectedListener=new MarkSelectionLayout.OnMarkSelectedListener() {
        @Override
        public void onMarkSelected(int markId) {
            Toast.makeText(ImageEditActivity.this, String.format("clicked : [%d]", markId), Toast.LENGTH_SHORT).show();
        }
    };
    private void setupMarkWidget(){
        MarkSelectionLayout markSelectionLayout=findViewById(R.id.layout_mark_selection);
        markSelectionLayout.setOnMarkSelectedListener(onMarkSelectedListener);
        ColorSelectionLayout colorSelectionLayout=findViewById(R.id.layout_color_selection);
        colorSelectionLayout.setOnColorSelectedListener(onColorSelectedListener);
    }


    private void setupFilterWidget(){
        List<ImageFilter> processors=new ArrayList<>();
    }
    OptionBarLayout tuneOptionsLayout;
    OptionControlListener optionControlListener=new OptionControlListener() {
        @Override
        public void onOptionChanged(OptionControl sender, String name, Object value) {
            switch (name){
                case TuneOptionsLayout.AS_BRIGHTNESS:
                    imageEditorLayout.setBrightness(((float)value)*2);
                    break;
                case TuneOptionsLayout.AS_CONTRAST:
                    imageEditorLayout.setContrast(((float)value)*2);
                    break;
                case TuneOptionsLayout.AS_SATURATION:
                    imageEditorLayout.setSaturation(((float)value)*3);
                    break;
            }
        }

        @Override
        public void onCommit(OptionControl sender, Bundle Options) {

        }

        @Override
        public void onCancel(OptionControl sender) {

        }
    };

    private void setupTuneWidget() {
        tuneOptionsLayout=findViewById(R.id.layout_tune_options);
        tuneOptionsLayout.setOptionControlListener(optionControlListener);
    }

    protected void setResultError(Throwable throwable) {
        setResult(IMAGE_PROCESS_RESULT_ERROR, new Intent().putExtra(EXTRA_PROCESS_ERROR, throwable));
    }

    void setModeChanged(int mode) {
        imageEditorLayout.setMode(mode);
        layoutCropOptions.setVisibility(mode==MODE_CROP ? VISIBLE:GONE);
        layoutScaleWheel.setVisibility(mode==MODE_CROP ? VISIBLE:GONE);
        layoutRotationWheel.setVisibility(mode==MODE_CROP ? VISIBLE:GONE);

        layoutFilterOptions.setVisibility(mode == MODE_FILTER ? View.VISIBLE : View.GONE);
        layoutTuneOptions.setVisibility(mode == MODE_TUNE ? View.VISIBLE : View.GONE);
        layoutMarkOptions.setVisibility(mode == MODE_MARK ? View.VISIBLE : View.GONE);
    }

    private void initiateViews() {
        imageEditorLayout = findViewById(R.id.layout_image_editor);
        imageEditorLayout.setTransformImageListener(imageListener);
        layoutRotationWheel = findViewById(R.id.layout_rotate_wheel);
        layoutScaleWheel = findViewById(R.id.layout_scale_wheel);
        layoutCropOptions = findViewById(R.id.layout_crop_options);
        layoutFilterOptions = findViewById(R.id.layout_filter_options);
        layoutTuneOptions = findViewById(R.id.layout_tune_options);
        layoutMarkOptions = findViewById(R.id.layout_mark_options);
        modeSelectionLayout=findViewById(R.id.wrapper_actions);
        modeSelectionLayout.addOnEditModeChangeListener(new OnEditModeChangeListener() {
            @Override
            public void onModeChanged(Object sender, int mode) {
                Log.d(TAG, "onModeChanged: " + mode);
                setModeChanged(mode);
            }
        });
    }

    private void setupAppBar() {
        final Toolbar toolbar = findViewById(R.id.tool_bar);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_action_cancel).mutate();
        toolbar.setNavigationIcon(drawable);

        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_process_activity, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_accept).setVisible(!showLoader);
        menu.findItem(R.id.menu_loader).setVisible(showLoader);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.menu_accept) {
            saveImage();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (imageEditorLayout != null) {
            imageEditorLayout.cancelAllAnimations();
        }
    }

    protected void setResultUri(Uri uri, float resultAspectRatio, int offsetX, int offsetY, int imageWidth, int imageHeight) {
        setResult(RESULT_OK, new Intent()
            .putExtra(UCrop.EXTRA_OUTPUT_URI, uri)
            .putExtra(UCrop.EXTRA_OUTPUT_CROP_ASPECT_RATIO, resultAspectRatio)
            .putExtra(UCrop.EXTRA_OUTPUT_IMAGE_WIDTH, imageWidth)
            .putExtra(UCrop.EXTRA_OUTPUT_IMAGE_HEIGHT, imageHeight)
            .putExtra(UCrop.EXTRA_OUTPUT_OFFSET_X, offsetX)
            .putExtra(UCrop.EXTRA_OUTPUT_OFFSET_Y, offsetY)
        );
    }

    private void saveImage() {
        blockingView.setClickable(true);
        showLoader = true;
        supportInvalidateOptionsMenu();
        imageEditorLayout.saveImage(Bitmap.CompressFormat.JPEG, 100, new BitmapCropCallback() {
            @Override
            public void onBitmapCropped(@NonNull Uri resultUri, int offsetX, int offsetY, int imageWidth, int imageHeight) {
                setResultUri(resultUri, imageEditorLayout.getTargetAspectRatio(), offsetX, offsetY, imageWidth, imageHeight);
                finish();
            }

            @Override
            public void onCropFailure(@NonNull Throwable t) {
                setResultError(t);
                finish();
            }
        });
    }

}
