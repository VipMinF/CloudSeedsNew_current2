package com.sunstar.cloudseeds.logic.shangpinqi.presenter;

import com.classichu.classichu.classic.ClassicPresenter;
import com.sunstar.cloudseeds.logic.shangpinqi.contract.SPQAddContract;
import com.sunstar.cloudseeds.logic.shangpinqi.model.SPQAddModelImpl;

/**
* Created by louisgeek on 2017/03/16
*/
@Deprecated
public class SPQAddPresenterImpl extends ClassicPresenter<SPQAddContract.View,SPQAddContract.Model> implements SPQAddContract.Presenter{

    public SPQAddPresenterImpl(SPQAddContract.View view) {
        super(view, new SPQAddModelImpl());
    }

    @Override
    public void gainData() {

    }
}