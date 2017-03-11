package com.classichu.classichu.basic.helper;

import android.hardware.Camera;

/**
 * Created by louisgeek on 2017/2/21.
 */

public class CameraHelper {
    /**
     * 6.0以下系统
     * 返回true 表示可以使用  返回false表示不可以使用
     */
    public static boolean cameraCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            e.printStackTrace();
            isCanUse = false;
        }

        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }
}
