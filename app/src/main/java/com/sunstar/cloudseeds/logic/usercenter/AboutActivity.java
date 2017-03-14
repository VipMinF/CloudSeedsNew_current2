package com.sunstar.cloudseeds.logic.usercenter;

import android.view.View;

import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.classichu.classichu.classic.ClassicActivity;
import com.sunstar.cloudseeds.R;
import com.tencent.bugly.beta.Beta;

public class AboutActivity extends ClassicActivity {


    @Override
    protected int setupLayoutResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        setAppBarTitle("关于");
        //
        setOnNotFastClickListener(findById(R.id.btn_select), new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View view) {
                /**
                 * 参数1：isManual 用户手动点击检查，非用户点击操作请传false
                 参数2：isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗或toast]
                 */
                Beta.checkUpgrade(true,false);
            }
        });
    }

    @Override
    protected AppBarStyle configAppBarStyle() {
        return AppBarStyle.ClassicTitleBar;
    }

    @Override
    protected void initListener() {

    }
}
