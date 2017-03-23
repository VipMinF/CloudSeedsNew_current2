package com.sunstar.cloudseeds.logic.scan.model;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.scan.bean.QrcodeBean;
import com.sunstar.cloudseeds.logic.scan.contract.ScanQrCodeContract;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/17.
 */

public class QrcodeModelImpl implements ScanQrCodeContract.Model<QrcodeBean>{

    @Override
    public void loadData (String url,String qrcode_value, String secondary_or_tertiary_id,final BasicCallBack<QrcodeBean> basicCallBack) {
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("qrcode_value",qrcode_value);
        paramsMap.put("secondary_or_tertiary_id",secondary_or_tertiary_id);
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(url, HeadsParamsHelper.setupDefaultHeaders(),paramsMap,
                new GsonHttpRequestCallback<BasicBean<QrcodeBean>>() {
                    @Override
                    public BasicBean<QrcodeBean> OnSuccess(String result) {
                        BasicBean<QrcodeBean> BB= BasicBean.fromJson(result,QrcodeBean.class);
                        return BB;
                    }
                    @Override
                    public void OnSuccessOnUI(BasicBean<QrcodeBean> basicBean) {

                        QrcodeBean qrcodeBean=basicBean.getInfo().get(0);

                        if ("1".equals(basicBean.getCode())){

                            basicCallBack.onSuccess(qrcodeBean);

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
    public void loadData(String s, int i, int i1, String s1, BasicCallBack<QrcodeBean> basicCallBack) {

    }

}
