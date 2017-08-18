package com.sunstar.cloudseeds.logic.xuanzhu.model;

import android.util.Log;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.google.gson.Gson;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.logic.xuanzhu.bean.RecordListBean;


import java.security.acl.LastOwnerException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xiaoxian on 2017/5/2.
 */

public class RecordListModel {

    public static void loadNormalRecordList(String primary_id,final String pageNo,String pageSize, final  BasicCallBack<RecordListBean> basicCallBack)  {
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("userid", UserLoginHelper.getUserid());
        paramsMap.put("primary_id",primary_id);
        paramsMap.put("pageNo",pageNo);
        Log.v("pageno",pageNo);
        paramsMap.put("pageSize",pageSize);
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(UrlDatas.URL_GET_FARMLIST, HeadsParamsHelper.setupDefaultHeaders(),paramsMap,
                new GsonHttpRequestCallback<BasicBean<RecordListBean>>() {
                    RecordListBean recordListBean;
                    @Override//动态赋值
                    public BasicBean<RecordListBean> OnSuccess(String result) {
                        Gson gson = new Gson();
                        recordListBean = gson.fromJson(result,RecordListBean.class);
                        Log.v("recordlist",result);
                        BasicBean<RecordListBean> bb= BasicBean.fromJson(result,RecordListBean.class);
                        return bb;
                    }

                    @Override
                    public void OnSuccessOnUI(BasicBean<RecordListBean> basicBean) {

                        basicCallBack.onSuccess(recordListBean);

                    }

                    @Override
                    public void OnError(String errorMsg) {

                    }
                });

    }

}
