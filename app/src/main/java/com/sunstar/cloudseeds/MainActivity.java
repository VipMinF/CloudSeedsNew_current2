package com.sunstar.cloudseeds;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.classichu.classichu.basic.tool.SizeTool;
import com.classichu.classichu.classic.ClassicActivity;
import com.sunstar.cloudseeds.ui.MainFragment;
import com.sunstar.cloudseeds.ui.ScanFragment;
import com.sunstar.cloudseeds.ui.SearchFragment;
import com.sunstar.cloudseeds.ui.UserCenterFragment;

public class MainActivity extends ClassicActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setupLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initAppBar();
        initNavigationView();
        showMainFragment();
    }

    private void initNavigationView() {
        BottomNavigationView bottomNavigationView=findById(R.id.id_bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.id_navigation_home:
                        showMainFragment();
                        return true;
                    case R.id.id_navigation_search:
                        showSearchFragment();
                        return true;
                    case R.id.id_navigation_scan:
                        showScanFragment();
                        return true;
                    case R.id.id_navigation_usercenter:
                        showUserCenterFragment();
                        return true;
                }
                return false;
            }
        });
    }
    private void showUserCenterFragment() {
      getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.id_frame_layout_content, UserCenterFragment.newInstance("",""))
                .commitAllowingStateLoss();
    }

    private void showMainFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.id_frame_layout_content, MainFragment.newInstance("",""))
                .commitAllowingStateLoss();
    }
    private void showSearchFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.id_frame_layout_content, SearchFragment.newInstance("",""))
                .commitAllowingStateLoss();
    }
    private void showScanFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.id_frame_layout_content, ScanFragment.newInstance("",""))
                .commitAllowingStateLoss();
    }

    @Override
    protected void initListener() {

    }

    private void initAppBar(){
        setAppBarTitle("首页");
        //
        if (mClassicTitleBar!=null){
            mClassicTitleBar.setRightText("浙江美之澳科技有限公司")
                    .setLeftAndRightTextSize(SizeTool.dp2px(this,12))
                    .setRightMaxWidth(SizeTool.dp2px(this,210));
        }
    }


    @Override
    protected boolean configSwipeBackEnable() {
        return false;
    }
    @Override
    protected boolean configBackBtnEnable() {
        return false;
    }
    @Override
    protected AppBarStyle configAppBarStyle() {
        return AppBarStyle.ClassicTitleBar;
    }

}
