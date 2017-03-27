package com.sunstar.cloudseeds.logic.xuanzhu.contract;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.BasicContract;
import com.classichu.classichu.basic.BasicPresenter;
import com.sunstar.cloudseeds.logic.xuanzhu.bean.XuanZhuListBean;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/13.
 */

public interface XuanZhuListContract extends BasicContract{
     interface Presenter extends BasicPresenter {
         void gainCountData(int pageCount,String keyword);
         void gainMoreData(int pageNum,String keyword);
    }
     interface View<D> extends BasicContract.View<D> {
       String  setupGainDataSecondaryId();
    }
     interface Model<D> extends BasicContract.Model<D> {
        void loadData(String url,String secondary_id,  int pageNum,  int pageSize,  String keyword,
                       BasicCallBack<List<XuanZhuListBean.ListBean>> basicCallBack);
    }

}
