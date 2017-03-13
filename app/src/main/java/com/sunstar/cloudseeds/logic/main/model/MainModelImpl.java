package com.sunstar.cloudseeds.logic.main.model;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.data.BasicBean;
import com.sunstar.cloudseeds.logic.main.bean.TaiZhangBean;
import com.sunstar.cloudseeds.logic.main.contract.MainContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* Created by louisgeek on 2017/03/10
*/

public class MainModelImpl implements MainContract.Model<List<TaiZhangBean>>{


    @Override
    public void loadData(String url, final int pageNum, final int pageSize, String queryKey, final BasicCallBack<List<TaiZhangBean>> baseCallBack) {
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("userid","21");
        paramsMap.put("pagenum",String.valueOf(pageNum));
        paramsMap.put("pagesize",String.valueOf(pageSize));
        paramsMap.put("search_keyword","");
        HttpRequestManagerFactory.getRequestManager().getUrlBackStr(url,null,
                new GsonHttpRequestCallback<BasicBean<TaiZhangBean>>() {
            @Override
            public BasicBean<TaiZhangBean> OnSuccess(String result) {
                BasicBean<TaiZhangBean> BB=BasicBean.fromJson(result,TaiZhangBean.class);
                return BB;
            }

            @Override
            public void OnSuccessOnUI(BasicBean<TaiZhangBean> baseBean) {
                List<TaiZhangBean.ListBean> lbL=new ArrayList<TaiZhangBean.ListBean>();
                for (int i = 0; i < pageSize; i++) {
                    TaiZhangBean.ListBean lb=new TaiZhangBean.ListBean();
                    lb.setName("测试数据"+i+"===页码"+pageNum+"===每页显示"+pageSize);
                    lbL.add(lb);
                }
                baseBean.getInfo().get(0).setList(lbL);
                if (pageNum>3){
                    baseBean.getInfo().get(0).getList().clear();
                }
                if ("1".equals(baseBean.getCode())){
                    baseCallBack.onSuccess(baseBean.getInfo());
                }else{
                    baseCallBack.onError(baseBean.getMessage());
                }
            }

            @Override
            public void OnError(String errorMsg) {
                baseCallBack.onError(errorMsg);
            }
        });

    }
}