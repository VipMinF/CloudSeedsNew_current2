package com.sunstar.cloudseeds.logic.yuzhongtaizhang.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.classichu.classichu.classic.ClassicMvpFragment;
import com.classichu.dialogview.manager.DialogManager;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.AtyGoToWhere;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.YZTZActivity;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZDetailBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract.YZTZDetailContract;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.presenter.YZTZDetailPresenterImpl;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YZTZDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YZTZDetailFragment extends ClassicMvpFragment<YZTZDetailPresenterImpl>
implements YZTZDetailContract.View<YZTZDetailBean>{


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
    TextView id_tv_item_title_jzsj;
    Button id_btn_show_add;
    @Override
    protected void initView(View view) {
        id_tv_item_title_jzsj= findById(R.id.id_tv_item_title_jzsj);


        id_btn_show_add= findById(R.id.id_btn_show_add);

        toRefreshData();
    }

    @Override
    protected void initListener() {
        id_btn_show_add.setOnClickListener(new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View view) {
               //### ToastTool.showShortCenter("调查记录");
                startAty(YZTZActivity.class,createBundleExtraInt1(AtyGoToWhere.ADD));
            }
        });
    }

    @Override
    protected YZTZDetailPresenterImpl setupPresenter() {
        return new YZTZDetailPresenterImpl(this);
    }

    @Override
    protected void toRefreshData() {
        //###super.toRefreshData();
        mPresenter.gainData();
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
    public void setupData(YZTZDetailBean yztzDetailBean) {
        id_tv_item_title_jzsj.setText(yztzDetailBean.getSoaking_time());
    }

    @Override
    public void setupMoreData(YZTZDetailBean yztzDetailBean) {

    }


}
