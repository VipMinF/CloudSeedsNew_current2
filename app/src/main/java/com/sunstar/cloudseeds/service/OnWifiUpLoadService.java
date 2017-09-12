package com.sunstar.cloudseeds.service;


import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.classichu.classichu.basic.extend.DataHolderSingleton;
import com.classichu.classichu.basic.okhttp.GsonOkHttpCallback;
import com.classichu.classichu.basic.okhttp.OkHttpSingleton;
import com.classichu.classichu.basic.tool.DateTool;
import com.classichu.photoselector.imagespicker.ImagePickBean;
import com.google.gson.reflect.TypeToken;
import com.sunstar.cloudseeds.cache.ACache;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.imageupload.bean.BaseListBean;
import com.sunstar.cloudseeds.logic.imageupload.bean.UploadImageBackBean;
import com.sunstar.cloudseeds.logic.imageupload.helper.ImageUploadHelper;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.OnWifiUpLoadImageBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sunstar.cloudseeds.activity.ShowFailUpImgsActivity.mHandler;
import static com.sunstar.cloudseeds.widget.SettingPreferenceFragmentCompat.MyHandler;

/**
 * 用来WiFi下自动上传图片
 * Created by xiaoxian on 2017/6/12.
 */

public class OnWifiUpLoadService extends Service {


    private ProgressDialog progressDialog;

    /**
     * 定时器
     */
    public abstract class OnWifiUpLoadTimer {
        private long mCount;
        private Handler mHandler;
        private Thread mThread;

