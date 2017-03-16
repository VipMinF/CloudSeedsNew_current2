package com.sunstar.cloudseeds.logic.yuzhongtaizhang.presenter;
import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.classic.ClassicPresenter;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZDetailBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract.YZTZDetailContract;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.model.YZTZDetailModelImpl;

/**
* Created by louisgeek on 2017/03/13
*/

public class YZTZDetailPresenterImpl extends ClassicPresenter<YZTZDetailContract.View,
        YZTZDetailContract.Model> implements YZTZDetailContract.Presenter{

    public YZTZDetailPresenterImpl(YZTZDetailContract.View view) {
        super(view, new YZTZDetailModelImpl());
    }

    @Override
    public void gainData(String url) {
        mView.showProgress();
        mModel.loadData(url, new BasicCallBack<YZTZDetailBean>() {
            @Override
            public void onSuccess(YZTZDetailBean bean) {
                mView.hideProgress();
                mView.setupData(bean);
            }

            @Override
            public void onError(String msg) {
                mView.hideProgress();
                mView.showMessage(msg);
            }
        });
    }
}