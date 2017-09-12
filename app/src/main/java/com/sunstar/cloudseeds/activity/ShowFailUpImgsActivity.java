package com.sunstar.cloudseeds.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.adapter.ShowUpFailImagsAdapter;
import com.sunstar.cloudseeds.service.OnWifiUpLoadService;
import com.sunstar.cloudseeds.widget.ShowFailImgsItemDerector;

import java.util.ArrayList;

import static com.sunstar.cloudseeds.widget.SettingPreferenceFragmentCompat.getCurrentTime;

/**
 * 展示上传失败的图片的activity
 */
public class ShowFailUpImgsActivity extends AppCompatActivity {

    private RecyclerView showFailUpImgsRecyclerView;
    private Button show_image_btn;
    private ShowUpFailImagsAdapter showUpFailImagsAdapter;
    private ArrayList failUpImgsBean;
    private int flag;
    private OnWifiUpLoadService onWifiUpLoadService;

    public static Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 5:
                    Bundle data5 = msg.getData();
                    int size5 = data5.getInt("size");
                    if (size5==0){
                        String currentTime5 = getCurrentTime();

                    }else {

                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fail_up_imgs);
        Intent intent = new Intent(this, OnWifiUpLoadService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        initData();
        initView(failUpImgsBean, flag);
        doEvent();
    }

    ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            onWifiUpLoadService = ((OnWifiUpLoadService.OnWifiUpLoadBinder) service).getService();
            onWifiUpLoadService.startService(ShowFailUpImgsActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private void doEvent() {
        show_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWifiUpLoadService.uploadImage();
            }
        });
        /**
         * 给showUpFailImagsAdapter的item设置点击事件
         */
        showUpFailImagsAdapter.setOnItemClick(new ShowUpFailImagsAdapter.ItemClick() {
            @Override
            public void setItemClick(View view, int position) {
                //跳转到查看图片的页面
                Bundle extras = getIntent().getExtras();
                ArrayList failUpImgsBean = (ArrayList) extras.getSerializable("failUpImgsBean");
                Intent intent = new Intent(ShowFailUpImgsActivity.this, ShowUpFailImgInViewPagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("failUpImgsBean", failUpImgsBean);
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        failUpImgsBean = (ArrayList) extras.getSerializable("failUpImgsBean");
        flag = extras.getInt("flag");


    }

    private void recyclerViewSetInfo(ArrayList failUpImgsBean) {
        /**
         * 1.给showFailUpImgsRecyclerView设置布局类型
         * 2.给showFailUpImgsRecyclerView设置分割线
         * 3.给showFailUpImgsRecyclerView设置Adapter
         */
        showUpFailImagsAdapter = new ShowUpFailImagsAdapter(this, failUpImgsBean);
        showFailUpImgsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        showFailUpImgsRecyclerView.addItemDecoration(
                new ShowFailImgsItemDerector(this, ShowFailImgsItemDerector.VERTICAL_LIST));
        showFailUpImgsRecyclerView.setAdapter(showUpFailImagsAdapter);
    }

    private void initView(final ArrayList failUpImgsBean, int flag) {
        showFailUpImgsRecyclerView = (RecyclerView) this.findViewById(R.id.showFailUpImgsRecyclerView);
        show_image_btn = (Button) findViewById(R.id.show_image_btn);
//        List<ImagePickBean> imagePickBeanList = new ArrayList<>();
//        for (int i = 0; i < failUpImgsBean.size(); i++) {
//            OnWifiUpLoadImageBean onWifiUpLoadImageBean = (OnWifiUpLoadImageBean) failUpImgsBean.get(i);
//            ImagePickBean imagePickBean = onWifiUpLoadImageBean.getImagePickBean();
//            imagePickBeanList.add(imagePickBean);
//        }
//        final ImagePickUploadQueueManager imagsUP = new ImagePickUploadQueueManager("now_leftTitleStr",
//                "", imagePickBeanList, , "图片上传", 0, false) {
//            @Override
//            protected void uploadImageQueue_Complete(String thePreviousData, List<ImagePickBean> imagePickBeanList, ArrayList jsonArr) {
//                String spqedit_itemid = (String) DataHolderSingleton.getInstance().getData("spqedit_itemid");
//                SPQAddFragment spqAddFragment = new SPQAddFragment();
//                spqAddFragment.checkHasDelete(imagePickBeanList,spqedit_itemid);
//            }
//        };
//        onWifiUpLoadService = new OnWifiUpLoadService();

        if (flag == 90000) {
            /**
             * 数据下，缓存中未上传的图片
             */
//            show_image_btn.setText("使用手机数据上传");
            recyclerViewSetInfo(failUpImgsBean);
//            show_image_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    for (int i = 0; i < failUpImgsBean.size(); i++) {
//                        OnWifiUpLoadImageBean onWifiUpLoadImageBean = (OnWifiUpLoadImageBean) failUpImgsBean.get(i);
//                        ImagePickBean imagePickBean = onWifiUpLoadImageBean.getImagePickBean();
//                        String imagePathOrUrl = imagePickBean.getImagePathOrUrl();
//                        String currentTime = SettingPreferenceFragmentCompat.getCurrentTime();
//                        String time = currentTime.substring(10);
//                        imagsUP.uploadImageQueue_UploadLeftImages(imagePathOrUrl,time);
//                    }
//                    onWifiUpLoadService.uploadImage();
//                }
//            });
        } else if (flag == 90003) {
            /**
             * wifi状态下上传失败
             */
//            show_image_btn.setText("点击重试");
            recyclerViewSetInfo(failUpImgsBean);
//            show_image_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onWifiUpLoadService.uploadImage();
//                }
//            });
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
