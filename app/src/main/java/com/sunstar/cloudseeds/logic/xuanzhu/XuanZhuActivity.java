package com.sunstar.cloudseeds.logic.xuanzhu;

import android.view.View;

import com.classichu.classichu.classic.ClassicActivity;
import com.classichu.titlebar.widget.ClassicTitleBar;
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

    private XuanZhuListFragment mXuanZhuListFragment;

    @Override
    protected void initView() {
        String secondary_id=getBundleExtraStr1();
        String taizhangName=getBundleExtra().getString("taizhangName");
        String zuqunName=getBundleExtra().getString("zuqunName");
        String primary_id = getBundleExtra().getString("primary_id");
        mXuanZhuListFragment = XuanZhuListFragment.newInstance(secondary_id, taizhangName,zuqunName);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_frame_layout_content, mXuanZhuListFragment)
                .commitAllowingStateLoss();
        mXuanZhuListFragment.primary_id = primary_id;
        // setAppBarTitle("选株列表");
        mClassicTitleBar
                .setCenterText("品系详情")
                .setRightText("新增选株")
                .setOnTitleBarRightItemClickListener(new ClassicTitleBar.OnTitleBarRightItemClickListener() {
            @Override
            public void onRightClick(View view) {
                mXuanZhuListFragment.goAddSelectBeads4Aty();
            }
        });
    }


    @Override
    protected void initListener() {
        // OkHttpSingleton.getInstance().doGet();
    }
}
