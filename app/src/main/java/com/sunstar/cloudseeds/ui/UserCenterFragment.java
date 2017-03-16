package com.sunstar.cloudseeds.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.classichu.classichu.classic.ClassicFragment;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.widget.SettingPreferenceFragmentCompat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserCenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserCenterFragment extends ClassicFragment {

    public UserCenterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserCenterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserCenterFragment newInstance(String param1, String param2) {
        UserCenterFragment fragment = new UserCenterFragment();
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
        return R.layout.fragment_user_center;
    }

    @Override
    protected void initView(View view) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.id_frame_layout_content,new SettingPreferenceFragmentCompat())
                .commitAllowingStateLoss();

        UserLoginBean userloginbean= UserLoginHelper.userLoginBean(getActivity());

        TextView user_account = (TextView)findById(R.id.usercenter_account);
        user_account.setText(userloginbean.getUsername());

        TextView user_company = (TextView)findById(R.id.usercenter_company);
        user_company.setText(userloginbean.getCompany());

        TextView user_tickname = (TextView)findById(R.id.usercenter_tickname);
        user_tickname.setText(userloginbean.getTickname());

        TextView user_mobile = (TextView)findById(R.id.usercenter_mobile);
        user_mobile.setText(userloginbean.getMoible());

    }

    @Override
    protected void initListener() {

    }

}
