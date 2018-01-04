package com.uc.caseview.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.uc.caseview.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CameraFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CameraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CameraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CameraFragment newInstance(String param1, String param2) {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    View mFlashOptions;
    View mExposureOptions;
    View mGridOptions;
    void hideAll(){
        mFlashOptions.setVisibility(View.GONE);
        mExposureOptions.setVisibility(View.GONE);
        mGridOptions.setVisibility(View.GONE);
    }
    ImageButton mBtnFlashSetting;
    ImageButton mFlashAuto;
    ImageButton mFlashOn;
    ImageButton mFlashOff;
    View currentOptions;

    View.OnClickListener settingsItemClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int current=0;
            switch (v.getId()){
                case R.id.btn_flash_auto:
                    current=R.drawable.ic_camera_flash_auto_24dp;
                    break;
                case R.id.btn_flash_on:
                    current=R.drawable.ic_camera_flash_on_24dp;
                    break;
                case R.id.btn_flash_off:
                    current=R.drawable.ic_camera_flash_off_24dp;
                    break;
                case R.id.btn_exposure_neg_1:
                    current=R.drawable.ic_camera_exposure_neg_1_24dp;
                    break;
                case R.id.btn_exposure_neg_2:
                    current=R.drawable.ic_camera_exposure_neg_2_24dp;
                    break;
                case R.id.btn_exposure_zero:
                    current=R.drawable.ic_camera_exposure_zero_24dp;
                    break;
                case R.id.btn_exposure_plus_1:
                    current=R.drawable.ic_camera_exposure_plus_1_24dp;
                    break;
                case R.id.btn_exposure_plus_2:
                    current=R.drawable.ic_camera_exposure_plus_2_24dp;
                    break;
                case R.id.btn_grid_on:
                    current=R.drawable.ic_camera_grid_on_24dp;
                    break;
                case R.id.btn_grid_off:
                    current=R.drawable.ic_camera_grid_off_24dp;
                    break;
                case R.id.btn_wb_auto:
                    current=R.drawable.ic_camera_wb_auto_24dp;
                    break;
                case R.id.btn_wb_cloudy:
                    current=R.drawable.ic_camera_wb_cloudy_24dp;
                    break;
                case R.id.btn_wb_incandescent:
                    current=R.drawable.ic_camera_wb_incandescent_24dp;
                    break;
                case R.id.btn_wb_iridescent:
                    current=R.drawable.ic_camera_wb_iridescent_24dp;
                    break;
                case R.id.btn_wb_sunny:
                    current=R.drawable.ic_camera_wb_sunny_24dp;
                    break;
            }
            ImageButton currentButton=(ImageButton)v;
            currentButton.setImageResource(current);
            currentOptions.setVisibility(View.GONE);

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mExposureOptions=view.findViewById(R.id.layout_settings_exposure);
        mGridOptions=view.findViewById(R.id.layout_settings_grid);
        mBtnFlashSetting=view.findViewById(R.id.btn_settings_flash);
        mBtnFlashSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                currentOptions=v;
                v.setVisibility(View.VISIBLE);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
