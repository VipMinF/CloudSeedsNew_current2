package com.sunstar.cloudseeds.logic.main.model;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.data.CommDatas;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.main.bean.TaiZhangBean;
import com.sunstar.cloudseeds.logic.main.contract.MainContract;

import java.util.HashMap;
import java.util.List;

/**
 * Created by louisgeek on 2017/03/10
 */

public class MainModelImpl implements MainContract.Model<List<TaiZhangBean.ListBean>> {


    @Override
    public void loadData(String url, int pageNum, int pageSize, String queryKey, BasicCallBack<List<TaiZhangBean.ListBean>> basicCallBack) {
        loadData(url, "", "", "", "", pageNum, pageSize, queryKey, basicCallBack);

    }


    @Override
    public void loadData(String url, String year, String month, String product, String plan, int pageNum, int pageSize, String keyword, final BasicCallBack<List<TaiZhangBean.ListBean>> basicCallBack) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userid", "80735c93090f4dce9ef3afabc22a33e6");
        paramsMap.put("pageNo", String.valueOf(pageNum));
        paramsMap.put("pageSize", String.valueOf(pageSize));
        paramsMap.put("year", year);
        paramsMap.put("month", month);
        paramsMap.put("product", product);
        paramsMap.put("plan", plan);
        paramsMap.put("search_keyword", keyword);
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(url, HeadsParamsHelper.setupDefaultHeaders(), paramsMap,
                new GsonHttpRequestCallback<BasicBean<TaiZhangBean>>() {
                    @Override
                    public BasicBean<TaiZhangBean> OnSuccess(String result) {
                        BasicBean<TaiZhangBean> BB = BasicBean.fromJson(result, TaiZhangBean.class);
                        return BB;
                    }

                    @Override
                    public void OnSuccessOnUI(BasicBean<TaiZhangBean> basicBean) {
             /*   List<TaiZhangBean.ListBean> lbL=new ArrayList<>();
                for (int i = 0; i < pageSize; i++) {
                    TaiZhangBean.ListBean lb=new TaiZhangBean.ListBean();
                    lb.setName("测试数据"+i+"===页码"+pageNum+"===每页显示"+pageSize);
                    lbL.add(lb);
                }
               // basicBean.getInfo().get(0).setList(lbL);
                if (pageNum>3){
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