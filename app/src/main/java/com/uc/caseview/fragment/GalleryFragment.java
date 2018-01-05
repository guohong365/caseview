package com.uc.caseview.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uc.caseview.R;
import com.uc.caseview.adapter.ImageItemAdapter;
import com.uc.caseview.adapter.holder.ImageItemViewViewHolderFactory;
import com.uc.caseview.entity.RequestParams;

import java.util.ArrayList;

import static com.uc.caseview.entity.RequestParams.KEY_REQUEST;

public class GalleryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String BOTTOM_BAR = "BOTTOM_BAR";
    private static final String MESSAGE = "MESSAGE";

    private RequestParams request;
    public GalleryFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(int toolbar_id, String message) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putInt(BOTTOM_BAR, toolbar_id);
        args.putString(MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            request=getArguments().getParcelable(KEY_REQUEST);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.item_recycler_view, container, false);

        RecyclerView recyclerView= view.findViewById(R.id.ctrl_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false));
        ImageItemAdapter adapter=new ImageItemAdapter(getActivity(),
                new ArrayList<>(request.getImageItems()),
                new ImageItemViewViewHolderFactory(getContext(), 1));
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),1);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }
}
