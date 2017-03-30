package com.sunstar.cloudseeds.logic.shangpinqi.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.classichu.itemselector.bean.ItemSelectBean;
import com.classichu.itemselector.helper.ClassicItemSelectorDataHelper;
import com.classichu.photoselector.helper.ClassicPhotoUploaderDataHelper;
import com.classichu.photoselector.imagespicker.ImagePickBean;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.bean.InfoBean;
import com.sunstar.cloudseeds.data.CommDatas;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.EditItemRuleHelper;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.imageupload.ImagePickUploadQueueManager;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.SPQDetailBean;
import com.sunstar.cloudseeds.logic.shangpinqi.contract.SPQDetailContract;
import com.sunstar.cloudseeds.logic.shangpinqi.event.SPQDetailRefreshEvent;
import com.sunstar.cloudseeds.logic.shangpinqi.presenter.SPQDetailPresenterImpl;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    TableLayout id_tl_item_container;
    Button id_btn_submit;

    @Override
    protected void initView(View view) {
        id_btn_submit = findById(R.id.id_btn_submit);
        id_tl_item_container = findById(R.id.id_tl_item_container);
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

    private void submitData() {

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


                        uploadImages(imagePickBeanList);

                    }


                });
    }

    private void uploadImages(List<ImagePickBean> imagePickBeanList) {
        ImagePickUploadQueueManager imagePickUploadQueueManager
                = new ImagePickUploadQueueManager("DASDAS", "",
                imagePickBeanList, getChildFragmentManager(), "rewrwe", 0, false) {
            @Override
            protected void uploadImageQueue_Complete(String thePreviousData, List<ImagePickBean> imgList) {
                CLog.d("thePreviousData:" + thePreviousData);
                // CLog.d("webIDS:"+webIDS);

                checkHasDelete(imgList);

                //// FIXME: 2017/3/30
             /*   //刷新上页
                EventBus.getDefault().post(new SPQDetailRefreshEvent());
                //刷新本页
                toRefreshData();*/
            }
        };
        imagePickUploadQueueManager.uploadImageQueue_Start();
    }

    private void checkHasDelete(List<ImagePickBean> imgNewList) {
        StringBuilder stringBuilder = new StringBuilder();
        List<ImagePickBean> test_raw_imagePickBeanList =
                (List<ImagePickBean>) DataHolderSingleton.getInstance().getData("test_raw_imagePickBeanList");
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
                HashMap holad_path_code_map= (HashMap) DataHolderSingleton.getInstance().getData("holad_path_code_map");
                String imgCode= (String) holad_path_code_map.get(test_raw_imagePickBeanList.get(i).getImagePathOrUrl());
                //
                stringBuilder.append(imgCode);
                stringBuilder.append(",");
            }
        }

        String ps = stringBuilder.toString();
        if (!ps.equals("")) {
            ps = ps.substring(0, ps.length() - 1);
        }
        CLog.d("ps:" + ps);

        if (!ps.equals("")) {
            toDoDeleteImags(ps);
        }
    }

    private void toDoDeleteImags(String codeid) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("id", mNowTertiary_id);
        paramsMap.put("itemid", (String) DataHolderSingleton.getInstance().getData("spqedit_itemid"));
        paramsMap.put("codeid", codeid);
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(UrlDatas.URL_DELETE_IMAGE, HeadsParamsHelper.setupDefaultHeaders()
                , paramsMap, new GsonHttpRequestCallback<BasicBean>() {
                    @Override
                    public BasicBean OnSuccess(String s) {
                        CLog.d("S:SSSSSSSSS:"+s);
                        return BasicBean.fromJson(s, BasicBean.class);
                    }

                    @Override
                    public void OnSuccessOnUI(final BasicBean basicBean) {
                      ThreadTool.runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              ToastTool.showShort(basicBean.getMessage());
                          }
                      });
                    }

                    @Override
                    public void OnError(String s) {
                        CLog.e(s);
                    }
                }
        );
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
        //
        EditItemRuleHelper.generateSPQChildView(getActivity(), id_tl_item_container, kvbList);
    }

    @Override
    public void setupMoreData(SPQDetailBean spqDetailBean) {

    }

    @Override
    public String setupGainDataTertiaryId() {
        return mNowTertiary_id;
    }


}
