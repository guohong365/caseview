package com.uc.caseview.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uc.caseview.R;

import java.util.List;

/**
 * Displays media store data in a recycler view.
 */
public abstract class SimpleRecyclerViewFragment<DataType> extends Fragment
        implements LoaderManager.LoaderCallbacks<List<DataType>> {
    RecyclerView.LayoutManager layoutManager;
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(R.id.loader_id_media_store_data, null, this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.item_recycler_view, container, false);
        RecyclerView recyclerView = (RecyclerView) result.findViewById(R.id.ctrl_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        return result;
    }
}