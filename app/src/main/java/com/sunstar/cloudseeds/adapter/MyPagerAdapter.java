package com.sunstar.cloudseeds.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/11.
 */

public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<ImageView> imageViews;
    private int position;

    public MyPagerAdapter(ArrayList<ImageView> imageViews, int position) {
        this.imageViews = imageViews;
        this.position = position;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViews.get(position),position);

        return imageViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        container.removeView(imageViews.get(position));
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


}
