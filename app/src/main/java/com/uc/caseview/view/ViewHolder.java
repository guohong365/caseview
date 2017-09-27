package com.uc.caseview.view;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by guoho on 2017/6/9.
 */

public class ViewHolder {
    private View mConvertView;
    private SparseArray<View> mViews;
    private ViewHolder(Context context, ViewGroup parent, int layoutId){
        mViews=new SparseArray<>();
        mConvertView= LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId){
        if(convertView==null){
            return new ViewHolder(context, parent, layoutId);
        }
        return (ViewHolder) convertView.getTag();
    }

    public <T extends View> T getView(int viewId){
        View view=mViews.get(viewId);
        if(view==null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return  (T)view;
    }

    public View getConvertView(){
        return mConvertView;
    }
}
