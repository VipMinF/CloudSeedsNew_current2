package com.sunstar.cloudseeds.logic.shangpinqi.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.classichu.classichu.classic.ClassicFragment;
import com.classichu.dropselectview.bean.ClassfiyBean;
import com.classichu.dropselectview.widget.ClassicSelectView;
import com.sunstar.cloudseeds.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SPQAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SPQAddFragment extends ClassicFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SPQAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SPQAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SPQAddFragment newInstance(String param1, String param2) {
        SPQAddFragment fragment = new SPQAddFragment();
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
        return R.layout.fragment_spq_add;
    }

    @Override
    protected void initView(View view) {
       ClassicSelectView id_classic_select_view_zx= findById(R.id.id_classic_select_view_zx);
        List<ClassfiyBean> classfiyBeanList=new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            ClassfiyBean classfiyBean=new ClassfiyBean();
            classfiyBean.setName("dsa"+i);
            classfiyBean.setID(i);
            classfiyBeanList.add(classfiyBean);
        }
        id_classic_select_view_zx.setupClassfiyBeanList(classfiyBeanList);
    }

    @Override
    protected void initListener() {

    }

}
