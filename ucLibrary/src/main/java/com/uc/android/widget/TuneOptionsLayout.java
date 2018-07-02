package com.uc.android.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.uc.android.R;
import com.yalantis.ucrop.util.SelectedStateListDrawable;

public class TuneOptionsLayout extends LinearLayout implements OptionControl {
    OptionControlListener optionControlListener;

    public TuneOptionsLayout(Context context) {
        super(context);
    }

    public TuneOptionsLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TuneOptionsLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TuneOptionsLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public static final  String AS_BRIGHTNESS="brightness";
    public static final  String AS_CONTRAST="contrast";
    public static final  String AS_SATURATION="saturation";
    static final String[] types={AS_BRIGHTNESS, AS_CONTRAST, AS_SATURATION};
    int currentType=0;

    SeekBar seekBar;

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser) setOption(types[currentType], (float)progress/seekBar.getMax());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    OnClickListener onClickListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i=0; i<3; i ++){
                if(buttons[i].getId()==v.getId())
                {
                    if(currentType==i) break;
                    else {
                        optionControlListener.onCancel(TuneOptionsLayout.this);
                        buttons[i].setSelected(true);
                        currentType=i;
                        seekBar.setProgress(100);
                    }
                } else {
                    buttons[i].setSelected(false);
                }
            }
        }
    };
    ImageView[] buttons=new ImageView[3];
    @ColorInt
    int activeColor;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        activeColor=getContext().getResources().getColor(R.color.color_active_widget, null);
        seekBar=findViewById(R.id.option_seek_bar);
        seekBar.setMax(200);
        seekBar.setProgress(100);
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

        buttons[0]=findViewById(R.id.btn_brightness);
        buttons[1]=findViewById(R.id.btn_contrast);
        buttons[2]=findViewById(R.id.btn_saturation);
        for(ImageView view:buttons) {
            view.setOnClickListener(onClickListener);
            view.setImageDrawable(new SelectedStateListDrawable(view.getDrawable(), activeColor));
        }
        buttons[0].setSelected(true);
    }

    @Override
    public void setOptionControlListener(OptionControlListener optionControlListener) {
        this.optionControlListener=optionControlListener;
    }

    @Override
    public void setOption(String name, Object value) {
        if(this.optionControlListener!=null){
            this.optionControlListener.onOptionChanged(this,name, value);
        }
    }

    @Override
    public void commit() {
        if(this.optionControlListener!=null){
            Bundle bundle=new Bundle();
            bundle.putFloat(types[currentType], (float) seekBar.getProgress()/seekBar.getMax());
            this.optionControlListener.onCommit(this, bundle);
        }
    }

    @Override
    public void cancel() {
        if(this.optionControlListener!=null){
            this.optionControlListener.onCancel(this);
        }
    }
}
