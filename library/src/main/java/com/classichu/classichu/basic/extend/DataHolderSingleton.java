package com.classichu.classichu.basic.extend;


import com.classichu.classichu.app.CLog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by louisgeek on 2016/12/6.
 * 劲量少用
 * 使用时候注意内存缓存
 */

public class DataHolderSingleton {

    private Map<String, Object> mDataMap;
    /**
     * ====================================================
     */
    private static volatile DataHolderSingleton mInstance;

    /* 私有构造方法，防止被实例化 */
    private DataHolderSingleton() {
        mDataMap = new HashMap<>();
    }

    public static DataHolderSingleton getInstance() {
        if (mInstance == null) {
            synchronized (DataHolderSingleton.class) {
                if (mInstance == null) {
                    mInstance = new DataHolderSingleton();
                }
            }
        }
        return mInstance;
    }

    /**
     * ==========================================
     */


    public void putData(String key, Object value) {
        mDataMap.put(key, value);
    }

    public Object getData(String key) {
        return mDataMap.get(key);
    }

    public void showAllData() {
        for (String key : mDataMap.keySet()) {
            CLog.d(key);
            CLog.d(mDataMap.get(key).toString());
        }
    }


}
