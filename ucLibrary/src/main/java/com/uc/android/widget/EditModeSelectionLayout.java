package com.uc.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.uc.android.R;

import java.util.ArrayList;
import java.util.List;

import static com.uc.android.widget.ImageEditorLayout.*;

public class EditModeSelectionLayout extends LinearLayout implements EditModeChangedNotifier {
    List<OnEditModeChangeListener> modeChangeListeners=new ArrayList<>();

    public EditModeSelectionLayout(Context context) {
        this(context, null);
    }

    public EditModeSelectionLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditModeSelectionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public EditModeSelectionLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    ViewGroup modeCrop;
    ViewGroup modeFilter;
    ViewGroup modeTune;
    ViewGroup modeMark;
    private void selectModeActionView(int id){
        modeCrop.setSelected(id==modeCrop.getId());
        modeFilter.setSelected(id==modeFilter.getId());
        modeTune.setSelected(id==modeTune.getId());
        modeMark.setSelected(id==modeMark.getId());

    }
    OnClickListener itemClickListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            selectModeActionView(v.getId());
            notifyEditModeChanged((int)v.getTag());
        }
    };
    private void init(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.layout_mode_selection_box, this, true);
        modeCrop=findViewById(R.id.layout_action_geometry);
        modeCrop.setTag(MODE_CROP);
        modeFilter=findViewById(R.id.layout_action_filter);
        modeFilter.setTag(MODE_FILTER);
        modeTune=findViewById(R.id.layout_action_tune);
        modeTune.setTag(MODE_TUNE);
        modeMark=findViewById(R.id.layout_action_mark);
        modeMark.setTag(MODE_MARK);

        modeCrop.setOnClickListener(itemClickListener);
        modeFilter.setOnClickListener(itemClickListener);
        modeTune.setOnClickListener(itemClickListener);
        modeMark.setOnClickListener(itemClickListener);

    }


    @Override
    public void addOnEditModeChangeListener(OnEditModeChangeListener onEditModeChangeListener) {
        modeChangeListeners.add(onEditModeChangeListener);
    }

    @Override
    public void notifyEditModeChanged(int mode) {
        for(OnEditModeChangeListener listener:modeChangeListeners){
            listener.onModeChanged(this, mode);
        }
    }
}
