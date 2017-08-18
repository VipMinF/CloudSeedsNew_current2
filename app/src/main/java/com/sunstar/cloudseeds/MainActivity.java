package com.sunstar.cloudseeds;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import com.classichu.classichu.app.CLog;
import com.classichu.classichu.basic.tool.SizeTool;
import com.classichu.classichu.classic.ClassicActivity;
import com.classichu.photoselector.imagespicker.ImagePickBean;
import com.sunstar.cloudseeds.cache.ACache;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;
import com.sunstar.cloudseeds.logic.shangpinqi.SPQActivity;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.OnWifiUpLoadImageBean;
import com.sunstar.cloudseeds.service.OnWifiUpLoadService;
import com.sunstar.cloudseeds.ui.MainFragment;
import com.sunstar.cloudseeds.ui.ScanFragment;
import com.sunstar.cloudseeds.ui.SearchFragment;
import com.sunstar.cloudseeds.ui.UserCenterFragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends ClassicActivity {

    private Fragment mMainFragment;
    private Fragment mSearchFragment;
    private Fragment mScanFragment;
    private Fragment mUserCenterFragment;
    private  BroadcastReceiver delegateBroadcastReceiver;

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

    private Fragment mCurrentFragment;

    private void initFragmentList() {
        mMainFragment = MainFragment.newInstance("", "");
        mSearchFragment = SearchFragment.newInstance("", "");
        mScanFragment = ScanFragment.newInstance("", "");
        mUserCenterFragment = UserCenterFragment.newInstance("", "");
    }

    private void initNavigationView() {
        BottomNavigationView bottomNavigationView = findById(R.id.id_bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.id_navigation_home:
                        setAppBarTitle("首页");
                        showMainFragment();
                        return true;
                    case R.id.id_navigation_search:
                        setAppBarTitle("快速搜索");
                        showSearchFragment();
                        return true;
                    case R.id.id_navigation_scan:
                        setAppBarTitle("扫一扫");
                        showScanFragment();
                        return true;
                    case R.id.id_navigation_usercenter:
                        setAppBarTitle("用户中心");
                        showUserCenterFragment();
                        return true;
                }
                return false;
            }
        });
        //added by zy 6.12 用来启动在WiFi下自动上传图片功能
//        final ExecutorService mThreadPoolExecutor = Executors.newFixedThreadPool(5);
//        for (int i = 0; i <12 ; i++) {
//            final int t = i;
//            mThreadPoolExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Log.v("thread","当前线程"+t+"开启了");
//                        Thread.sleep(500);
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//
//        while (true) {
//            if (!mThreadPoolExecutor.isTerminated()) {
//                Log.v("threadshutdown","线程执行完成 "+ mThreadPoolExecutor.toString().substring( mThreadPoolExecutor.toString().length()-4,mThreadPoolExecutor.toString().length()-1));
//            }
//
//            try {
//                Thread.sleep(500);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }


        bindOnWifiUpLoadService();
    }

    private  void bindOnWifiUpLoadService() {
        Intent onWifiUpLoadServiceIntent = new Intent(this, OnWifiUpLoadService.class);
        bindService(onWifiUpLoadServiceIntent,mServiceConnection, Context.BIND_AUTO_CREATE);
    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
          OnWifiUpLoadService onWifiUpLoadService = ((OnWifiUpLoadService.OnWifiUpLoadBinder)iBinder).getService();
            onWifiUpLoadService.startService(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    /**
     * 控制 fragment 来回切换  的显示或隐藏
     *
     * @param fragment
     * @param containerViewId
     */
    private void showFragment(Fragment fragment, int containerViewId) {
        if (fragment!=mCurrentFragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //
            if (mCurrentFragment!=null){
            transaction.hide(mCurrentFragment);}
            if (!fragment.isAdded()) {
                // transaction.add(containerViewId, fragment).show(fragment).commitAllowingStateLoss();
                transaction.add(containerViewId, fragment).show(fragment).commitNow();
            } else {
                // transaction.show(fragment).commitAllowingStateLoss();
                transaction.show(fragment).commitNow();
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
        if (mMainFragment == null) {
            mMainFragment = MainFragment.newInstance("", "");
        }
        showFragment(mMainFragment, R.id.id_frame_layout_content);
    }

    private void showSearchFragment() {
        if (mSearchFragment == null) {
            mSearchFragment = SearchFragment.newInstance("", "");
        }
        showFragment(mSearchFragment, R.id.id_frame_layout_content);
    }

    private void showScanFragment() {
        if (mScanFragment == null) {
            mScanFragment = ScanFragment.newInstance("", "");
        }
        showFragment(mScanFragment, R.id.id_frame_layout_content);

    }

    private void showUserCenterFragment() {
        if (mUserCenterFragment == null) {
            mUserCenterFragment = UserCenterFragment.newInstance("", "");
        }
        showFragment(mUserCenterFragment,R.id.id_frame_layout_content);

    }

    @Override
    protected void initListener() {

    }

    private void initAppBar() {
        setAppBarTitle("首页");
        //
        if (mClassicTitleBar != null) {
            //update by lzy -2017.3.21
            UserLoginBean userloginbean = UserLoginHelper.userLoginBean();
            String company = "";
            if (userloginbean != null && userloginbean.getCompany() != null) {

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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        delegateBroadcastReceiver = null;
//        delegateBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                synchronized (this) {
//                    ImagePickBean bean = (ImagePickBean)intent.getExtras().get("image");
//                    if (bean != null) {
//                        ACache aCache= ACache.get(MainActivity.this);
//                        ArrayList imageCacheArr = (ArrayList) aCache.getAsObject("imageCache");
//                        int index= -1;
//                        for (int i = 0; i <imageCacheArr.size() ; i++) {
//                            OnWifiUpLoadImageBean imagePickBean = (OnWifiUpLoadImageBean) imageCacheArr.get(i);
//                            if (imagePickBean.getImagePickBean()!= null && bean.getImagePathOrUrl()!= null &&imagePickBean.getImagePickBean().getImagePathOrUrl().equals(bean.getImagePathOrUrl())){
//                                index = i;
//                                break;
//                            }
//                        }
//                        if (index >-1) {
//                            imageCacheArr.remove(imageCacheArr.get(index));
//                        }
//                        aCache.put("imageCache",imageCacheArr);
//                    }
//                }
//            }
//        };
//
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("com.classichu.photoselector.deleteImage");
//        registerReceiver(delegateBroadcastReceiver,intentFilter);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        //unregisterReceiver(delegateBroadcastReceiver);
//    }

}
