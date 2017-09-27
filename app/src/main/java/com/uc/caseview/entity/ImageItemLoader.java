package com.uc.caseview.entity;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Loads metadata from the media store for images and videos.
 */
public class ImageItemLoader extends AsyncTaskLoader<List<ImageItem>> {
    private DaoSession session;
    private Long caseId;
    private List<ImageItem> cached;
    private boolean observerRegistered = false;
    private final ForceLoadContentObserver forceLoadContentObserver = new ForceLoadContentObserver();

    public ImageItemLoader(Context context) {
        super(context);
    }

    @Override
    public void deliverResult(List<ImageItem> data) {
        if (!isReset() && isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (cached != null) {
            deliverResult(cached);
        }
        if (takeContentChanged() || cached == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();
        cached = null;
    }

    @Override
    protected void onAbandon() {
        super.onAbandon();
    }

    @Override
    public List<ImageItem> loadInBackground() {
        List<ImageItem> data = queryImages();
        Collections.sort(data, new Comparator<ImageItem>() {
            @Override
            public int compare(ImageItem mediaStoreData, ImageItem mediaStoreData2) {
                return Long.valueOf(mediaStoreData2.getDateTaken()).compareTo(mediaStoreData.getDateTaken());
            }
        });
        return data;
    }
    protected List<ImageItem> queryImages(){
        return  EntityUtils.getSession().getImageItemDao().queryBuilder().build().list();
    }

}