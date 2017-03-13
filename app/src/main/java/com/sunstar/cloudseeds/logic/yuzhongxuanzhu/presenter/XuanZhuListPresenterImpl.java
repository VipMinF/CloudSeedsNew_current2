package com.sunstar.cloudseeds.logic.yuzhongxuanzhu.presenter;

import android.os.Handler;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.classic.ClassicPresenter;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.yuzhongxuanzhu.bean.XuanZhuBean;
import com.sunstar.cloudseeds.logic.yuzhongxuanzhu.contract.XuanZhuListContract;
import com.sunstar.cloudseeds.logic.yuzhongxuanzhu.model.XuanZhuListModelImpl;

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
        mView.showProgress();
        mModel.loadData(UrlDatas.YU_ZHONG_XUAN_ZHU_LIST, ClassicRVHeaderFooterAdapter.PAGE_NUM_DEFAULT, pageCount, "", new BasicCallBack<List<XuanZhuBean.ListBean>>() {
            @Override
            public void onSuccess(List<XuanZhuBean.ListBean> data) {
                mView.hideProgress();
                mView.setupData(data);
            }

            @Override
            public void onError(String s) {
                mView.hideProgress();
                mView.showMessage(s);
            }
        });
    }

    @Override
    public void gainMoreData(int pageNum) {
        mModel.loadData(UrlDatas.YU_ZHONG_XUAN_ZHU_LIST, pageNum, ClassicRVHeaderFooterAdapter.PAGE_SIZE_DEFAULT, "", new BasicCallBack<List<XuanZhuBean.ListBean>>() {
            @Override
            public void onSuccess(final List<XuanZhuBean.ListBean> data) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mView.setupMoreData(data);
                    }
                }, 2 * 1000);
            }

            @Override
            public void onError(String msg) {
                mView.showMessage(msg);
            }
        });
    }
}
