package com.sunstar.cloudseeds.logic.xuanzhu.model;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.data.BasicBean;
import com.sunstar.cloudseeds.logic.xuanzhu.bean.XuanZhuListBean;
import com.sunstar.cloudseeds.logic.xuanzhu.contract.XuanZhuListContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by louisgeek on 2017/3/13.
 */

public  class XuanZhuListModelImpl implements XuanZhuListContract.Model<List<XuanZhuListBean.ListBean>>{

    @Override
    public void loadData(String url, final int pageNum, final int pageSize, String s1,
                         final BasicCallBack<List<XuanZhuListBean.ListBean>> basicCallBack) {
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("userid","21");
        paramsMap.put("pagenum",String.valueOf(pageNum));
        paramsMap.put("pagesize",String.valueOf(pageSize));
        paramsMap.put("search_keyword","");
        HttpRequestManagerFactory.getRequestManager().getUrlBackStr(url,null,
                new GsonHttpRequestCallback<BasicBean<XuanZhuListBean>>() {
                    @Override
                    public BasicBean<XuanZhuListBean> OnSuccess(String result) {
                        BasicBean<XuanZhuListBean> BB= BasicBean.fromJson(result,XuanZhuListBean.class);
                        return BB;
                    }

                    @Override
                    public void OnSuccessOnUI(BasicBean<XuanZhuListBean> basicBean) {
                        List<XuanZhuListBean.ListBean> lbL=new ArrayList<>();
                        for (int i = 0; i < pageSize; i++) {
                            XuanZhuListBean.ListBean lb=new XuanZhuListBean.ListBean();
                            lb.setName("xuan zhu测试数据"+i+"===页码"+pageNum+"===每页显示"+pageSize);
                            lb.setSelected_code("212"+i);
                            lbL.add(lb);
                        }
                        //##  basicBean.getInfo().get(0).setList(lbL);
                        if (pageNum>2){
                            lbL.clear();
                        }
                        if ("1".equals(basicBean.getCode())){
                            basicCallBack.onSuccess(lbL);
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
}