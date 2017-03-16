package com.sunstar.cloudseeds.logic.shangpinqi.model;

import com.classichu.classichu.basic.BasicCallBack;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.SPQAddBean;
import com.sunstar.cloudseeds.logic.shangpinqi.contract.SPQAddContract;

/**
* Created by louisgeek on 2017/03/16
*/
@Deprecated
public class SPQAddModelImpl implements SPQAddContract.Model<SPQAddBean>{

    @Override
    public void loadData(String url, int pageNum, int pageSize, String keyword, BasicCallBack<SPQAddBean> basicCallBack) {

    }

    @Override
    public void loadData(String url, BasicCallBack<SPQAddBean> basicCallBack) {

    }
}