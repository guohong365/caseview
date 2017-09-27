package com.uc.caseview.entity;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by guoho on 2017/9/21.
 */

public class CaseLoader extends AsyncTaskLoader<List<CaseItem>> {
    private DaoSession session;
    private Long caseId;
    private List<CaseItem> cached;
    private boolean observerRegistered = false;
    private final ForceLoadContentObserver forceLoadContentObserver = new ForceLoadContentObserver();

    public CaseLoader(Context context) {
        super(context);
    }

    @Override
    public void deliverResult(List<CaseItem> data) {
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
    public List<CaseItem> loadInBackground() {
        List<CaseItem> data = queryCases();
        Collections.sort(data, new Comparator<CaseItem>() {
            @Override
            public int compare(CaseItem item1, CaseItem item2) {
                return Long.valueOf(item2.getCreateTime()).compareTo(item1.getCreateTime());
            }
        });
        return data;
    }

    protected List<CaseItem> queryCases(){
        return  EntityUtils.getSession().getCaseItemDao().queryBuilder().build().list();
    }

}