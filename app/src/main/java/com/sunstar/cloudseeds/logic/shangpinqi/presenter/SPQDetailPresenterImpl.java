package com.sunstar.cloudseeds.logic.shangpinqi.presenter;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.classic.ClassicPresenter;
import com.sunstar.cloudseeds.logic.helper.CommPresenterHelper;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.SPQDetailBean;
import com.sunstar.cloudseeds.logic.shangpinqi.contract.SPQDetailContract;
import com.sunstar.cloudseeds.logic.shangpinqi.model.SPQDetailModelImpl;

/**
 * Created by louisgeek on 2017/03/16
 */
public class SPQDetailPresenterImpl extends ClassicPresenter<SPQDetailContract.View, SPQDetailContract.Model> implements SPQDetailContract.Presenter {

    public SPQDetailPresenterImpl(SPQDetailContract.View view) {
        super(view, new SPQDetailModelImpl());
    }


    @Override
    public void gainData(String url) {
        if (CommPresenterHelper.judgeCanNotContinue(mModel)) {
            return;
        }
        mView.showProgress();
        mModel.loadData(url, new BasicCallBack<SPQDetailBean>() {
            @Override
            public void onSuccess(SPQDetailBean bean) {
                if (mView != null) {
                    mView.hideProgress();
                    mView.setupData(bean);
                }
            }

            @Override
            public void onError(String msg) {
                CommPresenterHelper.doErrorThing(mView,msg);
            }
        });
    }
}