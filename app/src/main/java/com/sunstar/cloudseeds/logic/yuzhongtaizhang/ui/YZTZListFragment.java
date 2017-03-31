package com.sunstar.cloudseeds.logic.yuzhongtaizhang.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.adapter.widget.ClassicEmptyView;
import com.classichu.classichu.app.CLog;
import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.classichu.classichu.basic.tool.ThreadTool;
import com.classichu.classichu.basic.tool.ToastTool;
import com.classichu.classichu.classic.ClassicMvpFragment;
import com.classichu.dialogview.manager.DialogManager;
import com.classichu.dialogview.ui.ClassicDialogFragment;
import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.bean.InfoBean;
import com.sunstar.cloudseeds.data.CommDatas;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.scan.ScanQrCodeType;
import com.sunstar.cloudseeds.logic.scan.ScanQrcodeActivity;
import com.sunstar.cloudseeds.logic.search.SearchRecentHelper;
import com.sunstar.cloudseeds.logic.xuanzhu.XuanZhuActivity;
import com.sunstar.cloudseeds.logic.xuanzhu.bean.AddBeansBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.adapter.YZTZListAdapter;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZListBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract.YZTZListContract;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.model.AddSelectBeadsModel;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.presenter.YZTZListPresenterImpl;
import com.sunstar.cloudseeds.ui.SearchFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YZTZListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YZTZListFragment extends ClassicMvpFragment<YZTZListPresenterImpl> implements YZTZListContract.View<List<YZTZListBean.ListBean>> {

    public Boolean from_searchFragment;

    public YZTZListFragment() {
        // Required empty public constructor
    }

    private String primary_id;
    private String TaiZhangName;

    protected String mParam4;
    protected static final String ARG_PARAM4 = "param4";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YZTZListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YZTZListFragment newInstance(String param1, String param2, String param4) {
        YZTZListFragment fragment = new YZTZListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
        primary_id = mParam1;
        mQueryText = mParam2;
        TaiZhangName = mParam4;
    }

    @Override
    protected int setupLayoutResId() {
        return R.layout.fragment_yztz_list;
    }

    @Override
    protected void initView(View view) {

        TextView id_tv_TaiZhangName = findById(R.id.id_tv_TaiZhangName);
        id_tv_TaiZhangName.setText(TaiZhangName);

        initSearchView();
        //-- add by lzy -2017.3.20
        //隐藏搜索框
        from_searchFragment = false;
        Fragment searchFragment = getParentFragment();
        if (searchFragment != null && searchFragment instanceof SearchFragment) {
            from_searchFragment = true;
            SearchView searchV = findById(R.id.id_search_view);
            searchV.setVisibility(View.GONE);

        }
        if (from_searchFragment) {
            toSearchData();
        } else {
            toRefreshData();
        }
    }

    @Override
    protected void initListener() {

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
                mQueryText = query;
                toSearchData();
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
                                    toSearchData();
                                } else {//空白
                                    //刷新所有
                                    toRefreshData();
                                }
                                CLog.d("queryTextChanges:" + mQueryText);
                            }
                        });

                return false;
            }
        });
    }

    private void toSearchData() {
        mPresenter.gainCountData(mClassicRVHeaderFooterAdapter.getNowPageCount(), mQueryText);
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
        DialogManager.showTipDialog(getActivity(), "提示", msg, null);
    }

    @Override
    public String setupGainDataPrimaryId() {
        return primary_id;
    }

    @Override
    public void setupData(List<YZTZListBean.ListBean> yztzBeanList) {
        mClassicRVHeaderFooterAdapter.refreshDataList(yztzBeanList);
        //
        mRecyclerView.setVisibility(View.VISIBLE);//返回数据后 显示

        //-- add by lzy -2017.3.20
        //如果是搜索界面进来->保存搜索关键词
        if (yztzBeanList != null && from_searchFragment) {
            SearchRecentHelper.setRecentSearchkey(mContext, mQueryText);
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
                = new YZTZListAdapter(mContext, listBeanList, R.layout.item_list_yztz);
        ClassicEmptyView classicEmptyView = new ClassicEmptyView(getContext());
        classicEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        classicEmptyView.setOnEmptyViewClickListener(new ClassicEmptyView.OnEmptyViewClickListener() {
            @Override
            public void onClickTextView(View view) {
                super.onClickTextView(view);
                toRefreshData();
            }

            @Override
            public void onClickImageView(View view) {
                super.onClickImageView(view);
                toRefreshData();
            }

            @Override
            public void onClickEmptyView(View view) {
                super.onClickEmptyView(view);
                toRefreshData();
            }
        });
        adapter.setEmptyView(classicEmptyView);
        adapter.setOnItemClickListener(new ClassicRVHeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                super.onItemClick(itemView, position);
                //

            }
        });
        adapter.setOnItemOperationListener(new YZTZListAdapter.OnItemOperationListener() {
            @Override
            public void onItemShowDetail(int position) {
                super.onItemShowDetail(position);
                //
                YZTZListBean.ListBean listBean = (YZTZListBean.ListBean) mClassicRVHeaderFooterAdapter.getData(position);
                Bundle bundle=createBundleExtraStr1(listBean.getSecondary_id());
                bundle.putString("taizhangName",TaiZhangName);
                bundle.putString("zuqunName",listBean.getName());
                startAty(XuanZhuActivity.class, bundle);
                //2017年3月23日15:31:28 暂时不需要族群详细页 startAty(YZTZActivity.class,createBundleExtraInt1(AtyGoToWhere.DETAIL));
            }

            @Override
            public void onItemShowXuanZhu(int position) {
                super.onItemShowXuanZhu(position);
                //##ToastTool.showShortCenter("选株"+position);
                YZTZListBean.ListBean listBean = (YZTZListBean.ListBean) mClassicRVHeaderFooterAdapter.getData(position);
                goAddSelectBeads(listBean.getSecondary_id());
            }

            @Override
            public void onItemShowQrcode(int position) {
                super.onItemShowQrcode(position);
                //ToastTool.showShortCenter("绑定二维码"+position);
                YZTZListBean.ListBean listBean = (YZTZListBean.ListBean) mClassicRVHeaderFooterAdapter.getData(position);

                Bundle bundle = createBundleExtraInt1(ScanQrCodeType.bind_zuqun);
                bundle.putString(getResources().getString(R.string.scanqrcode_bundleextrakey_bindId), listBean.getSecondary_id());
                startAty(ScanQrcodeActivity.class, bundle);
            }
        });
        mRecyclerView.setVisibility(View.GONE);//初始化 不显示
        return adapter;
    }


    private void goAddSelectBeads(final String secondary_id) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("id", secondary_id);
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(UrlDatas.URL_GET_SELECT_BEADS_MAX_NUM,
                HeadsParamsHelper.setupDefaultHeaders(),
                paramsMap, new GsonHttpRequestCallback<BasicBean<AddBeansBean>>() {
                    @Override
                    public BasicBean<AddBeansBean> OnSuccess(String s) {
                        return BasicBean.fromJson(s, AddBeansBean.class);
                    }

                    @Override
                    public void OnSuccessOnUI(BasicBean<AddBeansBean> basicBean) {
                        if (basicBean == null) {
                            OnError(CommDatas.SERVER_ERROR);
                            return;
                        }
                        if (CommDatas.SUCCESS_FLAG.equals(basicBean.getCode())) {
                            if (basicBean.getInfo() != null && basicBean.getInfo().size() > 0) {

                                String maxNum = basicBean.getInfo().get(0).getShow_msg();
                                goAddSelectBeads4AtyContinue(secondary_id, maxNum);

                            } else {
                                OnError(basicBean.getMessage());
                            }
                        } else {
                            OnError(basicBean.getMessage());
                        }


                    }

                    @Override
                    public void OnError(final String s) {
                        ThreadTool.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastTool.showShort(s);
                            }
                        });
                    }
                });

    }

    private void goAddSelectBeads4AtyContinue(final String secondary_id, String maxNum) {
        DialogManager.showClassicDialog(getActivity(), "选株新增",
                "是否新增一个" + maxNum + "选株", new ClassicDialogFragment.OnBtnClickListener() {
                    @Override
                    public void onBtnClickOk(DialogInterface dialogInterface) {
                        super.onBtnClickOk(dialogInterface);

                        new AddSelectBeadsModel().goAddSelectBeads(getActivity(), secondary_id, new BasicCallBack<InfoBean>() {
                            @Override
                            public void onSuccess(InfoBean infoBean) {
                                //选择新增成功
                                DialogManager.hideLoadingDialogAutoAfterTip(
                                        infoBean.getShow_msg(),
                                        new DialogManager.OnAutoHide() {
                                            @Override
                                            public void autoHide() {
                                                //跳转
                                                startAty(XuanZhuActivity.class, createBundleExtraStr1(secondary_id));
                                            }
                                        });
                            }

                            @Override
                            public void onError(String s) {
                                showMessage(s);
                                DialogManager.hideLoadingDialog();
                            }
                        });

                    }
                });
    }
}
