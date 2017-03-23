package com.sunstar.cloudseeds.logic.login.model;


import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;
import com.sunstar.cloudseeds.logic.login.contract.LoginContract;

import java.util.HashMap;


/**
 * Created by Administrator on 2017/3/16.
 */

public class LoginModelImpl implements LoginContract.Model<UserLoginBean>{

    @Override
    public void loadData (String url, String username, final String psw, final BasicCallBack<UserLoginBean> basicCallBack) {
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("username",username);
        paramsMap.put("password",psw);
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(url,null,paramsMap,
                new GsonHttpRequestCallback<BasicBean<UserLoginBean>>() {
                    @Override
                    public BasicBean<UserLoginBean> OnSuccess(String result) {
                        BasicBean<UserLoginBean> BB= BasicBean.fromJson(result,UserLoginBean.class);
                        return BB;
                    }
                    @Override
                    public void OnSuccessOnUI(BasicBean<UserLoginBean> basicBean) {
                        UserLoginBean userloginBean=basicBean.getInfo().get(0);
                        if ("1".equals(basicBean.getCode())){

                            if ("1".equals(userloginBean.getShow_code())){
                                userloginBean.setPassword(psw);
                                basicCallBack.onSuccess(userloginBean);

                            }else{
                                basicCallBack.onError(userloginBean.getShow_msg());
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
    public void loadData(String s, int i, int i1, String s1, BasicCallBack<UserLoginBean> basicCallBack) {

    }

}
