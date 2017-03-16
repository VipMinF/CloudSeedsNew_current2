package com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.BasicContract;

/**
 * Created by louisgeek on 2017/3/13.
 */

public class YZTZDetailContract {

    public interface View<D> extends BasicContract.View<D> {
    }

    public interface Presenter {
        void gainData(String url);
    }

    public interface Model<D> extends BasicContract.Model<D>{
        void loadData(String url, BasicCallBack<D> basicCallBack);
    }
}