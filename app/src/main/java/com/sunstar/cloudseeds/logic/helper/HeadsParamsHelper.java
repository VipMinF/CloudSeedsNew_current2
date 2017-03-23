package com.sunstar.cloudseeds.logic.helper;

import com.classichu.classichu.basic.tool.DateTool;
import com.classichu.classichu.basic.tool.MD5Tool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by louisgeek on 2017/3/22.
 */

public class HeadsParamsHelper {
    // private SparseArray<>

    public static final String UserId = "A6971118873561";
    public static final String UserPassword = UserId + "UZ" + "8C757B31-A896-F477-C46D-4E27E05528D3" + "UZ";

    public static Map<String, String> setupDefaultHeaders() {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = DateTool.getChinaDateTime();

        Map<String, String> map = new HashMap<>();
        map.put("UserId", UserId);
        map.put("UserPassword", MD5Tool.encode(UserPassword + time));
        map.put("CurrentTime", time);
        return map;
    }


}
