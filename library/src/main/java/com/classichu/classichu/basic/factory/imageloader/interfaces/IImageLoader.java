package com.classichu.classichu.basic.factory.imageloader.interfaces;

import android.widget.ImageView;

/**
 * Created by louisgeek on 2016/12/28.
 */

public interface IImageLoader {
    void displayImage(ImageView imageView, String url, int width, int height);

    void displayImage(ImageView imageView, String url);

    void displayImageHasNoPlaceholder(ImageView imageView, String url);

    void displayImage(ImageView imageView, int imageResId);


    void loadImage(String url, ILoadImageCallback loadImageCallback);


}