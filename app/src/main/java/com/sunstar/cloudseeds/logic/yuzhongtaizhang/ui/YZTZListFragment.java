package com.sunstar.cloudseeds.logic.yuzhongtaizhang.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.adapter.widget.ClassicEmptyView;
import com.classichu.classichu.classic.ClassicMvpFragment;
import com.classichu.dialogview.manager.DialogManager;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.AtyGoToWhere;
import com.sunstar.cloudseeds.logic.scan.ScanQrCodeType;
import com.sunstar.cloudseeds.logic.scan.ScanQrcodeActivity;
import com.sunstar.cloudseeds.logic.search.SearchRecentHelper;
import com.sunstar.cloudseeds.logic.xuanzhu.XuanZhuActivity;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.YZTZActivity;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.adapter.YZTZListAdapter;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZListBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract.YZTZListContract;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.presenter.YZTZListPresenterImpl;
import com.sunstar.cloudseeds.ui.SearchFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YZTZListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YZTZListFragment extends ClassicMvpFragment<YZTZListPresenterImpl> implements YZTZListContract.View<List<YZTZListBean.ListBean>>{

    public Boolean from_searchFragment;

    public YZTZListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YZTZListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YZTZListFragment newInstance(String param1, String param2) {
        YZTZListFragment fragment = new YZTZListFragment();
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
    protected int setupLayoutResId() { return R.layout.fragment_yztz_list; }

    @Override
    protected void initView(View view) {

        //-- add by lzy -2017.3.20
        //隐藏搜索框
        from_searchFragment=false;
        Fragment searchFragment= getParentFragment();
        if(searchFragment !=null && searchFragment instanceof SearchFragment){
            from_searchFragment=true;
            SearchView searchV=findById(R.id.id_search_view);
            searchV.setVisibility(View.GONE);
        }
        toRefreshData();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected YZTZListPresenterImpl setupPresenter() {
        return new YZTZListPresenterImpl(this);
    }

    @Override
    protected int configSwipeRefreshLayoutResId() {
        return R.id.id_swipe_refresh_layout;
    }

    @Override
    protected int configRecyclerViewResId() {
        return R.id.id_recycler_view;
    }

    @Override
    protected void toLoadMoreData() {
        super.toLoadMoreData();
        mPresenter.gainMoreData(mClassicRVHeaderFooterAdapter.getNextPageNum());
    }

    @Override
    protected void toRefreshData() {
        super.toRefreshData();
        mPresenter.gainCountData(mClassicRVHeaderFooterAdapter.getNowPageCount());
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
     //###   ToastTool.showShortCenter(msg);
        DialogManager.showTipDialog(getActivity(),"提示",msg,null);
    }

    @Override
    public void setupData(List<YZTZListBean.ListBean> yztzBeanList) {
        mClassicRVHeaderFooterAdapter.refreshDataList(yztzBeanList);
        //
        mRecyclerView.setVisibility(View.VISIBLE);//返回数据后 显示

        //-- add by lzy -2017.3.20
        //如果是搜索界面进来->保存搜索关键词
        if(yztzBeanList!=null && from_searchFragment){
            SearchRecentHelper.setRecentSearchkey(mContext,mParam2);
        }
    }

    @Override
    public void setupMoreData(List<YZTZListBean.ListBean> yztzBeanList) {
        mClassicRVHeaderFooterAdapter.addDataListAtEnd(yztzBeanList);
        if (yztzBeanList.size() == 0) {
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
        List<YZTZListBean.ListBean> listBeanList = new ArrayList<>();
        YZTZListAdapter adapter
                = new YZTZListAdapter(mContext,listBeanList, R.layout.item_list_yztz);
        ClassicEmptyView classicEmptyView = new ClassicEmptyView(getContext());
        classicEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        adapter.setEmptyView(classicEmptyView);
        adapter.setOnItemClickListener(new ClassicRVHeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                super.onItemClick(itemView, position);
                //
                startAty(XuanZhuActivity.class);
            }
        });
        adapter.setOnItemOperationListener(new YZTZListAdapter.OnItemOperationListener() {
            @Override
            public void onItemShowDetail(int position) {
                super.onItemShowDetail(position);
                //
                startAty(YZTZActivity.class,createBundleExtraInt1(AtyGoToWhere.DETAIL));
            }

            @Override
            public void onItemShowXuanZhu(int position) {
                super.onItemShowXuanZhu(position);
                //##ToastTool.showShortCenter("选株"+position);
                //
                startAty(XuanZhuActivity.class);
            }

            @Override
            public void onItemShowQrcode(int position) {
                super.onItemShowQrcode(position);
                //ToastTool.showShortCenter("绑定二维码"+position);
                YZTZListBean.ListBean listBean= (YZTZListBean.ListBean) mClassicRVHeaderFooterAdapter.getData(position);

                Bundle bundle = createBundleExtraInt1(ScanQrCodeType.bind_zuqun);
                bundle.putString(getResources().getString(R.string.scanqrcode_bundleextrakey_bindId), listBean.getSecondary_id());
                startAty(ScanQrcodeActivity.class,bundle);
            }
        });
        mRecyclerView.setVisibility(View.GONE);//初始化 不显示
        return adapter;
    }

}
