package com.sunstar.cloudseeds.data;

/**
 * Created by louisgeek on 2017/3/10.
 */

public class UrlDatas {


    private   static  final String
            BASE_URL="https://git.oschina.net/louisgeek/Demo/raw/master/";
    public  static  final String
            TAI_ZHANG_LIST=String.format("%s%s",BASE_URL,"api/project/primary/list.json");

    public  static  final String
            YU_ZHONG_TAI_ZHANG_LIST=String.format("%s%s",BASE_URL,"api/project/secondary/list.json");

    public  static  final String
            YU_ZHONG_TAI_ZHANG_DETAIL=String.format("%s%s",BASE_URL,"api/project/secondary/detail.json");

    public  static  final String
            YU_ZHONG_XUAN_ZHU_LIST=String.format("%s%s",BASE_URL,"api/project/tertiary/list.json");

    public  static  final String
            YU_ZHONG_XUAN_ZHU_DETAIL=String.format("%s%s",BASE_URL,"api/project/tertiary/detail.json");

    public  static  final String
            Login_URL=String.format("%s%s",BASE_URL,"api/user/login.json");
}
