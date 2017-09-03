package com.sunstar.cloudseeds.logic.helper;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.classichu.classichu.basic.extend.DataHolderSingleton;
import com.classichu.classichu.basic.helper.VectorOrImageResHelper;
import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.classichu.classichu.basic.tool.SizeTool;
import com.classichu.dateselectview.widget.DateSelectView;
import com.classichu.imageshow.bean.ImageShowBean;
import com.classichu.imageshow.helper.ImageShowDataHelper;
import com.classichu.itemselector.bean.ItemSelectBean;
import com.classichu.itemselector.helper.ClassicItemSelectorDataHelper;
import com.classichu.lineseditview.LinesEditView;
import com.classichu.photoselector.helper.ClassicPhotoUploaderDataHelper;
import com.classichu.photoselector.imagespicker.ImagePickBean;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.Util.DensityUtil;
import com.sunstar.cloudseeds.bean.ImageCommBean;
import com.sunstar.cloudseeds.bean.ImageUploadCommBean;
import com.sunstar.cloudseeds.bean.KeyAndValueBean;
import com.sunstar.cloudseeds.cache.ACache;
import com.sunstar.cloudseeds.logic.shangpinqi.SPQActivity;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.OnWifiUpLoadImageBean;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.SPQDetailBean;
import com.sunstar.cloudseeds.logic.shangpinqi.ui.SPQAddFragment;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZDetailBean;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2017/3/16.
 */

public class EditItemRuleHelper {
    private static final String ITEM_TYPE_TEXT = "text";
    private static final String ITEM_TYPE_EDIT = "edit";
    private static final String ITEM_TYPE_LINES = "lines";
    private static final String ITEM_TYPE_TIME = "time";
    private static final String ITEM_TYPE_SELECT = "select";
    private static final String ITEM_TYPE_INTEGER = "integer";
    private static final String ITEM_TYPE_DOUBLE = "double";


