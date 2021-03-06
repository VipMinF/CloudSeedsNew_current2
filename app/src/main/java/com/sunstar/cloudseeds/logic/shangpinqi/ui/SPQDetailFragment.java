package com.sunstar.cloudseeds.logic.shangpinqi.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TableLayout;

import com.classichu.classichu.app.CLog;
import com.classichu.classichu.basic.extend.DataHolderSingleton;
import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.classichu.classichu.basic.tool.ThreadTool;
import com.classichu.classichu.basic.tool.ToastTool;
import com.classichu.classichu.classic.ClassicMvpFragment;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.AtyGoToWhere;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.EditItemRuleHelper;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.logic.shangpinqi.SPQActivity;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.SPQDetailBean;
import com.sunstar.cloudseeds.logic.shangpinqi.contract.SPQDetailContract;
import com.sunstar.cloudseeds.logic.shangpinqi.event.SPQDetailRefreshEvent;
import com.sunstar.cloudseeds.logic.shangpinqi.presenter.SPQDetailPresenterImpl;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SPQDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SPQDetailFragment extends ClassicMvpFragment<SPQDetailPresenterImpl>
        implements SPQDetailContract.View<SPQDetailBean> {


    private String mNowTertiary_id;
    private View rootView;
    private RootViewHelper rootViewHelper;
    public SPQDetailFragment() {
        // Required empty public constructor
    }

    @Override
    protected SPQDetailPresenterImpl setupPresenter() {
        return new SPQDetailPresenterImpl(this);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SPQDetialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SPQDetailFragment newInstance(String param1, String param2) {
        SPQDetailFragment fragment = new SPQDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public   void addParam(String param1, String param2,RootViewHelper rootViewHelper1) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        setArguments(args);
        rootViewHelper = rootViewHelper1;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mNowTertiary_id = mParam1;
    }

    @Override
    protected int setupLayoutResId() {
        return R.layout.fragment_spq_detail;
    }

    TableLayout id_tl_item_container;

    @Override
    protected void initView(View view) {
        id_tl_item_container = findById(R.id.id_tl_item_container);
        rootView = view;
        toRefreshData();
    }

    @Override
    protected void initListener() {
        //
        setOnNotFastClickListener(findById(R.id.id_btn_show_add), new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View view) {
                Bundle bundle = createBundleExtraInt1(AtyGoToWhere.ADD);
                bundle.putString("Tertiary_id", mNowTertiary_id);
                startAty(SPQActivity.class, bundle);
            }
        });
    }

    @Override
    protected void toRefreshData() {
        super.toRefreshData();
        mPresenter.gainData(UrlDatas.TERTIARY_DETAIL);
    }

    @Override
    protected int configSwipeRefreshLayoutResId() {
        return R.id.id_swipe_refresh_layout;
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
    public void showMessage(final String s) {
        ThreadTool.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastTool.showShort(s);
            }
        });
    }

    @Override
    public void setupData(SPQDetailBean spqDetailBean) {

        //
        List<SPQDetailBean.KeyValueBean> kvbList = spqDetailBean.getKey_value();

        String spqedit_resultid = spqDetailBean.getRule_id();
        DataHolderSingleton.getInstance().putData("spqedit_resultid",spqedit_resultid);
        String spqedit_plant_number = spqDetailBean.getPlant_number();
        DataHolderSingleton.getInstance().putData("spqedit_plant_number",spqedit_plant_number);
        String spqedit_companyid = UserLoginHelper.userLoginBean().getCompany();
        DataHolderSingleton.getInstance().putData("spqedit_companyid",spqedit_companyid);


        EditItemRuleHelper.generateSPQChildView(getActivity(), id_tl_item_container, kvbList,false);
    }

    @Override
    public void setupMoreData(SPQDetailBean spqDetailBean) {

    }

    @Override
    public String setupGainDataTertiaryId() {
        return mNowTertiary_id;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SPQDetailRefreshEvent event) {
        CLog.d("SPQDetailRefreshEvent");
        toRefreshData();
    }

    public  interface RootViewHelper {
       void getRootView(ViewGroup rootView ,View childView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (rootViewHelper != null){
            rootViewHelper.getRootView((ViewGroup)rootView, findById(R.id.id_spq_layout));
        }
    }
}
