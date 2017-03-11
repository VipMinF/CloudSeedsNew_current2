package com.classichu.classichu.classic;

/**
 * Created by louisgeek on 2016/12/4.
 */
public interface BasicModel<D> {
    void loadDataBase(String url, int pageNum, int pageSize, String queryKey, BasicCallBack<D> baseCallBack);
}