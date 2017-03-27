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
        mXuanZhuListFragment = XuanZhuListFragment.newInstance("", "");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_frame_layout_content, mXuanZhuListFragment)
                .commitAllowingStateLoss();
        // setAppBarTitle("选株列表");
        mClassicTitleBar.setCenterText("选株列表").setRightText("新增选株").setOnTitleBarRightItemClickListener(new ClassicTitleBar.OnTitleBarRightItemClickListener() {
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
