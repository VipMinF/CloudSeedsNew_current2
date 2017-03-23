package com.sunstar.cloudseeds.logic.yuzhongtaizhang.model;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.data.CommDatas;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZDetailBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract.YZTZDetailContract;

import java.util.HashMap;
import java.util.Map;

/**
* Created by louisgeek on 2017/03/13
*/

public class YZTZDetailModelImpl implements YZTZDetailContract.Model<YZTZDetailBean>{


    @Override
    public void loadData(String url, final BasicCallBack<YZTZDetailBean> basicCallBack) {
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("id", "f275ce6d5ed343449d65fe2d9f3ad313");
        //
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(url,
                HeadsParamsHelper.setupDefaultHeaders(),mapParams,
                new GsonHttpRequestCallback<BasicBean<YZTZDetailBean>>() {
                    @Override
                    public BasicBean<YZTZDetailBean> OnSuccess(String result) {
                        BasicBean<YZTZDetailBean> BB= BasicBean.fromJson(result,YZTZDetailBean.class);
                        return BB;
                    }

                    @Override
                    public void OnSuccessOnUI(BasicBean<YZTZDetailBean> basicBean) {
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

    @Override
    public void loadData(String s, int i, int i1, String s1, BasicCallBack<YZTZDetailBean> basicCallBack) {

    }
}