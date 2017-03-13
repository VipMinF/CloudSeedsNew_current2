package com.sunstar.cloudseeds.logic.yuzhongtaizhang.presenter;

import android.os.Handler;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.classic.ClassicPresenter;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract.YZTZContract;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.model.YZTZModelImpl;

import java.util.List;


/**
 * Created by louisgeek on 2017/03/13
 */

public class YZTZPresenterImpl extends ClassicPresenter<YZTZContract.View, YZTZContract.Model>
        implements YZTZContract.Presenter {


    public YZTZPresenterImpl(YZTZContract.View view) {
        super(view, new YZTZModelImpl());
    }

    @Override
    public void gainCountData(int pageCount) {
            mView.showProgress();
            mModel.loadData(UrlDatas.YU_ZHONG_TAI_ZHANG_LIST,ClassicRVHeaderFooterAdapter.PAGE_NUM_DEFAULT,pageCount, "", new BasicCallBack<List<YZTZBean.ListBean>>() {
                @Override
                public void onSuccess(List<YZTZBean.ListBean> data) {
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
        mModel.loadData(UrlDatas.YU_ZHONG_TAI_ZHANG_LIST, pageNum, ClassicRVHeaderFooterAdapter.PAGE_SIZE_DEFAULT, "", new BasicCallBack<List<YZTZBean.ListBean>>() {
            @Override
            public void onSuccess(final List<YZTZBean.ListBean> data) {
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