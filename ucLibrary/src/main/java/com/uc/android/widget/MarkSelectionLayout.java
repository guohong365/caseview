package com.uc.android.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.uc.android.R;
import com.uc.android.model.MarkItem;

import java.util.ArrayList;
import java.util.List;

public class MarkSelectionLayout extends LinearLayout {
    int MARK_SQUARE=0;
    int MARK_CIRCLE=1;
    int MARK_TRIANGLE=2;
    int MARK_ARROW_LEFT=3;
    int MARK_ARROW_RIGHT=4;
    int MARK_ARROW_DOWN=5;
    int MARK_ARROW_UP=6;
    private int markColor;
    private List<MarkItemView> markItemViewList=new ArrayList<>();
    OnMarkSelectedListener onMarkSelectedListener;
    OnClickListener onMarkClicked=new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(onMarkSelectedListener!=null){
                MarkItemView  view= (MarkItemView) v;
                onMarkSelectedListener.onMarkSelected(view.getMarkItem().getId());
            }

        }
    };

    public MarkSelectionLayout(Context context) {
        this(context, null);
    }

    public MarkSelectionLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarkSelectionLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        buildMarks();
    }

    public MarkSelectionLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        buildMarks();
    }

    public void setOnMarkSelectedListener(OnMarkSelectedListener onMarkSelectedListener) {
        this.onMarkSelectedListener = onMarkSelectedListener;
    }

    protected void buildMarks(){
        List<MarkItem> markItems=new ArrayList<>();
        markItems.add(new MarkItem(MARK_SQUARE, R.drawable.ic_square));
        markItems.add(new MarkItem(MARK_CIRCLE, R.drawable.ic_circle));
        markItems.add(new MarkItem(MARK_TRIANGLE, R.drawable.ic_triangle));
        markItems.add(new MarkItem(MARK_ARROW_LEFT, R.drawable.ic_arrow_left));
        markItems.add(new MarkItem(MARK_ARROW_RIGHT, R.drawable.ic_arrow_right));
        markItems.add(new MarkItem(MARK_ARROW_DOWN, R.drawable.ic_arrow_down));
        markItems.add(new MarkItem(MARK_ARROW_UP, R.drawable.ic_arrow_up));

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.weight=1;
        for(MarkItem item:markItems){
            MarkItemView itemView= (MarkItemView) LayoutInflater.from(getContext()).inflate(R.layout.item_mark, null);
            itemView.setMarkItem(item);
            itemView.setLayoutParams(lp);
            itemView.setClickable(true);
            itemView.setOnClickListener(onMarkClicked);
            addView(itemView);
            markItemViewList.add(itemView);
        }
    }

    public interface OnMarkSelectedListener {
        void onMarkSelected(int markId);
    }

    public void setMarkColor(int markColor){
        this.markColor=markColor;
        for(MarkItemView view:markItemViewList){
            view.setMarkColor(markColor);
        }
    }

}
