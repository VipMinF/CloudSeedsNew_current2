package com.sunstar.cloudseeds.logic.yuzhongtaizhang;

import com.classichu.classichu.classic.ClassicActivity;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.AtyGoToWhere;

public class YZTZActivity extends ClassicActivity {



    @Override
    protected int setupLayoutResId() {
        return R.layout.activity_yztz;
    }

    @Override
    protected AppBarStyle configAppBarStyle() {
        return AppBarStyle.ClassicTitleBar;
    }

    @Override
    protected void initView() {
        setAppBarTitle("育种台账");

        int goToWhere=getBundleExtraInt1();
        switch (goToWhere){
            case AtyGoToWhere.LIST:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_frame_layout_content, YZTZListFragment.newInstance("",""))
                        .commitAllowingStateLoss();
                break;
            case AtyGoToWhere.DETAIL:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_frame_layout_content, YZTZDetailFragment.newInstance("",""))
                        .commitAllowingStateLoss();
                break;
            case AtyGoToWhere.ADD:

                break;
        }

    }

    @Override
    protected void initListener() {

    }
}
