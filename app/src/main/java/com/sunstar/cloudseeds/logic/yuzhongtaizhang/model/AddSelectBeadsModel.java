package com.sunstar.cloudseeds.logic.yuzhongtaizhang.model;

import android.support.v4.app.FragmentActivity;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.classichu.dialogview.manager.DialogManager;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.bean.InfoBean;
import com.sunstar.cloudseeds.data.CommDatas;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by louisgeek on 2017/3/27.
 */

public class AddSelectBeadsModel {

    public void goAddSelectBeads(FragmentActivity fragmentActivity,String id, final BasicCallBack<InfoBean> basicCallBack) {
        DialogManager.showLoadingDialog(fragmentActivity, "请稍候...",true);
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("id",id);
        HttpRequestManagerFactory.getRequestManager()
                .postUrlBackStr(UrlDatas.SECONDARY_ADD_SELECT_BEADS,
                        HeadsParamsHelper.setupDefaultHeaders(), paramsMap,
                        new GsonHttpRequestCallback<BasicBean<InfoBean>>() {
                            @Override
                            public BasicBean<InfoBean> OnSuccess(String s) {
                                return BasicBean.fromJson(s, InfoBean.class);
                            }

                            @Override
                            public void OnSuccessOnUI(BasicBean<InfoBean> basicBean) {
                                if (basicBean == null) {
                                    basicCallBack.onError(CommDatas.SERVER_ERROR);
                                    return;
                                }
                                if (CommDatas.SUCCESS_FLAG.equals(basicBean.getCode())) {
                                    if (basicBean.getInfo() != null && basicBean.getInfo().size() > 0) {
                                        // showMessage(basicBean.getInfo().get(0).getShow_msg());
                                      basicCallBack.onSuccess(basicBean.getInfo().get(0));

                                    } else {
                                        basicCallBack.onError(basicBean.getMessage());
                                    }
                                } else {
                                    basicCallBack.onError(basicBean.getMessage());
                                }
                            }

                            @Override
                            public void OnError(String s) {
                                basicCallBack.onError(s);
                            }
                        });

    }
}
