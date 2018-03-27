package com.uc.caseview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.uc.android.Selectable;
import com.uc.android.adapter.RecyclerViewAdapterBase;
import com.uc.caseview.adapter.holder.ImageItemViewHolder;
import com.uc.caseview.adapter.holder.ImageItemViewViewHolderFactory;
import com.uc.caseview.entity.ImageItem;
import com.uc.caseview.utils.FileUtils;
import com.uc.caseview.utils.GlobalHolder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageItemAdapter extends RecyclerViewAdapterBase<ImageItemViewHolder> {
    public ImageItemAdapter(Context context, List<Selectable> items,
                            ImageItemViewViewHolderFactory viewHolderFactory) {
        super(context, items, viewHolderFactory);
    }

    @Override
    protected void onAssigned(ImageItemViewHolder holder, Selectable item) {
        ImageItem imageItem=(ImageItem)item;
        File file= FileUtils.getImageFile(context, imageItem.getName());
        Bitmap bitmap=null;
        if(!file.exists()){
            try {
                Log.v(TAG, "\n[" + file.toString() + "]\n [" + file.getCanonicalPath() + "]\n[" + file.getAbsolutePath() + "]");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.v(TAG, "file exists. file size=[" + 1 + "]");
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (bitmap == null) {
                Log.w(TAG, "load bitmap failed.");
            }
        }
        Glide.with(context)
                .load(Uri.fromFile(file))
                .override(256,256)
                .into(holder.imageView);
    }
}
