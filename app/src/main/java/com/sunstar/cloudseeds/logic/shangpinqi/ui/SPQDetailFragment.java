package com.sunstar.cloudseeds.logic.shangpinqi.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.classichu.classichu.classic.ClassicFragment;
import com.sunstar.cloudseeds.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SPQDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SPQDetailFragment extends ClassicFragment {



    public SPQDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SPQDetialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SPQDetailFragment newInstance(String param1, String param2) {
        SPQDetailFragment fragment = new SPQDetailFragment();
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

    @Override
    protected int setupLayoutResId() {
        return R.layout.fragment_spq_detail;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initListener() {

    }

}
