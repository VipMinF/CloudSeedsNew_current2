package com.classichu.classichu.classic;

import android.os.Bundle;

/**
 * Created by louisgeek on 2017/2/22.
 */

public abstract class ClassicMvpActivity<P extends ClassicPresenter>  extends ClassicActivity{
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        mPresenter = setupPresenter();
    }

    protected abstract P setupPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.recycleViewAndModel();
    }
}
