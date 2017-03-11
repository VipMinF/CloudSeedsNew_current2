package com.classichu.classichu.classic;

/**
 * Created by louisgeek on 2016/12/4.
 */

public class ClassicPresenter <V extends BasicContract.View,M extends BasicContract.Model> {
    protected V mView;
    protected M mModel;

    public ClassicPresenter(V view, M model) {
        this.mView = view;
        this.mModel = model;
    }
/* public void setupViewAndModel(V view, M model) {
     this.mView = view;
     this.mModel= model;
 }*/
    public void recycleViewAndModel() {
        this.mView = null;
        this.mModel=null;
    }

}