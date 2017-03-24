package com.sunstar.cloudseeds;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.classichu.classichu.app.CLog;
import com.classichu.classichu.basic.tool.SizeTool;
import com.classichu.classichu.classic.ClassicActivity;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;
import com.sunstar.cloudseeds.ui.MainFragment;
import com.sunstar.cloudseeds.ui.ScanFragment;
import com.sunstar.cloudseeds.ui.SearchFragment;
import com.sunstar.cloudseeds.ui.UserCenterFragment;

public class MainActivity extends ClassicActivity{

    private  Fragment mMainFragment;
    private  Fragment mSearchFragment;
    private  Fragment mScanFragment;
    private  Fragment mUserCenterFragment;
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

        initFragmentList();
        //
        showMainFragment();
    }

    private void initFragmentList() {
        mMainFragment=MainFragment.newInstance("","");
        mSearchFragment=SearchFragment.newInstance("","");
        mScanFragment=ScanFragment.newInstance("","");
        mUserCenterFragment=UserCenterFragment.newInstance("","");
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



    private Fragment mCurrentFragment=new Fragment();
    /**
     * 控制 fragment 来回切换  的显示或隐藏
     * @param fragment
     * @param containerViewId
     */
    private void showFragment(Fragment fragment,int containerViewId) {
   if (mCurrentFragment!=fragment) {
           FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //
           transaction.hide(mCurrentFragment);
           if (!fragment.isAdded()) {
               // transaction.add(containerViewId, fragment).show(fragment).commitAllowingStateLoss();
                transaction.add(containerViewId, fragment).show(fragment).commit();
            } else {
               // transaction.show(fragment).commitAllowingStateLoss();
                transaction.show(fragment).commit();
            }
       mCurrentFragment = fragment;
 }
}

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        CLog.d("onConfigurationChanged");
    }

    private void showMainFragment() {
        if (mMainFragment==null){
            mMainFragment=MainFragment.newInstance("","");
        }
        showFragment(mMainFragment,R.id.id_frame_layout_content);
    }
    private void showSearchFragment() {
        if (mSearchFragment==null){
            mSearchFragment=SearchFragment.newInstance("","");
        }
        showFragment(mSearchFragment,R.id.id_frame_layout_content);
    }
    private void showScanFragment() {
        if (mScanFragment==null){
            mScanFragment=ScanFragment.newInstance("","");
        }
        showFragment(mScanFragment,R.id.id_frame_layout_content);
    }
    private void showUserCenterFragment() {
        if (mUserCenterFragment==null){
            mUserCenterFragment=UserCenterFragment.newInstance("","");
        }
        showFragment(mUserCenterFragment,R.id.id_frame_layout_content);
    }
    @Override
    protected void initListener() {

    }

    private void initAppBar(){
        setAppBarTitle("首页");
        //
        if (mClassicTitleBar!=null){
            //update by lzy -2017.3.21
            UserLoginBean userloginbean = UserLoginHelper.userLoginBean();
            String company="";
            if (userloginbean!=null&&userloginbean.getCompany()!=null){

            }
            mClassicTitleBar.setRightText(company)
                    .setLeftAndRightTextSize(SizeTool.dp2px(12))
                    .setRightMaxWidth(SizeTool.dp2px(210));
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
