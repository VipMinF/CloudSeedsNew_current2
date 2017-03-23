package com.sunstar.cloudseeds.logic.shangpinqi.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.classichu.classichu.app.CLog;
import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.classichu.classichu.classic.ClassicMvpFragment;
import com.classichu.itemselector.bean.ItemSelectBean;
import com.classichu.itemselector.helper.ClassicItemSelectorDataHelper;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.EditItemRuleHelper;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.SPQDetailBean;
import com.sunstar.cloudseeds.logic.shangpinqi.contract.SPQDetailContract;
import com.sunstar.cloudseeds.logic.shangpinqi.presenter.SPQDetailPresenterImpl;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SPQAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SPQAddFragment extends ClassicMvpFragment<SPQDetailPresenterImpl> implements SPQDetailContract.View<SPQDetailBean> {
    public SPQAddFragment() {
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
     * @return A new instance of fragment SPQAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SPQAddFragment newInstance(String param1, String param2) {
        SPQAddFragment fragment = new SPQAddFragment();
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
        return R.layout.fragment_spq_add;
    }

    TableLayout id_tl_item_container;
    Button id_btn_submit;
    @Override
    protected void initView(View view) {
        id_btn_submit= findById(R.id.id_btn_submit);
        id_tl_item_container = findById(R.id.id_tl_item_container);
         toRefreshData();
    }


    @Override
    protected void initListener() {
       setOnNotFastClickListener(id_btn_submit, new OnNotFastClickListener() {
           @Override
           protected void onNotFastClick(View view) {
               submitData();
           }
       });
    }

    private void submitData() {
      String json=  EditItemRuleHelper.generateViewBackJson(id_tl_item_container);
      CLog.d("zzqqff:"+json);
        //
        Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }

    @Override
    protected void toRefreshData() {
        super.toRefreshData();
        mPresenter.gainData(UrlDatas.SPQ_ADD_ITEM_RULE);
    }

    @Override
    protected int configSwipeRefreshLayoutResId() {
        return R.id.id_swipe_refresh_layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CLog.d("zhufq:" + requestCode);
        ClassicItemSelectorDataHelper.callAtOnActivityResult(requestCode, resultCode, data,
                new ClassicItemSelectorDataHelper.ItemSelectBackData() {
                    @Override
                    public void backData(View view, List<ItemSelectBean> list) {

                        StringBuilder sb = new StringBuilder();
                        for (ItemSelectBean isb : list
                                ) {
                            if (isb.isSelected()) {
                                sb.append(isb.getItemTitle());
                            }
                        }
                        TextView tv = (TextView) view;
                        tv.setText(sb.toString());
                    }
                });
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
    public void showMessage(String s) {

    }

    @Override
    public void setupData(SPQDetailBean spqDetailBean) {
        //
        List<SPQDetailBean.KeyValueBean> kvbList = spqDetailBean.getKey_value();
        //
        EditItemRuleHelper.generateSPQChildView(getActivity(),id_tl_item_container,kvbList,true);
    }

    @Override
    public void setupMoreData(SPQDetailBean spqDetailBean) {

    }
}
