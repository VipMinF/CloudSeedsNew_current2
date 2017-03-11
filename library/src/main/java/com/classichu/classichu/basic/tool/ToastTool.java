package com.classichu.classichu.basic.tool;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.classichu.classichu.R;
import com.classichu.classichu.app.ClassicApplication;
import com.classichu.classichu.basic.helper.ClassicContextHelper;
import com.classichu.classichu.basic.helper.VectorOrImageResHelper;


/**
 * Created by louisgeek on 2016/12/15.
 */

public class ToastTool {


    private static Toast mToast;
    private static Toast mImageToast;

    /**
     * @param message
     * @param duration
     * @return
     */
    private static Toast initToast(CharSequence message, int duration, int gravity) {
        if (mToast == null) {
            mToast = Toast.makeText(ClassicApplication.getAppContext(), message, duration);
        } else {
            mToast.setText(message);
            mToast.setDuration(duration);
            if (gravity != 0) {
                mToast.setGravity(gravity, 0, 0);
            }
        }
        return mToast;
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(String message) {
        initToast(message, Toast.LENGTH_SHORT, 0).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(String message) {
        initToast(message, Toast.LENGTH_LONG, 0).show();
    }

    /**
     * 自定义显示Toast位置
     *
     * @param message
     */
    public static void showShortCenter(String message) {
        initToast(message, Toast.LENGTH_SHORT, Gravity.CENTER).show();
    }

    /**
     * 自定义显示Toast位置
     *
     * @param message
     */
    public static void showLongCenter(String message) {
        initToast(message, Toast.LENGTH_LONG, Gravity.CENTER).show();
    }

    /**
     * 显示有image的toast
     *
     * @param message
     * @param drawable
     * @return
     */
    private static Toast initImageToast(final String message, final Drawable drawable) {
        Context context = ClassicContextHelper.getAppContext();
        if (mImageToast == null) {
            mImageToast = new Toast(context);
            mImageToast.setDuration(Toast.LENGTH_SHORT);
            mImageToast.setGravity(Gravity.CENTER, 0, 0);
            //
            View view = LayoutInflater.from(context).inflate(R.layout.layout_toast_image, null);
            TextView tv = (TextView) view.findViewById(R.id.id_tv_toast);
            tv.setText(TextUtils.isEmpty(message) ? "" : message);
            ImageView iv = (ImageView) view.findViewById(R.id.id_iv_toast);
            if (drawable != null) {
                iv.setVisibility(View.VISIBLE);
                iv.setImageDrawable(drawable);
            } else {
                iv.setVisibility(View.GONE);
            }
            //
            mImageToast.setView(view);
        } else {
            View view = mImageToast.getView();
            //
            TextView tv = (TextView) view.findViewById(R.id.id_tv_toast);
            tv.setText(TextUtils.isEmpty(message) ? "" : message);
            ImageView iv = (ImageView) view.findViewById(R.id.id_iv_toast);
            if (drawable != null) {
                iv.setVisibility(View.VISIBLE);
                iv.setImageDrawable(drawable);
            } else {
                iv.setVisibility(View.GONE);
            }
        }
        return mImageToast;

    }

    /**
     * @param message
     * @param imageResId
     * @return
     */
    public static void showImage(final String message, int imageResId) {
        Drawable drawable = VectorOrImageResHelper.getDrawable(ClassicApplication.getAppContext(), imageResId);
        initImageToast(message, drawable).show();
    }

    public static void showImageOk(final String message) {
        showImage(message, R.drawable.ic_check_circle_black_24dp);
    }

    public static void showImageInfo(final String message) {
        showImage(message, R.drawable.ic_info_black_24dp);
    }

    public static void showImageWarn(final String message) {
        showImage(message, R.drawable.ic_error_black_24dp);
    }
}