package com.sunstar.cloudseeds.service;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;


import com.classichu.classichu.basic.okhttp.GsonOkHttpCallback;
import com.classichu.classichu.basic.okhttp.OkHttpSingleton;
import com.classichu.classichu.basic.tool.DateTool;
import com.classichu.photoselector.imagespicker.ImagePickBean;
import com.google.gson.reflect.TypeToken;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.imageupload.bean.BaseListBean;
import com.sunstar.cloudseeds.logic.imageupload.bean.UploadImageBackBean;
import com.sunstar.cloudseeds.logic.imageupload.helper.ImageUploadHelper;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.OnWifiUpLoadImageBean;


import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import  com.sunstar.cloudseeds.cache.ACache;


import java.io.File;
import  java.util.concurrent.ExecutorService;
import java.util.ArrayList;
import java.util.concurrent.Executors;

/**
 *
 * 用来WiFi下自动上传图片
 * Created by xiaoxian on 2017/6/12.
 */

public class OnWifiUpLoadService extends Service {

    public class OnWifiUpLoadBinder extends Binder {
    public  OnWifiUpLoadService getService() {
            return  OnWifiUpLoadService.this;
        }
    }

    /**
     * 定时器
     */
     public abstract class OnWifiUpLoadTimer {
         private long mCount;
         private Handler mHandler;
         private  Thread mThread;

         /**
          *
          * @param count 循环多少次
          * @param delay 延时多少毫秒
          * @param isExecuteNow 是否立即执行
          */
      public  OnWifiUpLoadTimer(final long count, final long delay,boolean isOnmainThread,final boolean isExecuteNow) {
          mCount = count;
          if(mCount == 0) mCount = Long.MAX_VALUE;
          mHandler = new Handler(){
              @Override
              public void handleMessage(Message msg) {
                  super.handleMessage(msg);
                 runOnUIThread();
              }
          };


          mThread =  new Thread(new Runnable() {
              @Override
              public void run() {
                  for (int i = 0; i <mCount-1 ; i++) {
                      try {
                          if(!isExecuteNow)
                              Thread.sleep(delay);
                      }catch (Exception e){
                          e.printStackTrace();
                      }
                      doWorking();
                      mHandler.sendEmptyMessage(1);
                  }
              }
          });

      }

      public  void start() {
          if(mThread != null)
          mThread.start();
      }

      public  void stop() {
          mThread.stop();
          mHandler = null;
      }

    public abstract void  doWorking();
    public abstract void  runOnUIThread();

    }

    /**
     * 网络状态监控
     */

    public class  NetWorkStatesMonitor extends BroadcastReceiver {
        private  Context context;
        private  int badCount;
        private  long preNetWorkTotalBytes;
        private  float speed;
        public  NetWorkStatesMonitor (Context context) {
            this.context = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
           if(isWifiAndAvaiable()) {
               uploadImage();
           }

        }

        public int getNetWorkStates() {
            if(context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    return  networkInfo.getType();
                }
            }
            return -1;
        }

