package com.sunstar.cloudseeds.logic.xuanzhu.presenter;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.classic.ClassicPresenter;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.CommPresenterHelper;
import com.sunstar.cloudseeds.logic.xuanzhu.bean.XuanZhuListBean;
import com.sunstar.cloudseeds.logic.xuanzhu.contract.XuanZhuListContract;
import com.sunstar.cloudseeds.logic.xuanzhu.model.XuanZhuListModelImpl;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/13.
 */

public class XuanZhuListPresenterImpl extends ClassicPresenter<XuanZhuListContract.View, XuanZhuListContract.Model>
        implements XuanZhuListContract.Presenter {

    public XuanZhuListPresenterImpl(XuanZhuListContract.View view) {
        super(view, new XuanZhuListModelImpl());
    }

    @Override
    public void gainCountData(int pageCount) {
        if (CommPresenterHelper.judgeCanNotContinue(mModel)) {
            return;
        }
        mView.showProgress();
        mModel.loadData(UrlDatas.TERTIARY_LIST, ClassicRVHeaderFooterAdapter.PAGE_NUM_DEFAULT, pageCount, "", new BasicCallBack<List<XuanZhuListBean.ListBean>>() {
            @Override
            public void onSuccess(List<XuanZhuListBean.ListBean> data) {
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
        mModel.loadData(UrlDatas.TERTIARY_LIST, pageNum, ClassicRVHeaderFooterAdapter.PAGE_SIZE_DEFAULT, "", new BasicCallBack<List<XuanZhuListBean.ListBean>>() {
            @Override
            public void onSuccess(final List<XuanZhuListBean.ListBean> data) {
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
        mModel.loadData(UrlDatas.TERTIARY_LIST,
                ClassicRVHeaderFooterAdapter.PAGE_NUM_DEFAULT, pageCount, keyword, new BasicCallBack<List<XuanZhuListBean.ListBean>>() {
                    @Override
                    public void onSuccess(List<XuanZhuListBean.ListBean> data) {
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
        mModel.loadData(UrlDatas.TERTIARY_LIST, pageNum,
                ClassicRVHeaderFooterAdapter.PAGE_SIZE_DEFAULT, keyword, new BasicCallBack<List<XuanZhuListBean.ListBean>>() {
                    @Override
                    public void onSuccess(final List<XuanZhuListBean.ListBean> data) {
                        mView.setupMoreData(data);
                    }

                    @Override
                    public void onError(String msg) {
                        mView.showMessage(msg);
                    }
                });
    }
}
