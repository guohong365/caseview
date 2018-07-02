package com.uc.android.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.uc.android.R;
import com.uc.android.model.ColorItem;

public class ColorSelectionLayout extends LinearLayout {
    private static final String TAG="ColorSelectionLayout";

    public interface OnColorSelectedListener{
        void onColorSelected(int selectedColor);
    }
    private OnClickListener colorItemClickListener=new OnClickListener() {
        @Override
        public void onClick(View v) {

            if(onColorSelectedListener!=null){
                ColorItemView view=(ColorItemView)v;
                v.setSelected(true);
                onColorSelectedListener.onColorSelected(view.getColorItem().getColor());
            }
        }
    };

    private OnColorSelectedListener onColorSelectedListener;

    public ColorSelectionLayout(Context context) {
        this(context, null);
    }

    public ColorSelectionLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorSelectionLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        buildColorSelections();
    }

    public ColorSelectionLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        buildColorSelections();
    }

    public void setOnColorSelectedListener(OnColorSelectedListener onColorSelectedListener) {
        this.onColorSelectedListener = onColorSelectedListener;
    }

    public void buildColorSelections() {
        Log.d(TAG, "build selections....");
        int[] colors=getResources().getIntArray(R.array.mark_colors);
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        for(int i=0; i< colors.length; i++){
            ColorItemView itemView= (ColorItemView) layoutInflater.inflate(R.layout.item_color, null);
            //itemView.setLayoutParams(lp);
            itemView.setClickable(true);
            itemView.setOnClickListener(colorItemClickListener);
            itemView.setColorItem(new ColorItem(colors[i]));
            addView(itemView);
        }
    }
}
