package com.sunstar.cloudseeds.logic.search;

import android.content.Context;

import com.classichu.classichu.basic.extend.ACache;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/20.
 */

public class SearchRecentHelper {

    private static final  String cacheKey="recentsearchlist";
    private static int max_listsize=20;

    public static void setRecentSearchkey(Context context,String key){

        ArrayList<String> list =(ArrayList)getRecentSearchList_FromAcache(context);
        if (list == null){
            list= new ArrayList<>();
        }
         if (!hasValue(key,list)){
             list.add(key);
         }
        while(list.size()>max_listsize){
            list.remove(0);
        }
        saveRecentSearchKey_ToAcahe(context,list);
    }

    public static void saveRecentSearchKey_ToAcahe(Context context, ArrayList<String> list){
        ACache macache = ACache.get(context);
        macache.put(cacheKey,list);
    }

    public static Object getRecentSearchList_FromAcache(Context context){
        ACache macache = ACache.get(context);
        return macache.getAsObject(cacheKey);
    }

    public static boolean hasValue(String key,ArrayList<String> list){
        for (String value:list) {
            if (key.equals(value)){
                return true;
            }
        }
        return false;
    }
}
