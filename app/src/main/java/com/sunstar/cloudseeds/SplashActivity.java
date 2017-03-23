package com.sunstar.cloudseeds;

import android.os.Handler;
import android.widget.ImageView;

import com.classichu.classichu.app.CLog;
import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.data.FinalData;
import com.classichu.classichu.basic.factory.imageloader.ImageLoaderFactory;
import com.classichu.classichu.basic.tool.SharedPreferencesTool;
import com.classichu.classichu.classic.ClassicActivity;
import com.sunstar.cloudseeds.logic.guide.GuideActivity;
import com.sunstar.cloudseeds.logic.login.LoginActivity;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;

public class SplashActivity extends ClassicActivity {


    @Override
    protected int setupLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        ImageView id_iv_start_image = findById(R.id.id_iv_start_image);
        //  GlideHelper.displayImageRes(id_iv_start_image, R.drawable.img_welcome);
        ImageLoaderFactory.getManager().displayImage(id_iv_start_image, R.drawable.img_welcome);
        delayJump(1000);
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

    /**
     * 延迟x毫秒进入
     */
    private void delayJump(long delayMilliseconds) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextWhere();
            }
        }, delayMilliseconds);
    }


    private void goToGuide() {
        startAty(GuideActivity.class);
        finish();
    }

    private void goToMain() {
        startAty(MainActivity.class);
        finish();
    }

    private void goToLogin() {
        startAty(LoginActivity.class);
        finish();
    }

    /**
     *
     */
    private void nextWhere() {

        boolean hasOpenedGuide = (boolean) SharedPreferencesTool.get(mContext,
                FinalData.SP_HAS_OPENED_GUIDE, false);
        CLog.d("hasOpenedGuide DD:" + hasOpenedGuide);
        if (hasOpenedGuide) {

            UserLoginHelper.autoLogin_Online(mContext, new BasicCallBack<UserLoginBean>() {
                @Override
                public void onSuccess(UserLoginBean userLoginBean) {
                    goToMain();
                }
                @Override
                public void onError(String s) {
                    //Handler handler= new Handler();
                    //handler.post(runnableUi);
                    goToLogin();
                }
            });
        } else {
            goToGuide();
        }
    }

    Runnable  runnableUi=new  Runnable(){
        @Override
        public void run() {
            goToLogin();
        }
    };

}
