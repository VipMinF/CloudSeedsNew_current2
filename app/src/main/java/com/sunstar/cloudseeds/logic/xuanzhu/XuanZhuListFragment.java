package com.sunstar.cloudseeds.logic.xuanzhu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.adapter.widget.ClassicEmptyView;
import com.classichu.classichu.app.CLog;
import com.classichu.classichu.basic.tool.ToastTool;
import com.classichu.classichu.classic.ClassicMvpFragment;
import com.classichu.dialogview.manager.DialogManager;
import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.AtyGoToWhere;
import com.sunstar.cloudseeds.logic.shangpinqi.SPQActivity;
import com.sunstar.cloudseeds.logic.xuanzhu.adapter.XuanZhuListAdapter;
import com.sunstar.cloudseeds.logic.xuanzhu.bean.XuanZhuListBean;
import com.sunstar.cloudseeds.logic.xuanzhu.contract.XuanZhuListContract;
import com.sunstar.cloudseeds.logic.xuanzhu.presenter.XuanZhuListPresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link XuanZhuListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XuanZhuListFragment extends ClassicMvpFragment<XuanZhuListPresenterImpl>
        implements XuanZhuListContract.View<List<XuanZhuListBean.ListBean>>{



    public XuanZhuListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment XuanZhuListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static XuanZhuListFragment newInstance(String param1, String param2) {
        XuanZhuListFragment fragment = new XuanZhuListFragment();
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
        return R.layout.fragment_xuan_zhu_list;
    }

    @Override
    protected void initView(View view) {
        initSearchView();
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
        List<XuanZhuListBean.ListBean> listBeanList = new ArrayList<>();
        XuanZhuListAdapter adapter
                = new XuanZhuListAdapter(mContext,listBeanList, R.layout.item_list_xuan_zhu);
        ClassicEmptyView classicEmptyView = new ClassicEmptyView(getContext());
        classicEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        adapter.setEmptyView(classicEmptyView);
        adapter.setOnItemClickListener(new ClassicRVHeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                super.onItemClick(itemView, position);
                // ToastTool.showShortCenter("sda" + position);
                //###startAty(SPQActivity.class);
            }
        });
        adapter.setOnItemOperationListener(new XuanZhuListAdapter.OnItemOperationListener() {
            @Override
            public void onItemShowDetail(int position) {
                super.onItemShowDetail(position);
              //###  ToastTool.showShortCenter("onItemShowDetail" + position);
                startAty(SPQActivity.class,createBundleExtraInt1(AtyGoToWhere.DETAIL));
            }

            @Override
            public void onItemShowQrcode(int position) {
                super.onItemShowQrcode(position);
                ToastTool.showShortCenter("onItemShowQrcode" + position);
            }

            @Override
            public void onItemShowSpqDc(int position) {
                super.onItemShowSpqDc(position);
              //##  ToastTool.showShortCenter("onItemShowSpqDc" + position);
                startAty(SPQActivity.class,createBundleExtraInt1(AtyGoToWhere.ADD));
            }
        });
        mRecyclerView.setVisibility(View.GONE);//初始化 不显示
        return adapter;
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
    public void setupData(List<XuanZhuListBean.ListBean> listBeen) {
        mClassicRVHeaderFooterAdapter.refreshDataList(listBeen);
        //
        mRecyclerView.setVisibility(View.VISIBLE);//返回数据后 显示


    }

    @Override
    public void setupMoreData(List<XuanZhuListBean.ListBean> listBeen) {
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



    private String mQueryText;
    private void initSearchView() {
        final SearchView searchView = findById(R.id.id_search_view);
        //设置搜索图标是否显示在搜索框内
        searchView.setIconifiedByDefault(false);//The default value is true   ，设置为false直接展开显示 左侧有放大镜  右侧无叉叉   有输入内容后有叉叉
        //!!! searchView.setIconified(false);//true value will collapse the SearchView to an icon, while a false will expand it. 左侧无放大镜 右侧直接有叉叉
        //  searchView.onActionViewExpanded();//直接展开显示 左侧无放大镜 右侧无叉叉 有输入内容后有叉叉 内部调用了setIconified(false);
        //searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setQueryHint("请输入关键字");//设置查询提示字符串
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               /* mQueryText = query;
                mPresenter.querySSQData();*/
                CLog.d("onQueryTextSubmit:" + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                //RxBinding
                RxSearchView.queryTextChanges(searchView)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        //对用户输入的关键字进行过滤
                        /*.filter(new Func1<CharSequence, Boolean>() {
                            @Override
                            public Boolean call(CharSequence charSequence) {
                                return charSequence.toString().trim().length() > 0;
                            }
                        })*/
                        .subscribe(new Consumer<CharSequence>() {
                            @Override
                            public void accept(@NonNull CharSequence charSequence) throws Exception {
                                mQueryText = charSequence.toString();
                                if (mQueryText.trim().length() > 0) {
                                    // mPresenter.querySSQData();
                                    // TODO: 2017/1/18
                                } else {//空白
                                    //  mPresenter.gainCountData(Integer.MAX_VALUE);
                                    // TODO: 2017/1/18
                                }
                                CLog.d("queryTextChanges:" + mQueryText);
                            }
                        });

                return false;
            }
        });
    }
}
