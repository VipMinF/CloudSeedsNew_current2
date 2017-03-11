package com.classichu.classichu.basic.factory.httprequest.impls;


import com.classichu.classichu.basic.factory.httprequest.interfaces.IHttpRequestCallback;
import com.classichu.classichu.basic.factory.httprequest.interfaces.IHttpRequestManager;
import com.classichu.classichu.basic.okhttp.ParamsTool;
import com.classichu.classichu.basic.tool.HttpTool;

import java.util.Map;

/**
 * Created by louisgeek on 2016/12/28.
 */
@Deprecated //headersMap未用上 // TODO: 2017/3/8
public class SystemHttpRequestManager implements IHttpRequestManager {

    @Override
    public void getUrlBackStr(String url,Map<String, String> headersMap, final IHttpRequestCallback httpRequestCallback) {
        //
        HttpTool.getUrlBackString(url, new HttpTool.OnUrlBackStringCallBack() {
            @Override
            public void onSuccess(String backStr) {
                httpRequestCallback.onSuccess(backStr);
            }

            @Override
            public void onError(String errorMsg) {
                httpRequestCallback.onFailure(errorMsg);
            }
        });
    }

    @Override
    public void postUrlBackStr(String url,Map<String, String> headersMap, Map<String, String> paramsMap, final IHttpRequestCallback httpRequestCallback) {
        //
        HttpTool.postUrlBackString(url, ParamsTool.paramsMapToStr(paramsMap), new HttpTool.OnUrlBackStringCallBack() {
            @Override
            public void onSuccess(String backStr) {
                httpRequestCallback.onSuccess(backStr);
            }

            @Override
            public void onError(String errorMsg) {
                httpRequestCallback.onFailure(errorMsg);
            }
        });
    }

}
