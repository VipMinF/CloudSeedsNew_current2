package com.sunstar.cloudseeds.logic.helper;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.classichu.classichu.basic.helper.VectorOrImageResHelper;
import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.classichu.classichu.basic.tool.SizeTool;
import com.classichu.dateselectview.widget.DateSelectView;
import com.classichu.itemselector.bean.ItemSelectBean;
import com.classichu.itemselector.helper.ClassicItemSelectorDataHelper;
import com.classichu.lineseditview.LinesEditView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.bean.KeyAndValueBean;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.SPQDetailBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2017/3/16.
 */

public class EditItemRuleHelper {
    private static final String ITEM_TYPE_TEXT="text";
    private static final String ITEM_TYPE_EDIT="edit";
    private static final String ITEM_TYPE_LINES="lines";
    private static final String ITEM_TYPE_TIME="time";
    private static final String ITEM_TYPE_SELECT="select";


    public static String generateViewBackJson(TableLayout tableLayout){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("code","2121");
        jsonObject.addProperty("test","dsad");
        JsonArray jsonArray=new JsonArray();
        for (int i = 0; i <  tableLayout.getChildCount();i++) {
            JsonObject jsonObject_child=new JsonObject();
            TableRow tableRow= (TableRow)tableLayout.getChildAt(i);
            View view= tableRow.getChildAt(1);//第二个
            String key= (String) view.getTag(R.id.hold_view_key);
            String code="";
            if (view.getTag(R.id.hold_view_code)!=null) {
                code = (String) view.getTag(R.id.hold_view_code);
            }
            String value="";
            if (view instanceof  DateSelectView){
                DateSelectView dsv= (DateSelectView) view;
                value=dsv.getNowSelectData();
            }else if (view instanceof  LinesEditView){
                LinesEditView lev= (LinesEditView) view;
                value=lev.getContentText();
            }else if (view instanceof  EditText){
                EditText et= (EditText) view;
                value=et.getText().toString();
            }else if (view instanceof  TextView){
                TextView tv= (TextView) view;
                value=tv.getText().toString();
            }
            jsonObject_child.addProperty("key",key);
            jsonObject_child.addProperty("value",value);
            jsonObject_child.addProperty("code",code);
            //
            jsonArray.add(jsonObject_child);
        }
        jsonObject.add("list",jsonArray);
        return  jsonObject.toString();
    }


