package com.sunstar.cloudseeds.logic.imageupload.helper;

import android.graphics.Bitmap;
import android.util.Base64;

import com.classichu.classichu.basic.extend.DataHolderSingleton;
import com.classichu.classichu.basic.tool.ImageTool;
import com.classichu.photoselector.imagespicker.ImagePickBean;
import com.google.gson.Gson;
import com.sunstar.cloudseeds.logic.imageupload.ImagePickUploadQueueManager;
import com.sunstar.cloudseeds.logic.imageupload.bean.UploadBase64ImgsBean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2017/3/27.
 */

public class ImageUploadHelper {
    public static List<ImagePickBean> getLeftImageListFromInstance(String queueNameNoSame) {
        List<ImagePickBean> imagePickBeanList
                = (List<ImagePickBean>) DataHolderSingleton.getInstance()
                .getData(ImagePickUploadQueueManager.KEY_IMAGES_QUEUE_LIST_ + queueNameNoSame);

        List<ImagePickBean> needImagePickBeanList = new ArrayList<>();
        if (imagePickBeanList != null && imagePickBeanList.size() > 0) {
            //遍历
            for (int i = 0; i < imagePickBeanList.size(); i++) {
                ImagePickBean imagePickBean = imagePickBeanList.get(i);
                String imageWebIdStr = imagePickBean.getImageWebIdStr();
                if (imageWebIdStr == null || imageWebIdStr.equals("")) {
                    needImagePickBeanList.add(imagePickBean);
                }
            }
        }
        return needImagePickBeanList;
    }
    /**
     * 把bitmap转换成String
     *
     * @param filePath
     * @return
     */
    private static String bitmapToString(String filePath) {

        int orientation = ImageTool.getBitmapDegree(filePath);//获取旋转角度

        Bitmap bitmap = ImageTool.decodeSampledBitmapFromPath(filePath,480, 800);

        if(Math.abs(orientation) > 0){
            bitmap =  ImageTool.rotateBitmapByDegree( bitmap,orientation);//旋转图片
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] bytes = baos.toByteArray();
        //
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
    public static String getBase64ImgsJsonStr(String companyid,String plantnumber,String filePath, String fileTime, int nowUserID) {

        File file=new File(filePath);
        String fileName=file.getName();

        String Base64Str=bitmapToString(filePath);

        List<UploadBase64ImgsBean> list=new ArrayList<>();

        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
        System.out.println(prefix);
        //用String的endsWith(".java")方法判断是否问指定文件类型。
        String prefixHasDot="."+prefix;
        UploadBase64ImgsBean imgs1=new UploadBase64ImgsBean(nowUserID,companyid,plantnumber, fileName,fileTime, prefixHasDot, Base64Str);
        // UploadImgs imgs2=new UploadImgs(NowUserID, "120.jpg", ".jpg", Base64Str);
        list.add(imgs1);
        // list.add(imgs2);
        Gson gson=new Gson();
        String base64ImgsJsonStr= gson.toJson(list);
        return base64ImgsJsonStr;

    }
}