        /**
         * 是否连接到网络 且网络有用
         * @return
         */
        public boolean isNetWorkAviable() {
            if (context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() ) {
                    return  networkInfo.isAvailable();
                }
            }
            return false;
        }

        /**
         * 是否是手机网络
         * @return
         */
        public  boolean isMobileAndAviable() {
            return getNetWorkStates() == ConnectivityManager.TYPE_MOBILE && isNetWorkAviable();
        }

        /**
         * 是否是WiFi网络
         * @return
         */
        public  boolean isWifiAndAvaiable() {
            return getNetWorkStates() == ConnectivityManager.TYPE_WIFI && isNetWorkAviable();
        }


        public void showNetWorkState(){
            if(!isNetWorkAviable()) {
                Toast.makeText(context,"网络无法连接",Toast.LENGTH_SHORT).show();
            }

            if(isWifiAndAvaiable()) {
                Toast.makeText(context,"WIFI网络状态",Toast.LENGTH_SHORT).show();
            }

            if (isMobileAndAviable()) {

                Toast.makeText(context,"移动网络状态",Toast.LENGTH_SHORT).show();
            }

        }

        /**
         * 测网速
         */
        public void monitorNetWorkSpeed(){
            badCount = 0;
            preNetWorkTotalBytes = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();
            OnWifiUpLoadTimer onWifiUpLoadTimer = new OnWifiUpLoadTimer(0,2000,false,false) {
                @Override
                public void doWorking() {
                    long nowNetWorkTotalRxBytes = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();
                      speed = (float) ((nowNetWorkTotalRxBytes - preNetWorkTotalBytes)/1024.0/2.0);
                     if (speed <20 && speed != 0) {
                        badCount++;
                    }
                    preNetWorkTotalBytes = nowNetWorkTotalRxBytes;
                }

                @Override
                public void runOnUIThread() {
                    if (badCount == 4 ) {
                        badCount = 0;
                        Toast.makeText(context,"当前网络状态不稳定",Toast.LENGTH_SHORT).show();
                    }

                    if(speed <= 0.0) {
                        Toast.makeText(context,"网络无法连接",Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(context,"当前网速:"+speed+"kb/s",Toast.LENGTH_SHORT).show();
                }
            };
            onWifiUpLoadTimer.start();
        }
    }


    /**
     * 上传图片任务
     */
   public class UpLoadImageTask implements Runnable {
        private OnWifiUpLoadImageBean mImagePickBean;
        public  UpLoadImageTask(OnWifiUpLoadImageBean bean) {
            mImagePickBean = bean;
        }

       @Override
       public void run() {
           Log.d("onWifiUpLaodImages","任务上传中 "+mImagePickBean.getItemid()+" "+mImagePickBean.getImagePickBean().getImagePathOrUrl());
           if (new File(mImagePickBean.getImagePickBean().getImagePathOrUrl()).exists()) {
               String imageTime = TextUtils.isEmpty(mImagePickBean.getImagePickBean().getImageTime())? DateTool.getChinaTime():mImagePickBean.getImagePickBean().getImageTime();
               String dataJson = "{\"base64ImgsJsonStr\":" +
                       ImageUploadHelper.getBase64ImgsJsonStr(mImagePickBean.getResultid(),
                               mImagePickBean.getItemid(),
                               mImagePickBean.getCompanyid(),
                               mImagePickBean.getPlant_number(),mImagePickBean.getImagePickBean().getImagePathOrUrl(),imageTime, mImagePickBean.getUserID()) + "}";
               OkHttpSingleton.getInstance().doPostJsonString(mImagePickBean.getUploadUrl(),
                       HeadsParamsHelper.setupDefaultHeaders(), null, dataJson,
                       new GsonOkHttpCallback<BaseListBean<UploadImageBackBean>>() {
                           @Override
                           public BaseListBean<UploadImageBackBean> OnSuccess(String msg, int code) {
                               if(msg.indexOf("上传失败")>0) {
                                   return null;
                               }
                               TypeToken<BaseListBean<UploadImageBackBean>> typeToken = new TypeToken<BaseListBean<UploadImageBackBean>>() {
                               };
                               return BaseListBean.fromJsonFive(msg, typeToken);

                           }
                           @Override
                           public void OnSuccessOnUI(BaseListBean<UploadImageBackBean> listBean, int code) {
                               synchronized (this){
                                   Log.d("onWifiUpLaodImages",mImagePickBean.getImagePickBean().getImagePathOrUrl()+" "+"任务成功");
                                   deleteTempImage(mImagePickBean.getImagePickBean()); //成功则删除图片拷贝
                                   unUploadArr.remove(mImagePickBean);
                               }
                           }
                           @Override
                           public void OnError(String msg, int code) {
                               synchronized (this){
                                   Log.d("onWifiUpLaodImages",mImagePickBean.getImagePickBean().getImagePathOrUrl()+" "+"任务失败");
                                   failedUpLoadArr.add(mImagePickBean);
                                   unUploadArr.remove(mImagePickBean);

                               }
                           }
                       });
           }
       }
   }

    private  NetWorkStatesMonitor mNetWorkStatesMonitor ;
    private  final OnWifiUpLoadBinder mOnWifiUpLoadBinder = new OnWifiUpLoadBinder();
    private  Context mContext;
    private  static ExecutorService mThreadPoolExecutor = Executors.newFixedThreadPool(5);
    private  static  ArrayList unUploadArr = new ArrayList(); //剩余上传图片
    private  static ArrayList failedUpLoadArr = new ArrayList();//失败上传图片
    private  int errorCount = 200;//允许失败次数
    private  Thread monitorThread ;
    private  Runnable monitorTask = new Runnable() {
        @Override
        public void run() {
            Log.d("onWifiUpLaodImages","线程监听开启");
            int size ;
            while (true) {
                synchronized (this){
                    size = unUploadArr.size();
                }
                try {
                    if (size == 0) {
                        Log.d("onWifiUpLaodImages","全部上传任务完成"+mThreadPoolExecutor.toString());
                        dealWithFailed();
                        break;
                    }
                    Thread.sleep(1000*5);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mOnWifiUpLoadBinder;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mNetWorkStatesMonitor);
        super.onDestroy();
    }

    public void  startService(Context context) {
        mContext = context;
        mNetWorkStatesMonitor = new NetWorkStatesMonitor(mContext);
        //mNetWorkStatesMonitor.monitorNetWorkSpeed();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mNetWorkStatesMonitor,intentFilter);

        ACache aCache = ACache.get(mContext);  ArrayList imageCacheArr = (ArrayList) aCache.getAsObject("imageCache");

        if (imageCacheArr == null) {
            imageCacheArr = new ArrayList();
            aCache.put("imageCache",imageCacheArr);
        }

    }


    private  void uploadImage() {
            ACache aCache = ACache.get(mContext);
            ArrayList imageCacheArr = (ArrayList) aCache.getAsObject("imageCache");
            //清空掉无用数据
            ArrayList tempArr = new ArrayList();
            tempArr.addAll(imageCacheArr);
            for (int i = 0; i < imageCacheArr.size(); i++) {
                OnWifiUpLoadImageBean bean = (OnWifiUpLoadImageBean) imageCacheArr.get(i);
                if (bean != null) {
                    if (bean.getImagePickBean() == null) {
                        tempArr.remove(bean);
                    }
                }
            }

            synchronized (this) {
                tempArr.addAll(failedUpLoadArr);
                unUploadArr.addAll(tempArr);
                failedUpLoadArr.removeAll(failedUpLoadArr);
            }

        //开启上传进度监听
        if(monitorThread == null) {
            monitorThread = new Thread(monitorTask);
            monitorThread.start();
        }else {
            if(!monitorThread.isAlive()) {
                monitorThread = null;
                monitorThread = new Thread(monitorTask);
                monitorThread.start();
            }
        }

        //执行上传任务
        for (int i = 0; i < tempArr.size(); i++) {
            OnWifiUpLoadImageBean bean = (OnWifiUpLoadImageBean) tempArr.get(i);
            mThreadPoolExecutor.execute(new UpLoadImageTask(bean));
        }

        synchronized (this) {
            aCache.put("imageCache",new ArrayList());
        }
    }

    /**
     * 失败的图片继续上传
     */
    private void dealWithFailed() {
       if (failedUpLoadArr.size() >0) {
           errorCount--;
           if (errorCount >0)
            uploadImage();
       }else {
           errorCount = 200;
       }
    }

    /**
     * 上传成功则删除拷贝的图片
     * @param bean
     * @return
     */
    private boolean deleteTempImage(ImagePickBean bean) {
        File file = new File(bean.getImagePathOrUrl());
        if(file.exists()) {
           return file.delete();
        }
        return false;
    }

    public  NetWorkStatesMonitor getNetWorkMonitor(Context context) {

        return new NetWorkStatesMonitor(context);
    }
    public  void stopService() {
        this.stopSelf();
    }

    }

