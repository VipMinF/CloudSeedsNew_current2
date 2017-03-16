package com.sunstar.cloudseeds.logic.yuzhongtaizhang.model;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZDetailBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract.YZTZDetailContract;

import java.util.HashMap;

/**
* Created by louisgeek on 2017/03/13
*/

public class YZTZDetailModelImpl implements YZTZDetailContract.Model<YZTZDetailBean>{


    @Override
    public void loadData(String url, final BasicCallBack<YZTZDetailBean> basicCallBack) {
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("userid","21");

        HttpRequestManagerFactory.getRequestManager().getUrlBackStr(url,null,
                new GsonHttpRequestCallback<BasicBean<YZTZDetailBean>>() {
                    @Override
                    public BasicBean<YZTZDetailBean> OnSuccess(String result) {
                        BasicBean<YZTZDetailBean> BB= BasicBean.fromJson(result,YZTZDetailBean.class);
                        return BB;
                    }

                    @Override
                    public void OnSuccessOnUI(BasicBean<YZTZDetailBean> basicBean) {

                        YZTZDetailBean yztzDetailBean=basicBean.getInfo().get(0);

                        if ("1".equals(basicBean.getCode())){
                            basicCallBack.onSuccess(yztzDetailBean);
                        }else{
                            basicCallBack.onError(basicBean.getMessage());
                        }
                    }

                    @Override
                    public void OnError(String errorMsg) {
                        basicCallBack.onError(errorMsg);
                    }
                });
    }

    @Override
    public void loadData(String s, int i, int i1, String s1, BasicCallBack<YZTZDetailBean> basicCallBack) {

    }
}