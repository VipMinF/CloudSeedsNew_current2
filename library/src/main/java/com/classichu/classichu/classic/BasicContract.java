package com.classichu.classichu.classic;

/**
 * Created by louisgeek on 2016/12/4.
 */

public interface BasicContract {

     interface View<D> extends BasicView<D> {
     }

     interface Presenter extends BasicPresenter{
    }
     interface Model<D> extends BasicModel<D> {

    }


}