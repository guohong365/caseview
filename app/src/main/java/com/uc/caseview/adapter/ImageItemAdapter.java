package com.uc.caseview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.uc.android.Selectable;
import com.uc.android.adapter.RecyclerViewAdapterBase;
import com.uc.caseview.adapter.holder.ImageItemViewHolder;
import com.uc.caseview.adapter.holder.ImageItemViewViewHolderFactory;
import com.uc.caseview.entity.ImageItem;
import com.uc.caseview.utils.FileUtils;
import com.uc.caseview.utils.GlideRequest;
import com.uc.caseview.utils.GlideRequests;
import com.uc.caseview.utils.GlobalHolder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageItemAdapter extends RecyclerViewAdapterBase<ImageItemViewHolder> {
    private final GlideRequest<Drawable> glideRequest;
    public ImageItemAdapter(Context context, List<Selectable> items,
                            ImageItemViewViewHolderFactory viewHolderFactory,
                            GlideRequests glideRequests) {
        super(context, items, viewHolderFactory);
        this.glideRequest = glideRequests.asDrawable().apply(GlobalHolder.getRequestOptions());
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
            Log.v(TAG, "file exists. file size=[" +  1+ "]");
            try {
                Log.v(TAG, "\n[" + file.toString() + "]\n [" + file.getCanonicalPath() + "]\n[" + file.getAbsolutePath() + "]");
                bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                if(bitmap==null){
                    Log.v(TAG, "load bitmap failed.");
                } else {
                    Log.v(TAG, "load bitmap successfully");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        glideRequest
                .clone()
                .load(Uri.fromFile(file))
                .into(holder.imageView);
    }
}
