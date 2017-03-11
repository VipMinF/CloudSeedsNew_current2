package com.sunstar.cloudseeds.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.adapter.widget.ClassicEmptyView;
import com.classichu.classichu.basic.tool.ToastTool;
import com.classichu.classichu.classic.ClassicMvpFragment;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.main.adapter.MainAdapter;
import com.sunstar.cloudseeds.logic.main.bean.TaiZhangBean;
import com.sunstar.cloudseeds.logic.main.contract.MainContract;
import com.sunstar.cloudseeds.logic.main.presenter.MainPresenterImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends ClassicMvpFragment<MainPresenterImpl>
        implements MainContract.View<List<TaiZhangBean>> {
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

    @Override
    protected void initView(View view) {
       toRefreshData();
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
        ToastTool.showShortCenter(msg);
    }

    @Override
    public void setupData(List<TaiZhangBean> data) {
        List<TaiZhangBean.ListBean> beanList = data.get(0).getList();
        mClassicRVHeaderFooterAdapter.refreshDataList(beanList);
        //
        mRecyclerView.setVisibility(View.VISIBLE);//返回数据后 显示



    }

    @Override
    public void setupMoreData(List<TaiZhangBean> data) {
        List<TaiZhangBean.ListBean> beanList = data.get(0).getList();
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
                = new MainAdapter(mContext,listBeanList, R.layout.item_list_tai_zhang);
        ClassicEmptyView classicEmptyView = new ClassicEmptyView(getContext());
        classicEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        classicRVHeaderFooterAdapter.setEmptyView(classicEmptyView);
        classicRVHeaderFooterAdapter.setOnItemClickListener(new ClassicRVHeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                super.onItemClick(itemView, position);
                ToastTool.showShortCenter("sda" + position);
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