        /**
         * @param count        循环多少次
         * @param delay        延时多少毫秒
         * @param isExecuteNow 是否立即执行
         */
        public OnWifiUpLoadTimer(final long count, final long delay, boolean isOnmainThread, final boolean isExecuteNow) {
            mCount = count;
            if (mCount == 0) mCount = Long.MAX_VALUE;
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    runOnUIThread();
                }
            };


            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < mCount - 1; i++) {
                        try {
                            if (!isExecuteNow)
                                Thread.sleep(delay);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        doWorking();
                        mHandler.sendEmptyMessage(1);
                    }
                }
            });

        }

        public void start() {
            if (mThread != null)
                mThread.start();
        }

        public void stop() {
            mThread.stop();
            mHandler = null;
        }

        public abstract void doWorking();

        public abstract void runOnUIThread();

    }

    /**
     * 网络状态监控
     */

    public class NetWorkStatesMonitor extends BroadcastReceiver {
        private Context context;
        private int badCount;
        private long preNetWorkTotalBytes;
        private float speed;

        public NetWorkStatesMonitor(Context context) {
            this.context = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (isWifiAndAvaiable()) {
                uploadImage();
            }

        }

        public int getNetWorkStates() {
            if (context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager)
                        context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    return networkInfo.getType();
                }
            }
            return -1;
        }

        /**
         * 是否连接到网络 且网络有用
         *
         * @return
         */
        public boolean isNetWorkAviable() {
            if (context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager)
                        context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    return networkInfo.isAvailable();
                }
            }
            return false;
        }

        /**
         * 是否是手机网络，可用
         *
         * @return
         */
        public boolean isMobileAndAviable() {
            return getNetWorkStates() == ConnectivityManager.TYPE_MOBILE && isNetWorkAviable();
        }


        /**
         * 是否是WiFi网络，可用
         *
         * @return
         */
        public boolean isWifiAndAvaiable() {
            return getNetWorkStates() == ConnectivityManager.TYPE_WIFI && isNetWorkAviable();
        }


        public void showNetWorkState() {
            if (!isNetWorkAviable()) {
                Toast.makeText(context, "网络无法连接", Toast.LENGTH_SHORT).show();
            }

            if (isWifiAndAvaiable()) {
                Toast.makeText(context, "WIFI网络状态", Toast.LENGTH_SHORT).show();
            }

            if (isMobileAndAviable()) {

                Toast.makeText(context, "移动网络状态", Toast.LENGTH_SHORT).show();
            }

        }

        /**
         * 测网速
         */
        public void monitorNetWorkSpeed() {
            badCount = 0;
            preNetWorkTotalBytes = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();
            OnWifiUpLoadTimer onWifiUpLoadTimer = new OnWifiUpLoadTimer(0, 2000, false, false) {
                @Override
                public void doWorking() {
                    long nowNetWorkTotalRxBytes = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();
                    speed = (float) ((nowNetWorkTotalRxBytes - preNetWorkTotalBytes) / 1024.0 / 2.0);
                    if (speed < 20 && speed != 0) {
                        badCount++;
                    }
                    preNetWorkTotalBytes = nowNetWorkTotalRxBytes;
                }

                @Override
                public void runOnUIThread() {
                    if (badCount == 4) {
                        badCount = 0;
                        Toast.makeText(context, "当前网络状态不稳定", Toast.LENGTH_SHORT).show();
                    }

                    if (speed <= 0.0) {
                        Toast.makeText(context, "网络无法连接", Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(context, "当前网速:" + speed + "kb/s", Toast.LENGTH_SHORT).show();
                }
            };
            onWifiUpLoadTimer.start();
        }
    }

    public static final String KEY_IMAGES_QUEUE_LIST_ = "KEY_IMAGES_QUEUE_LIST_";
    private static final String KEY_IMAGES_QUEUE_NEW_WEB_IDS_ = "KEY_IMAGES_QUEUE_NEW_WEB_IDS_";
    private static final String KEY_IMAGES_QUEUE_COUNT_ = "KEY_IMAGES_QUEUE_COUNT_";
    private static final String KEY_IMAGES_QUEUE_LEFT_COUNT_ = "KEY_IMAGES_QUEUE_LEFT_COUNT_";

    /**
     * 上传图片任务
     */
    public class UpLoadImageTask implements Runnable {
        private OnWifiUpLoadImageBean mImagePickBean;

        private String mQueueNameNoSame;

        public UpLoadImageTask(OnWifiUpLoadImageBean bean, String queueNameNotTheSame) {
            mImagePickBean = bean;
            mQueueNameNoSame = queueNameNotTheSame;
        }


        /**
         * 【上传图片】2.执行上传
         */
        private void uploadImageQueue_ExecueQueue() {
            Log.d("图片上传", "execueImageQueue xxx");
            //
            //取出没有上传过的图片
            List<ImagePickBean> needImagePickBeanList = ImageUploadHelper.getLeftImageListFromInstance(mQueueNameNoSame);

            if (needImagePickBeanList != null && needImagePickBeanList.size() > 0) {
                for (int i = 0; i < needImagePickBeanList.size(); i++) {
                    ImagePickBean imagePickBean = needImagePickBeanList.get(i);


                    //
                    //执行
//                    uploadImageQueue_UploadLeftImages(imagePickBean.getImagePathOrUrl(),
//                            TextUtils.isEmpty(imagePickBean.getImageTime())? DateTool.getChinaTime():imagePickBean.getImageTime());

                    break;//一个就结束
                }
            }
        }

        /**
         * 更新上传进度
         */
        public void getUpdateLoadPicsProgress() {
            int allCount = (int) DataHolderSingleton.getInstance().getData(KEY_IMAGES_QUEUE_COUNT_ + mQueueNameNoSame);
            int leftCount = (int) DataHolderSingleton.getInstance().getData(KEY_IMAGES_QUEUE_LEFT_COUNT_ + mQueueNameNoSame);
            int lefCountNew = leftCount - 1;
            //剩余没上传的图片
//            int yuCount = lefCountNew;
//            new SettingPreferenceFragmentCompat().setMSummary(yuCount);
            DataHolderSingleton.getInstance().putData(KEY_IMAGES_QUEUE_LEFT_COUNT_ + mQueueNameNoSame, lefCountNew);

            //更新  instance 内
//                for (int i = 0; i < imagePickBeanList.size(); i++) {
//                    ImagePickBean imagePickBean = imagePickBeanList.get(i);
//               /* if (imageName.equals(imagePickBean.getImageName()) &&
//                        imagePickedTimeAndOrderTag.equals(imagePickBean.getImagePickedTimeAndOrderTag())) {*/
//                    if (imagePath.equals(imagePickBean.getImagePathOrUrl())) {
//                        imagePickBeanList.get(i).setImageWebIdStr(uploadImageBackBean.getBigImageName());//imageUrl 标志上传成功
//                        imagePickBeanList.get(i).setImagePathOrUrl(uploadImageBackBean.getBigImageName());
//                        imagePickBeanList.get(i).setImageName(uploadImageBackBean.getCode());//暂存Code
//                        break;
//                    }
//                }


            //切记在UI线程中直接执行  网络请求 在子线程
//                new Handler().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        uploadImageQueue_ExecueQueue();//继续执行
//                    }
//                });

        }



        @Override
        public void run() {
            Log.d("onWifiUpLaodImages", "任务上传中 " + mImagePickBean.getItemid() + " " + mImagePickBean.getImagePickBean().getImagePathOrUrl());
            if (new File(mImagePickBean.getImagePickBean().getImagePathOrUrl()).exists()) {
                /**
                 * 上传的时间
                 */
                showDialog();

                String imageTime = TextUtils.isEmpty(mImagePickBean.getImagePickBean().getImageTime()) ? DateTool.getChinaTime() : mImagePickBean.getImagePickBean().getImageTime();
                String dataJson = "{\"base64ImgsJsonStr\":" +
                        ImageUploadHelper.getBase64ImgsJsonStr(mImagePickBean.getResultid(),
                                mImagePickBean.getItemid(),
                                mImagePickBean.getCompanyid(),
                                mImagePickBean.getPlant_number(), mImagePickBean.getImagePickBean().getImagePathOrUrl(), imageTime, mImagePickBean.getUserID()) + "}";
                OkHttpSingleton.getInstance().doPostJsonString(mImagePickBean.getUploadUrl(),
                        HeadsParamsHelper.setupDefaultHeaders(), null, dataJson,
                        new GsonOkHttpCallback<BaseListBean<UploadImageBackBean>>() {


                            @Override
                            public BaseListBean<UploadImageBackBean> OnSuccess(String msg, int code) {
                                if (msg.indexOf("上传失败") > 0) {
                                    return null;
                                }
                                TypeToken<BaseListBean<UploadImageBackBean>> typeToken = new TypeToken<BaseListBean<UploadImageBackBean>>() {
                                };
                                return BaseListBean.fromJsonFive(msg, typeToken);

                            }

                            @Override
                            public void OnSuccessOnUI(BaseListBean<UploadImageBackBean> listBean, int code) {
                                synchronized (this) {
                                    Log.i("onWifiUpLaodImages", mImagePickBean.getImagePickBean().getImagePathOrUrl() + " " + "任务成功");
                                    deleteTempImage(mImagePickBean.getImagePickBean()); //成功则删除图片拷贝
                                    unUploadArr.remove(mImagePickBean);
                                    ACache aCache = ACache.get(mContext);
                                    ArrayList imageArr = (ArrayList) aCache.getAsObject("imageCache");
                                    int size = imageArr.size();
                                    progressDialog.setProgress(size);
                                }
                                progressDialog.cancel();
                            }

                            @Override
                            public void OnError(String msg, int code) {
                                synchronized (this) {
                                    Log.i("onWifiUpLaodImages", mImagePickBean.getImagePickBean().getImagePathOrUrl() + " " + "任务失败");
                                    failedUpLoadArr.add(mImagePickBean);
                                    /**
                                     * 将上传失败的图片源发送到settingPreference展示
                                     */
                                    Message obtain = Message.obtain();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("failedUpLoadArr", failedUpLoadArr);
                                    obtain.what = 3;
                                    obtain.setData(bundle);
                                    MyHandler.sendMessage(obtain);
                                    unUploadArr.remove(mImagePickBean);
                                }
                            }
                        });
            }
        }
    }

    private NetWorkStatesMonitor mNetWorkStatesMonitor;
    private final OnWifiUpLoadBinder mOnWifiUpLoadBinder = new OnWifiUpLoadBinder();
    private Context mContext;
    private static ExecutorService mThreadPoolExecutor = Executors.newFixedThreadPool(5);
    public static ArrayList unUploadArr = new ArrayList(); //剩余上传图片
    public static ArrayList failedUpLoadArr = new ArrayList();//失败上传图片
    private int errorCount = 200;//允许失败次数
    private Thread monitorThread;
    public static int size;
    final int MAX_PROGRESS = 100;
    private void showDialog(){
        ACache aCache = ACache.get(mContext);
        ArrayList imageArr = (ArrayList) aCache.getAsObject("imageCache");
        if (imageArr!=null){
            int total = imageArr.size();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setProgress(0);
            progressDialog.setTitle("图片正在上传中...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(total);
            progressDialog.show();

        }
    }

    /**
     * 上传线程
     */
    private Runnable monitorTask = new Runnable() {
        @Override
        public void run() {
            Log.d("onWifiUpLaodImages", "线程监听开启");

            while (true) {
                synchronized (this) {
                    size = unUploadArr.size();
                }
                try {
                    if (size == 0) {
                        Log.i("--onWifiUpLaodImages--", "—————————————————————全部上传任务完成————————————————"
                                + mThreadPoolExecutor.toString());
                        dealWithFailed();
                        Message obtain = Message.obtain();
                        Bundle bundle = new Bundle();
                        bundle.putInt("size",size);
                        obtain.setData(bundle);
                        obtain.what=5;
                        MyHandler.sendMessage(obtain);
                        mHandler.sendMessage(obtain);
                        break;
                    }
                    Thread.sleep(1000 * 5);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public class OnWifiUpLoadBinder extends Binder {
        public OnWifiUpLoadService getService() {
            return OnWifiUpLoadService.this;
        }
    }

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

    public void startService(Context context) {
        mContext = context;
        mNetWorkStatesMonitor = new NetWorkStatesMonitor(mContext);
        //mNetWorkStatesMonitor.monitorNetWorkSpeed();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mNetWorkStatesMonitor, intentFilter);

        ACache aCache = ACache.get(mContext);
        ArrayList imageCacheArr = (ArrayList) aCache.getAsObject("imageCache");

        if (imageCacheArr == null) {
            imageCacheArr = new ArrayList();
            aCache.put("imageCache", imageCacheArr);
        }

    }

    Handler mainHandler = new Handler(Looper.getMainLooper());

    public void upDateUI() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                //已在主线程中，可以更新UI
            }
        });
    }


    /**
     * 上传图片
     */
    public void uploadImage() {
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
        //剩余没有上传的图片
        synchronized (this) {
            tempArr.addAll(failedUpLoadArr);
            unUploadArr.addAll(tempArr);
            failedUpLoadArr.removeAll(failedUpLoadArr);
        }
        //开启上传进度监听
        if (monitorThread == null) {
            monitorThread = new Thread(monitorTask);
            /**
             * 开启上传
             */
            monitorThread.start();
        } else {
            if (!monitorThread.isAlive()) {
                monitorThread = null;
                monitorThread = new Thread(monitorTask);
                monitorThread.start();
            }
        }
        /**
         * 执行上传图片的任务
         *
         * tempArr.size()要上传图片的张数
         *
         */
        final ArrayList imageArr = (ArrayList) aCache.getAsObject("imageCache");
        int size = imageArr.size();
        OnWifiUpLoadService.NetWorkStatesMonitor netWorkMonitor =
                new OnWifiUpLoadService().getNetWorkMonitor(mContext);
        final boolean wifiAndAvaiable = netWorkMonitor.isWifiAndAvaiable();

        int sum = 0;
        int ySum = 0;
        for (int i = 0; i < tempArr.size(); i++) {
            sum = sum + 1;
            ySum = tempArr.size() - sum;
            Message obtain = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putInt("ySum", ySum);
            obtain.what = 0;
            obtain.setData(bundle);
            MyHandler.sendMessage(obtain);
            OnWifiUpLoadImageBean bean = (OnWifiUpLoadImageBean) tempArr.get(i);
            //执行上传图片的任务
            mThreadPoolExecutor.execute(new UpLoadImageTask(bean, "now_leftTitleStr"));
        }
        synchronized (this) {
            aCache.put("imageCache", new ArrayList());
        }
    }


    /**
     * 失败的图片继续上传
     */
    private void dealWithFailed() {
        if (failedUpLoadArr.size() > 0) {
            errorCount--;
            if (errorCount > 0)
                uploadImage();
        } else {
            errorCount = 200;
        }
    }

    /**
     * 上传成功则删除拷贝的图片
     *
     * @param bean
     * @return
     */
    private boolean deleteTempImage(ImagePickBean bean) {
        File file = new File(bean.getImagePathOrUrl());
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public NetWorkStatesMonitor getNetWorkMonitor(Context context) {

        return new NetWorkStatesMonitor(context);
    }

    public void stopService() {
        this.stopSelf();
    }

}

