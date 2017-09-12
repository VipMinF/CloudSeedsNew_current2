package com.sunstar.cloudseeds.logic.yuzhongtaizhang;

import com.classichu.classichu.classic.ClassicActivity;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.AtyGoToWhere;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.ui.YZTZAddFragment;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.ui.YZTZDetailFragment;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.ui.YZTZListFragment;

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

        /**
         * 计划详情
         *
         */
        int goToWhere=getBundleExtraInt1();
        switch (goToWhere){
            case AtyGoToWhere.LIST:
                String primary_id=getBundleExtraStr1();
                String TaiZhangName=getBundleExtra().getString("TaiZhangName");
                setAppBarTitle("计划详情");
                // mClassicTitleBar.setCenterTextSize(SizeTool.dp2px(16)).setCenterText(TaiZhangName);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_frame_layout_content, YZTZListFragment.newInstance(primary_id,"",TaiZhangName))
                        .commitAllowingStateLoss();
                break;
            case AtyGoToWhere.DETAIL:
                setAppBarTitle("族群信息");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_frame_layout_content, YZTZDetailFragment.newInstance("",""))
                        .commitAllowingStateLoss();
                break;
            case AtyGoToWhere.ADD:
                setAppBarTitle("族群信息记录");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_frame_layout_content, YZTZAddFragment.newInstance("",""))
                        .commitAllowingStateLoss();
                break;
        }

    }

    @Override
    protected void initListener() {

    }
}
