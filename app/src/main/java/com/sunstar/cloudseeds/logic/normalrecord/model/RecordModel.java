package com.sunstar.cloudseeds.logic.normalrecord.model;

import android.util.Log;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.logic.normalrecord.Bean.DateTimeBean;
import com.sunstar.cloudseeds.logic.normalrecord.Bean.FarmTypeListBean;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.google.gson.Gson;

import retrofit2.http.Url;
/**
 * Created by xiaoxian on 2017/4/24.
 */

/**http://192.168.1.80:8080/yunzhong/api/dailyrecord/farm_type
        农事类型数据接口  参数userid
        http://192.168.1.80:8080/yunzhong/api/dailyrecord/farmlist
        日常事件数据列表接口  参数userid  primary_id pageNo pageSize
        http://192.168.1.80:8080/yunzhong/api/dailyrecord/save
        日常事件数据报错接口  参数userid  primary_id type detail time
 */

public class RecordModel {

    static List<FarmTypeListBean.ListBean> listbean;
  public ArrayList<String> farmTypeList; //农事类型
  public String farmType;//选择的农事类型
  public List imageDataSource;//图片数据
  public ArrayList<String> dateTimeList;//记录时间
  public String dateTime;//选择的时间

//  public void loadData(String url, final BasicCallBack<FarmTypeListBean> basicCallBack) {
//    HashMap<String,String> paramsMap=new HashMap<>();
////    paramsMap.put("userid", UserLoginHelper.getUserid());
//    //paramsMap.put("id",id);
//    paramsMap.put("id","0005d477b31643e19916f98b37514afe");
//
//    HttpRequestManagerFactory.getRequestManager().postUrlBackStr(url, HeadsParamsHelper.setupDefaultHeaders(),paramsMap,
//            new GsonHttpRequestCallback<BasicBean<FarmTypeListBean>>() {
//              @Override//动态赋值
//              public BasicBean<FarmTypeListBean> OnSuccess(String result) {
//                  Log.v("农事记录",result);
//                  Gson gson = new Gson();
//                     FarmTypeListBean listBean = gson.fromJson(result,FarmTypeListBean.class);
//                  Log.v("FarmTypeListBean",listBean.getInfo()+"");
//                  BasicBean<FarmTypeListBean> bb= BasicBean.fromJson(result,FarmTypeListBean.class);
//                  return bb;
//             }
//
//              @Override
//              public void OnSuccessOnUI(BasicBean<FarmTypeListBean> basicBean) {
//
//                if (basicBean == null) {
//                  basicCallBack.onError(CommDatas.SERVER_ERROR);
//                  return;
//                }
//                if (CommDatas.SUCCESS_FLAG.equals(basicBean.getCode())) {
//                  if (basicBean.getInfo() != null && basicBean.getInfo().size() > 0) {
//                   // basicCallBack.onSuccess(basicBean.getInfo().get(0));
//
//
//                  } else {
//                    basicCallBack.onError(basicBean.getMessage());
//                  }
//                } else {
//                  basicCallBack.onError(basicBean.getMessage());
//                }
//1
//              }
//
//              @Override
//              public void OnError(String errorMsg) {
//                basicCallBack.onError(errorMsg);
//              }
//            });
//  }

public RecordModel() {
    super();
    farmTypeList = new ArrayList<String>();
    dateTimeList = new ArrayList<String>();

}

