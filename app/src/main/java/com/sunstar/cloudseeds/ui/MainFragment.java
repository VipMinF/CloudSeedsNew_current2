package com.sunstar.cloudseeds.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.adapter.widget.ClassicEmptyView;
import com.classichu.classichu.basic.tool.ScreenTool;
import com.classichu.classichu.classic.ClassicMvpFragment;
import com.classichu.dialogview.manager.DialogManager;
import com.classichu.dropselectview.widget.ClassicSelectView;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.main.adapter.MainAdapter;
import com.sunstar.cloudseeds.logic.main.bean.TaiZhangBean;
import com.sunstar.cloudseeds.logic.main.contract.MainContract;
import com.sunstar.cloudseeds.logic.main.presenter.MainPresenterImpl;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.YZTZActivity;
import com.sunstar.cloudseeds.widget.ClassicMaskFrameLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends ClassicMvpFragment<MainPresenterImpl>
        implements MainContract.View<List<TaiZhangBean.ListBean>> {
    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int setupLayoutResId() {
        return R.layout.fragment_main;
    }
    ClassicMaskFrameLayout mMaskFrameLayout;
    @Override
    protected void initView(View view) {
        toRefreshData();

    /*    Button id_clcik=findById(R.id.id_clcik);
        id_clcik.setOnClickListener(new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View view) {
                mMaskFrameLayout.toggleMask();
            }
        });*/
        ClassicSelectView id_csv=findById(R.id.id_csv);
        ClassicSelectView id_csv2=findById(R.id.id_csv2);
        ClassicSelectView id_csv3=findById(R.id.id_csv3);
        id_csv.setSelectViewContentMaxHeight((int) (ScreenTool.getScreenHeight(mContext)*0.6));
        id_csv.setSelectViewContentMaxHeight((int) (ScreenTool.getScreenHeight(mContext)*0.6));
        id_csv.setSelectViewContentMaxHeight((int) (ScreenTool.getScreenHeight(mContext)*0.6));
        mMaskFrameLayout= findById(R.id.id_mfl);


        id_csv.setOnSelectViewStatusChangeListener(new ClassicSelectView.OnSelectViewStatusChangeListener() {
            @Override
            public void onSelectViewShow() {
                super.onSelectViewShow();
                mMaskFrameLayout.showMask();
            }

            @Override
            public void onSelectViewDismiss() {
                super.onSelectViewDismiss();
                mMaskFrameLayout.hideMask();
            }

            @Override
            public void onItemSelected(String key, String keyWithOutFix, String name) {
                super.onItemSelected(key, keyWithOutFix, name);
            }
        });

        id_csv2.setOnSelectViewStatusChangeListener(new ClassicSelectView.OnSelectViewStatusChangeListener() {
            @Override
            public void onSelectViewShow() {
                super.onSelectViewShow();
                mMaskFrameLayout.showMask();
            }

            @Override
            public void onSelectViewDismiss() {
                super.onSelectViewDismiss();
                mMaskFrameLayout.hideMask();
            }

            @Override
            public void onItemSelected(String key, String keyWithOutFix, String name) {
                super.onItemSelected(key, keyWithOutFix, name);
            }
        });


        id_csv3.setOnSelectViewStatusChangeListener(new ClassicSelectView.OnSelectViewStatusChangeListener() {
            @Override
            public void onSelectViewShow() {
                super.onSelectViewShow();
                mMaskFrameLayout.showMask();
            }

            @Override
            public void onSelectViewDismiss() {
                super.onSelectViewDismiss();
                mMaskFrameLayout.hideMask();
            }

            @Override
            public void onItemSelected(String key, String keyWithOutFix, String name) {
                super.onItemSelected(key, keyWithOutFix, name);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void showProgress() {
        showSwipeRefreshLayout();
    }

    @Override
    public void hideProgress() {
        hideSwipeRefreshLayout();
    }

    @Override
    public void showMessage(String msg) {
        //##  ToastTool.showShortCenter(msg);
        DialogManager.showTipDialog(getActivity(), "提示", msg, null);
    }

    @Override
    public void setupData(List<TaiZhangBean.ListBean> beanList) {
        mClassicRVHeaderFooterAdapter.refreshDataList(beanList);
        //
        mRecyclerView.setVisibility(View.VISIBLE);//返回数据后 显示


    }

    @Override
    public void setupMoreData(List<TaiZhangBean.ListBean> beanList) {
        mClassicRVHeaderFooterAdapter.addDataListAtEnd(beanList);
        if (beanList.size() == 0) {
            //所有数据加载完毕
            mClassicRVHeaderFooterAdapter.showFooterViewLoadComplete();
        } else {
            //一次加载完成
            mClassicRVHeaderFooterAdapter.turnNextPageNum();
            mClassicRVHeaderFooterAdapter.showFooterViewNormal();
        }
    }

    @Override
    protected ClassicRVHeaderFooterAdapter configClassicRVHeaderFooterAdapter() {
        List<TaiZhangBean.ListBean> listBeanList = new ArrayList<>();
        ClassicRVHeaderFooterAdapter classicRVHeaderFooterAdapter
                = new MainAdapter(mContext, listBeanList, R.layout.item_list_tai_zhang);
        ClassicEmptyView classicEmptyView = new ClassicEmptyView(getContext());
        classicEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        classicRVHeaderFooterAdapter.setEmptyView(classicEmptyView);
        classicRVHeaderFooterAdapter.setOnItemClickListener(new ClassicRVHeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                super.onItemClick(itemView, position);
                // ToastTool.showShortCenter("sda" + position);
                startAty(YZTZActivity.class);
            }
        });
        mRecyclerView.setVisibility(View.GONE);//初始化 不显示
        return classicRVHeaderFooterAdapter;
    }

    @Override
    protected MainPresenterImpl setupPresenter() {
        return new MainPresenterImpl(this);
    }


    @Override
    protected int configRecyclerViewResId() {
        return R.id.id_recycler_view;
    }

    @Override
    protected int configSwipeRefreshLayoutResId() {
        return R.id.id_swipe_refresh_layout;
    }

    @Override
    protected void toRefreshData() {
        super.toRefreshData();
        mPresenter.gainCountData(mClassicRVHeaderFooterAdapter.getNowPageCount());

    }

    @Override
    protected void toLoadMoreData() {
        super.toLoadMoreData();
        mPresenter.gainMoreData(mClassicRVHeaderFooterAdapter.getNextPageNum());
    }
}
