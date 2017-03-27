package com.sunstar.cloudseeds.logic.main.contract;

import com.classichu.classichu.basic.BasicContract;

/**
 * Created by louisgeek on 2017/3/10.
 */

public  interface MainContract extends BasicContract {
     interface View<D> extends BasicContract.View<D> {
         String setupFilterDateYear();
         String setupFilterDateMonth();
         String setupFilterProduct();
         String setupFilterPlan();
    }
}