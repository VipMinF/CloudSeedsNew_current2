package com.sunstar.cloudseeds.logic.guide;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.data.FinalData;
import com.classichu.classichu.basic.tool.SharedPreferencesTool;
import com.classichu.classichu.basic.viewpager.DepthPageTransformer;
import com.classichu.classichu.classic.ClassicActivity;
import com.sunstar.cloudseeds.MainActivity;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.ImagesDatas;
import com.sunstar.cloudseeds.logic.guide.adapter.GuidePagerAdapter;
import com.sunstar.cloudseeds.logic.guide.bean.GuideBean;
import com.sunstar.cloudseeds.logic.login.LoginActivity;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends ClassicActivity {

    @Override
    protected int setupLayoutResId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {

        initGuide();
        setupImageData();
    }

    private void setupImageData() {
        List<GuideBean> guideBeanList = new ArrayList<>();
        for (int i = 0; i < ImagesDatas.imageThumbUrls.length; i++) {
            if (i > 3) {
                break;
            }
            GuideBean guideBean = new GuideBean();
            guideBean.setImagepath(ImagesDatas.imageThumbUrls[i]);
            guideBeanList.add(guideBean);
        }
        //
        mGuidePagerAdapter.refreshDataList(guideBeanList);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected boolean configSwipeBackEnable() {
        return false;
    }

    @Override
    protected boolean configStatusBarColorEnable() {
        return false;
    }

    private  GuidePagerAdapter mGuidePagerAdapter;
    private List<GuideBean> mGuideBeanList = new ArrayList<>();
    private void initGuide() {
        TextView id_tv_jump_over = findById(R.id.id_tv_jump_over);
        id_tv_jump_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHasOpenedGuide();
                //
                goToMain();
            }
        });
        ViewPager viewPager = findById(R.id.id_view_pager);
        mGuidePagerAdapter = new GuidePagerAdapter(mGuideBeanList);
        mGuidePagerAdapter.setOnGuideClickListener(new GuidePagerAdapter.OnGuideClickListener() {
            @Override
            public void onJumpInClick(View v) {
                setHasOpenedGuide();
                goToMain();

            }
        });
        viewPager.setAdapter(mGuidePagerAdapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
    }

    private void setHasOpenedGuide() {
        //
        SharedPreferencesTool.put(FinalData.SP_HAS_OPENED_GUIDE, true);
    }

    private void goToLogin() {

        startAty(LoginActivity.class);
        finish();
    }


    private Handler handler= new Handler();

    private void goToMain() {

       UserLoginHelper.autoLogin_Online(new BasicCallBack<UserLoginBean>() {
            @Override
            public void onSuccess(UserLoginBean userLoginBean) {
                goToMain();
                startAty(MainActivity.class);
                finish();
            }
            @Override
            public void onError(String s) {

                handler.post(runnableUi);
                //goToLogin();
            }
        });

    }

    Runnable  runnableUi=new  Runnable(){
        @Override
        public void run() {
            goToLogin();
        }
    };

}
