package com.sunstar.cloudseeds.logic.login;

import android.content.Context;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.extend.ACache;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;
import com.sunstar.cloudseeds.logic.login.model.LoginModelImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;


/**
 * Created by Administrator on 2017/3/15.
 */

public class UserLoginHelper {

    private static final  String cachename="LoginCache";
    private static final  String cacheKey="userloginbean";

    public static void saveUserLoginBean_ToAcahe(Context context, UserLoginBean userloginbean){
        ACache macache = ACache.get(context,cachename);
        macache.put(cacheKey,userloginbean);
    }

    public static Object getUserLoginBean_FromAcache(Context context){
        ACache macache = ACache.get(context,cachename);
        return macache.getAsObject(cacheKey);
    }

    public static Boolean autoLogin(Context context) {

        UserLoginBean userloginbean = (UserLoginBean)getUserLoginBean_FromAcache(context);
        if(userloginbean!= null && userloginbean.getUserid().length() > 0) {
            return true;
        }
        return false;
    }

    public static void autoLoginApp(final Context context,final BasicCallBack<UserLoginBean> loginCallBack) {

        UserLoginBean userloginbean = (UserLoginBean)getUserLoginBean_FromAcache(context);
        if(userloginbean!= null && userloginbean.getUserid().length() > 0) {

            LoginModelImpl loginmodelimpl= new LoginModelImpl();
            loginmodelimpl.loadData(UrlDatas.Login_URL ,userloginbean.getUsername(),userloginbean.getPassword(),new BasicCallBack<UserLoginBean>(){
                @Override
                public void onSuccess(UserLoginBean userloginBean) {

                    loginCallBack.onSuccess(userloginBean);
                    UserLoginHelper.saveUserLoginBean_ToAcahe(context,userloginBean);
                }
                @Override
                public void onError(String s) {
                    loginCallBack.onError(s);
                }
            });

        }else {
            loginCallBack.onError("登录失败");
        }
    }

    public static Boolean loginOut(Context context){

        ACache macache = ACache.get(context,cachename);
        return  macache.remove(cacheKey);
    }


    public static  UserLoginBean userLoginBean(Context context){
        return  (UserLoginBean) getUserLoginBean_FromAcache(context);
    }





    /**
     * 删除本地信息
     */
    public static void loginOut(File file) {

        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 序列化存储文件
     * @param object
     * @param file
     */
    public static void writeObject(Object object,File file){
        ObjectOutputStream oos;
        boolean bb = file.exists();
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(object);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化
     * @param file
     */
    public static Object readObject(File file){
        ObjectInputStream ois = null;
        Object object = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            object = ois.readObject();
            ois.close();
        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

}
