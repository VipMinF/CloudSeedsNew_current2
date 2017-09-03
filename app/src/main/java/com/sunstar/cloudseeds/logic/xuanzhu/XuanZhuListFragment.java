package com.sunstar.cloudseeds.logic.xuanzhu;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import com.sunstar.cloudseeds.data.AtyGoToWhere;
import com.sunstar.cloudseeds.data.CommDatas;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.CommPresenterHelper;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.normalrecord.NormalRecordActivity;
import com.sunstar.cloudseeds.logic.scan.ScanQrCodeType;
import com.sunstar.cloudseeds.logic.scan.ScanQrcodeActivity;
import com.sunstar.cloudseeds.logic.shangpinqi.SPQActivity;
import com.sunstar.cloudseeds.logic.xuanzhu.adapter.RecordListAdapter;
import com.sunstar.cloudseeds.logic.xuanzhu.adapter.XuanZhuListAdapter;
import com.sunstar.cloudseeds.logic.xuanzhu.bean.AddBeansBean;
import com.sunstar.cloudseeds.logic.xuanzhu.bean.RecordListBean;
import com.sunstar.cloudseeds.logic.xuanzhu.bean.XuanZhuListBean;
import com.sunstar.cloudseeds.logic.xuanzhu.contract.XuanZhuListContract;
import com.sunstar.cloudseeds.logic.xuanzhu.event.XuanZhuListRefreshEvent;
import com.sunstar.cloudseeds.logic.xuanzhu.model.RecordListModel;
import com.sunstar.cloudseeds.logic.xuanzhu.model.XuanZhuListModelImpl;
import com.sunstar.cloudseeds.logic.xuanzhu.presenter.XuanZhuListPresenterImpl;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.model.AddSelectBeadsModel;
import com.sunstar.cloudseeds.logic.shangpinqi.ui.SPQDetailFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
 * Use the {@link XuanZhuListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XuanZhuListFragment extends ClassicMvpFragment<XuanZhuListPresenterImpl>
        implements XuanZhuListContract.View<List<XuanZhuListBean.ListBean>> {

    private RecyclerView recordView;
    private String secondary_id;
    public  String primary_id;
    private int page = 1;
    private ArrayList <RecordListBean.ListInfo>resuroceData = new ArrayList();
    private LinearLayoutManager linearLayoutManager;
    private RecordListAdapter recordListAdapter;
    private ViewPager viewPager;
    private SwipeRefreshLayout recordSwipeRefreshLayout;
    private MagicIndicator magicIndicator;
    private Button recordListButton;
    private CommonNavigator commonNavigator;
    private ArrayList<String> titleList;
    private ArrayList<View> viewArr;
    private FrameLayout frameLayout;
    public XuanZhuListFragment() {
        // Required empty public constructor
    }

    private String taizhangName;
    private String zuqunName;

    protected String mParam4;

    protected static final String ARG_PARAM4 = "param4";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment XuanZhuListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static XuanZhuListFragment newInstance(String param1, String param2, String param4) {
        XuanZhuListFragment fragment = new XuanZhuListFragment();
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
        secondary_id = mParam1;
        taizhangName = mParam2;
        zuqunName = mParam4;
    }


    @Override
    protected int setupLayoutResId() {
        return R.layout.fragment_xuan_zhu_list;
    }

    @Override
    protected void initView(View view) {

        TextView id_tv_TaiZhangName = findById(R.id.id_tv_TaiZhangName);
        id_tv_TaiZhangName.setText(taizhangName);
        TextView id_tv_ZuQunName = findById(R.id.id_tv_ZuQunName);
        frameLayout = (FrameLayout) getActivity().findViewById(R.id.id_frame_layout_content);
        id_tv_ZuQunName.setText(zuqunName);
        initSearchView();
        display();
        toRefreshData();
    }

    public void display() {
        LinearLayout linearLayout = (LinearLayout) mRootLayout;
        linearLayout.removeView(mSwipeRefreshLayout);

        final LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        viewArr = new ArrayList();
        viewPager = findById(R.id.id_xuanzhu_viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });

        XuanZhuListModelImpl model = new XuanZhuListModelImpl();
        model.loadData(UrlDatas.TERTIARY_LIST,secondary_id, ClassicRVHeaderFooterAdapter.PAGE_NUM_DEFAULT, 10, "", new BasicCallBack<List<XuanZhuListBean.ListBean>>() {
            @Override
            public void onSuccess(List<XuanZhuListBean.ListBean> data) {
              if(data != null) {
                final  int count = data.size();
                  titleList = new ArrayList<String>();
                  titleList.add("株系数据");
                  titleList.add("商品期数据");
                  titleList.add("日常数据");

                  getActivity().runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          String[] title;
                          if (count == 0){
                             titleList.remove("株系数据");
                          }
                          magicIndicator = findById(R.id.magic_indicator);
                          commonNavigator = new CommonNavigator(getContext());
                          commonNavigator.setAdjustMode(true);
                          commonNavigator.setAdapter(new CommonNavigatorAdapter() {
                              @Override
                              public int getCount() {
                                  return titleList.size();
                              }

                              @Override
                              public IPagerTitleView getTitleView(Context context, final int index) {

                                  SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);

                                  simplePagerTitleView.setText(titleList.get(index));
                                  simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                                  simplePagerTitleView.setSelectedColor(Color.parseColor("#58A758"));

                                  simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          viewPager.setCurrentItem(index);
                                      }
                                  });

                                  return simplePagerTitleView;
                              }

                              @Override
                              public IPagerIndicator getIndicator(Context context) {
                                  LinePagerIndicator indicator = new LinePagerIndicator(context);
                                  indicator.setColors(Color.parseColor("#007700"));
                                  return indicator;
                              }
                          });
                          magicIndicator.setNavigator(commonNavigator);

                          final View layoutView =  layoutInflater.inflate(R.layout.fragment_recordlist,null);
                          recordSwipeRefreshLayout = (SwipeRefreshLayout) layoutView.findViewById(R.id.id_swipe_refresh_layout);
                          recordSwipeRefreshLayout.setProgressViewEndTarget(false,100);
                          recordSwipeRefreshLayout.setColorSchemeResources(new int[]{com.classichu.classichu.R.color.colorAccent});
                          recordSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                              @Override
                              public void onRefresh() {
                                  recorListRefresh();
                              }
                          });
                          recordListButton = (Button) layoutView.findViewById(R.id.id_recordlist_record);
                          recordListButton.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  gotoRecordACtivity();
                              }
                          });

                           final PagerAdapter pagerAdapter = new PagerAdapter() {
                              @Override
                              public int getCount() {
                                  return viewArr.size();
                              }

                              @Override
                              public Object instantiateItem(ViewGroup container, int position) {
                                  View view = viewArr.get(position);
                                  container.removeView(view);
                                  container.addView(viewArr.get(position));
                                  return view;
                              }

                              @Override
                              public void destroyItem(ViewGroup container, int position, Object object) {
                                  container.removeView(viewArr.get(position));
                              }

                              @Override
                              public boolean isViewFromObject(View view, Object object) {
                                  return view == object;
                              }

                               @Override
                               public int getItemPosition(Object object) {
                                   return POSITION_NONE;
                               }
                           };
                          viewPager.setAdapter(pagerAdapter);
                          SPQDetailFragment spqDetailFragment = new SPQDetailFragment();
                          spqDetailFragment.addParam(secondary_id,"", new SPQDetailFragment.RootViewHelper() {
                              @Override
                              public void getRootView(ViewGroup viewGroup ,View childView) {
                                  if (count > 0)  viewArr.add(mSwipeRefreshLayout);
                                  if(childView != null) {
                                      viewGroup.removeView(childView);
                                      viewArr.add(childView);
                                  }
                                  viewArr.add(layoutView);
                                  pagerAdapter.notifyDataSetChanged();
                              }
                          });


                          getFragmentManager().beginTransaction().add(R.id.id_frame_layout_content,spqDetailFragment).hide(spqDetailFragment).commit();
                          final Context context = getActivity();
                          final ArrayList<String> imageurlArr = new ArrayList<String>();
                          recordSwipeRefreshLayout.setRefreshing(true);
                          RecordListModel.loadNormalRecordList(secondary_id, page+"", "10", new BasicCallBack<RecordListBean>() {
                              @Override
                              public void onSuccess(RecordListBean recordListBean) {
                                  recordSwipeRefreshLayout.setRefreshing(false);
                                  if (recordListBean != null) {
                                      //没有日常记录就隐藏
                                      if (!recordListBean.getInfo().get(0).getAll_count().equals("0"))  {
                                          List listInfo = recordListBean.getInfo().get(0).getList();
                                          for (int i = 0; i < listInfo.size(); i++) {
                                              RecordListBean.ListInfo info = (RecordListBean.ListInfo)listInfo.get(i);
                                              resuroceData.add(info);
                                          }
                                      }
                                  }
                                  recordListAdapter = new RecordListAdapter(context, resuroceData, new RecordListAdapter.OnclickItemListener() {
                                      @Override
                                      public void onItemClick(View view, int position) {
                                          if (resuroceData.size()<=0)  return;
                                          imageurlArr.removeAll(imageurlArr);
                                          RecordListBean.ListInfo listInfo = resuroceData.get(position);
                                          Bundle bundle = new Bundle();
                                          bundle.putString("primary_id",secondary_id);
                                          bundle.putString("edit","1");
                                          bundle.putString("record","1");
                                          bundle.putString("time",listInfo.getTime());
                                          bundle.putString("detail",listInfo.getDetail());
                                          bundle.putString("farm",listInfo.getFarm_type_name());
                                          if(listInfo.getImage().size()>0)

                                              for (RecordListBean.imageInfo imageinfo:
                                                      listInfo.getImage()) {
                                                    imageurlArr.add(imageinfo.getBigImageName().replace("\\","/"));
                                              }
                                          bundle.putStringArrayList("images", (ArrayList<String>)imageurlArr);
                                          Intent intent = new Intent();
                                          intent.putExtras(bundle);
                                          intent.setClass(context, NormalRecordActivity.class);
                                          startActivity(intent);
                                      }
                                  });
                                  recordView = (RecyclerView) layoutView.findViewById(R.id.id_recyclerview);
                                  linearLayoutManager = new LinearLayoutManager(viewPager.getContext());
                                  recordView.setLayoutManager(linearLayoutManager);
                                  recordView.setHasFixedSize(true);
                                  recordView.setAdapter(recordListAdapter);
                                  page++;

                                  recordView.addOnScrollListener(new RecycleViewOnScorllViewListener(linearLayoutManager) {
                                      @Override
                                      public void onLoadMore() {
                                          Log.v("loadmore",""+page);
                                          onLoadMoreInformation();
                                      }
                                  });
                              }

                              @Override
                              public void onError(String s) {
                                  recordSwipeRefreshLayout.setRefreshing(false);
                              }
                          });
                      }
                  });
              }
            }

            @Override
            public void onError(String s) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
