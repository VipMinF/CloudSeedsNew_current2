package com.sunstar.cloudseeds.logic.yuzhongtaizhang.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TableLayout;

import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.classichu.classichu.classic.ClassicMvpFragment;
import com.classichu.dialogview.manager.DialogManager;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.AtyGoToWhere;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.EditItemRuleHelper;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.YZTZActivity;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZDetailBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract.YZTZDetailContract;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.presenter.YZTZDetailPresenterImpl;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YZTZDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YZTZDetailFragment extends ClassicMvpFragment<YZTZDetailPresenterImpl>
        implements YZTZDetailContract.View<YZTZDetailBean> {


    public YZTZDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YZTZDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YZTZDetailFragment newInstance(String param1, String param2) {
        YZTZDetailFragment fragment = new YZTZDetailFragment();
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
        return R.layout.fragment_yztz_detail;
    }

    TableLayout id_tl_item_container;

    @Override
    protected void initView(View view) {
        id_tl_item_container = findById(R.id.id_tl_item_container);

        toRefreshData();
    }

    @Override
    protected void initListener() {
        setOnNotFastClickListener(findById(R.id.id_btn_show_add), new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View view) {
                startAty(YZTZActivity.class, createBundleExtraInt1(AtyGoToWhere.ADD));
            }
        });
    }

    @Override
    protected YZTZDetailPresenterImpl setupPresenter() {
        return new YZTZDetailPresenterImpl(this);
    }

    @Override
    protected void toRefreshData() {
        super.toRefreshData();
        mPresenter.gainData(UrlDatas.YU_ZHONG_TAI_ZHANG_DETAIL);
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
    protected int configSwipeRefreshLayoutResId() {
        return R.id.id_swipe_refresh_layout;
    }

    @Override
    public void showMessage(String msg) {
        //###   ToastTool.showShortCenter(msg);
        DialogManager.showTipDialog(getActivity(), "提示", msg, null);
    }

    @Override
    public void setupData(YZTZDetailBean yztzDetailBean) {
        List<YZTZDetailBean.KeyValueBean> keyValueBeanList = yztzDetailBean.getKey_value();
        EditItemRuleHelper.generateYZTZChildView(getActivity(), id_tl_item_container, keyValueBeanList);
    }

    @Override
    public void setupMoreData(YZTZDetailBean yztzDetailBean) {

    }


}
