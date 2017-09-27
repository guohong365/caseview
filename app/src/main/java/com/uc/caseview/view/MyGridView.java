package com.uc.caseview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.uc.caseview.utils.SysUtils;

/**
 * Created by guoho on 2017/6/8.
 */

public class MyGridView extends GridView {
    private int mLayoutWidth;
    private int mLayoutSapcing;

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
    public int setupLayout(Context context, int count, View viewGroup, int imageWidthInPx, int spacingInPx, int limitCutOffInDp, int requestColumns){
        int width=context.getResources().getDisplayMetrics().widthPixels - SysUtils.px2dip(context,limitCutOffInDp);
        int itemWidth;
        int columns;
        int layoutWidth;
        int columnWidth=imageWidthInPx + spacingInPx;

        if(requestColumns <=0){
            int maxColumnCount=width /columnWidth;
            if(maxColumnCount>count){
                columns=count;
            } else {
                columns=maxColumnCount;
            }
            layoutWidth=columns* columnWidth - spacingInPx;
            itemWidth=imageWidthInPx;
        } else {
            int space;
            switch (count) {
                case 1:
                    columns = 1;
                    space = 0;
                    break;
                case 2:
                    columns = 2;
                    space = spacingInPx;
                    break;
                default:
                    columns = 3;
                    space = spacingInPx * 2;
                    break;
            }
            itemWidth = (width - spacingInPx * 2) / 3;
            layoutWidth = itemWidth * columns + space;
        }
        ViewGroup.LayoutParams params =viewGroup.getLayoutParams();
        params.width= layoutWidth;
        setNumColumns(columns);
        return itemWidth;
    }
}
