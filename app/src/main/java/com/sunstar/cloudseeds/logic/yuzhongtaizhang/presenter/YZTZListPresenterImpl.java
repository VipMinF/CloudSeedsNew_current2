package com.sunstar.cloudseeds.logic.yuzhongtaizhang.presenter;

import android.os.Handler;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.classic.ClassicPresenter;
import com.sunstar.cloudseeds.data.UrlDatas;
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
            mView.showProgress();
            mModel.loadData(UrlDatas.YU_ZHONG_TAI_ZHANG_LIST,ClassicRVHeaderFooterAdapter.PAGE_NUM_DEFAULT,pageCount, "", new BasicCallBack<List<YZTZListBean.ListBean>>() {
                @Override
                public void onSuccess(List<YZTZListBean.ListBean> data) {
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
        mModel.loadData(UrlDatas.YU_ZHONG_TAI_ZHANG_LIST, pageNum, ClassicRVHeaderFooterAdapter.PAGE_SIZE_DEFAULT, "", new BasicCallBack<List<YZTZListBean.ListBean>>() {
            @Override
            public void onSuccess(final List<YZTZListBean.ListBean> data) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mView.setupMoreData(data);
                    }
                },2*1000);
            }

            @Override
            public void onError(String msg) {
                mView.showMessage(msg);
            }
        });
    }
}