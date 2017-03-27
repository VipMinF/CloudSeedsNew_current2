package com.sunstar.cloudseeds.logic.yuzhongtaizhang.presenter;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.classic.ClassicPresenter;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.CommPresenterHelper;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZListBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract.YZTZListContract;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.model.YZTZListModelImpl;

import java.util.List;


/**
 * Created by louisgeek on 2017/03/13
 */

public class YZTZListPresenterImpl extends ClassicPresenter<YZTZListContract.View, YZTZListContract.Model>
        implements YZTZListContract.Presenter {


    public YZTZListPresenterImpl(YZTZListContract.View view) {
        super(view, new YZTZListModelImpl());
    }


    @Override
    public void gainCountData(int pageCount) {
        if (CommPresenterHelper.judgeCanNotContinue(mModel)) {
            return;
        }
        mView.showProgress();

        mModel.loadData(UrlDatas.SECONDARY_LIST,mView.setupGainDataPrimaryId(), ClassicRVHeaderFooterAdapter.PAGE_NUM_DEFAULT, pageCount, "", new BasicCallBack<List<YZTZListBean.ListBean>>() {
            @Override
            public void onSuccess(List<YZTZListBean.ListBean> data) {
                if (mView != null) {
                    mView.hideProgress();
                    mView.setupData(data);
                }
            }

            @Override
            public void onError(String s) {
                CommPresenterHelper.doErrorThing(mView,s);
            }
        });
    }

    @Override
    public void gainMoreData(int pageNum) {
        if (CommPresenterHelper.judgeCanNotContinue(mModel)) {
            return;
        }
        mModel.loadData(UrlDatas.SECONDARY_LIST,mView.setupGainDataPrimaryId(), pageNum, ClassicRVHeaderFooterAdapter.PAGE_SIZE_DEFAULT, "", new BasicCallBack<List<YZTZListBean.ListBean>>() {
            @Override
            public void onSuccess(final List<YZTZListBean.ListBean> data) {
                mView.setupMoreData(data);
            }

            @Override
            public void onError(String msg) {
                mView.showMessage(msg);
            }
        });
    }

    @Override
    public void gainCountData(int pageCount, String keyword) {
        if (CommPresenterHelper.judgeCanNotContinue(mModel)) {
            return;
        }
        mView.showProgress();
        mModel.loadData(UrlDatas.SECONDARY_LIST,mView.setupGainDataPrimaryId(), ClassicRVHeaderFooterAdapter.PAGE_NUM_DEFAULT, pageCount, keyword, new BasicCallBack<List<YZTZListBean.ListBean>>() {
            @Override
            public void onSuccess(List<YZTZListBean.ListBean> data) {
                if (mView != null) {
                    mView.hideProgress();
                    mView.setupData(data);
                }
            }

            @Override
            public void onError(String s) {
                CommPresenterHelper.doErrorThing(mView,s);
            }
        });
    }

    @Override
    public void gainMoreData(int pageNum, String keyword) {
        if (CommPresenterHelper.judgeCanNotContinue(mModel)) {
            return;
        }
        mModel.loadData(UrlDatas.SECONDARY_LIST,mView.setupGainDataPrimaryId(), pageNum, ClassicRVHeaderFooterAdapter.PAGE_SIZE_DEFAULT, keyword, new BasicCallBack<List<YZTZListBean.ListBean>>() {
            @Override
            public void onSuccess(final List<YZTZListBean.ListBean> data) {
                mView.setupMoreData(data);
            }

            @Override
            public void onError(String msg) {
                mView.showMessage(msg);
            }
        });
    }
}