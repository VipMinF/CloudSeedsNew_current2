package com.sunstar.cloudseeds.logic.main.contract;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.BasicContract;

/**
 * Created by louisgeek on 2017/3/10.
 */

public  interface MainContract extends BasicContract {
     interface Model<D> extends BasicContract.Model<D> {
         void loadData(String url,String year,String month,String product,String plan, int pageNum, int pageSize, String keyword, BasicCallBack<D> var5);
    }
     interface View<D> extends BasicContract.View<D> {
         String setupFilterDateYear();
         String setupFilterDateMonth();
         String setupFilterProduct();
         String setupFilterPlan();
    }
}