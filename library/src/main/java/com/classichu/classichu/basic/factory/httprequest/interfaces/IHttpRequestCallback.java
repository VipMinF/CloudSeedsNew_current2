package com.classichu.classichu.basic.factory.httprequest.interfaces;

/**
 * Created by louisgeek on 2016/12/28.
 */

public interface IHttpRequestCallback {
    void onSuccess(String result);
    void onFailure(String errorMsg);
}
