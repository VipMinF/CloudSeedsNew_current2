package com.sunstar.cloudseeds.logic.usercenter.contract;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.BasicContract;

/**
 * Created by Administrator on 2017/3/16.
 */

public class ChangePassWordContract {


    public interface View<D> extends BasicContract.View<D> {
    }

    public interface Presenter {
        void gainData();
    }

    public interface Model<D> extends BasicContract.Model<D>{
        void loadData(String url,String str1 , String str2 , String str3,String str4,BasicCallBack<D> basicCallBack);
    }

}
