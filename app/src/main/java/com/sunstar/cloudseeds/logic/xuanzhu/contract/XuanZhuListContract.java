package com.sunstar.cloudseeds.logic.xuanzhu.contract;

import com.classichu.classichu.basic.BasicContract;
import com.classichu.classichu.basic.BasicPresenter;

/**
 * Created by louisgeek on 2017/3/13.
 */

public interface XuanZhuListContract extends BasicContract{
     interface Presenter extends BasicPresenter {
         void gainCountData(int pageCount,String keyword);
         void gainMoreData(int pageNum,String keyword);
    }
}
