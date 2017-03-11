package com.classichu.classichu.classic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by louisgeek on 2017/2/22.
 */

public abstract class ClassicMvpFragment<P extends ClassicPresenter>  extends ClassicFragment{
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = setupPresenter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.recycleViewAndModel();
    }

    protected abstract P setupPresenter();
}
