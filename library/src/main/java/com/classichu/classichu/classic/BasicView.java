package com.classichu.classichu.classic;

/**
 * Created by louisgeek on 2016/12/4.
 */

public interface BasicView<D> {
    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void setupData(D data);

    void setupMoreData(D data);
}