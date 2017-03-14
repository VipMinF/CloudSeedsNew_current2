package com.sunstar.cloudseeds.logic.usercenter;

import com.classichu.classichu.classic.ClassicActivity;
import com.sunstar.cloudseeds.R;

public class AboutActivity extends ClassicActivity {


    @Override
    protected int setupLayoutResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {

        /**
         * fix
         *
         * @deprecated  com.android.support:design:25.2.0 已修复
         */
        //### fixPasswordToggleViewInCenter();
        setAppBarTitle("关于");
    }

    @Override
    protected AppBarStyle configAppBarStyle() {
        return AppBarStyle.ClassicTitleBar;
    }

    @Override
    protected void initListener() {

    }
}
