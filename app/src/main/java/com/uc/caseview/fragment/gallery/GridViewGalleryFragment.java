package com.uc.caseview.fragment.gallery;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.uc.caseview.entity.ImageItem;

import java.util.List;

/**
 * Created by guoho on 2017/9/21.
 */

public class GridViewGalleryFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<ImageItem>>{
    public static final String LOADER_ID="LOADER_ID";

    private LoaderManager.LoaderCallbacks<List<ImageItem>> loaderCallbacks;

    public void setLoaderCallbacks(LoaderManager.LoaderCallbacks<List<ImageItem>> loaderCallbacks) {
        this.loaderCallbacks = loaderCallbacks;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int loaderId= getArguments().getInt(LOADER_ID, -1);
        getLoaderManager().initLoader(loaderId, getArguments(), (android.app.LoaderManager.LoaderCallbacks<Object>) loaderCallbacks);
    }

    @Override
    public Loader<List<ImageItem>> onCreateLoader(int id, Bundle args) {
        return loaderCallbacks==null ? null : loaderCallbacks.onCreateLoader(id, args);
    }

    @Override
    public void onLoadFinished(Loader<List<ImageItem>> loader, List<ImageItem> data) {
        if(loaderCallbacks!=null) loaderCallbacks.onLoadFinished(loader, data);
    }

    @Override
    public void onLoaderReset(Loader<List<ImageItem>> loader) {
        if(loaderCallbacks!=null) loaderCallbacks.onLoaderReset(loader);
    }
}
