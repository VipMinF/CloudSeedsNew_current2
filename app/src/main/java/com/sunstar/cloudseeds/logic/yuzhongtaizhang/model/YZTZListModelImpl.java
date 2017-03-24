package com.sunstar.cloudseeds.logic.yuzhongtaizhang.model;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.data.CommDatas;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZListBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract.YZTZListContract;

import java.util.HashMap;
import java.util.List;


/**
 * Created by louisgeek on 2017/03/13
 */

public class YZTZListModelImpl implements YZTZListContract.Model<List<YZTZListBean.ListBean>> {

    @Override
    public void loadData(String url, final int pageNum, final int pageSize, final String keyword,
                         final BasicCallBack<List<YZTZListBean.ListBean>> basicCallBack) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userid", UserLoginHelper.getUserid());
        paramsMap.put("pageNo", String.valueOf(pageNum));
        paramsMap.put("pageSize", String.valueOf(pageSize));
        paramsMap.put("search_keyword", keyword);
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(url, HeadsParamsHelper.setupDefaultHeaders(), paramsMap,
                new GsonHttpRequestCallback<BasicBean<YZTZListBean>>() {
                    @Override
                    public BasicBean<YZTZListBean> OnSuccess(String result) {
                        BasicBean<YZTZListBean> BB = BasicBean.fromJson(result, YZTZListBean.class);
                        return BB;
                    }

                    @Override
                    public void OnSuccessOnUI(BasicBean<YZTZListBean> basicBean) {
                     /*   List<YZTZListBean.ListBean> lbL=new ArrayList<>();
                        for (int i = 0; i < pageSize; i++) {
                            YZTZListBean.ListBean lb=new YZTZListBean.ListBean();
                            lb.setName("yztz 测"+keyword+i+"===页码"+pageNum+"===每页显示"+pageSize);
                            lb.setPlant_code("品种分类"+i);
                            lb.setSeed_batches("种子批号"+i);
                            lb.setSow_num("50"+i);
                            lb.setConfirm_num("30"+i);
                           if (i%3==0){
                               lb.setStatus("2");
                           }else{
                               lb.setStatus("1");
                           }
                            lbL.add(lb);
                        }*/
                        //##  basicBean.getInfo().get(0).setList(lbL);
                       /* if (pageNum>2){
                            lbL.clear();
                        }*/
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