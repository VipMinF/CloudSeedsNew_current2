package com.sunstar.cloudseeds.logic.shangpinqi.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;


import com.classichu.classichu.app.CLog;
import com.classichu.classichu.basic.extend.DataHolderSingleton;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.classichu.classichu.basic.tool.ThreadTool;
import com.classichu.classichu.basic.tool.ToastTool;
import com.classichu.classichu.classic.ClassicMvpFragment;
import com.classichu.imageshow.bean.ImageShowBean;
import com.classichu.itemselector.bean.ItemSelectBean;
import com.classichu.itemselector.helper.ClassicItemSelectorDataHelper;
import com.classichu.photoselector.helper.ClassicPhotoUploaderDataHelper;
import com.classichu.photoselector.imagespicker.ImagePickBean;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.bean.ImageCommBean;
import com.sunstar.cloudseeds.bean.ImageUploadCommBean;
import com.sunstar.cloudseeds.bean.InfoBean;
import com.sunstar.cloudseeds.bean.KeyAndValueBean;
import com.sunstar.cloudseeds.cache.ACache;
import com.sunstar.cloudseeds.data.CommDatas;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.EditItemRuleHelper;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.imageupload.ImagePickUploadQueueManager;
import com.sunstar.cloudseeds.logic.imageupload.bean.DeleteImageBackBean;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.OnWifiUpLoadImageBean;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.SPQDetailBean;
import com.sunstar.cloudseeds.logic.shangpinqi.contract.SPQDetailContract;
import com.sunstar.cloudseeds.logic.shangpinqi.event.SPQDetailRefreshEvent;
import com.sunstar.cloudseeds.logic.shangpinqi.presenter.SPQDetailPresenterImpl;
import com.sunstar.cloudseeds.service.OnWifiUpLoadService;

