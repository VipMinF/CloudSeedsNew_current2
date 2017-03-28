package com.sunstar.cloudseeds.logic.shangpinqi;

import android.os.Bundle;

import com.classichu.classichu.classic.ClassicActivity;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.AtyGoToWhere;
import com.sunstar.cloudseeds.logic.shangpinqi.ui.SPQAddFragment;
import com.sunstar.cloudseeds.logic.shangpinqi.ui.SPQDetailFragment;

public class SPQActivity extends ClassicActivity {
    private String mNowTertiary_id;
    @Override
    protected int setupLayoutResId() {
        return R.layout.activity_spq;
    }

    @Override
    protected void initView() {
        int goToWhere=getBundleExtraInt1();

        Bundle bundle=getBundleExtra();
        mNowTertiary_id=bundle.getString("Tertiary_id");
        switch (goToWhere){
            case AtyGoToWhere.LIST:
         /*       setAppBarTitle("育种台账计划");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_frame_layout_content, YZTZListFragment.newInstance("",""))
                        .commitAllowingStateLoss();*/
              /*  setAppBarTitle("商品期调查记录dsadasd");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_frame_layout_content, SPQAddFragment.newInstance("",""))
                        .commitAllowingStateLoss();*/
                break;
            case AtyGoToWhere.DETAIL:
                setAppBarTitle("商品期调查");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_frame_layout_content, SPQDetailFragment.newInstance(mNowTertiary_id,""))
                        .commitAllowingStateLoss();
                break;
            case AtyGoToWhere.ADD:
                setAppBarTitle("商品期调查记录");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_frame_layout_content, SPQAddFragment.newInstance(mNowTertiary_id,""))
                        .commitAllowingStateLoss();
                break;
        }
    }

    @Override
    protected void initListener() {

    }


    @Override
    protected AppBarStyle configAppBarStyle() {
        return AppBarStyle.ClassicTitleBar;
    }


}
