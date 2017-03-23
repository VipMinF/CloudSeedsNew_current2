package com.sunstar.cloudseeds.logic.login.contract;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.BasicContract;

/**
 * Created by Administrator on 2017/3/16.
 */

public class LoginContract {


    public interface View<D> extends BasicContract.View<D> {
    }

    public interface Presenter {
        void gainData(String loginname,String password);
    }

    public interface Model<D> extends BasicContract.Model<D>{
        void loadData(String url,String str1 , String str2 ,BasicCallBack<D> basicCallBack);
    }

}