import android.view.ViewTreeObserver;
import android.graphics.Rect;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SPQAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SPQAddFragment extends ClassicMvpFragment<SPQDetailPresenterImpl> implements SPQDetailContract.View<SPQDetailBean> {
    public SPQAddFragment() {
        // Required empty public constructor
    }

    private String mNowTertiary_id;
    private LinearLayout rootView;
    public  String beforeResult;

    @Override
    protected SPQDetailPresenterImpl setupPresenter() {
        return new SPQDetailPresenterImpl(this);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SPQAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SPQAddFragment newInstance(String param1, String param2) {
        SPQAddFragment fragment = new SPQAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mNowTertiary_id = mParam1;
    }


    @Override
    protected int setupLayoutResId() {
        return R.layout.fragment_spq_add;
    }

   public TableLayout id_tl_item_container;
    Button id_btn_submit;

    @Override
    protected void initView(View view) {
        id_btn_submit = findById(R.id.id_btn_submit);
        id_tl_item_container = findById(R.id.id_tl_item_container);
        rootView = findById(R.id.id_sqp_add_rootView) ;

        toRefreshData();
    }



    @Override
    protected void initListener() {
        setOnNotFastClickListener(id_btn_submit, new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View view) {
                submitData();

            }
        });
    }

    public void submitData() {

        String result = EditItemRuleHelper.generateViewBackString(id_tl_item_container);
        //CLog.d("zzqqff:" + result);
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("id", mNowTertiary_id);
        stringMap.put("itemvalue", result);
        //
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(UrlDatas.TERTIARY_SAVE,
                HeadsParamsHelper.setupDefaultHeaders(), stringMap,
                new GsonHttpRequestCallback<BasicBean<InfoBean>>() {

                    @Override
                    public BasicBean<InfoBean> OnSuccess(String s) {
                        return BasicBean.fromJson(s, InfoBean.class);
                    }

                    @Override
                    public void OnSuccessOnUI(BasicBean<InfoBean> basicBean) {
                        if (basicBean == null) {
                            showMessage(CommDatas.SERVER_ERROR);
                            return;
                        }
                        if (CommDatas.SUCCESS_FLAG.equals(basicBean.getCode())) {
                            if (basicBean.getInfo() != null && basicBean.getInfo().size() > 0) {
                                //
                                ToastTool.showShort(basicBean.getInfo().get(0).getShow_msg());
                                //
                                EventBus.getDefault().post(new SPQDetailRefreshEvent());
                                //
                                getActivity().onBackPressed();

                            } else {
                                showMessage(basicBean.getMessage());
                            }
                        } else {
                            showMessage(basicBean.getMessage());
                        }
                    }

                    @Override
                    public void OnError(String s) {
                        showMessage(s);
                    }
                }

        );
    }

    @Override
    protected void toRefreshData() {
        super.toRefreshData();
        mPresenter.gainData(UrlDatas.TERTIARY_EDIT);
        beforeResult = EditItemRuleHelper.generateViewBackString(id_tl_item_container);
    }

    @Override
    protected int configSwipeRefreshLayoutResId() {
        return R.id.id_swipe_refresh_layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CLog.d("zhufq:" + requestCode);
        ClassicItemSelectorDataHelper.callAtOnActivityResult(requestCode, resultCode, data,
                new ClassicItemSelectorDataHelper.ItemSelectBackData() {
                    @Override
                    public void backData(View view, List<ItemSelectBean> list) {

                        StringBuilder sb = new StringBuilder();
                        for (ItemSelectBean isb : list
                                ) {
                            if (isb.isSelected()) {
                                sb.append(isb.getItemTitle());
                            }
                        }
                        TextView tv = (TextView) view;
                        tv.setText(sb.toString());
                    }
                });

        //图片选择返回
        ClassicPhotoUploaderDataHelper.callAtOnActivityResult(requestCode, resultCode, data,
                new ClassicPhotoUploaderDataHelper.PhotoSelectorBackData() {

                    @Override
                    public void backData(List<ImagePickBean> imagePickBeanList) {

                        Log.d("DSAD", "backData: " + imagePickBeanList);
                        OnWifiUpLoadService.NetWorkStatesMonitor netWorkStatesMonitor = new OnWifiUpLoadService().getNetWorkMonitor(getContext());
                        //4g下
                        if (netWorkStatesMonitor.isMobileAndAviable()) {
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("onwifiupload", Context.MODE_PRIVATE);
                            boolean isTrue = sharedPreferences.getBoolean("onwifiupload",false);
                            if (isTrue) {
                                //是新增还是删除了
                                ArrayList<ImagePickBean> imagesArr = new ArrayList();//用来储存新增照片
                                String item_id = (String) DataHolderSingleton.getInstance().getData("spqedit_itemid");
                                ArrayList<ImagePickBean>  imagePickBeanListSec = new ArrayList( (List<ImagePickBean>)
                                        DataHolderSingleton.getInstance().getData(item_id+"test_raw_imagePickBeanList"));
                                ACache aCache = ACache.get(getContext());
                                ArrayList imageCacheArr = (ArrayList) aCache.getAsObject("imageCache");

                                //全部删除了
                                if(imagePickBeanList.size() == 0) {
                                    imagesArr.removeAll(imagesArr);
                                    //缓存有，则删除缓存
                                    synchronized (this) {
                                        for (int i = 0; i <imagePickBeanListSec.size() ; i++) {
                                            ImagePickBean imagePickBean = imagePickBeanListSec.get(i);
                                            for (int j = 0; j <imageCacheArr.size() ; j++) {
                                                OnWifiUpLoadImageBean onWifiUpLoadImageBean = (OnWifiUpLoadImageBean) imageCacheArr.get(j);
                                                ImagePickBean imagePickBeanSec = onWifiUpLoadImageBean.getImagePickBean();
                                                if (imagePickBean != null && imagePickBeanSec != null) {
                                                    if (imagePickBean.getImagePathOrUrl().equals(imagePickBeanSec.getImagePathOrUrl())) {
                                                        imageCacheArr.remove(onWifiUpLoadImageBean);
                                                        File file = new File(imagePickBeanSec.getImagePathOrUrl());
                                                        if(file.exists()) {
                                                            file.delete();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        aCache.put("imageCache",imageCacheArr);
                                        //删除线上的
                                        checkHasDelete(new ArrayList<ImagePickBean>(),item_id);
                                    }
                                }

                                //全部是新增的
                                if(imagePickBeanListSec.size() == 0) {
                                    imagesArr.addAll(imagePickBeanList);
                                    //可能部分新增部分删除
                                }else {
                                    imagesArr.removeAll(imagesArr);
                                    imagesArr.addAll(imagePickBeanList);
                                    ArrayList<ImagePickBean>deleteImagesArr = new ArrayList<ImagePickBean>(imagePickBeanListSec);
                                    //过滤删除的
                                    for (int i = 0; i <imagePickBeanList.size() ; i++) {
                                        ImagePickBean imagePickBean = imagePickBeanList.get(i);
                                        for (int j = 0; j < imagePickBeanListSec.size(); j++) {
                                            ImagePickBean imagePickBeanSec = imagePickBeanListSec.get(j);
                                            if (imagePickBean.getImagePathOrUrl().equals(imagePickBeanSec.getImagePathOrUrl())) {
                                                deleteImagesArr.remove(imagePickBeanSec);
                                            }
                                        }
                                    }
                                    //处理删除的图片
                                    synchronized (this) {
                                        if (deleteImagesArr.size() > 0) {
                                            for (int i = 0; i <deleteImagesArr.size() ; i++) {
                                                ImagePickBean imagePickBean = deleteImagesArr.get(i);
                                                for (int j = 0; j <imageCacheArr.size() ; j++) {
                                                    OnWifiUpLoadImageBean onWifiUpLoadImageBean = (OnWifiUpLoadImageBean) imageCacheArr.get(j);
                                                    ImagePickBean imagePickBeanSec = onWifiUpLoadImageBean.getImagePickBean();
                                                    if (imagePickBean != null && imagePickBeanSec != null) {
                                                        if (imagePickBean.getImagePathOrUrl().equals(imagePickBeanSec.getImagePathOrUrl())) {
                                                            File file = new File(imagePickBeanSec.getImagePathOrUrl());
                                                            if(file.exists()) {
                                                                file.delete();
                                                            }
                                                            imageCacheArr.remove(onWifiUpLoadImageBean);
                                                        }
                                                    }
                                                }
                                            }
                                            aCache.put("imageCache",imageCacheArr);
                                            //删除线上的
                                            checkHasDelete(imagePickBeanList,item_id);
                                        }
                                    }

                                        deleteImagesArr.removeAll(deleteImagesArr);

                                    //过滤新增
                                    for (int i = 0; i <imagePickBeanListSec.size() ; i++) {
                                        ImagePickBean imagePickBean = imagePickBeanListSec.get(i);
                                        for (int j = 0; j <imagePickBeanList.size() ; j++) {
                                            ImagePickBean imagePickBeanSec = imagePickBeanList.get(j);
                                            if (imagePickBeanSec.getImagePathOrUrl().equals(imagePickBean.getImagePathOrUrl())) {
                                                imagesArr.remove(imagePickBeanSec);
                                            }
                                        }
                                    }
                                }

                                //added by zy 2017.6.13 缓存商品期图片
                                if (imageCacheArr != null ) {
                                    if (imagesArr.size() > 0) {
                                        //拷贝图片
                                        for (int i = 0; i <imagesArr.size() ; i++) {
                                            ImagePickBean bean = imagesArr.get(i);
                                            if(bean.getImageWebIdStr() == null || bean.getImageWebIdStr().equals("")) {
                                                String copyImagePath = copyImage(bean);
                                                if (copyImagePath != null ) {
                                                    bean.setImagePathOrUrl(copyImagePath);
                                                }
                                                OnWifiUpLoadImageBean imageBean = new OnWifiUpLoadImageBean();
                                                imageBean.setItemid(item_id);
                                                imageBean.setCompanyid(UserLoginHelper.userLoginBean().getCompany());
                                                imageBean.setResultid((String) DataHolderSingleton.getInstance().getData("spqedit_resultid"));
                                                imageBean.setPlant_number((String) DataHolderSingleton.getInstance().getData("spqedit_plant_number"));
                                                imageBean.setUserID(0);
                                                imageBean.setImagePickBean(bean);
                                                imageBean.setUploadUrl(UrlDatas.URL_UPLOAD_IMAGES);
                                                imageCacheArr.add(imageBean);
                                            }

                                        }
                                    }
                                    synchronized (this) {
                                        aCache.put("imageCache",imageCacheArr);
                                    }
                                    //保存数据
                                    DataHolderSingleton.getInstance().putData(item_id+"test_raw_imagePickBeanList",imagePickBeanList);
                                }
                                //只要有网络就上传
                            }else {
                                uploadImages(imagePickBeanList);
                            }
                            //wifi下
                        }else if (netWorkStatesMonitor.isWifiAndAvaiable()){
                            uploadImages(imagePickBeanList);
                        }
                    }
                });
    }

    /**
     *拷贝图片
     * @param bean
     * @return
     */
    private String copyImage(ImagePickBean bean) {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File oldfile = new File(bean.getImagePathOrUrl());
            File sdCard = Environment.getExternalStorageDirectory();
            String newPath = sdCard.getAbsolutePath() +File.separator+"wifiuplaodimagetemp"+File.separator+oldfile.getName();
            File newFile = new File(newPath);
            if(!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }
            if(!oldfile.isFile()) return null;
            try {
                InputStream inputStream = new FileInputStream(oldfile);
                FileOutputStream outputStream = new FileOutputStream(newFile);
                byte[] buf = new  byte[1024];
                int byteRead ;
                while ((byteRead = inputStream.read(buf)) != -1) {
                   outputStream.write(buf, 0, byteRead);
                }
                outputStream.close();
                inputStream.close();
                return newPath;
            }catch (Exception e){
                return  null;
            }
        }
        return null;
    }


    private void uploadImages(List<ImagePickBean> imagePickBeanList) {
        String now_leftTitleStr = (String) DataHolderSingleton.getInstance().getData("now_leftTitleStr");

        ImagePickUploadQueueManager imagePickUploadQueueManager
                = new ImagePickUploadQueueManager("now_leftTitleStr", "",
                imagePickBeanList, getChildFragmentManager(), now_leftTitleStr + "图片上传", 0, false) {
            @Override
            protected void uploadImageQueue_Complete(String thePreviousData, List<ImagePickBean> imgList,ArrayList jasonArr) {
                CLog.d("thePreviousData:" + thePreviousData);
                // CLog.d("webIDS:"+webIDS);
                String spqedit_itemid = (String) DataHolderSingleton.getInstance().getData("spqedit_itemid");
                checkHasDelete(imgList, spqedit_itemid);
            }
        };
        imagePickUploadQueueManager.uploadImageQueue_Start();
    }

    private void checkHasDelete(List<ImagePickBean> imgNewList, String itemid) {
        StringBuilder stringBuilder = new StringBuilder();
        List<ImagePickBean> test_raw_imagePickBeanList =
                (List<ImagePickBean>) DataHolderSingleton.getInstance().getData(itemid + "test_raw_imagePickBeanList");
        for (int i = 0; i < test_raw_imagePickBeanList.size(); i++) {
            //
            boolean hasIt = false;
            for (ImagePickBean imagePickBean : imgNewList) {
                if (imagePickBean.getImagePathOrUrl().equals(
                        test_raw_imagePickBeanList.get(i).getImagePathOrUrl())) {
                    hasIt = true;
                    break;
                }
            }
            if (!hasIt) {
                HashMap holad_path_code_map = (HashMap) DataHolderSingleton.getInstance().getData(itemid+"holad_path_code_map");
                String imgCode = (String) holad_path_code_map.get(test_raw_imagePickBeanList.get(i).getImagePathOrUrl());
                //
                if (imgCode != null) {
                    stringBuilder.append(imgCode);
                    stringBuilder.append(",");
                }
            }
        }

        String ps = stringBuilder.toString();
        if (!ps.equals("")) {
            ps = ps.substring(0, ps.length() - 1);
        }
        CLog.d("ps:" + ps);

        if (!ps.equals("")) {
            toDoDeleteImags(ps, imgNewList, itemid);
        } else {
            //刷新上页
            EventBus.getDefault().post(new SPQDetailRefreshEvent());
            toRefreshNowImageListDatas(imgNewList, itemid);
        }
    }

    private void toDoDeleteImags(String codeid, final List<ImagePickBean> imagePickBeanList, final String itemid) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("id",mNowTertiary_id);
        paramsMap.put("itemid", (String) DataHolderSingleton.getInstance().getData("spqedit_itemid"));
        paramsMap.put("codeid", codeid);
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(UrlDatas.URL_DELETE_IMAGE, HeadsParamsHelper.setupDefaultHeaders()
                , paramsMap, new GsonHttpRequestCallback<BasicBean<DeleteImageBackBean>>() {
                    @Override
                    public BasicBean<DeleteImageBackBean> OnSuccess(String s) {
                        CLog.d("S:SSSSSSSSS:" + s);
                        return BasicBean.fromJson(s, DeleteImageBackBean.class);
                    }

                    @Override
                    public void OnSuccessOnUI(final BasicBean<DeleteImageBackBean> basicBean) {
                        if (basicBean == null) {
                            showMessage(CommDatas.SERVER_ERROR);
                            return;
                        }
                        if (CommDatas.SUCCESS_FLAG.equals(basicBean.getCode())) {
                            if (basicBean.getInfo() != null && basicBean.getInfo().size() > 0) {

                                //
                                toRefreshNowImageListDatas(imagePickBeanList, itemid);

                                //
                                ThreadTool.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastTool.showShort(basicBean.getMessage());
                                    }
                                });

                            } else {
                                OnError(basicBean.getMessage());
                            }
                        } else {
                            OnError(basicBean.getMessage());
                        }
                    }

                    @Override
                    public void OnError(final String s) {
                        CLog.e(s);
                        ThreadTool.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastTool.showShort(s);
                            }
                        });
                    }
                }
        );
    }

    private void toRefreshNowImageListDatas(List<ImagePickBean> imagePickBeanList, String itemid) {
        //刷新上页
        EventBus.getDefault().post(new SPQDetailRefreshEvent());
        mPresenter.updateImageCode(UrlDatas.TERTIARY_EDIT, new SPQDetailPresenterImpl.UpdateImageCode<SPQDetailBean>() {
            @Override
            public void updateImageCode(SPQDetailBean spqDetailBean) {
                List<SPQDetailBean.KeyValueBean> kvbList = spqDetailBean.getKey_value();
                for (int i = 0; i < kvbList.size(); i++) {
                    List<SPQDetailBean.KeyValueBean.ImagesBean> images = kvbList.get(i).getImages();
                    String rightCode = kvbList.get(i).getCode();
                    List<ImageCommBean> imageCommmBeanList = new ArrayList<>();
                    if (images != null && images.size() > 0) {
                        for (SPQDetailBean.KeyValueBean.ImagesBean image : images) {
                            ImageCommBean bean = new ImageCommBean();
                            bean.setImg_title("dadsa");
                            bean.setSmall_img_url(image.getWsmallImage250_250Name());
                            bean.setImg_url(image.getBigImageName());
                            bean.setImg_CODE(image.getCode());
                            imageCommmBeanList.add(bean);
                        }
                    }
                    final List<ImageShowBean> imageShowBeanList = new ArrayList<>();
                    Map<String, String> path_code_map = new HashMap<>();
                    if (imageCommmBeanList != null && imageCommmBeanList.size() > 0) {
                        for (int j = 0; j < imageCommmBeanList.size(); j++) {
                            ImageShowBean bean = new ImageShowBean();
                            bean.setTitle(imageCommmBeanList.get(j).getImg_title());
                            bean.setImageUrl(imageCommmBeanList.get(j).getImg_url().replace("\\", "/"));
                            imageShowBeanList.add(bean);
                            //
                            path_code_map.put(bean.getImageUrl(), imageCommmBeanList.get(j).getImg_CODE());
                        }
                        DataHolderSingleton.getInstance().putData(rightCode+"holad_path_code_map", path_code_map);
                    }
                }
            }
        });



        List<ImagePickBean> imagePickBeanListClone = new ArrayList<>(imagePickBeanList);
        imagePickBeanList.clear();
        for (int i = 0; i <imagePickBeanListClone.size() ; i++) {
            String newUrl = imagePickBeanListClone.get(i).getImagePathOrUrl().replace("\\","/");
            ImagePickBean ipb=imagePickBeanListClone.get(i);
            ipb.setImagePathOrUrl(newUrl);
            ipb.setImageWebIdStr(newUrl);
            imagePickBeanList.add(ipb);
        }

        //本地缓存的图片
//        ArrayList tempUploadArr = new ArrayList();
//        synchronized (this) {
//            ACache aCache = ACache.get(getContext());
//            ArrayList imageArr = (ArrayList) aCache.getAsObject("imageCache");
//            for (int i = 0; i <imageArr.size() ; i++) {
//                OnWifiUpLoadImageBean bean = (OnWifiUpLoadImageBean) imageArr.get(i);
//                if (itemid != null && bean.getItemid().equals(itemid) && bean.getImagePickBean() != null) {
//                    ImagePickBean imagePickBean = bean.getImagePickBean();
//                    imagePickBean.setImageWebIdStr(imagePickBean.getImagePathOrUrl());
//                    tempUploadArr.add(imagePickBean);
//                }
//            }
//        }
//        imagePickBeanList.addAll(tempUploadArr);
        DataHolderSingleton.getInstance().putData(itemid + "test_raw_imagePickBeanList", imagePickBeanList);

    }


    @Override
    public void showProgress() {
        showSwipeRefreshLayout();
    }

    @Override
    public void hideProgress() {
        hideSwipeRefreshLayout();
    }

    @Override
    public void showMessage(final String s) {
        ThreadTool.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastTool.showShort(s);
            }
        });
    }

    @Override
    public void setupData(SPQDetailBean spqDetailBean) {
        //
        List<SPQDetailBean.KeyValueBean> kvbList = spqDetailBean.getKey_value();
        //开始自动生成界面
        EditItemRuleHelper.generateSPQChildView(getActivity(), id_tl_item_container, kvbList,true);
        //List<SPQDetailBean.KeyValueBean> kvbList = spqDetailBean.getKey_value();

        String spqedit_resultid = spqDetailBean.getRule_id();
        DataHolderSingleton.getInstance().putData("spqedit_resultid",spqedit_resultid);
        String spqedit_plant_number = spqDetailBean.getPlant_number();
        DataHolderSingleton.getInstance().putData("spqedit_plant_number",spqedit_plant_number);
        String spqedit_companyid = UserLoginHelper.userLoginBean().getCompany();
        DataHolderSingleton.getInstance().putData("spqedit_companyid",spqedit_companyid);


        beforeResult = EditItemRuleHelper.generateViewBackString(id_tl_item_container);
    }

    @Override
    public void setupMoreData(SPQDetailBean spqDetailBean) {

    }

    @Override
    public String setupGainDataTertiaryId() {
        return mNowTertiary_id;
    }

    @Override
    public void onResume() {
        super.onResume();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                id_btn_submit.setVisibility(View.VISIBLE);
            }
        };
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = rootView.getRootView().getHeight();
                if(screenHeight != r.bottom) {
                    id_btn_submit.setVisibility(View.GONE);
                }else  {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sleep(80);
                            handler.sendEmptyMessage(1);
                        }catch (Exception e) {

                        }
                    }
                }).start();
                }

            }
        });
    }

}
