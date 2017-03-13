package com.sunstar.cloudseeds.logic.yuzhongxuanzhu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.adapter.widget.ClassicEmptyView;
import com.classichu.classichu.classic.ClassicMvpFragment;
import com.classichu.dialogview.manager.DialogManager;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.yuzhongxuanzhu.adapter.XuanZhuAdapter;
import com.sunstar.cloudseeds.logic.yuzhongxuanzhu.bean.XuanZhuBean;
import com.sunstar.cloudseeds.logic.yuzhongxuanzhu.contract.XuanZhuListContract;
import com.sunstar.cloudseeds.logic.yuzhongxuanzhu.presenter.XuanZhuListPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link XuanZhuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XuanZhuFragment extends ClassicMvpFragment<XuanZhuListPresenterImpl>
        implements XuanZhuListContract.View<List<XuanZhuBean.ListBean>>{



    public XuanZhuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment XuanZhuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static XuanZhuFragment newInstance(String param1, String param2) {
        XuanZhuFragment fragment = new XuanZhuFragment();
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
        return R.layout.fragment_xuan_zhu;
    }

    @Override
    protected void initView(View view) {
        toRefreshData();
    }

    @Override
    protected void initListener() {

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
    protected ClassicRVHeaderFooterAdapter configClassicRVHeaderFooterAdapter() {
        List<XuanZhuBean.ListBean> listBeanList = new ArrayList<>();
        ClassicRVHeaderFooterAdapter classicRVHeaderFooterAdapter
                = new XuanZhuAdapter(mContext,listBeanList, R.layout.item_list_yu_zhong_xuan_zhu);
        ClassicEmptyView classicEmptyView = new ClassicEmptyView(getContext());
        classicEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        classicRVHeaderFooterAdapter.setEmptyView(classicEmptyView);
        classicRVHeaderFooterAdapter.setOnItemClickListener(new ClassicRVHeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                super.onItemClick(itemView, position);
                // ToastTool.showShortCenter("sda" + position);
                //###startAty(YZTZActivity.class);
            }
        });
        mRecyclerView.setVisibility(View.GONE);//初始化 不显示
        return classicRVHeaderFooterAdapter;
    }
    @Override
    protected XuanZhuListPresenterImpl setupPresenter() {
        return new XuanZhuListPresenterImpl(this);
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
        DialogManager.showTipDialog(getActivity(),"提示",msg,null);
    }

    @Override
    public void setupData(List<XuanZhuBean.ListBean> listBeen) {
        mClassicRVHeaderFooterAdapter.refreshDataList(listBeen);
        //
        mRecyclerView.setVisibility(View.VISIBLE);//返回数据后 显示


    }

    @Override
    public void setupMoreData(List<XuanZhuBean.ListBean> listBeen) {
        mClassicRVHeaderFooterAdapter.addDataListAtEnd(listBeen);
        if (listBeen.size() == 0) {
            //所有数据加载完毕
            mClassicRVHeaderFooterAdapter.showFooterViewLoadComplete();
        } else {
            //一次加载完成
            mClassicRVHeaderFooterAdapter.turnNextPageNum();
            mClassicRVHeaderFooterAdapter.showFooterViewNormal();
        }
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
