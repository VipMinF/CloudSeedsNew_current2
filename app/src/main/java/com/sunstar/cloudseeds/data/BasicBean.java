package com.sunstar.cloudseeds.data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by louisgeek on 2017/3/10.
 */

public class BasicBean<T> {
    /**
     * code : 1
     * message : 请求成功
     * info : [{"show_code":"1","show_msg":"列表获取成功","all_count":"3","all_pages":"1","now_pagenum":"1","now_pagesize":"10","list":[{"name":"dsadasd","primary_id":"1212312","prot_ype":"品种分类","plan_type":"计划类型","time":"2017-3-2 10:02:51","status":"2"},{"name":"dsadasd","primary_id":"1212312","prot_ype":"品种分类","plan_type":"计划类型","time":"2017-3-2 10:02:51","status":"2"},{"name":"dsadasd","primary_id":"1212312","pro_type":"品种分类","plan_type":"计划类型","time":"2017-3-2 10:02:51","status":"2"}]}]
     */

    private String code;
    private String message;
    private List<T> info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getInfo() {
        return info;
    }

    public void setInfo(List<T> info) {
        this.info = info;
    }


    /**
     *================================================
     */

    /**
     * 用法
     * TypeToken<BaseBean<OthersBean>> typeToken=new TypeToken<BaseBean<OthersBean>>(){};
     * BaseBean<OthersBean> baseBean=BaseBean.fromJsonOne(body,typeToken);
     * @param json
     * @param token
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json,TypeToken<T> token) {
        if (json == null || json.trim().length() <= 0) {
            Log.d("BaseBeanXxx", "fromJson: json is error");
            return null;
        }
        Gson gson = new Gson();
        Type objectType = token.getType();
        return gson.fromJson(json, objectType);
    }

    /**
     * 用法
     * BaseBean.fromJson(result,OthersBean.class);
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json,Class clazz) {
        //
        Gson gson = new Gson();
        Type objectType = dealParameterizedTypeInner(BasicBean.class,clazz);
        return gson.fromJson(json, objectType);
    }
    private static ParameterizedType dealParameterizedTypeInner(final Class raw, final Type... args) {
        ParameterizedType parameterizedType= new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
        return parameterizedType;
    }

}
