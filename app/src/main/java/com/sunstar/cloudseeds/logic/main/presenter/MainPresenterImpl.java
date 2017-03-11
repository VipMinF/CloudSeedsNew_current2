package com.sunstar.cloudseeds.logic.main.presenter;

import android.os.Handler;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.classichu.classic.BasicCallBack;
import com.classichu.classichu.classic.ClassicPresenter;
import com.sunstar.cloudseeds.data.UrlDatas;
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
            mView.showProgress();
            mModel.loadDataBase(UrlDatas.TAI_ZHANG_LIST, ClassicRVHeaderFooterAdapter.PAGE_NUM_DEFAULT, pageCount, "", new BasicCallBack<List<TaiZhangBean>>() {
                @Override
                public void onSuccess(List<TaiZhangBean> data) {
                        mView.hideProgress();
                        mView.setupData(data);
                }

                @Override
                public void onError(String msg) {
                       mView.hideProgress();
                       mView.showMessage(msg);
                }
            });
    }

    @Override
    public void gainMoreData(int pageNum) {
        mModel.loadDataBase(UrlDatas.TAI_ZHANG_LIST, pageNum, ClassicRVHeaderFooterAdapter.PAGE_SIZE_DEFAULT, "", new BasicCallBack<List<TaiZhangBean>>() {
            @Override
            public void onSuccess(final List<TaiZhangBean> data) {
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