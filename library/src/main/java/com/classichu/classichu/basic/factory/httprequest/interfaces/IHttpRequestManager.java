package com.classichu.classichu.basic.factory.httprequest.interfaces;

import java.util.Map;

/**
 * Created by louisgeek on 2016/12/28.
 */

public interface IHttpRequestManager {
    void getUrlBackStr(String url, Map<String, String> headersMap, IHttpRequestCallback httpRequestCallback);

    void postUrlBackStr(String url, Map<String, String> headersMap, Map<String, String> paramsMap, IHttpRequestCallback httpRequestCallback);


}
