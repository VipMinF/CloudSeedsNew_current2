package com.sunstar.cloudseeds.logic.xuanzhu;

import android.view.View;
import android.widget.Toast;

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

    @Override
    protected void initView() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_frame_layout_content, XuanZhuListFragment.newInstance("",""))
                .commitAllowingStateLoss();
        // setAppBarTitle("选株列表");
        mClassicTitleBar.setCenterText("选株列表").setRightText("新增选株").setOnTitleBarRightItemClickListener(new ClassicTitleBar.OnTitleBarRightItemClickListener() {
            @Override
            public void onRightClick(View view) {
                Toast.makeText(mContext, "新增选株", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initListener() {
       // OkHttpSingleton.getInstance().doGet();
    }
}
