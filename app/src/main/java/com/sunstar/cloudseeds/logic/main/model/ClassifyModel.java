package com.sunstar.cloudseeds.logic.main.model;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.main.bean.ClassifyDataBean;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/21.
 */

public class ClassifyModel {


    public void gainClassifyData(String url,final BasicCallBack<List<ClassifyDataBean.ListBean>> basicCallBack) {
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(url, HeadsParamsHelper.setupDefaultHeaders(),null,
                new GsonHttpRequestCallback<BasicBean<ClassifyDataBean>>() {
                    @Override
                    public BasicBean<ClassifyDataBean> OnSuccess(String s) {
                        return BasicBean.fromJson(s, ClassifyDataBean.class);
                    }

                    @Override
                    public void OnSuccessOnUI(BasicBean<ClassifyDataBean> listBeen) {
                        if (listBeen != null) {
                            basicCallBack.onSuccess(listBeen.getInfo().get(0).getList());
                        }
                    }

                    @Override
                    public void OnError(String s) {
                        basicCallBack.onError(s);
                    }
                });
    }
}
