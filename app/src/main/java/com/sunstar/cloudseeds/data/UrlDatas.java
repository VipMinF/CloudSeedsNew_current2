package com.sunstar.cloudseeds.data;

/**
 * Created by louisgeek on 2017/3/10.
 */

public class UrlDatas {
    private   static  final String
            BASE_URL="http://192.168.1.80:8080/yunzhong/";
    private   static  final String
            BASE_URL_ONLINE="http://192.168.1.67:8085/";
    public  static  final String
            MAIN_DATE_CLASSIFY=String.format("%s%s",BASE_URL,"api/classify/date_classify");
    public  static  final String
            MAIN_PLAN_CLASSIFY=String.format("%s%s",BASE_URL,"api/classify/plan_classify");
    public  static  final String
            MAIN_PRODUCT_CLASSIFY=String.format("%s%s",BASE_URL,"api/classify/product_classify");

    public  static  final String
            TAI_ZHANG_LIST=String.format("%s%s",BASE_URL,"api/project/primary/list");

    public  static  final String
            YU_ZHONG_TAI_ZHANG_LIST=String.format("%s%s",BASE_URL,"api/project/secondary/list");

    public  static  final String
            YU_ZHONG_TAI_ZHANG_DETAIL=String.format("%s%s",BASE_URL,"api/project/secondary/detail");

    public  static  final String
            YU_ZHONG_XUAN_ZHU_LIST=String.format("%s%s",BASE_URL,"api/project/tertiary/list");

    public  static  final String
            YU_ZHONG_XUAN_ZHU_DETAIL=String.format("%s%s",BASE_URL,"api/project/tertiary/detail");

    public  static  final String
            SPQ_ADD_ITEM_RULE=String.format("%s%s",BASE_URL,"api/rule/spq_add_item");

   /* public  static  final String
            ZQ_ADD_ITEM_RULE=String.format("%s%s",BASE_URL,"api/rule/zq_add_item.json");*/
    public  static  final String
            ZQ_ADD_ITEM_RULE=String.format("%s%s",BASE_URL_ONLINE,"api/rule/zq_add_item");


    public  static  final String
            Login_URL=String.format("%s%s",BASE_URL,"api/user/login");

    public  static  final String
            ChangePassWord_URL=String.format("%s%s",BASE_URL,"api/user/changepass");

    public  static  final String
            CheckOrBindQrCode_URL=String.format("%s%s",BASE_URL,"api/qrcode/query");


}
