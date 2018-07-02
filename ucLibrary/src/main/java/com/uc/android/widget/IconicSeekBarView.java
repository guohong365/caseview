package com.uc.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.uc.android.R;
import com.uc.android.image.ImageModifier;
import com.uc.android.model.ImageModifierOptions;

public class IconicSeekBarView extends LinearLayout
    implements SeekBar.OnSeekBarChangeListener{

    private static final int DEFAULT_MAX = 100;
    private static final int DEFAULT_PROGRESS=0;


    public interface OnProgressChangeListener{
        void onChanged(IconicSeekBarView sender, int progress, int max);
    }

    public IconicSeekBarView(Context context) {
        this(context, null);
    }

    public IconicSeekBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconicSeekBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public IconicSeekBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);


    }

    private void init(Context context, AttributeSet attrs){
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.IconicSeekBarView);
        int subLayoutId=array.getResourceId(R.styleable.IconicSeekBarView_layout_view, -1);
        if(subLayoutId<0) return;
        LayoutInflater inflater=LayoutInflater.from(context);
        inflater.inflate(subLayoutId, this, true);
        int id=array.getResourceId(R.styleable.IconicSeekBarView_layout_icon, -1);
        icon=findViewById(id);
        id=array.getResourceId(R.styleable.IconicSeekBarView_layout_seek_bar, -1);
        seekBar=findViewById(id);

        Drawable iconDrawable=array.getDrawable(R.styleable.IconicSeekBarView_icon);
        setIcon(iconDrawable);
        setMax(array.getInt(R.styleable.IconicSeekBarView_max, DEFAULT_MAX));
        setProgress(array.getInt(R.styleable.IconicSeekBarView_progress, DEFAULT_PROGRESS));
        seekBar.setOnSeekBarChangeListener(this);
        array.recycle();
    }

    private ImageView icon;
    private SeekBar seekBar;

    public void setIcon(int resId){
        icon.setImageResource(resId);
    }
    public void setIcon(Drawable iconDrawable){
        if(iconDrawable!=null){
            this.icon.setImageDrawable(iconDrawable);
            this.icon.setVisibility(VISIBLE);
        } else {
            this.icon.setVisibility(GONE);
        }
    }

    public void setMax(int max){
        seekBar.setMax(max);
    }

    public void setProgress(int progress){
        seekBar.setProgress(progress);
    }

    public int getMax(){
        return seekBar.getMax();
    }
    public int getProgress(){
        return seekBar.getProgress();
    }
    private OnProgressChangeListener onProgressChangeListener;

    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        this.onProgressChangeListener = onProgressChangeListener;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(onProgressChangeListener!=null){
            onProgressChangeListener.onChanged(this, progress, seekBar.getMax());
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
