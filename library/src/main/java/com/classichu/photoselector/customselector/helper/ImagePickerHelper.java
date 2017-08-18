package com.classichu.photoselector.customselector.helper;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.classichu.photoselector.tool.SdcardTool;
import com.classichu.photoselector.tool.ThreadTool;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by louisgeek on 2016/11/16.
 */

public class ImagePickerHelper {
    private static Map<String, List<String>> mGroupMap = new LinkedHashMap<>();
    private static boolean isSuccess = false;
    private static String message = "";

    /**
     * 扫描本地图片
     * @param activity
     * @param scanLocalImagesCallBack
     */
    public static void scanLocalImage(final Activity activity, final ScanLocalImagesCallBack scanLocalImagesCallBack) {
        //KLog.d("SDF 扫描开始");
        if (!SdcardTool.hasSDCardMounted()) {
        //    KLog.e("无外部存储");
            isSuccess = false;
            message = "无外部存储";
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                //ContentResolver
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = activity.getContentResolver();
                //只查询jpeg和png的图片
                Cursor cursor = mContentResolver.query(mImageUri, null,
                        null, null, MediaStore.Images.Media.DATE_ADDED);
                if (cursor == null) {
                   // KLog.d("mCursor is null");
                    isSuccess = false;
                    message = "mCursor is null";
                    return;
                }
                mGroupMap.clear();
                //add by zy 6.1 用来按时间顺序显示照片
                List<String> timeList = new ArrayList<String>();
                mGroupMap.put("timeList",timeList);

                while (cursor.moveToNext()) {
                    //获取图片的路径
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    if (path==null){continue;}
                    File file = new File(path);
                    Date data = new Date(file.lastModified());
                    Log.v("data",data.toString());
                    String name =file.getName();
                    //获取该图片的父路径名
                    String parentPath = file.getParentFile().getAbsolutePath();
                    if (parentPath==null){continue;}
                    //根据父路径将图片放入到Map中
                    if (!mGroupMap.containsKey(parentPath)) {
                        List<String> childList = new ArrayList<>();
                        childList.add(name);
                        mGroupMap.put(parentPath, childList);
                    } else {
                        mGroupMap.get(parentPath).add(name);
                    }
                    timeList.add(path);
                }

                //通知扫描图片完成
                isSuccess = true;
                message = "图片扫描完成";
              //  KLog.d("SDF 扫描结束");
                /**
                 *close
                 */
                cursor.close();


                if (isSuccess) {
                    ThreadTool.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            scanLocalImagesCallBack.onSuccess(mGroupMap);
                        }
                    });

                } else {
                    ThreadTool.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            scanLocalImagesCallBack.onError(message);
                        }
                    });
                }

            }
        }).start();

    }

    public interface ScanLocalImagesCallBack {
        void onSuccess(Map<String, List<String>> groupMap);

        void onError(String message);
    }
}
