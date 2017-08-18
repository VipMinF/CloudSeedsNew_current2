package com.sunstar.cloudseeds.logic.yuzhongtaizhang.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.classichu.classichu.app.CLog;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.classichu.classichu.basic.tool.ToastTool;
import com.classichu.classichu.classic.ClassicMvpFragment;
import com.classichu.itemselector.bean.ItemSelectBean;
import com.classichu.itemselector.helper.ClassicItemSelectorDataHelper;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.bean.InfoBean;
import com.sunstar.cloudseeds.data.CommDatas;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.EditItemRuleHelper;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZDetailBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract.YZTZDetailContract;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.presenter.YZTZDetailPresenterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YZTZAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YZTZAddFragment extends ClassicMvpFragment<YZTZDetailPresenterImpl>
        implements YZTZDetailContract.View<YZTZDetailBean> {


    public YZTZAddFragment() {
        // Required empty public constructor
    }

    @Override
    protected YZTZDetailPresenterImpl setupPresenter() {
        return new YZTZDetailPresenterImpl(this);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YZTZAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YZTZAddFragment newInstance(String param1, String param2) {
        YZTZAddFragment fragment = new YZTZAddFragment();
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
        return R.layout.fragment_yztz_add;
    }

    private TableLayout id_tl_item_container;
    Button id_btn_submit;

    @Override
    protected void initView(View view) {
        id_btn_submit = findById(R.id.id_btn_submit);
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

    @Override
    protected void toRefreshData() {
        super.toRefreshData();
        mPresenter.gainData(UrlDatas.SECONDARY_EDIT);
    }


    private void submitData() {
        String result = EditItemRuleHelper.generateViewBackString(id_tl_item_container);
        //CLog.d("zzqqff:" + result);

        Map<String, String> stringMap = new HashMap<>();
        //stringMap.put("id", "f275ce6d5ed343449d65fe2d9f3ad313");
        stringMap.put("itemvalue", result);
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(UrlDatas.SECONDARY_SAVE,
                HeadsParamsHelper.setupDefaultHeaders(), stringMap, new GsonHttpRequestCallback<BasicBean<InfoBean>>() {


                    @Override
                    public BasicBean<InfoBean> OnSuccess(String s) {
                        return BasicBean.fromJson(s, InfoBean.class);
                    }

                    @Override
                    public void OnSuccessOnUI(BasicBean<InfoBean> basicBean) {
                        if (basicBean == null) {
                            showMessage(CommDatas.SERVER_ERROR);
                            return;
                        }
                        if (CommDatas.SUCCESS_FLAG.equals(basicBean.getCode())) {
                            if (basicBean.getInfo() != null && basicBean.getInfo().size() > 0) {
                                //
                                ToastTool.showShort(basicBean.getInfo().get(0).getShow_msg());
                                //
                                getActivity().onBackPressed();
                            } else {
                                showMessage(basicBean.getMessage());
                            }
                        } else {
                            showMessage(basicBean.getMessage());
                        }
                    }

                    @Override
                    public void OnError(String s) {
                        showMessage(s);
                    }
                }

        );
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
    protected int configSwipeRefreshLayoutResId() {
        return R.id.id_swipe_refresh_layout;
    }

    @Override
    public void showMessage(String s) {

    }

    @Override
    public void setupData(YZTZDetailBean yztzBean) {
        //
        List<YZTZDetailBean.KeyValueBean> kvbList = yztzBean.getKey_value();
        //
        EditItemRuleHelper.generateYZTZChildView(getActivity(), id_tl_item_container, kvbList,true);
    }

    @Override
    public void setupMoreData(YZTZDetailBean yztzDetailBean) {

    }
}
