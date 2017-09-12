package com.sunstar.cloudseeds.adapter.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Administrator on 2017/9/11.
 */

public class BitmapOptions {

    public BitmapFactory.Options getOptions(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return options;
    }

    public Bitmap get2SamplesizeBitmap(String uploadUrl){
        Bitmap bitmap = BitmapFactory.decodeFile(uploadUrl, getOptions(2));
//        bitmap.setConfig(Bitmap.Config.RGB_565);
        return bitmap;
    }


}
