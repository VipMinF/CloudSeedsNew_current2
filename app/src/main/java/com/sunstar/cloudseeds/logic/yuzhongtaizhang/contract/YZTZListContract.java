package com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract;

import com.classichu.classichu.basic.BasicContract;
import com.classichu.classichu.basic.BasicPresenter;

/**
 * Created by louisgeek on 2017/3/13.
 */

public interface YZTZListContract extends BasicContract{

     interface Presenter extends BasicPresenter {
         void gainCountData(int pageCount,String keyword);
         void gainMoreData(int pageNum,String keyword);
    }

}