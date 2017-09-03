package com.sunstar.cloudseeds.logic.normalrecord;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Build;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sunstar.cloudseeds.logic.helper.EditItemRuleHelper;
import com.sunstar.cloudseeds.logic.shangpinqi.ui.SPQAddFragment;
import com.sunstar.cloudseeds.R;



import java.util.ArrayList;
import java.util.List;


import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;


//CloudSeedsNew1.3
/**
 * Created by xiaoxian on 2017/4/7.
 */

public class NormalRecordActivity extends AppCompatActivity {



    private Toolbar toolBar ;
    private NormalRecordFragment normalRecordFragment;
    private SPQAddFragment spqAddFragment;
    private MagicIndicator magicIndicator;
    private ViewPager viewPager;

    public void initToolbar() {
     setSupportActionBar(getToolbar());
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolBar.setPadding(0,getStatusBarHeight(this),0,0);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result =   EditItemRuleHelper.generateViewBackString(spqAddFragment.id_tl_item_container);
                if (result.equals(spqAddFragment.beforeResult)) {
                    finish();
                }

                AlertDialog.Builder dialog = new AlertDialog.Builder(NormalRecordActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("数据未保存！是否保存");
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                    }
                });
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                dialog.show();
            }

        });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_record);
        setTitle("");
        initToolbar();

//        //5.0以上
        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            // View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：
            //Activity布局全屏显示，但状态栏不会被隐藏覆盖，
            //状态栏依然可见，Activity顶端布局部分会被状态遮住。
            //SYSTEM_UI_FLAG_LAYOUT_STABLE ：
            //防止状态栏隐藏，保证你使用fitSystemWindows时候,系统UI边界
            //始终不会变，依然存在可见.即使你隐藏了所有，他依然存在，增加了UI稳定性，
//            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//状态栏颜色设置为透明。
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#259b24"));
       }
//
        //4.0 -5.0
        if(Build.VERSION.SDK_INT<=19){
            WindowManager.LayoutParams  myLayoutParams = getWindow().getAttributes();
            myLayoutParams.flags =(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|myLayoutParams.flags);
        }


        viewPager = (ViewPager)findViewById(R.id.id_record_viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });
        normalRecordFragment = new NormalRecordFragment();
        Bundle bundle = getIntent().getExtras();
        String record = bundle.getString("record");

         spqAddFragment =  SPQAddFragment.newInstance(bundle.getString("primary_id"),"");
        final ArrayList<Fragment>viewArrayList = new ArrayList<Fragment>();
        viewArrayList.add(spqAddFragment);
        viewArrayList.add(normalRecordFragment);
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return viewArrayList.get(position);
            }

            @Override
            public int getCount() {
                return viewArrayList.size();
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);


        final String[] titles = new String[]{"商品期记录","日常记录"};
        magicIndicator = (MagicIndicator)findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {

                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);

                simplePagerTitleView.setText(titles[index]);
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#58A758"));

                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });

                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#007700"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);

        if(record != null) viewPager.setCurrentItem(1,true);
    }


    protected  Toolbar getToolbar() {
        return  toolBar = (Toolbar) findViewById(R.id.id_activity_normal_toolbar);
    }


    @Override //处理照片选取回调
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
       int index = viewPager.getCurrentItem();
        if(index == 0) {
            spqAddFragment.onActivityResult(requestCode,resultCode,data);
        }else if (index == 1){
            normalRecordFragment.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static int getStatusBarHeight(Context context)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}




