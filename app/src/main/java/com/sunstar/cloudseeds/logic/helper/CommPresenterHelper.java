package com.sunstar.cloudseeds.logic.helper;

import com.classichu.classichu.basic.BasicContract;
import com.classichu.classichu.basic.tool.NetWorkTool;
import com.classichu.classichu.basic.tool.ToastTool;

/**
 * Created by louisgeek on 2017/3/23.
 */

public class CommPresenterHelper {
    public static boolean judgeCanNotContinue(BasicContract.Model mModel){
        boolean canNotContinue=false;
        if (!NetWorkTool.isNetWorkConnected()) {
            ToastTool.showShort("");
            canNotContinue= true;
        }
        if (mModel == null) {
            canNotContinue= true;
        }
        return canNotContinue;
    }
}
