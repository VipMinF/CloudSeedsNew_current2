package com.sunstar.cloudseeds.data;

/**
 * Created by louisgeek on 2017/3/10.
 */

public class UrlDatas {
    private   static  final String
                             BASE_URL="http://192.168.1.67:8085/";
    //   BASE_URL="http://192.168.1.80:8080/yunzhong/";

    // // BASE_URL="https://115.236.5.187:8080/yunzhong/";
  /* private   static  final String
            BASE_URL_Local="http://192.168.1.67:8086/";*/


    /**
     * 首页分类相关
     */
    public  static  final String
            MAIN_DATE_CLASSIFY=String.format("%s%s",BASE_URL,"api/classify/date_classify");
    public  static  final String
            MAIN_PLAN_CLASSIFY=String.format("%s%s",BASE_URL,"api/classify/plan_classify");
    public  static  final String
            MAIN_PRODUCT_CLASSIFY=String.format("%s%s",BASE_URL,"api/classify/product_classify");

    /**
     * 列表相关
     */
    public  static  final String
            PRIMARY_LIST=String.format("%s%s",BASE_URL,"api/project/primary/list");

    //
    public  static  final String
            SECONDARY_LIST=String.format("%s%s",BASE_URL,"api/project/secondary/list");

    public  static  final String
            SECONDARY_DETAIL=String.format("%s%s",BASE_URL,"api/project/secondary/detail");

    public  static  final String
            SECONDARY_EDIT=String.format("%s%s",BASE_URL,"api/project/secondary/edit");
    public  static  final String
            SECONDARY_SAVE=String.format("%s%s",BASE_URL,"api/project/secondary/save");

    public  static  final String
    SECONDARY_ADD_SELECT_BEADS=String.format("%s%s",BASE_URL,"api/project/secondary/addselectbeads");
    //
    public  static  final String
            TERTIARY_LIST=String.format("%s%s",BASE_URL,"api/project/tertiary/list");

    public  static  final String
            TERTIARY_DETAIL=String.format("%s%s",BASE_URL,"api/project/tertiary/detail");

    public  static  final String
            TERTIARY_EDIT=String.format("%s%s",BASE_URL,"api/project/tertiary/edit");
    /*public  static  final String
            TERTIARY_EDIT=String.format("%s%s",BASE_URL_OLD,"api/project/tertiary/edit.json");*/

    public  static  final String
            TERTIARY_SAVE=String.format("%s%s",BASE_URL,"api/project/tertiary/save");

    /**
     * 用户相关
     */
    public  static  final String
            Login_URL=String.format("%s%s",BASE_URL,"api/user/login");

    public  static  final String
            ChangePassWord_URL=String.format("%s%s",BASE_URL,"api/user/changepass");

    public  static  final String
            CheckOrBindQrCode_URL=String.format("%s%s",BASE_URL,"api/qrcode/query");

    /**
     * 图片上传
     */
    public  static  final String
            URL_UPLOAD_IMAGES=String.format("%s%s",BASE_URL,"api/image/noweatheraddimages");

    /**
     * 获取选株要创建的id
     */
    public  static  final String
            URL_GET_SELECT_BEADS_MAX_NUM=String.format("%s%s",BASE_URL,"api/project/secondary/getselectbeads");

    public  static  final String
            URL_DELETE_IMAGE=String.format("%s%s",BASE_URL,"api/project/tertiary/deleteimage");



}