    private static void generateChildView(String inputType, TableLayout tableLayout, final FragmentActivity fragmentActivity,
                                          final String leftTitleStr , String rightValue , String rightKey, String rightCode,
                                          List<KeyAndValueBean> options, List<KeyAndValueBean> configs){
        Context context=fragmentActivity;
        //
        int padding=12;
        int paddingCustomTopBottom=10;
        int paddingCustomLeftRight=padding;
        //
        TextView leftTitle=new TextView(fragmentActivity);
        //高 填充副本  宽永远都是MATCH_PARENT
        leftTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        leftTitle.setGravity(Gravity.CENTER_VERTICAL);
        leftTitle.setText(leftTitleStr);
        leftTitle.setPadding(SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding),
                SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding));
        leftTitle.setBackgroundResource(R.drawable.shape_form_frame_right_bottom);
        //
        switch (inputType) {
            case ITEM_TYPE_TEXT:
                TableRow tableRow= new TableRow(context);
                tableRow.addView(leftTitle);
                //
                TextView rightTitle=new TextView(context);
                rightTitle.setText(rightValue);
                rightTitle.setLines(1);
                rightTitle.setGravity(Gravity.CENTER_VERTICAL);
                rightTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                rightTitle.setTag(R.id.hold_view_key,rightKey);
                rightTitle.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                rightTitle.setPadding(SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding),
                        SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding));
                tableRow.addView(rightTitle);
                tableLayout.addView(tableRow);
                break;
            case ITEM_TYPE_EDIT:
                TableRow tableRow2= new TableRow(context);
                tableRow2.addView(leftTitle);
                //
                EditText rightEdit=new EditText(context);
                rightEdit.setHint("请输入"+leftTitleStr);
                rightEdit.setTextColor(ContextCompat.getColor(context,R.color.textColorSecondary));
                rightEdit.setText(rightValue);
                rightEdit.setSelectAllOnFocus(true);
                rightEdit.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
                rightEdit.setLines(1);
                rightEdit.setGravity(Gravity.CENTER_VERTICAL);
                rightEdit.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                rightEdit.setTag(R.id.hold_view_key,rightKey);
                rightEdit.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                rightEdit.setPadding(SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding),
                        SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding));
                tableRow2.addView(rightEdit);
                tableLayout.addView(tableRow2);
                break;
            case ITEM_TYPE_TIME:
                TableRow tableRow3= new TableRow(context);
                tableRow3.addView(leftTitle);
                //
                DateSelectView rightDate=new DateSelectView(context);
                //rightDate.setupDateText(kvbList.get(i).getKey());
                rightDate.setTextColor(ContextCompat.getColor(context,R.color.textColorSecondary));
                rightDate.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                //rightDate.setText(rightValue);
                /**=====================================*/
                String startData="";
                String endData="";
                String showData=rightValue;
                if (configs!=null&&configs.size()>0){
                    for (KeyAndValueBean config : configs) {
                        switch (config.getKey().toLowerCase()){
                            case "before":
                                endData=config.getValue();
                                break;
                            case "after":
                                startData=config.getValue();
                                break;
                            default:
                                break;
                        }
                    }
                }
                if ("".equals(startData)&&"".equals(endData)){
                    rightDate.setupDateText(showData);
                }else{
                    rightDate.setupDateText(showData,startData,endData);
                }
                /**=====================================*/
                rightDate.setLines(1);
                rightDate.setGravity(Gravity.CENTER_VERTICAL);
                rightDate.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                rightDate.setTag(R.id.hold_view_key,rightKey);
                rightDate.setPadding(SizeTool.dp2px(context,paddingCustomLeftRight),SizeTool.dp2px(context,paddingCustomTopBottom),
                        SizeTool.dp2px(context,paddingCustomLeftRight),SizeTool.dp2px(context,paddingCustomTopBottom));
                tableRow3.addView(rightDate);
                tableLayout.addView(tableRow3);
                break;
            case ITEM_TYPE_LINES:

                TableRow tableRow4= new TableRow(context);
                tableRow4.addView(leftTitle);
                //
                LinesEditView rightLines=new LinesEditView(context);
                /**=====================================*/
                String maxCountStr = "";
                String isIgnoreCnOrEn = "";
                if (configs!=null&&configs.size()>0){
                    for (KeyAndValueBean config : configs) {
                        switch (config.getKey().toLowerCase()){
                            case "max":
                                maxCountStr=config.getValue();
                                break;
                            case "ignorecnoren":
                                isIgnoreCnOrEn=config.getValue();
                                break;
                            default:
                                break;
                        }
                    }
                }
                if (!"".equals(maxCountStr)){
                    rightLines.setMaxCount(Integer.valueOf(maxCountStr));
                }
                if (!"".equals(isIgnoreCnOrEn)){
                    rightLines.setIgnoreCnOrEn(isIgnoreCnOrEn.equals("1"));
                }
                /**=====================================*/

                rightLines.setHintText("请输入"+leftTitleStr);
                rightLines.setContentText(rightValue);
                rightLines.setTag(R.id.hold_view_key,rightKey);
                rightLines.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                tableRow4.addView(rightLines);
                tableLayout.addView(tableRow4);
                break;
            case ITEM_TYPE_SELECT:
                TableRow tableRow5= new TableRow(context);
                 tableRow5.addView(leftTitle);
                //
                TextView rightSelect=new TextView(context);

                /**=====================================*/
                final List<ItemSelectBean> itemSelectBeanList = new ArrayList<>();
                if (options!=null&&options.size()>0){
                    for (int i1 = 0; i1 < options.size(); i1++) {
                        ItemSelectBean itemSelectBean = new ItemSelectBean();
                        itemSelectBean.setItemid(i1);
                        itemSelectBean.setItemTitle(options.get(i1).getValue());
                        itemSelectBean.setItemKey(options.get(i1).getKey());
                        itemSelectBeanList.add(itemSelectBean);
                    }
                }
                /**=====================================*/
                rightSelect.setText(rightValue);
                rightSelect.setTextColor(ContextCompat.getColor(context,R.color.textColorSecondary));
                rightSelect.setLines(1);
                rightSelect.setGravity(Gravity.CENTER_VERTICAL);
                rightSelect.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                rightSelect.setTag(R.id.hold_view_key,rightKey);
                rightSelect.setTag(R.id.hold_view_code,rightCode);
                rightSelect.setTag(R.id.hold_view_list,itemSelectBeanList);
                rightSelect.setOnClickListener(new OnNotFastClickListener() {
                    @Override
                    protected void onNotFastClick(View view) {
                        //
                        List<ItemSelectBean> list=new ArrayList<>();
                        if (view.getTag(R.id.hold_view_list)!=null){
                            list= (List<ItemSelectBean>) view.getTag(R.id.hold_view_list);
                        }
                        ClassicItemSelectorDataHelper.setDataAndToItemSelect(leftTitleStr,view,fragmentActivity,list);
                    }
                });
               rightSelect.setCompoundDrawablesWithIntrinsicBounds(null,null,
                        VectorOrImageResHelper.getDrawable(context,R.drawable.ic_keyboard_arrow_right_black_24dp),null);
               rightSelect.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                rightSelect.setPadding(SizeTool.dp2px(fragmentActivity,paddingCustomLeftRight),SizeTool.dp2px(fragmentActivity,paddingCustomTopBottom),
                        SizeTool.dp2px(fragmentActivity,paddingCustomLeftRight),SizeTool.dp2px(fragmentActivity,paddingCustomTopBottom));
               tableRow5.addView(rightSelect);
                tableLayout.addView(tableRow5);
                break;
            default:
                break;
        }
    }


    public static void generateSPQChildView(final FragmentActivity fragmentActivity, TableLayout tableLayout,
                                    List<SPQDetailBean.KeyValueBean> keyValueBeanList) {
        tableLayout.removeAllViews();
        for (int i = 0; i < keyValueBeanList.size(); i++) {
            String inputType = keyValueBeanList.get(i).getInput_type();
            String leftTitleStr = keyValueBeanList.get(i).getTitle();
            String rightValue = keyValueBeanList.get(i).getValue();
            String rightKey = keyValueBeanList.get(i).getKey();
            String rightCode = keyValueBeanList.get(i).getCode();
            List<SPQDetailBean.KeyValueBean.InputOptionsBean> options = keyValueBeanList.get(i).getInput_options();
            List<SPQDetailBean.KeyValueBean.InputConfigsBean> configs= keyValueBeanList.get(i).getInput_configs();
            List<KeyAndValueBean> kvList_options=new ArrayList<>();
            if (options!=null&&options.size()>0){
                for (SPQDetailBean.KeyValueBean.InputOptionsBean option : options) {
                    KeyAndValueBean keyAndValueBean=new KeyAndValueBean();
                    keyAndValueBean.setKey(option.getOption_code());
                    keyAndValueBean.setValue(option.getOption_value());
                    kvList_options.add(keyAndValueBean);
                }
            }

            List<KeyAndValueBean> kvList_configs=new ArrayList<>();
            if (configs!=null&&configs.size()>0){
                for (SPQDetailBean.KeyValueBean.InputConfigsBean config : configs) {
                    KeyAndValueBean keyAndValueBean=new KeyAndValueBean();
                    keyAndValueBean.setKey(config.getConfig());
                    keyAndValueBean.setValue(config.getConfig_value());
                    kvList_configs.add(keyAndValueBean);
                }
            }

            generateChildView(inputType,tableLayout,fragmentActivity,leftTitleStr,rightValue,rightKey,rightCode,
                    kvList_options,kvList_configs);
        }
    }


    public static void generateYZTZChildView(final FragmentActivity fragmentActivity, TableLayout tableLayout,
                                         List<YZTZDetailBean.KeyValueBean> keyValueBeanList) {
        tableLayout.removeAllViews();
        for (int i = 0; i < keyValueBeanList.size(); i++) {
            String inputType = keyValueBeanList.get(i).getInput_type();
            String leftTitleStr = keyValueBeanList.get(i).getTitle();
            String rightValue = keyValueBeanList.get(i).getValue();
            String rightKey = keyValueBeanList.get(i).getKey();
            String rightCode = keyValueBeanList.get(i).getCode();

            List<YZTZDetailBean.KeyValueBean.InputOptionsBean> options = keyValueBeanList.get(i).getInput_options();
            List<YZTZDetailBean.KeyValueBean.InputConfigsBean> configs= keyValueBeanList.get(i).getInput_configs();
            List<KeyAndValueBean> kvList_options=new ArrayList<>();
            if (options!=null&&options.size()>0){
                for (YZTZDetailBean.KeyValueBean.InputOptionsBean option : options) {
                    KeyAndValueBean keyAndValueBean=new KeyAndValueBean();
                    keyAndValueBean.setKey(option.getOption_code());
                    keyAndValueBean.setValue(option.getOption_value());
                    kvList_options.add(keyAndValueBean);
                }
            }

            List<KeyAndValueBean> kvList_configs=new ArrayList<>();
            if (configs!=null&&configs.size()>0){
                for (YZTZDetailBean.KeyValueBean.InputConfigsBean config : configs) {
                    KeyAndValueBean keyAndValueBean=new KeyAndValueBean();
                    keyAndValueBean.setKey(config.getConfig());
                    keyAndValueBean.setValue(config.getConfig_value());
                    kvList_configs.add(keyAndValueBean);
                }
            }

            generateChildView(inputType,tableLayout,fragmentActivity,leftTitleStr,rightValue,rightKey,rightCode,
                    kvList_options,kvList_configs);
        }
    }
}
