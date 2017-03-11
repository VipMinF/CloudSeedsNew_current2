package com.classichu.classichu.classic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.classichu.classichu.R;
import com.classichu.classichu.app.CLog;
import com.classichu.classichu.basic.data.FinalData;
import com.classichu.classichu.basic.event.BasicEvent;
import com.classichu.classichu.basic.helper.ClassicSwipeBackHelper;
import com.classichu.classichu.basic.helper.PermissionsHelper;
import com.classichu.classichu.basic.helper.StatusBarHelper;
import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.classichu.titlebar.widget.ClassicTitleBar;
import com.jude.swipbackhelper.SwipeListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class ClassicActivity extends AppCompatActivity {
    //开启vector
    static {
        /**
         * 可以正常使用Selector这样的DrawableContainers了。同时，
         * 你还开启了类似android:drawableLeft这样的compound drawable的使用权限，
         * 以及RadioButton的使用权限，以及ImageView’s src属性
         */
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected String mTag;
    protected Context mContext;
    protected Activity mActivity;
    protected Toolbar mToolbar;
    protected ClassicTitleBar mClassicTitleBar;

    /**
     * ===================================protected lifecycle=============================
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(setupLayoutResId());*/
        initContentView(setupLayoutResId());

        mTag = this.getClass().getSimpleName();
        mContext = this;
        mActivity = this;
        CLog.d("onCreate");

        /**
         * SwipeBack
         */
        ClassicSwipeBackHelper.initCallAtOnCreated(this, configSwipeBackEnable(), mSwipeListener);
        /**
         * StatusBar
         */
        if (configStatusBarColorEnable()) {
            if (configSwipeBackEnable()) {
                StatusBarHelper.setColorForSwipeBack(this, configStatusBarColorEnable(), configStatusBarColorResId());
            } else {
                 StatusBarHelper.setColor(this, configStatusBarColorEnable(), configStatusBarColorResId());
            }
            //修正StatusBarHelper造成的背景色改变
            getContentViewRootLayout().setBackgroundColor(ContextCompat.getColor(this, R.color.windowBackground));
        }


        /**
         * 消息
         */
        EventBus.getDefault().register(this);
        /**
         * last
         */
        initView();
        initListener();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        /**
         * 侧滑返回
         */
        ClassicSwipeBackHelper.configCallAtOnPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 侧滑返回
         */
        ClassicSwipeBackHelper.configCallAtOnDestroy(this);
        /**
         * 消息
         */
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (configMenuResId() != 0) {
            getMenuInflater().inflate(configMenuResId(), menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // TODO: 2017/2/22
        PermissionsHelper.callAtOnRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    /**
     * ===================================protected abstract=============================
     */
    protected abstract int setupLayoutResId();

    protected abstract void initView();

    protected abstract void initListener();
    /**
     * =================================protected===============================
     */
    /**
     * 侧滑开关
     */
    protected boolean configSwipeBackEnable() {
        return true;
    }

    protected AppBarStyle configAppBarStyle() {
        return AppBarStyle.None;
    }

    /**
     *
     */
    protected boolean configStatusBarColorEnable() {
        return true;
    }

    protected int configStatusBarColorResId() {
        return 0;
    }

    protected int configMenuResId() {
        return 0;
    }

    protected boolean configBackBtnEnable() {
        return true;
    }

    /**
     * Find View
     */
    protected <V extends View> V findById(int resId) {
        return (V) findViewById(resId);
    }

    protected <V extends View> V findById(View view, int resId) {
        return (V) view.findViewById(resId);
    }

    protected void startAty(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void startAty(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void startAtyForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    protected void startAtyForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    protected String getBundleExtraStr1() {
        String bundleExtra = null;
        Bundle bundle = getBundleExtra();
        if (bundle != null) {
            bundleExtra = bundle.getString(FinalData.BUNDLE_EXTRA_KEY_1, "");
        }
        return bundleExtra;
    }

    protected int getBundleExtraInt1() {
        int bundleExtra = 0;
        Bundle bundle = getBundleExtra();
        if (bundle != null) {
            bundleExtra = bundle.getInt(FinalData.BUNDLE_EXTRA_KEY_1);
        }
        return bundleExtra;
    }

    protected Bundle getBundleExtra() {
        Bundle bundle = null;
        if (getIntent() != null) {
            bundle = getIntent().getExtras();
        }
        return bundle;
    }

    protected Bundle createBundleExtraInt1(int value1) {
        Bundle bundle = new Bundle();
        bundle.putInt(FinalData.BUNDLE_EXTRA_KEY_1, value1);
        return bundle;
    }

    protected Bundle createBundleExtraStr1(String value1) {
        Bundle bundle = new Bundle();
        bundle.putString(FinalData.BUNDLE_EXTRA_KEY_1, value1);
        return bundle;
    }

    /**
     * 获取根视图的容器 包含标题栏 content等
     */
    protected View getContentView() {
        //DecorView是一个FrameLayout子类  里面有一个id为content的FrameLayout用来存放我们的布局
        return getWindow().getDecorView().findViewById(android.R.id.content);
    }

    /**
     * 获取根视图  xml根节点 Layout
     */
    protected View getContentViewRootLayout() {
        //DecorView是一个FrameLayout子类  里面有一个id为content的FrameLayout用来存放我们的布局
        ViewGroup viewGroup = (ViewGroup) getContentView();
        return viewGroup.getChildAt(0);
    }


    /**
     * 防止重复点击的监听
     *
     * @param view
     * @param onNotFastClickListener
     */
    protected void setOnNotFastClickListener(final View view, final OnNotFastClickListener onNotFastClickListener) {
        if (view != null) {
            view.setOnClickListener(onNotFastClickListener);
        }
    }

    protected enum AppBarStyle {
        None, ToolBar, ToolBar_InFrame, ToolBar_InMerge,
        ClassicTitleBar, ClassicTitleBar_InFrame, ClassicTitleBar_InMerge
    }

    protected void setAppBarTitle(int resId) {
        setAppBarTitle(getString(resId));
    }

    protected void setAppBarTitle(CharSequence title) {
        if (configAppBarStyle() == AppBarStyle.ToolBar ||
                configAppBarStyle() == AppBarStyle.ToolBar_InMerge ||
                configAppBarStyle() == AppBarStyle.ToolBar_InFrame
                ) {
            if (mToolbar != null) {
                mToolbar.setTitle(title);
            }
        } else if (configAppBarStyle() == AppBarStyle.ClassicTitleBar ||
                configAppBarStyle() == AppBarStyle.ClassicTitleBar_InMerge ||
                configAppBarStyle() == AppBarStyle.ClassicTitleBar_InFrame
                ) {
            mClassicTitleBar.setCenterText(title);
        }
    }
    /**
     * =====================================public===========================
     */
    /**
     * 防止抛异常
     * EventBusException：its super classes have no public methods with the @Subscribe annotation
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThreadAty(BasicEvent event) {
        CLog.d("onEventMainThreadAty");
    }

    /**
     * =====================================private===========================
     */
    private void initContentView(int layoutResID) {
        if (AppBarStyle.ToolBar.equals(configAppBarStyle())) {
            initToolBarContentView(layoutResID, R.layout.root_layout_linearlayout);
        } else if (AppBarStyle.ToolBar_InFrame.equals(configAppBarStyle())) {
            initToolBarContentView(layoutResID, R.layout.root_layout_framelayout);
        } else if (AppBarStyle.ToolBar_InMerge.equals(configAppBarStyle())) {
            initToolBarContentView(layoutResID, R.layout.root_layout_merge);
        } else if (AppBarStyle.ClassicTitleBar.equals(configAppBarStyle())) {
            initClassicTitleBarContentView(layoutResID, R.layout.root_layout_linearlayout);
        } else if (AppBarStyle.ClassicTitleBar_InFrame.equals(configAppBarStyle())) {
            initClassicTitleBarContentView(layoutResID, R.layout.root_layout_framelayout);
        } else if (AppBarStyle.ClassicTitleBar_InMerge.equals(configAppBarStyle())) {
            initClassicTitleBarContentView(layoutResID, R.layout.root_layout_merge);
        } else {
            setContentView(layoutResID);
        }

    }


    protected void initClassicTitleBarContentView(int layoutResID, int rootLayoutResID) {
        setContentView(rootLayoutResID);
        //懒加载
        ViewStubCompat id_vs_title = findById(R.id.id_vs_title);
        id_vs_title.setLayoutResource(R.layout.layout_classic_titlebar);
        id_vs_title.inflate();

        mClassicTitleBar = findById(R.id.id_classic_titlebar);
        if (mClassicTitleBar != null) {
            if (configBackBtnEnable()) {
                mClassicTitleBar.setLeftImageClassicBack()
                        .setOnTitleBarLeftItemClickListener(new ClassicTitleBar.OnTitleBarLeftItemClickListener() {
                            @Override
                            public void onLeftClick(View view) {
                                //结束当前aty
                                finish();
                            }
                        });
            }
        }

        ViewStubCompat id_vs_content = findById(R.id.id_vs_content);
        id_vs_content.setLayoutResource(layoutResID);
        id_vs_content.inflate();
    }


    protected void initToolBarContentView(int layoutResID, int rootLayoutResID) {
        setContentView(rootLayoutResID);
        //懒加载
        ViewStubCompat id_vs_title = findById(R.id.id_vs_title);
        id_vs_title.setLayoutResource(R.layout.layout_classic_toolbar);
        id_vs_title.inflate();

        mToolbar = findById(R.id.id_toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            if (configBackBtnEnable()) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                //必须设置在setSupportActionBar(mToolbar);后才有效
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //结束当前aty
                        finish();
                    }
                });
            }
        }

        ViewStubCompat id_vs_content = findById(R.id.id_vs_content);
        id_vs_content.setLayoutResource(layoutResID);
        id_vs_content.inflate();
    }

    /**
     * 侧滑监听
     */
    private SwipeListener mSwipeListener = new SwipeListener() {
        @Override
        public void onScroll(float percent, int px) {

        }

        @Override
        public void onEdgeTouch() {

        }

        @Override
        public void onScrollToClose() {

        }
    };

}
