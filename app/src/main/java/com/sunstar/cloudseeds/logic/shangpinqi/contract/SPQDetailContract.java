package com.sunstar.cloudseeds.logic.shangpinqi.contract;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.BasicContract;

/**
 * Created by louisgeek on 2017/3/16.
 */
public class SPQDetailContract {
    public interface View<D> extends BasicContract.View<D> {
        String setupGainDataTertiaryId();
    }

    public interface Presenter {
        void gainData(String url);
    }

    public interface Model<D> extends BasicContract.Model<D>{
        void loadData(String url, BasicCallBack<D> basicCallBack);
        void loadData(String url,String id, BasicCallBack<D> basicCallBack);
    }
}
