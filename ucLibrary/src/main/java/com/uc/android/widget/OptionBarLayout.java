package com.uc.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.uc.android.R;

public class OptionBarLayout extends RelativeLayout implements OptionControlListener {
    OptionControlListener optionControlListener;
    private static  final String TAG="OptionBarLayout";
    public void setOptionControlListener(OptionControlListener optionControlListener) {
        this.optionControlListener = optionControlListener;
    }

    public OptionBarLayout(Context context) {
        this(context, null);
    }

    public OptionBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public OptionBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public OptionBarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    ImageView btnApply;
    ImageView btnCancel;
    OptionControl optionControl;
    OnClickListener onClickListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.option_action_apply){
                optionControl.commit();
            } else if(v.getId()==R.id.option_action_cancel){
                optionControl.cancel();
            }
        }
    };
    private void init(Context context, AttributeSet attrs){
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.OptionBarLayout);
        LayoutInflater inflater=LayoutInflater.from(getContext());
        inflater.inflate(R.layout.layout_option_bar, this, true);
        btnApply=findViewById(R.id.option_action_apply);
        btnCancel=findViewById(R.id.option_action_cancel);
        if(array.getBoolean(R.styleable.OptionBarLayout_show_apply_buttons, true)){
            btnApply.setVisibility(VISIBLE);
            btnCancel.setVisibility(VISIBLE);
            btnApply.setOnClickListener(onClickListener);
            btnCancel.setOnClickListener(onClickListener);
        } else {
            btnApply.setVisibility(GONE);
            btnCancel.setVisibility(GONE);
        }
        int subLayout=array.getResourceId(R.styleable.OptionBarLayout_option_control, -1);
        if(subLayout!=-1) {
            FrameLayout frameLayout = findViewById(R.id.option_controls);
            inflater.inflate(subLayout, frameLayout, true);
            optionControl=(OptionControl) frameLayout.getChildAt(0);
            optionControl.setOptionControlListener(this);
        } else {
            Log.e(TAG, "no layout resource found.");
        }
    }

    @Override
    public void onOptionChanged(OptionControl sender, String name, Object value) {
        if(this.optionControlListener!=null) {
            this.optionControlListener.onOptionChanged(sender, name, value);
        }
    }

    @Override
    public void onCommit(OptionControl sender, Bundle options) {
        if(this.optionControlListener!=null) {
            this.optionControlListener.onCommit(sender, options);
        }
    }

    @Override
    public void onCancel(OptionControl sender) {
        if(this.optionControlListener!=null){
            this.optionControlListener.onCancel(sender);
        }
    }
}
