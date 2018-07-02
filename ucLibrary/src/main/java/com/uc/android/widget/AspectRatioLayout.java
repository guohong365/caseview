package com.uc.android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.uc.android.R;
import com.yalantis.ucrop.model.AspectRatio;
import com.yalantis.ucrop.view.CropImageView;
import com.yalantis.ucrop.view.widget.AspectRatioTextView;

import java.util.ArrayList;
import java.util.List;

public class AspectRatioLayout extends LinearLayout implements OptionControl {
    private static final String TAG="AspectRatioLayout";
    public AspectRatioLayout(Context context) {
        this(context, null);
    }

    public AspectRatioLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AspectRatioLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public AspectRatioLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    List<FrameLayout> cropAspectRatioViews=new ArrayList<>();
    void init(Context context, AttributeSet attrs){
        Log.d(TAG, "control is creating...");
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.AspectRatioLayout);
        int labelsId= array.getResourceId(R.styleable.AspectRatioLayout_labels, R.array.labels_aspect_ratio);
        String[] labels= context.getResources().getStringArray(labelsId);
        int ratiosId=array.getResourceId(R.styleable.AspectRatioLayout_ratios, R.array.values_aspect_ratio);
        int[] ratios=context.getResources().getIntArray(ratiosId);
        int base = array.getInt(R.styleable.AspectRatioLayout_ratio_base, 36);

        LinearLayout wrapper = findViewById(R.id.layout_aspect_ratio);
        int activeColor=context.getColor(R.color.color_active_widget);
        FrameLayout frameLayout;
        AspectRatioTextView textView;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);

        lp.weight = 1;
        ColorStateList stateList = new ColorStateList(
            new int[][]{
                new int[]{android.R.attr.state_selected},
                new int[]{0}},
            new int[]{
                activeColor,
                context.getColor(android.R.color.white)
            }
        );
        int padding=getResources().getDimensionPixelSize(R.dimen.margin_option_controls);
        LayoutInflater inflater=LayoutInflater.from(context);
        for (int i=0; i< labels.length; i++){
            AspectRatio ratio=new AspectRatio(
                labels[i], ratios[i]==0 ? CropImageView.SOURCE_IMAGE_ASPECT_RATIO : ratios[i] ,
                ratios[i]==0 ? CropImageView.SOURCE_IMAGE_ASPECT_RATIO : base);
            frameLayout = (FrameLayout) inflater.inflate(R.layout.item_aspect_ratio, null);
            frameLayout.setLayoutParams(lp);
            //frameLayout.setPadding(0, padding, 0, padding);
            textView = (AspectRatioTextView) frameLayout.getChildAt(0);
            textView.setActiveColor(activeColor);
            textView.setTextColor(stateList);
            textView.setAspectRatio(ratio);
            addView(frameLayout);
            if(i==2){
                frameLayout.setSelected(true);
            }
            cropAspectRatioViews.add(frameLayout);

            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    current=((AspectRatioTextView) ((ViewGroup) v).getChildAt(0)).getAspectRatio(v.isSelected());
                    if (!v.isSelected()) {
                        for (ViewGroup viewGroup1 : cropAspectRatioViews) {
                            viewGroup1.setSelected(viewGroup1 == v);
                        }
                    }
                    setOption("ratio", current);
                }
            });
        }

        array.recycle();
    }
    float current;
    OptionControlListener optionControlListener;

    @Override
    public void setOptionControlListener(OptionControlListener optionControlListener) {
        this.optionControlListener=optionControlListener;
    }

    @Override
    public void setOption(String name, Object value) {
        if(optionControlListener!=null){
            optionControlListener.onOptionChanged(this,name, value);
        }
    }

    @Override
    public void commit() {
        if(optionControlListener!=null){
            Bundle bundle=new Bundle();
            bundle.putFloat("ratio", current);
            optionControlListener.onCommit(this,bundle);
        }
    }

    @Override
    public void cancel() {
        if(optionControlListener!=null){
            optionControlListener.onCancel(this);
        }
    }
}
