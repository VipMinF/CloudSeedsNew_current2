package com.sunstar.cloudseeds.logic.main.presenter;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.classic.ClassicPresenter;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.CommPresenterHelper;
import com.sunstar.cloudseeds.logic.main.bean.TaiZhangBean;
import com.sunstar.cloudseeds.logic.main.contract.MainContract;
import com.sunstar.cloudseeds.logic.main.model.MainModelImpl;

import java.util.List;

/**
* Created by louisgeek on 2017/03/10
*/

public class MainPresenterImpl extends ClassicPresenter<MainContract.View,MainContract.Model>
        implements MainContract.Presenter{

    public MainPresenterImpl(MainContract.View view) {
        super(view, new MainModelImpl());
    }

    @Override
    public void gainCountData(int pageCount) {
        if (CommPresenterHelper.judgeCanNotContinue(mModel)) {
            return;
        }
            mView.showProgress();
            mModel.loadData(UrlDatas.PRIMARY_LIST,
                    mView.setupFilterDateYear(),
                    mView.setupFilterDateMonth(),
                    mView.setupFilterProduct(),
                    mView.setupFilterPlan(),
                    ClassicRVHeaderFooterAdapter.PAGE_NUM_DEFAULT, pageCount, "", new BasicCallBack<List<TaiZhangBean.ListBean>>() {
                @Override
                public void onSuccess(List<TaiZhangBean.ListBean> data) {
                    if (mView != null) {
                        mView.hideProgress();
                        mView.setupData(data);
                    }
                }

                @Override
                public void onError(String msg) {
                    CommPresenterHelper.doErrorThing(mView,msg);
                }
            });
    }

    @Override
    public void gainMoreData(int pageNum) {
        if (CommPresenterHelper.judgeCanNotContinue(mModel)) {
            return;
        }
        mModel.loadData(UrlDatas.PRIMARY_LIST, pageNum, ClassicRVHeaderFooterAdapter.PAGE_SIZE_DEFAULT, "", new BasicCallBack<List<TaiZhangBean.ListBean>>() {
            @Override
            public void onSuccess(final List<TaiZhangBean.ListBean> data) {
                mView.setupMoreData(data);
            }

            @Override
            public void onError(String msg) {
                mView.showMessage(msg);
            }
        });
    }
}