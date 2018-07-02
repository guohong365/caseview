package com.uc.android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.uc.android.R;
import com.uc.android.model.ActionItem;
import com.yalantis.ucrop.util.SelectedStateListDrawable;

public class ActionItemView extends LinearLayout {
    ActionItem actionItem=new ActionItem("actionItem", -1, 0,  STYLE_LABEL_ONLY);
    public ActionItemView(Context context) {
        this(context, null);
    }

    public ActionItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ActionItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public ActionItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void setActionItem(@NonNull ActionItem actionItem) {
        this.actionItem.copyFrom(actionItem);
        this.setStyle(actionItem.getStyle());
        applyItemInfo(getContext().getResources().getDrawable(actionItem.getIcon(), null), actionItem.getLabel(), actionItem.getActiveColor());
        invalidate();
    }

    public ActionItem getActionItem() {
        return actionItem;
    }
    private ImageView actionIcon;
    private TextView actionLabel;
    public static final int STYLE_ICON_ONLY=0;
    public static final int STYLE_LABEL_ONLY=1;
    public static final int STYLE_BOTH=2;
    private void init(Context context, AttributeSet attrs){
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.ActionItemView);
        int layout=array.getResourceId(R.styleable.ActionItemView_layout, R.layout.item_action);
        LayoutInflater.from(context).inflate(layout, this, true);
        actionIcon=findViewById(R.id.action_item_image);
        actionLabel=findViewById(R.id.action_item_label);
        setClickable(true);

        Drawable drawable=array.getDrawable(R.styleable.ActionItemView_icon);
        String label=array.getString(R.styleable.ActionItemView_label);
        int style=array.getInt(R.styleable.ActionItemView_style, STYLE_LABEL_ONLY);
        setStyle(style);

        int color=array.getColor(R.styleable.ActionItemView_active_color, context.getColor(android.R.color.holo_blue_bright));
        array.recycle();

        if(drawable!=null){
            applyItemInfo(drawable, label, color);
        }
    }
    public void setStyle(int style){
        switch (style){
            case STYLE_ICON_ONLY:
                actionLabel.setVisibility(GONE);
                actionIcon.setVisibility(VISIBLE);
                break;
            case STYLE_LABEL_ONLY:
                actionLabel.setVisibility(VISIBLE);
                actionIcon.setVisibility(GONE);
                break;
            case STYLE_BOTH:
                actionLabel.setVisibility(VISIBLE);
                actionIcon.setVisibility(VISIBLE);
                break;
        }
    }
    void applyItemInfo(Drawable drawable, String label, int color){
        actionLabel.setText(label);
        int textColor=actionLabel.getTextColors().getDefaultColor();
        ColorStateList stateList=new ColorStateList(new int[][]{
            new int[]{android.R.attr.state_selected},
            new int[]{0}
        }, new int[]{ color, textColor });
        actionLabel.setTextColor(stateList);
        actionIcon.setImageDrawable(new SelectedStateListDrawable(drawable, color));
        invalidate();
    }
}
