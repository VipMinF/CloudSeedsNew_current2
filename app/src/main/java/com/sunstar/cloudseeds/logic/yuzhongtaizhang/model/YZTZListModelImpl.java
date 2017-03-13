package com.sunstar.cloudseeds.logic.yuzhongtaizhang.model;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.data.BasicBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZListBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract.YZTZListContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
* Created by louisgeek on 2017/03/13
*/

public class YZTZListModelImpl implements YZTZListContract.Model<List<YZTZListBean.ListBean>> {

    @Override
    public void loadData(String url, final int pageNum, final int pageSize, String s1,
                         final BasicCallBack<List<YZTZListBean.ListBean>> basicCallBack) {
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("userid","21");
        paramsMap.put("pagenum",String.valueOf(pageNum));
        paramsMap.put("pagesize",String.valueOf(pageSize));
        paramsMap.put("search_keyword","");
        HttpRequestManagerFactory.getRequestManager().getUrlBackStr(url,null,
                new GsonHttpRequestCallback<BasicBean<YZTZListBean>>() {
                    @Override
                    public BasicBean<YZTZListBean> OnSuccess(String result) {
                        BasicBean<YZTZListBean> BB= BasicBean.fromJson(result,YZTZListBean.class);
                        return BB;
                    }

                    @Override
                    public void OnSuccessOnUI(BasicBean<YZTZListBean> basicBean) {
                        List<YZTZListBean.ListBean> lbL=new ArrayList<>();
                        for (int i = 0; i < pageSize; i++) {
                            YZTZListBean.ListBean lb=new YZTZListBean.ListBean();
                            lb.setName("yztz 测试数据"+i+"===页码"+pageNum+"===每页显示"+pageSize);
                            lb.setPlant_code("品种分类"+i);
                            lb.setSeed_batches("种子批号"+i);
                            lb.setSow_num("50"+i);
                            lb.setConfirm_num("30"+i);
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