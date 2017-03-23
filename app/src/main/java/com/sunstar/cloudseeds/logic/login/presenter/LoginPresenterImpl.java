package com.sunstar.cloudseeds.logic.login.presenter;

/**
 * Created by Administrator on 2017/3/21.
 */

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.classic.ClassicPresenter;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.CommPresenterHelper;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;
import com.sunstar.cloudseeds.logic.login.contract.LoginContract;
import com.sunstar.cloudseeds.logic.login.model.LoginModelImpl;


public class LoginPresenterImpl extends ClassicPresenter<LoginContract.View, LoginContract.Model> implements LoginContract.Presenter {

    public LoginPresenterImpl(LoginContract.View view) {
        super(view, new LoginModelImpl());
    }

    @Override
    public void gainData(String loginname, String password) {
        if (CommPresenterHelper.judgeCanNotContinue(mModel)) {
            return;
        }
        mView.showProgress();

        mModel.loadData(UrlDatas.Login_URL, loginname, password, new BasicCallBack<UserLoginBean>() {
            @Override
            public void onSuccess(UserLoginBean userloginBean) {
                if (mView != null) {
                    mView.hideProgress();
                    mView.setupData(userloginBean);
                }
            }

            @Override
            public void onError(String msg) {
                CommPresenterHelper.doErrorThing(mView,msg);
            }
        });
    }

}
