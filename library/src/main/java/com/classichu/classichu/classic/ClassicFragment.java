package com.classichu.classichu.classic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.classichu.R;
import com.classichu.classichu.app.CLog;
import com.classichu.classichu.basic.data.FinalData;
import com.classichu.classichu.basic.event.BasicEvent;
import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.classichu.classichu.basic.listener.OnRecyclerViewTouchListener;
import com.classichu.classichu.basic.tool.ViewTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by louisgeek on 2016/10/31.
 */

public abstract class ClassicFragment extends Fragment {
    //
    protected static final String ARG_PARAM1 = "param1";
    protected static final String ARG_PARAM2 = "param2";
    protected static final String ARG_PARAM3 = "param3";

    protected String mParam1;
    protected String mParam2;
    protected int mParam3;

    protected static String mTag;
    protected Context mContext;
    protected View mRootLayout;

    protected RecyclerView mRecyclerView;
    protected ClassicRVHeaderFooterAdapter mClassicRVHeaderFooterAdapter;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    /**
     * ===================================protected lifecycle=============================
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mTag = this.getClass().getSimpleName();

        mRootLayout = inflater.inflate(setupLayoutResId(), container, false);
        //
        EventBus.getDefault().register(this);
        //
        initRecyclerViewAndAdapter();
        //
        initSwipeRefreshLayout();
        //
        /**
         * last
         */
        initView(mRootLayout);
        initListener();


        return mRootLayout;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * ===================================protected abstract=============================
     */
    protected abstract int setupLayoutResId();

    protected abstract void initView(View rootLayout);

    protected abstract void initListener();

    /**
     * =======================================protected===================================
     */
    protected void toRefreshData() {

        mClassicRVHeaderFooterAdapter.showFooterViewNormal();
        CLog.d("qqq toRefreshData");
    }
    protected void toLoadMoreData() {
        //
        mClassicRVHeaderFooterAdapter.showFooterViewDataLoading();
        CLog.d("qqq toLoadMoreData");
    }
    protected int configRecyclerViewResId() {
        return 0;
    }
    protected int configSwipeRefreshLayoutResId() {
        return 0;
    }

    protected ClassicRVHeaderFooterAdapter configClassicRVHeaderFooterAdapter() {
        return null;
    }

    protected void showSwipeRefreshLayout() {
        if (mSwipeRefreshLayout!=null){
        // mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });}
    }

    protected void hideSwipeRefreshLayout() {
        if (mSwipeRefreshLayout!=null) {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 600);
        }
    }


    protected <V extends View> V findById(int resId) {
        return (V) mRootLayout.findViewById(resId);
    }

    protected void startAty(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    protected void startAty(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtras(bundle);
        startActivity(intent);
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
     * 防止重复点击的监听
     *
     * @param view
     * @param onNotFastClickListener
     */
    protected void setOnNotFastClickListener(final View view,
                                             final OnNotFastClickListener onNotFastClickListener) {
        if (view != null) {
            view.setOnClickListener(onNotFastClickListener);
        }
    }
    /**
     * =====================================private===========================
     */
    private  void  initSwipeRefreshLayout(){
        if (configSwipeRefreshLayoutResId() == 0) {
            return;
        }
        mSwipeRefreshLayout = findById(configSwipeRefreshLayoutResId());
        // mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        //mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CLog.d("onRefresh刷新数据");
                toRefreshData();
            }
        });
    }

    private void initRecyclerViewAndAdapter() {
        if (configRecyclerViewResId() == 0) {
            return;
        }
        /**
         *设置RecyclerView
         */
        mRecyclerView = findById(configRecyclerViewResId());
        if (configClassicRVHeaderFooterAdapter() == null) {
            return;
        }
        //config
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).build());

        /**
         *设置Adapter
         */
        mClassicRVHeaderFooterAdapter = configClassicRVHeaderFooterAdapter();
            /**
             *RecyclerView设置Adapter
             */
            mRecyclerView.setAdapter(mClassicRVHeaderFooterAdapter);

            mRecyclerView.setOnTouchListener(new OnRecyclerViewTouchListener() {
               @Override
               public void onScrollUp() {

               }

               @Override
               public void onScrollDown() {
                   if (ViewTool.isReachedBottom(mRecyclerView)&&
                           !mClassicRVHeaderFooterAdapter.isDataLoading()&&
                           !mClassicRVHeaderFooterAdapter.isLoadComplete()
                           ){
                       toLoadMoreData();
                   }
               }
           });



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
    public void onEventMainThreadFragment(BasicEvent event) {
        CLog.d("onEventMainThreadFragment");
    }
}
