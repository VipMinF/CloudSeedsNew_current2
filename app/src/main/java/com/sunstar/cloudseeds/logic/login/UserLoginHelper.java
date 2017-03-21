package com.sunstar.cloudseeds.logic.login;

import android.content.Context;
import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.extend.ACache;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.data.EnCodes;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;
import com.sunstar.cloudseeds.logic.login.model.LoginModelImpl;

import static com.sunstar.cloudseeds.data.EnCodes.decodeHex;
import static com.sunstar.cloudseeds.data.EnCodes.digest;
import static com.sunstar.cloudseeds.data.EnCodes.unescapeHtml;


/**
 * Created by Administrator on 2017/3/15.
 */

public class UserLoginHelper {

    private static final  String cachename="LoginCache";
    private static final  String cacheKey="userloginbean";
    public static final int SALT_SIZE = 8;
    public static final int HASH_INTERATIONS = 1024;
    private static final String SHA1 = "SHA-1";


    public static void saveUserLoginBean_ToAcahe(Context context, UserLoginBean userloginbean){
        ACache macache = ACache.get(context,cachename);
        macache.put(cacheKey,userloginbean);
    }

    public static Object getUserLoginBean_FromAcache(Context context){
        ACache macache = ACache.get(context,cachename);
        return macache.getAsObject(cacheKey);
    }

    public static Boolean autoLogin_Onlocal(Context context) {

        UserLoginBean userloginbean = (UserLoginBean)getUserLoginBean_FromAcache(context);
        if(userloginbean!= null && userloginbean.getUserid().length() > 0) {
            return true;
        }
        return false;
    }

    public static void autoLogin_Online(final Context context,final BasicCallBack<UserLoginBean> loginCallBack) {

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
     * 生成加密的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptionPassword(String plainPassword) {
        String plain = unescapeHtml(plainPassword);
        byte[] salt = EnCodes.generateSalt(SALT_SIZE);
        byte[] hashPassword = EnCodes.digest(plain.getBytes(), SHA1,salt, HASH_INTERATIONS);
        return  EnCodes.encodeHex(salt)+ EnCodes.encodeHex(hashPassword);
    }


    /**
     * 验证密码
     */
    public static Boolean validatePassWord(String psw,String entryptedPsw){

        String pain= unescapeHtml(psw);
        byte[] salt =decodeHex(entryptedPsw.substring(0,16));
        byte[] hashPassword =digest(pain.getBytes(), SHA1,salt, HASH_INTERATIONS);
        String psssword =  EnCodes.encodeHex(salt)  +  EnCodes.encodeHex(hashPassword);
        return entryptedPsw.equals(psssword);
    }


}
