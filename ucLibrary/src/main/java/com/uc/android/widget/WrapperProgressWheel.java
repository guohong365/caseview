package com.uc.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.uc.android.R;

public class WrapperProgressWheel extends LinearLayout {

    public interface LabelCallback{
        String getLabel(WrapperProgressWheel progressWheel, float distance, float total, float ticks);
    }
    LabelCallback labelCallback;
    ProgressWheelView.OnScrollingListener onScrollingListener;

    public void setOnScrollingListener(ProgressWheelView.OnScrollingListener onScrollingListener) {
        this.onScrollingListener = onScrollingListener;
    }

    public void setLabelCallback(LabelCallback labelCallback) {
        this.labelCallback = labelCallback;
    }

    public void setLabel(String label) {
        this.label.setText(label);
    }

    public WrapperProgressWheel(Context context) {
        this(context, null);
    }

    public WrapperProgressWheel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public WrapperProgressWheel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public WrapperProgressWheel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    TextView label;
    ProgressWheelView wheel;
    private  void init(Context context, AttributeSet attrs){
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.WrapperProgressWheel);
        int layout=array.getResourceId(R.styleable.WrapperProgressWheel_layout, -1);
        if(layout!=-1){
            LayoutInflater.from(context).inflate(layout, this, true);
        }
        label=findViewById(R.id.label);
        wheel=findViewById(R.id.wheel);
        if(array.getBoolean(R.styleable.WrapperProgressWheel_with_label, true)){
            label.setVisibility(VISIBLE);
        } else {
            label.setVisibility(GONE);
        }
        wheel.setClickable(!array.getBoolean(R.styleable.WrapperProgressWheel_read_only, false));
        wheel.setOnScrollingListener(new ProgressWheelView.OnScrollingListener() {
            @Override
            public void onScrollStart() {
                if(onScrollingListener!=null){
                    onScrollingListener.onScrollStart();
                }
            }
            @Override
            public void onScroll(float delta, float totalDistance, float ticks) {
                if(labelCallback!=null){
                    label.setText(labelCallback.getLabel(WrapperProgressWheel.this, delta, totalDistance, ticks));
                }
                if(onScrollingListener!=null){
                    onScrollingListener.onScroll(delta, totalDistance, ticks);
                }
            }
            @Override
            public void onScrollEnd() {
                if(onScrollingListener!=null){
                    onScrollingListener.onScrollEnd();
                }
            }
        });
    }
//    private  void init(Context context, AttributeSet attrs){
//        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.WrapperProgressWheel);
//        if(array.getBoolean(R.styleable.WrapperProgressWheel_with_label, true)){
//            label=new TextView(context);
//            label.setTextColor(array.getColor(R.styleable.WrapperProgressWheel_text_color,getResources().getColor(android.R.color.white, null)));
//            label.setTextSize(array.getDimension(R.styleable.WrapperProgressWheel_text_size, getResources().getDimension(R.dimen.size_label)));
//            LayoutParams layoutParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            if(getOrientation()== HORIZONTAL){
//                layoutParams.gravity= Gravity.CENTER_HORIZONTAL;
//            } else {
//                layoutParams.gravity=Gravity.CENTER_VERTICAL;
//                label.setRotation(-90);
//            }
//            label.setLayoutParams(layoutParams);
//        }
//        wheel = getOrientation() == LinearLayout.HORIZONTAL ? new HorizontalProgressWheelView(context) : new VerticalProgressWheelView(context);
//        wheel.setMiddleLineColor(array.getColor(R.styleable.WrapperProgressWheel_middle_line_color, getResources().getColor(R.color.color_wheel_middle_line, null)));
//        wheel.setLineColor(array.getColor(R.styleable.WrapperProgressWheel_line_color, getResources().getColor(R.color.color_progress_wheel_line, null)));
//        wheel.setLineLength(array.getDimensionPixelSize(R.styleable.WrapperProgressWheel_wheel_line_length, getResources().getDimensionPixelSize(R.dimen.length_progress_wheel_line)));
//        wheel.setLineWidth(array.getDimensionPixelSize(R.styleable.WrapperProgressWheel_wheel_line_width, getResources().getDimensionPixelSize(R.dimen.width_progress_wheel_line)));
//        wheel.setLineSpace(array.getDimensionPixelSize(R.styleable.WrapperProgressWheel_wheel_line_space, getResources().getDimensionPixelSize(R.dimen.margin_progress_wheel_line)));
//        wheel.setClickable(!array.getBoolean(R.styleable.WrapperProgressWheel_read_only, false));
//        wheel.setTotalScrollDistance(array.getFloat(R.styleable.WrapperProgressWheel_total_distance, 0));
//        LayoutParams layoutParams=new LayoutParams(getOrientation()==HORIZONTAL ? ViewGroup.LayoutParams.MATCH_PARENT: wheel.getLineLength(), getOrientation()==HORIZONTAL ? wheel.getLineLength() : ViewGroup.LayoutParams.MATCH_PARENT );
//        wheel.setLayoutParams(layoutParams);
//        wheel.setOnScrollingListener(new ProgressWheelView.OnScrollingListener() {
//            @Override
//            public void onScrollStart() {
//                if(onScrollingListener!=null){
//                    onScrollingListener.onScrollStart();
//                }
//            }
//
//            @Override
//            public void onScroll(float delta, float totalDistance, float ticks) {
//                if(labelCallback!=null){
//                    label.setText(labelCallback.getLabel(WrapperProgressWheel.this, delta, totalDistance, ticks));
//                }
//                if(onScrollingListener!=null){
//                    onScroll(delta, totalDistance, ticks);
//                }
//            }
//            @Override
//            public void onScrollEnd() {
//                if(onScrollingListener!=null){
//                    onScrollEnd();
//                }
//            }
//        });
//    }
}