 public void loadFarmTypeList(final BasicCallBack<FarmTypeListBean> basicCallBack) {
     HashMap<String,String> paramsMap=new HashMap<>();
     paramsMap.put("userid",UserLoginHelper.getUserid());
     HttpRequestManagerFactory.getRequestManager().postUrlBackStr(UrlDatas.URL_GET_FARMTYPE, HeadsParamsHelper.setupDefaultHeaders(),paramsMap,
             new GsonHttpRequestCallback<BasicBean<FarmTypeListBean>>() {
                 @Override//动态赋值
                 public BasicBean<FarmTypeListBean> OnSuccess(String result) {
                     Log.v("农事记录",result);
                     Gson gson = new Gson();
                     FarmTypeListBean farmTypeListBean = gson.fromJson(result,FarmTypeListBean.class);
                     List< FarmTypeListBean.ListBean > list = farmTypeListBean.getInfo();
                     listbean = list;
                     for (FarmTypeListBean.ListBean listben:list) {
                         String name = listben.getName();
                         farmTypeList.add(name);
                     }
                     farmTypeList.add("请选择农事类型");
                     Log.v("farmtypeList",farmTypeList+"");
                     BasicBean<FarmTypeListBean> bb= BasicBean.fromJson(result,FarmTypeListBean.class);
                     return bb;
                 }

                 @Override
                 public void OnSuccessOnUI(BasicBean<FarmTypeListBean> basicBean) {
                    basicCallBack.onSuccess(null);

                 }

                 @Override
                 public void OnError(String errorMsg) {

                 }
             });
 }



 public void loadDateTimeList(String primary_id,final BasicCallBack<DateTimeBean> basicCallBack)  {
     HashMap<String,String> paramsMap=new HashMap<>();
     paramsMap.put("userid",UserLoginHelper.getUserid());
     paramsMap.put("primary_id",primary_id);
    // paramsMap.put("pageNo","1");
     //paramsMap.put("pageSize","5");
     HttpRequestManagerFactory.getRequestManager().postUrlBackStr(UrlDatas.URL_GET_FARMLIST, HeadsParamsHelper.setupDefaultHeaders(),paramsMap,
             new GsonHttpRequestCallback<BasicBean<DateTimeBean>>() {
                 @Override//动态赋值
                 public BasicBean<DateTimeBean> OnSuccess(String result) {
                     Gson gson = new Gson();
                     //DateTimeBean dateTimeBean = gson.fromJson(result,DateTimeBean.class);
                     Log.v("日常展示",result);
                     BasicBean<DateTimeBean> bb= BasicBean.fromJson(result,DateTimeBean.class);
                     return bb;
                 }

                 @Override
                 public void OnSuccessOnUI(BasicBean<DateTimeBean> basicBean) {
                     basicCallBack.onSuccess(null);

                 }

                 @Override
                 public void OnError(String errorMsg) {

                 }
             });

 }

    public void uploadNormalRecord(String primary_id,String type,String detail,String time,final BasicCallBack<FarmTypeListBean> basicCallBack ,String image) {
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("userid",UserLoginHelper.getUserid());
        paramsMap.put("primary_id",primary_id);
        paramsMap.put("detail",detail);
        paramsMap.put("time",time);
        paramsMap.put("image",image);
        String code = "";
        for (FarmTypeListBean.ListBean bean:listbean
             ) {
            String name = bean.getName();
            if(name.equals(type))
                code = bean.getCode();
        }
        paramsMap.put("type",code);
        Log.v("normalrecordcode",code);
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(UrlDatas.NormalRecord_SAVE, HeadsParamsHelper.setupDefaultHeaders(),paramsMap,
                new GsonHttpRequestCallback<BasicBean<DateTimeBean>>() {
                    @Override//动态赋值
                    public BasicBean<DateTimeBean> OnSuccess(String result) {
                        Log.v("normalrecordjson",result);
                        return null;
                    }

                    @Override
                    public void OnSuccessOnUI(BasicBean<DateTimeBean> basicBean) {
                        basicCallBack.onSuccess(null);

                    }

                    @Override
                    public void OnError(String errorMsg) {
                        basicCallBack.onError(errorMsg);
                    Log.v("errorMsg",errorMsg);
                    }
                });
    }

  public ArrayList<String>getFarmTypeList() {
      return  farmTypeList;
  }

  public ArrayList<String>getDateTimeList() {
      return  dateTimeList;
  }

}






