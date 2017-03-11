package com.classichu.classichu.app;

import android.util.Log;


/**
 * Created by louisgeek on 2017/2/19.
 * 经典Log
 */

public class CLog {
    private static final String mTAG = "CLog";
    private static final boolean mEnable = true;

    public static void v(String msg) {
        if (mEnable) {
            Log.v(mTAG, msg);
        }
    }

    public static void d(String msg) {
        if (mEnable) {
            Log.d(mTAG, msg);
        }
    }

    public static void i(String msg) {
        if (mEnable) {
            Log.i(mTAG, msg);
        }
    }

    public static void w(String msg) {
        if (mEnable) {
            Log.w(mTAG, msg);
        }
    }
    public static void e(String msg) {
        if (mEnable) {
            Log.e(mTAG, msg);
        }
    }
    /**
     * "hamburger".substring(4, 8) returns "urge"
     * "smiles".substring(1, 5) returns "mile"
     *
     * @param msg
     */
    public static void longLog(String msg) {
        if (!mEnable) {
            return;
        }
        int logMaxLength = 4000;
        if (msg.length() > logMaxLength) {
            int outSize = msg.length() % logMaxLength == 0 ? msg.length() / logMaxLength : msg.length() / logMaxLength + 1;
            for (int i = 0; i < outSize; i++) {
                if (i == outSize - 1) {//最后一行
                    Log.i(mTAG, msg.substring(i * logMaxLength));
                } else {
                    Log.i(mTAG, msg.substring(i * logMaxLength, (i+1) * logMaxLength));
                }
            }
        } else {
            Log.i(mTAG, msg);
        }
    }

}
