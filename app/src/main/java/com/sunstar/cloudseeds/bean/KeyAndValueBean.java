package com.sunstar.cloudseeds.data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by louisgeek on 2017/3/16.
 */

public class KeyAndValueBean {
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String key;
    private String value;




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
