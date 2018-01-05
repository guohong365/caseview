package com.uc.caseview.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.uc.caseview.R;
import com.uc.caseview.entity.ImageItem;
import com.uc.caseview.utils.FileUtils;

import java.util.List;

public class MyGridViewAdapter extends BaseAdapter {
    private List<ImageItem> mImages;
    private Context mContext;
    private boolean selectable;
    //private FinalBitmap mImageLoader;
    private int wh;
    public MyGridViewAdapter(@NonNull Context context, @NonNull List<ImageItem> images, int itemWidthInDx){
        this.mImages=images;
        LayoutInflater mInflater = LayoutInflater.from(context);
        this.mContext=context;
        //mImageLoader=FinalBitmap.create(context);
        //mImageLoader.configLoadfailImage(R.drawable.ic_error_image).configLoadingImage(R.drawable.ic_loading).configBitmapLoadThreadSize(5);
        this.wh= itemWidthInDx;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isSelectable() {
        return selectable;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object getItem(int position) {
        return mImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageItem item=mImages.get(position);
        ViewHolder holder=ViewHolder.get(mContext, convertView, parent, R.layout.item_image);
        ImageView imageView=holder.getView(R.id.ctrl_image_view);
        CheckBox checkBox=holder.getView(R.id.ctrl_image_view);
        if(isSelectable()){
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setChecked(item.isSelected());
        } else {
            checkBox.setVisibility(View.GONE);
        }
        String fileName= FileUtils.getImagePathName(mContext, mImages.get(position).getName());
        Glide.with(mContext)
                .load(fileName)
                .thumbnail(0.1f)
                .into(imageView);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(wh,wh);
        imageView.setLayoutParams(layoutParams);
        return holder.getConvertView();
    }
}
