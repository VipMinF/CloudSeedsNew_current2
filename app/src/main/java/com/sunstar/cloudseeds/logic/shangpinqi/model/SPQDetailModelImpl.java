package com.sunstar.cloudseeds.logic.shangpinqi.model;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.data.CommDatas;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.SPQDetailBean;
import com.sunstar.cloudseeds.logic.shangpinqi.contract.SPQDetailContract;

import java.util.HashMap;

/**
* Created by louisgeek on 2017/03/16
*/
public class SPQDetailModelImpl implements SPQDetailContract.Model<SPQDetailBean>{

    @Override
    public void loadData(String url, int pageNum, int pageSize, String keyword, BasicCallBack<SPQDetailBean> basicCallBack) {

    }

    @Override
    public void loadData(String url, final BasicCallBack<SPQDetailBean> basicCallBack) {
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("id","f275ce6d5ed343449d65fe2d9f3ad313");

        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(url, HeadsParamsHelper.setupDefaultHeaders(),paramsMap,
                new GsonHttpRequestCallback<BasicBean<SPQDetailBean>>() {
                    @Override
                    public BasicBean<SPQDetailBean> OnSuccess(String result) {
                        BasicBean<SPQDetailBean> BB= BasicBean.fromJson(result,SPQDetailBean.class);
                        return BB;
                    }

                    @Override
                    public void OnSuccessOnUI(BasicBean<SPQDetailBean> basicBean) {

                        if (basicBean == null) {
                            basicCallBack.onError(CommDatas.SERVER_ERROR);
                            return;
                        }
                        if (CommDatas.SUCCESS_FLAG.equals(basicBean.getCode())) {
                            if (basicBean.getInfo() != null && basicBean.getInfo().size() > 0) {
                                basicCallBack.onSuccess(basicBean.getInfo().get(0));
                            } else {
                                basicCallBack.onError(basicBean.getMessage());
                            }
                        } else {
                            basicCallBack.onError(basicBean.getMessage());
                        }

                    }

                    @Override
                    public void OnError(String errorMsg) {
                        basicCallBack.onError(errorMsg);
                    }
                });
    }
}