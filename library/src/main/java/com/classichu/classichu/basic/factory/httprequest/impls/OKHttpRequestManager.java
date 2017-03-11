package com.classichu.classichu.basic.factory.httprequest.impls;


import com.classichu.classichu.basic.okhttp.OkHttpHelper;
import com.classichu.classichu.basic.okhttp.StringOkHttpCallback;
import com.classichu.classichu.basic.factory.httprequest.interfaces.IHttpRequestCallback;
import com.classichu.classichu.basic.factory.httprequest.interfaces.IHttpRequestManager;

import java.util.Map;

/**
 * Created by louisgeek on 2016/12/28.
 */

public class OKHttpRequestManager implements IHttpRequestManager {

    @Override
    public void getUrlBackStr(String url,Map<String, String> headersMap, final IHttpRequestCallback httpRequestCallback) {
        //
        new OkHttpHelper.Builder().headers(headersMap).callback(new StringOkHttpCallback() {
            @Override
            public void OnSuccess(String result, int statusCode) {
                httpRequestCallback.onSuccess(result);
            }

            @Override
            public void OnError(String errorMsg, int statusCode) {
                httpRequestCallback.onFailure(errorMsg);
            }
        }).build().doGetUrl(url);
    }

    @Override
    public void postUrlBackStr(String url, Map<String, String> headersMap, Map<String, String> paramsMap, final IHttpRequestCallback httpRequestCallback) {
        //
        new OkHttpHelper.Builder().headers(headersMap).params(paramsMap).callback(new StringOkHttpCallback() {
            @Override
            public void OnSuccess(String result, int statusCode) {
                httpRequestCallback.onSuccess(result);
            }

            @Override
            public void OnError(String errorMsg, int statusCode) {
                httpRequestCallback.onFailure(errorMsg);
            }
        }).build().doPostUrl(url);
    }
}
