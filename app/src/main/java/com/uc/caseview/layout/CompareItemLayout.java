package com.uc.caseview.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.uc.android.camera.MultiToggleImageButton;
import com.uc.caseview.R;

/**
 * Created by guohog on 2018/1/15.
 */

public class CompareItemLayout extends FrameLayout {
    private ImageView mImageView;
    private SeekBar mSeekBar;
    private ImageButton mTakePhoto;
    private ImageButton mEditPhoto;
    private MultiToggleImageButton mMarks;
    private Bitmap mBitmap;

    public CompareItemLayout(Context context) {
        super(context);
    }

    public CompareItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CompareItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CompareItemLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView=findViewById(R.id.compare_item_image);


        mSeekBar=findViewById(R.id.zoom_seekbar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mTakePhoto=findViewById(R.id.btn_take_photo);
        mEditPhoto=findViewById(R.id.btn_edit_photo);
        mMarks=findViewById(R.id.btn_marks);

    }
}
