package com.sunstar.cloudseeds.logic.usercenter.model;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.logic.usercenter.bean.SimpleBean;
import com.sunstar.cloudseeds.logic.usercenter.contract.ChangePassWordContract;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/16.
 */

public class ChangePassWordImpl implements ChangePassWordContract.Model<SimpleBean> {

    @Override
    public  void loadData (String url,String userId,String psw_old, String psw, String psw_again,final BasicCallBack<SimpleBean> basicCallBack) {
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("userid",userId);
        paramsMap.put("password",psw);
        paramsMap.put("password2",psw_again);
        paramsMap.put("passwordOld",psw_old);
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(url,null, paramsMap,
                new GsonHttpRequestCallback<BasicBean<SimpleBean>>() {
                    @Override
                    public BasicBean<SimpleBean> OnSuccess(String result) {
                        BasicBean<SimpleBean> BB= BasicBean.fromJson(result,SimpleBean.class);
                        return BB;
                    }
                    @Override
                    public void OnSuccessOnUI(BasicBean<SimpleBean> basicBean) {
                        SimpleBean simpleBean=basicBean.getInfo().get(0);
                        if ("1".equals(basicBean.getCode())){
                            if (simpleBean.getShow_code().equals("1")){
                                basicCallBack.onSuccess(simpleBean);
                            }else{
                                basicCallBack.onError(simpleBean.getShow_msg());
                            }
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

    @Override
    public void loadData(String s, int i, int i1, String s1, BasicCallBack<SimpleBean> basicCallBack) {

    }




}
