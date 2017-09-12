package com.sunstar.cloudseeds.logic.normalrecord;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.extend.DataHolderSingleton;
import com.classichu.imageshow.bean.ImageShowBean;
import com.classichu.imageshow.helper.ImageShowDataHelper;
import com.classichu.photoselector.helper.ClassicPhotoUploaderDataHelper;
import com.classichu.photoselector.helper.ClassicSelectPhotoHelper;
import com.classichu.photoselector.imagespicker.ImagePickBean;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.imageupload.ImagePickUploadQueueManager;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.logic.normalrecord.Bean.FarmTypeListBean;
import com.sunstar.cloudseeds.logic.normalrecord.adapter.GrideAdapter;
import com.sunstar.cloudseeds.logic.normalrecord.model.RecordModel;
import com.sunstar.cloudseeds.widget.DateSelectView;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 日常记录的fragment
 *
 * Created by xiaoxian on 2017/5/10.
 */

public class NormalRecordFragment extends Fragment {
    final String DEFALUT_FARM_PLACEHOLDER = "请选择农事类型";

    private View mRootView;
    private Context context;
    private Spinner famTypeSpinner;
    private EditText recordText;
    private Button saveButton;
    private List<String> famTypeArr;
    public GrideAdapter grideAdapter;
    private String primary_id;
    private RecordModel recordModel;
    private DateSelectView dateSelectView;
    private Bundle bundle;
    private String farm;
    private ArrayList PathArr;
    private boolean saveFailed;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_record, container, false);
        initView();
        return mRootView;
    }


    private void initView() {
        recordText = (EditText) mRootView.findViewById(R.id.id_normal_record_textView);
        famTypeSpinner = (Spinner) mRootView.findViewById(R.id.id_normal_record_spinner);
        saveButton = (Button) mRootView.findViewById(R.id.id_fragement_record_save);
        dateSelectView = (DateSelectView) mRootView.findViewById(R.id.id_normal_record_dateSeletView);

        dateSelectView.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary));
        dateSelectView.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String dateString = formatter.format(curDate);
        dateSelectView.setupDateText(dateString);

        ImageView imageView = (ImageView) mRootView.findViewById(R.id.id_imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 图片选择器，选择的图片并添加到grideAdapter.dataSource里边，最多选择五张
                 */
                ClassicPhotoUploaderDataHelper.setDataAndToPhotoSelector(getActivity(), grideAdapter.dataSoruce, 5);
            }
        });
        /**
         * 保存日常记录的信息
         *
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        bundle = getActivity().getIntent().getExtras();
        primary_id = bundle.getString("primary_id");
        String edit = bundle.getString("edit");
        String time, detail;
        List<String> images;
        recordModel = new RecordModel();
        recordModel.loadFarmTypeList(new BasicCallBack<FarmTypeListBean>() {
            @Override
            public void onSuccess(FarmTypeListBean farmTypeListBean) {
                famTypeArr = recordModel.getFarmTypeList();
                ArrayList array = new ArrayList();
                for (int i = 0; i < famTypeArr.size(); i++) {
                    HashMap map = new HashMap();
                    String name = famTypeArr.get(i);
                    map.put("name", name);
                    map.put("icon", R.drawable.ic_expand_more_black_24dp);
                    array.add(map);
                }

                // 设置农事选择框
                SimpleAdapter simpleAdapter = new SimpleAdapter(context, array, R.layout.spinner_item,
                        new String[]{"name", "icon"}, new int[]{R.id.item_title, R.id.id_spinner_image}) {
                    @Override
                    public int getCount() {
                        int count = super.getCount();
                        return count > 0 ? count - 1 : count;
                    }
                };

                famTypeSpinner.setAdapter(simpleAdapter);
                famTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {
                        Log.v("famTypeArrType", famTypeArr.get(pos));
                        recordModel.farmType = famTypeArr.get(pos);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                famTypeSpinner.setSelection(famTypeArr.size() - 1, true);
                farm = bundle.getString("farm");
                if (farm != null && !farm.equals("")) {
                    for (int i = 0; i < famTypeArr.size(); i++) {
                        String farmi = famTypeArr.get(i);
                        if (farm.equals(farmi)) {
                            famTypeSpinner.setSelection(i, false);
                        }
                    }
                }
            }

            @Override
            public void onError(String s) {

            }
        });

        grideAdapter = new GrideAdapter(context, null, new GrideAdapter.OnRecyclerItemClickLister() {
            @Override
            public void onItemClick(View view, int positon) {
                //图片已加载
                if (positon > 100) {
                    ArrayList<ImageShowBean> imagesArray = new ArrayList();
                    /**
                     * 图片的信息
                     */
                    ImageShowBean showBean = new ImageShowBean();
                    //设置图片的路径和URL
                    showBean.setImageUrl(grideAdapter.dataSoruce.get(positon - 101).getImagePathOrUrl());
                    //将图片的信息添加到ArrayList集合
                    imagesArray.add(showBean);
                    ImageShowDataHelper.setDataAndToImageShow(context, imagesArray, 0, false);
                } else {
                    ClassicPhotoUploaderDataHelper.setDataAndToPhotoSelector(getActivity(), null, 5 - (positon));
                }

                Log.d("itemClick", "" + positon + ":" + view);
            }
        });
        //recyclerView.setAdapter(grideAdapter);

        //编辑模式进来
        if (edit != null) {
            if (edit.equals("1")) {
                time = bundle.getString("time");
                detail = bundle.getString("detail");
                images = bundle.getStringArrayList("images");
                dateSelectView.setupDateText(time);
                recordText.setText(detail);
                if (images != null && images.size() > 0) {
                    for (int i = 0; i < images.size(); i++) {

                        String imagesUrl = images.get(i);
                        ImagePickBean pickbean = new ImagePickBean();
                        pickbean.setImagePathOrUrl(imagesUrl);
                        grideAdapter.dataSoruce.add(pickbean);
                    }
                    grideAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    //保存数据
    private void save() {
        if (!checkInformaton()) {
            return;
        }

        bundle = getActivity().getIntent().getExtras();
        String edit = bundle.getString("edit");
        if (edit != null) {
            uploadNormalRecord("");
        } else {
            if (grideAdapter.dataSoruce.size() > 0) {
                uploadImages();
            } else {
                uploadNormalRecord("");
            }
        }

    }


    //检查输入信息
    private boolean checkInformaton() {
        boolean success = true;
        if (recordModel.farmType == null) {
            return false;
        }

        if (recordModel.farmType.isEmpty() || recordModel.farmType.equals(DEFALUT_FARM_PLACEHOLDER)) {
            showMessageDiolg("请选择农事类型");
            return false;
        }


        if (recordText.getText().toString().isEmpty() && recordText.getText().toString().equals("")) {
            showMessageDiolg("请输入农事记录详情");
            return false;
        }

        if (dateSelectView.getNowSelectData().toString().equals("1970-01-01")) {
            showMessageDiolg("请选择农事记录时间");
            return false;
        }

        return success;
    }


    //提示信息
    private void showMessageDiolg(String message) {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context);
        normalDialog.setMessage(message);
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        normalDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        normalDialog.setCancelable(false);
        // 显示
        normalDialog.show();
    }

    //上传图片
    private void uploadImages() {
        DataHolderSingleton.getInstance().putData("spqedit_resultid", "0");
        DataHolderSingleton.getInstance().putData("spqedit_itemid", "0");
        DataHolderSingleton.getInstance().putData("spqedit_plant_number", "0");
        String spqedit_companyid = UserLoginHelper.userLoginBean().getCompany();
        DataHolderSingleton.getInstance().putData("spqedit_companyid", spqedit_companyid);
        ImagePickUploadQueueManager imagePickUploadQueueManager
                = new ImagePickUploadQueueManager("noramlrecord", "",
                grideAdapter.dataSoruce, getFragmentManager(), "日常记录" + "图片上传", 0, false,
                UrlDatas.URL_NORMALERECORD_IMAGE_UPLOAD) {
            @Override
            protected void uploadImageQueue_Complete(String thePreviousData,
                                                     List<ImagePickBean> imgList, ArrayList jasonArr) {
                if (!saveFailed) {
                    PathArr = jasonArr;
                }
                //多张图片拼接字符串
                String jasonAppend = "";
                if (saveFailed) jasonArr = PathArr;
                if (jasonArr != null) {
                    if (jasonArr.size() > 0) {
                        for (int i = 0; i < jasonArr.size(); i++) {
                            String jason = (String) jasonArr.get(i);
                            jason = jason.substring(35, jason.length() - 2);
                            jason = jason + ",";
                            jasonAppend += jason;
                        }
                        jasonAppend = "[" + jasonAppend.substring(0, jasonAppend.length() - 1) + "]";
                    }
                    jasonAppend = new String(Base64.encode(jasonAppend.getBytes
                            (Charset.forName("UTF-8")), Base64.DEFAULT), Charset.forName("UTF-8"));
                }
                uploadNormalRecord(jasonAppend);
            }
        };
        imagePickUploadQueueManager.uploadImageQueue_Start();
    }


    //上传日常数据
    private void uploadNormalRecord(String image) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("保存中....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        final Handler mHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(context, "保存成功", Toast.LENGTH_LONG).show();
                super.handleMessage(msg);
//               Intent intent = new Intent();
//               intent.setClass(getActivity(),YZTZActivity.class);
//               intent.putExtras(bundle);
//               startActivity(intent);
                getActivity().finish();
            }
        };

        final Handler errorHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(context, "保存失败", Toast.LENGTH_LONG).show();
                super.handleMessage(msg);
            }
        };

        Log.v("normalrecordinfomation", "primary_id:" + primary_id + "farmType:" + recordModel.farmType +
                "detail:" + recordText.getText().toString() + "time:" + dateSelectView.getNowSelectData());
        recordModel.uploadNormalRecord(primary_id, recordModel.farmType, recordText.getText().toString(),
                dateSelectView.getNowSelectData(), new BasicCallBack<FarmTypeListBean>() {
                    @Override
                    public void onSuccess(FarmTypeListBean farmTypeListBean) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (Exception e) {

                                } finally {
                                    progressDialog.dismiss();
                                    mHandle.sendEmptyMessage(0);

                                }
                            }
                        }).start();

                    }

                    @Override
                    public void onError(String s) {
                        saveFailed = true;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (Exception e) {

                                } finally {
                                    progressDialog.dismiss();
                                    errorHandle.sendEmptyMessage(0);
                                }
                            }
                        }).start();
                    }

                }, image);
    }


    @Override //处理照片选取回调
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        ClassicSelectPhotoHelper.callAtOnActivityResult(getActivity(), requestCode, resultCode,
                data, new ClassicSelectPhotoHelper.OnBackImageListener() {
                    @Override
                    public void onBackImagePath(String imagePath) {
                        super.onBackImagePath(imagePath);
                        Toast.makeText(getActivity(), "onBackImagePath imagePath:" + imagePath, Toast.LENGTH_SHORT).show();
                    }

                    @Override //裁剪成功回调
                    public void onUCropBackImagePath(String imagePath) {
                        super.onUCropBackImagePath(imagePath);

                        Toast.makeText(getActivity(), "onUCropBackImagePath imagePath:" + imagePath, Toast.LENGTH_SHORT).show();
                    }

                    @Override //裁剪出错回调
                    public void onUCropBackError(Throwable cropError) {
                        super.onUCropBackError(cropError);
                        Toast.makeText(getActivity(), "onUCropBackError", Toast.LENGTH_SHORT).show();
                    }
                });

        /**
         * 图片选择结果的回调
         *
         */
        ClassicPhotoUploaderDataHelper.callAtOnActivityResult(requestCode, resultCode, data,
                new ClassicPhotoUploaderDataHelper.PhotoSelectorBackData() {
            @Override
            public void backData(List<ImagePickBean> imagePickBeanList) {
                Log.v("realcount", grideAdapter.dataSoruce.size() + "");
                grideAdapter.dataSoruce.addAll(grideAdapter.dataSoruce.size(), imagePickBeanList);
                grideAdapter.addItem();
                Log.d("imageData", "" + imagePickBeanList);
            }
        });

    }
}
