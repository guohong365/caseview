package com.uc.caseview.entity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

/**
 * Created by guoho on 2017/9/21.
 */

public class CaseLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<CaseItem>> {
    final Context context;

    public CaseLoaderCallbacks(Context context) {
        this.context = context;
    }

    @Override
    public Loader<List<CaseItem>> onCreateLoader(int id, Bundle args) {
        return new CaseLoader(context);
    }

    @Override
    public void onLoadFinished(Loader<List<CaseItem>> loader, List<CaseItem> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<CaseItem>> loader) {

    }

/*
    @Override
    public Loader<List<CaseItem>> onCreateLoader(int i, Bundle bundle) {
        return new CaseLoader(context);
    }

    @Override
    public void onLoadFinished(Loader<List<CaseItem>> loader, List<CaseItem> caseItems) {
        GlideRequests glideRequests = GlideApp.with(this);
        RecyclerAdapter adapter =
                new RecyclerAdapter(context, caseItems, glideRequests);
        RecyclerViewPreloader<CaseItem> preloader =
                new RecyclerViewPreloader<>(glideRequests, adapter, adapter, 3);
        recyclerView.addOnScrollListener(preloader);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<List<CaseItem>> loader) {
        // Do nothing.
    }
    */
}
