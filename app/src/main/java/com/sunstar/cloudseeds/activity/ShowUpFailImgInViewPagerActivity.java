package com.sunstar.cloudseeds.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.adapter.MyPagerAdapter;
import com.sunstar.cloudseeds.adapter.utils.BitmapOptions;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.OnWifiUpLoadImageBean;

import java.util.ArrayList;

public class ShowUpFailImgInViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_up_fail_img_in_view_pager);
        initData();

    }

    private void initView(ArrayList<ImageView> imageViews,int position) {
        viewPager = (ViewPager) findViewById(R.id.show_image_viewPager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(imageViews, position);
        viewPager.setAdapter(myPagerAdapter);
    }


    private void initData() {
        Bundle extras = getIntent().getExtras();
        ArrayList failUpImgsBean = (ArrayList)extras.getSerializable("failUpImgsBean");
        int position = extras.getInt("position");
        ArrayList<ImageView> imageViews=new ArrayList<>();
        ImageView imageView = new ImageView(this);
        for (int i = 0; i < failUpImgsBean.size(); i++) {
            OnWifiUpLoadImageBean onWifiUpLoadImageBean = (OnWifiUpLoadImageBean) failUpImgsBean.get(i);
            String uploadUrl = onWifiUpLoadImageBean.getUploadUrl();
            Bitmap samplesizeBitmap = new BitmapOptions().get2SamplesizeBitmap(uploadUrl);
            imageView.setImageBitmap(samplesizeBitmap);
            imageViews.add(imageView);
        }
        initView(imageViews,position);
    }
}
