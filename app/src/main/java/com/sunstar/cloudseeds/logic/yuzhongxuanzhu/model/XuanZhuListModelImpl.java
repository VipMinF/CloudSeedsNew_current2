package com.sunstar.cloudseeds.logic.yuzhongxuanzhu.model;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.data.BasicBean;
import com.sunstar.cloudseeds.logic.yuzhongxuanzhu.bean.XuanZhuBean;
import com.sunstar.cloudseeds.logic.yuzhongxuanzhu.contract.XuanZhuListContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by louisgeek on 2017/3/13.
 */

public  class XuanZhuListModelImpl implements XuanZhuListContract.Model<List<XuanZhuBean.ListBean>>{

    @Override
    public void loadData(String url, final int pageNum, final int pageSize, String s1,
                         final BasicCallBack<List<XuanZhuBean.ListBean>> basicCallBack) {
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("userid","21");
        paramsMap.put("pagenum",String.valueOf(pageNum));
        paramsMap.put("pagesize",String.valueOf(pageSize));
        paramsMap.put("search_keyword","");
        HttpRequestManagerFactory.getRequestManager().getUrlBackStr(url,null,
                new GsonHttpRequestCallback<BasicBean<XuanZhuBean>>() {
                    @Override
                    public BasicBean<XuanZhuBean> OnSuccess(String result) {
                        BasicBean<XuanZhuBean> BB= BasicBean.fromJson(result,XuanZhuBean.class);
                        return BB;
                    }

                    @Override
                    public void OnSuccessOnUI(BasicBean<XuanZhuBean> basicBean) {
                        List<XuanZhuBean.ListBean> lbL=new ArrayList<>();
                        for (int i = 0; i < pageSize; i++) {
                            XuanZhuBean.ListBean lb=new XuanZhuBean.ListBean();
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
