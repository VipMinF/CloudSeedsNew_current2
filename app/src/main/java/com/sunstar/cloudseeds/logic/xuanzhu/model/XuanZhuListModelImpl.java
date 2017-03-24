package com.sunstar.cloudseeds.logic.xuanzhu.model;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.data.CommDatas;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.logic.xuanzhu.bean.XuanZhuListBean;
import com.sunstar.cloudseeds.logic.xuanzhu.contract.XuanZhuListContract;

import java.util.HashMap;
import java.util.List;

/**
 * Created by louisgeek on 2017/3/13.
 */

public  class XuanZhuListModelImpl implements XuanZhuListContract.Model<List<XuanZhuListBean.ListBean>>{

    @Override
    public void loadData(String url, final int pageNum, final int pageSize, final String keyword,
                         final BasicCallBack<List<XuanZhuListBean.ListBean>> basicCallBack) {
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("userid", UserLoginHelper.getUserid());
        paramsMap.put("pageNo", String.valueOf(pageNum));
        paramsMap.put("pageSize", String.valueOf(pageSize));
        paramsMap.put("search_keyword",keyword);
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(url, HeadsParamsHelper.setupDefaultHeaders(),paramsMap,
                new GsonHttpRequestCallback<BasicBean<XuanZhuListBean>>() {
                    @Override
                    public BasicBean<XuanZhuListBean> OnSuccess(String result) {
                        BasicBean<XuanZhuListBean> BB= BasicBean.fromJson(result,XuanZhuListBean.class);
                        return BB;
                    }

                    @Override
                    public void OnSuccessOnUI(BasicBean<XuanZhuListBean> basicBean) {
                        if (basicBean == null) {
                            basicCallBack.onError(CommDatas.SERVER_ERROR);
                            return;
                        }
                        if (CommDatas.SUCCESS_FLAG.equals(basicBean.getCode())) {
                            if (basicBean.getInfo() != null && basicBean.getInfo().size() > 0) {
                                basicCallBack.onSuccess(basicBean.getInfo().get(0).getList());
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
