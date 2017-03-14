package com.sunstar.cloudseeds.logic.xuanzhu;

import com.classichu.classichu.classic.ClassicActivity;
import com.sunstar.cloudseeds.R;

public class XuanZhuActivity extends ClassicActivity {

    @Override
    protected int setupLayoutResId() {
        return R.layout.activity_xuan_zhu;
    }

    @Override
    protected AppBarStyle configAppBarStyle() {
        return AppBarStyle.ClassicTitleBar;
    }

    @Override
    protected void initView() {
        setAppBarTitle("选株列表");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_frame_layout_content, XuanZhuListFragment.newInstance("",""))
                .commitAllowingStateLoss();
    }

    @Override
    protected void initListener() {

    }
}
