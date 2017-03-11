package com.classichu.classichu.basic.helper;

import android.app.Activity;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.swipbackhelper.SwipeListener;

/**
 * Created by louisgeek on 2017/2/19.
 */

public class ClassicSwipeBackHelper {

    public static void initCallAtOnCreated(Activity activity,boolean swipeBackEnable,SwipeListener swipeListener) {
        SwipeBackHelper.onCreate(activity);
        //
        int DEFAULT_SCRIM_COLOR = 0x99000000;
        //可在SwipeBackHelper.onCreate()之后进行如下参数设置：
        SwipeBackHelper.getCurrentPage(activity)//获取当前页面
                .setSwipeBackEnable(swipeBackEnable)//设置是否可滑动
                //.setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.3f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setScrimColor(DEFAULT_SCRIM_COLOR)//底层阴影颜色
                .setClosePercent(0.8f)//触发关闭Activity百分比
                .setSwipeRelateEnable(false)//是否与下一级activity联动(微信效果)。默认关
                .setSwipeRelateOffset(500)//activity联动时的偏移量。默认500px。
                //##webview list 不能侧滑setDisallowInterceptTouchEvent(true)//不抢占事件，默认关（事件将先由子View处理再由滑动关闭处理）
                .setDisallowInterceptTouchEvent(false)//不抢占事件，默认关（事件将先由子View处理再由滑动关闭处理）
                .addListener(swipeListener);
    }
    public static void configCallAtOnPostCreate(Activity activity) {
        SwipeBackHelper.onPostCreate(activity);
    }
    public static void configCallAtOnDestroy(Activity activity) {
        SwipeBackHelper.onDestroy(activity);
    }
}
