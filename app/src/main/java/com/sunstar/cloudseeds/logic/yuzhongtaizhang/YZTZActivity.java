package com.sunstar.cloudseeds.logic.yuzhongtaizhang;

import com.classichu.classichu.classic.ClassicActivity;
import com.sunstar.cloudseeds.R;

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
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.id_frame_layout_content,YZTZFragment.newInstance("",""))
                    .commitAllowingStateLoss();
    }

    @Override
    protected void initListener() {

    }
}
