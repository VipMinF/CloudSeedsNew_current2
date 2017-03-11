package com.classichu.classichu.basic.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by louisgeek on 2016/12/8.
 */
@Deprecated
public class UrlTool {
    public static String encodeUrl(String str) {
        try {
            str = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
    @Deprecated
    public static String appendParam(String url, Map<String, String> queryStrMap) {
        return  appendParam(url,mapToQueryStr(queryStrMap));
    }
    @Deprecated //待完善
    public static String appendParam(String url, String queryStr) {
        if (url.contains("?")) {
            if (url.contains("&")) {
                if (url.endsWith("&")) {
                    url = url + queryStr;
                } else {
                    url = url + "&" + queryStr;
                }
            } else {
                if (url.endsWith("?")) {
                    url = url + queryStr;
                } else {
                    url = url + "&" + queryStr;
                }
            }
        } else {
            url = url + "?" + queryStr;
        }
        return url;
    }

    public static String mapToQueryStr(Map<String, String> map) {
        StringBuilder string = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            string.append(entry.getKey());
            string.append("=");
            string.append(entry.getValue());
            string.append("&");
        }
        return string.toString();
    }
}