    public static String generateViewBackString(TableLayout tableLayout) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            View view = tableRow.getChildAt(1);//第二个
            TextView ss = (TextView) tableRow.getChildAt(0);//第二个
            String key = (String) view.getTag(R.id.hold_view_key);
            String code = "";
            if (view.getTag(R.id.hold_view_code) != null) {
                code = (String) view.getTag(R.id.hold_view_code);
            }
            String value = "";
            if (view instanceof DateSelectView) {
                DateSelectView dsv = (DateSelectView) view;
                value = dsv.getNowSelectData();
                if (value.equals("1970-01-01")){
                    value = "";
                }
            } else if (view instanceof LinesEditView) {
                LinesEditView lev = (LinesEditView) view;
                value = lev.getContentText();
            } else if (view instanceof EditText) {
                EditText et = (EditText) view;
                value = et.getText().toString();
            } else if (view instanceof TextView) {
                TextView tv = (TextView) view;
                value = tv.getText().toString();
            }
            if (view.getTag(R.id.hold_view_input_type_text) != null &&
                    "true".equals(view.getTag(R.id.hold_view_input_type_text))) {//
                //do nothing
                Log.d("QQWQ", "generateViewBackString: ");
            } else {
                Log.d("QQWQTTTTT", "SASA: ");
                sb.append(value.equals("")?"empty":value);
                sb.append(",");
            }
        }
        String result = sb.toString();
      /*  if (!"".equals(result)) {
            result = result.substring(0, result.length());
        }*/
        return result;
    }

    @Deprecated
    public static String generateViewBackJson(TableLayout tableLayout) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", "2121");
        jsonObject.addProperty("test", "dsad");
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            JsonObject jsonObject_child = new JsonObject();
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            View view = tableRow.getChildAt(1);//第二个
            String key = (String) view.getTag(R.id.hold_view_key);
            String code = "";
            if (view.getTag(R.id.hold_view_code) != null) {
                code = (String) view.getTag(R.id.hold_view_code);
            }
            String value = "";
            if (view instanceof DateSelectView) {
                DateSelectView dsv = (DateSelectView) view;
                value = dsv.getNowSelectData();
            } else if (view instanceof LinesEditView) {
                LinesEditView lev = (LinesEditView) view;
                value = lev.getContentText();
            } else if (view instanceof EditText) {
                EditText et = (EditText) view;
                value = et.getText().toString();
            } else if (view instanceof TextView) {
                TextView tv = (TextView) view;
                value = tv.getText().toString();
            }
            jsonObject_child.addProperty("key", key);
            jsonObject_child.addProperty("value", value);
            jsonObject_child.addProperty("code", code);
            //
            jsonArray.add(jsonObject_child);
        }
        jsonObject.add("list", jsonArray);
        return jsonObject.toString();
    }


    public EditItemRuleHelper() {
    }

    private static void generateChildView(String inputType, final TableLayout tableLayout, final FragmentActivity fragmentActivity,
                                          final String leftTitleStr, final String rightValue, String rightKey, final String rightCode, String rightHint,
                                          List<KeyAndValueBean> options, List<KeyAndValueBean> configs,
                                          List<ImageCommBean> imageCommmBeanList,
                                          ImageUploadCommBean imageUploadCommBean, boolean isFromEdit
    ) {
        WeakReference<FragmentActivity> weakReferenceAty = new WeakReference<>(fragmentActivity);

        final Context context = weakReferenceAty.get();

        // tableLayout.setPadding(10,10,10,10);

        int padding = 10;
        //
        final TextView leftTitle = new TextView(fragmentActivity);
        //高 填充副本  宽永远都是MATCH_PARENT 作用在子控件
        TableRow.LayoutParams commTableRowLayoutParams4UI = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT);
        //##commTableRowLayoutParams4UI.setMargins(SizeTool.dp2px(10),SizeTool.dp2px(10),SizeTool.dp2px(10),SizeTool.dp2px(10));
        leftTitle.setLayoutParams(commTableRowLayoutParams4UI);
        leftTitle.setGravity(Gravity.CENTER_VERTICAL);
        leftTitle.setText(leftTitleStr);
        leftTitle.setTextColor(Color.parseColor("#333333"));
        leftTitle.setBackgroundResource(R.drawable.shape_form_frame_right_bottom);
        /*setBackgroundResource之前  有问题 */

        leftTitle.setPadding(SizeTool.dp2px(padding), 0,
                SizeTool.dp2px(padding), 0);

        TextView rightImage = new TextView(fragmentActivity);
        //高 填充副本  宽永远都是MATCH_PARENT
        rightImage.setLayoutParams(commTableRowLayoutParams4UI);
        //rightImage.setGravity(Gravity.CENTER_VERTICAL);
        rightImage.setBackgroundResource(isFromEdit?R.drawable.shape_form_frame_right_bottom:
                R.drawable.shape_form_frame_bottom_text);
        rightImage.setTextColor(isFromEdit? Color.parseColor("#333333") : Color.parseColor("#666666"));
              /*setBackgroundResource之前  有问题 */
        rightImage.setPadding(SizeTool.dp2px(5), 0,
                SizeTool.dp2px(5), 0);
        final List<ImageShowBean> imageShowBeanList = new ArrayList<>();
        Map<String, String> path_code_map = new HashMap<>();
        if (imageCommmBeanList != null && imageCommmBeanList.size() > 0) {
            for (int i = 0; i < imageCommmBeanList.size(); i++) {
                ImageShowBean bean = new ImageShowBean();
                bean.setTitle(imageCommmBeanList.get(i).getImg_title());
                bean.setImageUrl(imageCommmBeanList.get(i).getImg_url().replace("\\", "/"));
                imageShowBeanList.add(bean);
                //
                path_code_map.put(bean.getImageUrl(), imageCommmBeanList.get(i).getImg_CODE());
            }
            DataHolderSingleton.getInstance().putData(rightCode+"holad_path_code_map", path_code_map);
        }

        //在图片没有上传的时候取缓存
        ArrayList tempUploadArr = new ArrayList();
        synchronized (fragmentActivity) {
            ACache aCache = ACache.get(fragmentActivity);
            ArrayList imageArr = (ArrayList) aCache.getAsObject("imageCache");
            for (int i = 0; i <imageArr.size() ; i++) {
                OnWifiUpLoadImageBean bean = (OnWifiUpLoadImageBean) imageArr.get(i);
                if (rightCode != null && bean.getItemid().equals(rightCode) && bean.getImagePickBean() != null) {
                    ImagePickBean imagePickBean = bean.getImagePickBean();
                    imagePickBean.setImageWebIdStr(imagePickBean.getImagePathOrUrl());
                    tempUploadArr.add(imagePickBean);
                }
            }
        }

        if (imageUploadCommBean != null && !TextUtils.isEmpty(imageUploadCommBean.getImg_upload_title())) {
            //有上传

            final List<ImagePickBean> imagePickBeanList = new ArrayList<>();
            for (int i = 0; i < imageShowBeanList.size(); i++) {
                ImagePickBean ipb = new ImagePickBean();
                ipb.setImagePathOrUrl(imageShowBeanList.get(i).getImageUrl());
                ipb.setImageWebIdStr(imageShowBeanList.get(i).getImageUrl());//必须设置
                // ipb.setImageName(imageShowBeanList.get(i).get());///
                imagePickBeanList.add(ipb);
            }

            imagePickBeanList.addAll(tempUploadArr);
            DataHolderSingleton.getInstance().putData(rightCode+"test_raw_imagePickBeanList", imagePickBeanList);

            rightImage.setOnClickListener(new OnNotFastClickListener() {
                @Override
                protected void onNotFastClick(View view) {

                    String spqedit_itemid = rightCode;
                    List<ImagePickBean>  imagePickBeanList =
                          (List<ImagePickBean>)
                                  DataHolderSingleton.getInstance().getData(spqedit_itemid+"test_raw_imagePickBeanList");
                    DataHolderSingleton.getInstance().putData("now_leftTitleStr", leftTitleStr);
                    ClassicPhotoUploaderDataHelper.setDataAndToPhotoSelector(fragmentActivity,imagePickBeanList, 5);


                    for (ImagePickBean imagePickBean:imagePickBeanList
                         ) {
                        Log.d("dsada","=====start=====");
                        Log.d("dsada", "onNotFastClick: imagePickBean:"+imagePickBean.getImagePathOrUrl());
                        Log.d("dsada","=====end=====");
                    }
                    DataHolderSingleton.getInstance().putData("spqedit_itemid", spqedit_itemid);
                    //fragmentActivity.startActivity(new Intent(fragmentActivity, ClassicPhotoSelectorActivity.class));
                    // ImageShowDataHelper.setDataAndToImageShow(view.getContext(), imageShowBeanList, 0, true);

                }
            });
            if (imageShowBeanList.size() > 0) {
                //有图
                rightImage.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        VectorOrImageResHelper.getDrawable(R.drawable.ic_add_box_black_24dp), null);
            } else {
                //无图
                rightImage.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        VectorOrImageResHelper.getDrawable(R.drawable.ic_add_box_black_24dp), null);
            }

        } else {
            //无上传

            //在图片没有上传的时候取缓存
            ArrayList tempNoUploadArr =new ArrayList();
            synchronized (fragmentActivity) {
                ACache aCache = ACache.get(fragmentActivity);
                ArrayList imageArr = (ArrayList) aCache.getAsObject("imageCache");
                for (int i = 0; i <imageArr.size() ; i++) {
                    OnWifiUpLoadImageBean bean = (OnWifiUpLoadImageBean) imageArr.get(i);
                    if (rightCode != null && bean.getItemid().equals(rightCode) && bean.getImagePickBean() != null) {
                        ImagePickBean imagePickBean = bean.getImagePickBean();
                        imagePickBean.setImageWebIdStr(imagePickBean.getImageWebIdStr());

                        ImageShowBean imageShowBean = new ImageShowBean();
                        imageShowBean.setTitle("   ");
                        imageShowBean.setImageUrl(imagePickBean.getImagePathOrUrl());
                        tempNoUploadArr.add(imageShowBean);
                    }
                }
            }
            imageShowBeanList.addAll(tempNoUploadArr);

            if (imageShowBeanList.size()  > 0) {
                //有图
                List<ImagePickBean> imagePickBeanList = new ArrayList<>();
                for (int i = 0; i < imageShowBeanList.size(); i++) {
                    ImagePickBean ipb = new ImagePickBean();
                    ipb.setImagePathOrUrl(imageShowBeanList.get(i).getImageUrl());
                    ipb.setImageWebIdStr(imageShowBeanList.get(i).getImageUrl());//必须设置
                    // ipb.setImageName(imageShowBeanList.get(i).get());///
                    imagePickBeanList.add(ipb);
                }

               // DataHolderSingleton.getInstance().putData("show_raw_imagePickBeanList", imagePickBeanList);
                rightImage.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        VectorOrImageResHelper.getDrawable(R.drawable.image_display), null);
                rightImage.setOnClickListener(new OnNotFastClickListener() {
                    @Override
                    protected void onNotFastClick(View view) {
                        ImageShowDataHelper.setDataAndToImageShow(view.getContext(), imageShowBeanList, 0, true);
                    }
                });
            }

        }

        if(inputType == null || inputType.equals("") || inputType.equals(" ")) return;
        switch (inputType) {
            case ITEM_TYPE_TEXT:
                TableRow tableRow = new TableRow(context);
                tableRow.setBackgroundColor(Color.WHITE);

                tableRow.addView(leftTitle);
                //
                TextView rightTitle = new TextView(context);
                rightTitle.setText(rightValue);
                rightTitle.setLines(1);
                rightTitle.setGravity(Gravity.CENTER_VERTICAL);
                rightTitle.setLayoutParams(commTableRowLayoutParams4UI);
                rightTitle.setTag(R.id.hold_view_key, rightKey);
                rightTitle.setTag(R.id.hold_view_input_type_text, "true");
                rightTitle.setBackgroundResource(R.drawable.shape_form_frame_bottom_text);
                /* 文本框 padding */
                rightTitle.setPadding(SizeTool.dp2px(padding), SizeTool.dp2px(padding), 0, SizeTool.dp2px(padding));
                tableRow.addView(rightTitle);
                //
                if (rightImage != null) {
                    rightImage.setBackgroundResource(R.drawable.shape_form_frame_bottom_text);
                    tableRow.addView(rightImage);
                }
                tableLayout.addView(tableRow);
                break;
            case ITEM_TYPE_EDIT:
                final TableRow tableRow2 = new TableRow(context);
                tableRow2.addView(leftTitle);
                //
                EditText rightEdit = new EditText(context);
                rightEdit.setHint(rightHint);
                rightEdit.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary));
                rightEdit.setText(rightValue);
                rightEdit.setSelectAllOnFocus(true);
                rightEdit.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                rightEdit.setHintTextColor(ContextCompat.getColor(context, R.color.textColorSecondary));
                rightEdit.setLines(1);
                rightEdit.setGravity(Gravity.CENTER_VERTICAL);
                rightEdit.setLayoutParams(commTableRowLayoutParams4UI);
                rightEdit.setTag(R.id.hold_view_key, rightKey);
                rightEdit.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                /* 输入框 padding */
                rightEdit.setPadding(SizeTool.dp2px(padding), SizeTool.dp2px(padding), 0, SizeTool.dp2px(padding));
                tableRow2.addView(rightEdit);
                //
                if (rightImage != null) {
                    tableRow2.addView(rightImage);
                }
                tableLayout.addView(tableRow2);
                break;
            case ITEM_TYPE_INTEGER:
             final TableRow tableRow6 = new TableRow(context);
                tableRow6.addView(leftTitle);
                //
                EditText rightEditInteger = new EditText(context);
                rightEditInteger.setHint(rightHint);
                rightEditInteger.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary));
                rightEditInteger.setText(rightValue);
                rightEditInteger.setSelectAllOnFocus(true);
                rightEditInteger.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                rightEditInteger.setLines(1);
                rightEditInteger.setHintTextColor(ContextCompat.getColor(context, R.color.textColorSecondary));
                rightEditInteger.setInputType(InputType.TYPE_CLASS_NUMBER);
                rightEditInteger.setGravity(Gravity.CENTER_VERTICAL);
                rightEditInteger.setLayoutParams(commTableRowLayoutParams4UI);
                rightEditInteger.setTag(R.id.hold_view_key, rightKey);
                rightEditInteger.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                  /* 输入框 padding */
                rightEditInteger.setPadding(SizeTool.dp2px(padding), SizeTool.dp2px(padding), 0, SizeTool.dp2px(padding));
                tableRow6.addView(rightEditInteger);
                //

                if (rightImage != null) {
                    tableRow6.addView(rightImage);
                }

                tableLayout.addView(tableRow6);
                break;
            case ITEM_TYPE_DOUBLE:
                final  TableRow tableRow7 = new TableRow(context);

                tableRow7.addView(leftTitle);
                //
                EditText rightEditDouble = new EditText(context);
                rightEditDouble.setHint(rightHint);
                rightEditDouble.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary));
                rightEditDouble.setText(rightValue);
                rightEditDouble.setSelectAllOnFocus(true);
                rightEditDouble.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                rightEditDouble.setLines(1);
                rightEditDouble.setHintTextColor(ContextCompat.getColor(context, R.color.textColorSecondary));

                //rightEditDouble.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                rightEditDouble.setInputType(8194);//http://www.cnblogs.com/janehlp/p/6129053.html
                rightEditDouble.setGravity(Gravity.CENTER_VERTICAL);
                rightEditDouble.setLayoutParams(commTableRowLayoutParams4UI);
                rightEditDouble.setTag(R.id.hold_view_key, rightKey);
                rightEditDouble.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                  /* 输入框 padding */
                rightEditDouble.setPadding(SizeTool.dp2px(padding), SizeTool.dp2px(padding), 0, SizeTool.dp2px(padding));
                tableRow7.addView(rightEditDouble);
                if (rightImage != null) {
                    tableRow7.addView(rightImage);
                }
                tableLayout.addView(tableRow7);
                break;
            case ITEM_TYPE_TIME:
                TableRow tableRow3 = new TableRow(context);
                tableRow3.addView(leftTitle);
                //
                DateSelectView rightDate = new DateSelectView(context);
                //rightDate.setupDateText(kvbList.get(i).getKey());
                rightDate.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary));
                rightDate.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                //rightDate.setText(rightValue);
                /**=====================================*/
                String startData = "";
                String endData = "";
                String showData = rightValue;
                if (configs != null && configs.size() > 0) {
                    for (KeyAndValueBean config : configs) {
                        switch (config.getKey().toLowerCase()) {
                            case "before":
                                endData = config.getValue();
                                break;
                            case "after":
                                startData = config.getValue();
                                break;
                            default:
                                break;
                        }
                    }
                }
                if ("".equals(startData) && "".equals(endData)) {
                    try {
                        rightDate.setupDateText(showData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    rightDate.setupDateText(showData, startData, endData);
                }
                /**=====================================*/
                rightDate.setLines(1);
                rightDate.setGravity(Gravity.CENTER_VERTICAL);
                rightDate.setLayoutParams(commTableRowLayoutParams4UI);
                rightDate.setTag(R.id.hold_view_key, rightKey);
                tableRow3.addView(rightDate);
                //
                if (rightImage != null) {
                    tableRow3.addView(rightImage);
                }
                tableLayout.addView(tableRow3);
                break;
            case ITEM_TYPE_LINES:

                TableRow tableRow4 = new TableRow(context);
                tableRow4.addView(leftTitle);
                //
                LinesEditView rightLines = new LinesEditView(context);
                /**=====================================*/
                String maxCountStr = "";
                String isIgnoreCnOrEn = "";
                if (configs != null && configs.size() > 0) {
                    for (KeyAndValueBean config : configs) {
                        switch (config.getKey().toLowerCase()) {
                            case "max":
                                maxCountStr = config.getValue();
                                break;
                            case "ignorecnoren":
                                isIgnoreCnOrEn = config.getValue();
                                break;
                            default:
                                break;
                        }
                    }
                }
                if (!"".equals(maxCountStr)) {
                    rightLines.setMaxCount(Integer.valueOf(maxCountStr));
                }
                if (!"".equals(isIgnoreCnOrEn)) {
                    rightLines.setIgnoreCnOrEn(isIgnoreCnOrEn.equals("1"));
                }
                /**=====================================*/

                rightLines.setHintText(rightHint);
                rightLines.setContentText(rightValue);
                rightLines.setTag(R.id.hold_view_key, rightKey);
                rightLines.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                tableRow4.addView(rightLines);
                //
                if (rightImage != null) {
                    tableRow4.addView(rightImage);
                }
                tableLayout.addView(tableRow4);
                break;
            case ITEM_TYPE_SELECT:
                TableRow tableRow5 = new TableRow(context);
                tableRow5.addView(leftTitle);
                //
                TextView rightSelect = new TextView(context);

                /**=====================================*/
                final List<ItemSelectBean> itemSelectBeanList = new ArrayList<>();
                if (options != null && options.size() > 0) {
                    for (int i1 = 0; i1 < options.size(); i1++) {
                        ItemSelectBean itemSelectBean = new ItemSelectBean();
                        itemSelectBean.setItemid(i1);
                        itemSelectBean.setItemTitle(options.get(i1).getValue());
                        itemSelectBean.setItemKey(options.get(i1).getKey());
                        itemSelectBeanList.add(itemSelectBean);
                    }
                }
                /**=====================================*/
                rightSelect.setText(TextUtils.isEmpty(rightValue) ? "请选择" : rightValue);
                rightSelect.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary));
                rightSelect.setLines(1);
                rightSelect.setGravity(Gravity.CENTER_VERTICAL);
                rightSelect.setLayoutParams(commTableRowLayoutParams4UI);
                rightSelect.setTag(R.id.hold_view_key, rightKey);
                rightSelect.setTag(R.id.hold_view_code, rightCode);
                rightSelect.setTag(R.id.hold_view_list, itemSelectBeanList);
                rightSelect.setOnClickListener(new OnNotFastClickListener() {
                    @Override
                    protected void onNotFastClick(View view) {
                        //
                        List<ItemSelectBean> list = new ArrayList<>();
                        if (view.getTag(R.id.hold_view_list) != null) {
                            list = (List<ItemSelectBean>) view.getTag(R.id.hold_view_list);
                        }
                        ClassicItemSelectorDataHelper.setDataAndToItemSelect(leftTitleStr, view, fragmentActivity, list);

                    }
                });
                rightSelect.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        VectorOrImageResHelper.getDrawable(R.drawable.ic_keyboard_arrow_right_black_24dp), null);
                rightSelect.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                  /* select padding */
                rightSelect.setPadding(SizeTool.dp2px(padding), SizeTool.dp2px(6), 0, SizeTool.dp2px(6));
                tableRow5.addView(rightSelect);
                //
                if (rightImage != null) {
                    tableRow5.addView(rightImage);
                }
                tableLayout.addView(tableRow5);
                break;
            default:
                break;
        }
        //
        //### tableLayout.setPadding(SizeTool.dp2px(10),0,SizeTool.dp2px(10),0);
    }


    public static void generateSPQChildView(final FragmentActivity fragmentActivity, TableLayout tableLayout,
                                            List<SPQDetailBean.KeyValueBean> keyValueBeanList,boolean  isFromEdit) {
        tableLayout.removeAllViews();
        for (int i = 0; i < keyValueBeanList.size(); i++) {
            String inputType = keyValueBeanList.get(i).getInput_type();
            String leftTitleStr = keyValueBeanList.get(i).getTitle();
            String rightValue = keyValueBeanList.get(i).getValue();
            String rightKey = keyValueBeanList.get(i).getKey();
            String rightCode = keyValueBeanList.get(i).getCode();
            String rightHint = keyValueBeanList.get(i).getIntro();
            List<SPQDetailBean.KeyValueBean.InputOptionsBean> options = keyValueBeanList.get(i).getInput_options();
            List<SPQDetailBean.KeyValueBean.InputConfigsBean> configs = keyValueBeanList.get(i).getInput_configs();
            List<SPQDetailBean.KeyValueBean.ImagesBean> images = keyValueBeanList.get(i).getImages();
            SPQDetailBean.KeyValueBean.ImgUploadOptionsBean img_upload_options = keyValueBeanList.get(i).getImg_upload_options();
            List<KeyAndValueBean> kvList_options = new ArrayList<>();
            if (options != null && options.size() > 0) {
                for (SPQDetailBean.KeyValueBean.InputOptionsBean option : options) {
                    KeyAndValueBean keyAndValueBean = new KeyAndValueBean();
                    keyAndValueBean.setKey(option.getOption_code());
                    keyAndValueBean.setValue(option.getOption_value());
                    kvList_options.add(keyAndValueBean);
                }
            }

            List<KeyAndValueBean> kvList_configs = new ArrayList<>();
            if (configs != null && configs.size() > 0) {
                for (SPQDetailBean.KeyValueBean.InputConfigsBean config : configs) {
                    KeyAndValueBean keyAndValueBean = new KeyAndValueBean();
                    keyAndValueBean.setKey(config.getConfig());
                    keyAndValueBean.setValue(config.getConfig_value());
                    kvList_configs.add(keyAndValueBean);
                }
            }


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

            ImageUploadCommBean imageUploadCommBean = new ImageUploadCommBean();
            imageUploadCommBean.setImg_upload_key(img_upload_options.getImg_upload_key());
            imageUploadCommBean.setImg_upload_max_count(img_upload_options.getImg_upload_max_count());
            imageUploadCommBean.setImg_upload_title(img_upload_options.getImg_upload_title());

            generateChildView(inputType, tableLayout, fragmentActivity, leftTitleStr, rightValue, rightKey, rightCode,rightHint,
                    kvList_options, kvList_configs, imageCommmBeanList, imageUploadCommBean,isFromEdit);
        }
    }


    public static void generateYZTZChildView(final FragmentActivity fragmentActivity, TableLayout tableLayout,
                                             List<YZTZDetailBean.KeyValueBean> keyValueBeanList,boolean isFromEdit) {
        tableLayout.removeAllViews();
        for (int i = 0; i < keyValueBeanList.size(); i++) {
            String inputType = keyValueBeanList.get(i).getInput_type();
            String leftTitleStr = keyValueBeanList.get(i).getTitle();
            String rightValue = keyValueBeanList.get(i).getValue();
            String rightKey = keyValueBeanList.get(i).getKey();
            String rightCode = keyValueBeanList.get(i).getCode();
            String rightHint = "";

            List<YZTZDetailBean.KeyValueBean.InputOptionsBean> options = keyValueBeanList.get(i).getInput_options();
            List<YZTZDetailBean.KeyValueBean.InputConfigsBean> configs = keyValueBeanList.get(i).getInput_configs();
            List<KeyAndValueBean> kvList_options = new ArrayList<>();
            if (options != null && options.size() > 0) {
                for (YZTZDetailBean.KeyValueBean.InputOptionsBean option : options) {
                    KeyAndValueBean keyAndValueBean = new KeyAndValueBean();
                    keyAndValueBean.setKey(option.getOption_code());
                    keyAndValueBean.setValue(option.getOption_value());
                    kvList_options.add(keyAndValueBean);
                }
            }

            List<KeyAndValueBean> kvList_configs = new ArrayList<>();
            if (configs != null && configs.size() > 0) {
                for (YZTZDetailBean.KeyValueBean.InputConfigsBean config : configs) {
                    KeyAndValueBean keyAndValueBean = new KeyAndValueBean();
                    keyAndValueBean.setKey(config.getConfig());
                    keyAndValueBean.setValue(config.getConfig_value());
                    kvList_configs.add(keyAndValueBean);
                }
            }

            generateChildView(inputType, tableLayout, fragmentActivity, leftTitleStr, rightValue, rightKey, rightCode,
                    rightHint,kvList_options, kvList_configs, null, null,isFromEdit);
        }
    }


}
