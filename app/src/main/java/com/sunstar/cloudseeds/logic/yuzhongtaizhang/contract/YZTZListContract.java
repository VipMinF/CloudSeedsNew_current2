package com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.BasicContract;
import com.classichu.classichu.basic.BasicPresenter;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZListBean;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/13.
 */

public interface YZTZListContract extends BasicContract{
     interface View<D> extends BasicContract.View<D> {
       String setupGainDataPrimaryId();
    }
     interface Presenter extends BasicPresenter {
         void gainCountData(int pageCount,String keyword);
         void gainMoreData(int pageNum,String keyword);
    }
     interface Model<D> extends BasicContract.Model<D> {
          void loadData(String url,String primary_id , int pageNum,  int pageSize,  String keyword,
                               BasicCallBack<List<YZTZListBean.ListBean>> basicCallBack);
    }
}