//        if(viewPager.getAdapter() != null) {
//            viewPager.getAdapter().notifyDataSetChanged();
//        }
    }

    private void gotoRecordACtivity() {
        Bundle bundle = new Bundle();
        bundle.putString("primary_id",secondary_id);
        bundle.putString("record","1");
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(getContext(), NormalRecordActivity.class);
        startActivity(intent);
    }

    //日常记录加载更多
    private  void  onLoadMoreInformation() {
        if(mSwipeRefreshLayout.isRefreshing()) return;
         recordListAdapter.footLoadState = 0;
        recordListAdapter.notifyDataSetChanged();
        RecordListModel.loadNormalRecordList(secondary_id, page+"", "10", new BasicCallBack<RecordListBean>() {
            @Override
            public void onSuccess(RecordListBean recordListBean) {
                if (recordListBean != null) {
                    int preSize = resuroceData.size();
                    List listInfo = recordListBean.getInfo().get(0).getList();
                    for (int i = 0; i < listInfo.size(); i++) {
                        RecordListBean.ListInfo info = (RecordListBean.ListInfo)listInfo.get(i);
                        resuroceData.add(info);
                    }

                    if(resuroceData.size() >preSize) {
                        page++;
                        recordListAdapter.footLoadState = -1;
                        recordListAdapter.notifyDataSetChanged();
                    }else {
                        recordListAdapter.footLoadState = 1;
                        recordListAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onError(String s) {
                recordListAdapter.footLoadState = -1;
                recordListAdapter.resourcedata.remove(recordListAdapter.resourcedata.size()-1);
                if (recordListAdapter != null)
                recordListAdapter.notifyDataSetChanged();
            }
        });

    }

    //日常记录上拉
    private  void recorListRefresh() {
        recordSwipeRefreshLayout.setRefreshing(true);
        RecordListModel.loadNormalRecordList(secondary_id,"1","10", new BasicCallBack<RecordListBean>() {
            @Override
            public void onSuccess(RecordListBean recordListBean) {
                recordListAdapter.footLoadState = -1;
                recordSwipeRefreshLayout.setRefreshing(false);
                if (recordListBean != null) {
                    //没有日常记录就隐藏
                    if (recordListBean.getInfo().get(0).getAll_count().equals("0"))  {
                        return;
                    }
                     resuroceData.removeAll(resuroceData);
                    List listInfo = recordListBean.getInfo().get(0).getList();
                    for (int i = 0; i < listInfo.size(); i++) {
                        RecordListBean.ListInfo info = (RecordListBean.ListInfo)listInfo.get(i);
                        resuroceData.add(info);
                    }
                    if(recordView != null){
                        if (recordListAdapter != null) {
                            recordListAdapter.notifyDataSetChanged();
                            page = 2;
                        }

                    }

                }else {
                    return;
                }

                }

            @Override
            public void onError(String s) {

            }
        });
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
                = new XuanZhuListAdapter(mContext, listBeanList, R.layout.item_list_xuan_zhu);
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
            }
        });
        adapter.setOnItemOperationListener(new XuanZhuListAdapter.OnItemOperationListener() {
            @Override
            public void onItemShowDetail(int position) {
                super.onItemShowDetail(position);
                //###  ToastTool.showShortCenter("onItemShowDetail" + position);
                XuanZhuListBean.ListBean listBean = (XuanZhuListBean.ListBean) mClassicRVHeaderFooterAdapter.getData(position);
                Bundle bundle = createBundleExtraInt1(AtyGoToWhere.DETAIL);
                bundle.putString("Tertiary_id", listBean.getTertiary_id());
                startAty(SPQActivity.class, bundle);
            }

            @Override
            public void onItemShowQrcode(int position) {
                super.onItemShowQrcode(position);
                //ToastTool.showShortCenter("onItemShowQrcode" + position);
                XuanZhuListBean.ListBean listBean = (XuanZhuListBean.ListBean) mClassicRVHeaderFooterAdapter.getData(position);
                Bundle bundle = createBundleExtraInt1(ScanQrCodeType.bind_xuanzhu);
                bundle.putString(getResources().getString(R.string.scanqrcode_bundleextrakey_bindId), listBean.getTertiary_id());
                startAty(ScanQrcodeActivity.class, bundle);
            }

            @Override
            public void onItemShowSpqDc(int position) {
                super.onItemShowSpqDc(position);
                //##  ToastTool.showShortCenter("onItemShowSpqDc" + position);
                XuanZhuListBean.ListBean listBean = (XuanZhuListBean.ListBean) mClassicRVHeaderFooterAdapter.getData(position);
                Bundle bundle = createBundleExtraInt1(AtyGoToWhere.ADD);
                bundle.putString("Tertiary_id", listBean.getTertiary_id());
                startAty(SPQActivity.class, bundle);
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
        DialogManager.showTipDialog(getActivity(), "提示", msg, null);
    }

    @Override
    public String setupGainDataSecondaryId() {
        return secondary_id;
    }

    @Override
    public void setupData(List<XuanZhuListBean.ListBean> listBeen) {
        mClassicRVHeaderFooterAdapter.refreshDataList(listBeen);
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

    private void toSearchData() {
        mPresenter.gainCountData(mClassicRVHeaderFooterAdapter.getNowPageCount(), mQueryText);
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
                //
                toSearchData();
                CLog.d("onQueryTextSubmit:" + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                viewPager.setCurrentItem(0,true);
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


    public void goAddSelectBeads4Aty() {
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
                                goAddSelectBeads4AtyContinue(maxNum);

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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(XuanZhuListRefreshEvent event) {
        CLog.d("XuanZhuListRefreshEvent");
        toRefreshData();
    }

    private void goAddSelectBeads4AtyContinue(String maxNum) {
        DialogManager.showClassicDialog(getActivity(), "选株新增",
                "是否新增一个" + maxNum + "选株", new ClassicDialogFragment.OnBtnClickListener() {
                    @Override
                    public void onBtnClickOk(DialogInterface dialogInterface) {
                        super.onBtnClickOk(dialogInterface);
                        //###
                        new AddSelectBeadsModel().goAddSelectBeads(getActivity(), setupGainDataSecondaryId(),
                                new BasicCallBack<InfoBean>() {
                                    @Override
                                    public void onSuccess(InfoBean infoBean) {
                                       getActivity().runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               //加入株系页面
                                               if (commonNavigator.getAdapter().getCount() != 3) {
                                                   titleList.add(0,"株系记录");
                                                   commonNavigator.getAdapter().notifyDataSetChanged();
                                                   viewArr.add(0,mSwipeRefreshLayout);
                                                   viewPager.getAdapter().notifyDataSetChanged();
                                                   viewPager.setCurrentItem(0,true);
                                               }else {
                                                   viewPager.setCurrentItem(0,true);
                                               }
                                           }
                                       });

                                        //选择新增成功
                                        DialogManager.hideLoadingDialogAutoAfterTip(
                                                infoBean.getShow_msg(),
                                                new DialogManager.OnAutoHide() {
                                                    @Override
                                                    public void autoHide() {
                                                        //刷新
                                                        toRefreshData();
                                                    }
                                                });
                                    }
                                    @Override
                                    public void onError(String s) {
                                        showMessage(s);
                                    }
                                });

                    }
                });
    }



    public abstract class RecycleViewOnScorllViewListener extends  RecyclerView.OnScrollListener {
        private LinearLayoutManager linearLayoutManager;
        int lastVisibleItem, visibleItems,totalCount;
              public  RecycleViewOnScorllViewListener(LinearLayoutManager linearLayoutManager) {
                  super();
                  this.linearLayoutManager = linearLayoutManager;
              }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView,newState);
             if (visibleItems>0 && newState == recyclerView.SCROLL_STATE_IDLE  && lastVisibleItem == totalCount -1) {

                 Log.v("refresh","benginingloadmore");
                if( !recordSwipeRefreshLayout.isRefreshing())
                 onLoadMore();
             }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            visibleItems = linearLayoutManager.getChildCount();
            totalCount = linearLayoutManager.getItemCount();
        }

      public   abstract  void onLoadMore();
    }


}
