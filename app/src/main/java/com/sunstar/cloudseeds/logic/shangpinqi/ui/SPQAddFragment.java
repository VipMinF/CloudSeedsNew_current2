package com.sunstar.cloudseeds.logic.shangpinqi.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.classichu.classichu.app.CLog;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.classichu.classichu.classic.ClassicFragment;
import com.classichu.itemselector.ClassicItemSelectorDataHelper;
import com.classichu.itemselector.bean.ItemSelectBean;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.BasicBean;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.EditItemRuleHelper;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.SPQAddBean;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SPQAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SPQAddFragment extends ClassicFragment {


    public SPQAddFragment() {
        // Required empty public constructor
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

    @Override
    protected void initView(View view) {
        id_tl_item_container = findById(R.id.id_tl_item_container);


        initEditItemRuleData();
    }

    private void initEditItemRuleData() {
        HttpRequestManagerFactory.getRequestManager().getUrlBackStr(UrlDatas.SPQ_ADD_ITEM_RULE, null, new GsonHttpRequestCallback<BasicBean<SPQAddBean>>() {
            @Override
            public BasicBean<SPQAddBean> OnSuccess(String s) {
                return BasicBean.fromJson(s, SPQAddBean.class);
            }

            @Override
            public void OnSuccessOnUI(BasicBean<SPQAddBean> spqAddBeanBasicBean) {
                backData(spqAddBeanBasicBean.getInfo().get(0));
            }

            @Override
            public void OnError(String s) {

            }
        });

    }

    @Override
    protected void initListener() {
       setOnNotFastClickListener(findById(R.id.id_btn_submit), new OnNotFastClickListener() {
           @Override
           protected void onNotFastClick(View view) {
               submitData();
           }
       });
    }

    private void submitData() {
      String json=  EditItemRuleHelper.generateViewBackJson(id_tl_item_container);
      CLog.d("zzqqff:"+json);

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

    public void backData(SPQAddBean spqAddBean) {
        //
        List<SPQAddBean.KeyValueBean> kvbList = spqAddBean.getKey_value();
        //
        EditItemRuleHelper.generateSPQChildView(getActivity(),id_tl_item_container,kvbList);
    }


}
