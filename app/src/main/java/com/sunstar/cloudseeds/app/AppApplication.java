package com.sunstar.cloudseeds.app;

import com.classichu.classichu.app.ClassicApplication;
import com.squareup.leakcanary.LeakCanary;
import com.sunstar.cloudseeds.data.SdkDatas;
import com.tencent.bugly.Bugly;

/**
 * Created by louisgeek on 2017/3/8.
 */

public class AppApplication extends ClassicApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        /**
         *
         */
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        /**
         * 参数1：上下文对象
         参数2：注册时申请的APPID
         参数3：是否开启debug模式，true表示打开debug模式，false表示关闭调试模式

         init方法会自动检测更新，不需要再手动调用Beta.checkUpgrade(), 如需增加自动检查时机可以使用Beta.checkUpgrade(false,false);
         参数1：isManual 用户手动点击检查，非用户点击操作请传false
         参数2：isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗或toast]
        // =====
         /**
         * 参数1：isManual 用户手动点击检查，非用户点击操作请传false
         参数2：isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗或toast]
         */
        //Beta.checkUpgrade(true,false);
         //=====
         //*/
        Bugly.init(this, SdkDatas.BUGLY_API_ID, false);

    }
}
