package com.sunstar.cloudseeds.logic.helper;

import com.classichu.classichu.basic.BasicContract;
import com.classichu.classichu.basic.tool.NetWorkTool;
import com.classichu.classichu.basic.tool.ToastTool;
import com.sunstar.cloudseeds.data.CommDatas;

/**
 * Created by louisgeek on 2017/3/23.
 */

public class CommPresenterHelper {
    public static boolean judgeCanNotContinue(BasicContract.Model mModel){
        boolean canNotContinue=false;
        if (!NetWorkTool.isNetWorkConnected()) {
            ToastTool.showShort(CommDatas.NETWORK_ERROR);
            canNotContinue= true;
        }
        if (mModel == null) {
            canNotContinue= true;
        }
        return canNotContinue;
    }

    public static void doErrorThing(BasicContract.View mView,String errorStr){
        if (mView != null) {
            mView.hideProgress();
            mView.showMessage(errorStr);
        }
    }
}
