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
import com.classichu.itemselector.ClassicItemSelectorDataHelper;
import com.classichu.itemselector.bean.ItemSelectBean;
import com.classichu.lineseditview.LinesEditView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.SPQDetailBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2017/3/16.
 */

public class EditItemRuleHelper {

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
    public static void generateSPQChildView(final FragmentActivity fragmentActivity, TableLayout tableLayout,
                                    List<SPQDetailBean.KeyValueBean> keyValueBeanList) {
        tableLayout.removeAllViews();
        Context context=fragmentActivity;
        for (int i = 0; i < keyValueBeanList.size(); i++) {
            String inputType = keyValueBeanList.get(i).getInput_type();
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
            leftTitle.setText(keyValueBeanList.get(i).getTitle());
            leftTitle.setPadding(SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding),
                    SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding));
            leftTitle.setBackgroundResource(R.drawable.shape_form_frame_right_bottom);
            //
            switch (inputType) {
                case "text":
                    TableRow tableRow= new TableRow(context);
                    tableRow.addView(leftTitle);
                    //
                    TextView rightTitle=new TextView(context);
                    rightTitle.setText(keyValueBeanList.get(i).getValue());
                    rightTitle.setLines(1);
                    rightTitle.setTag(R.id.hold_view_key,keyValueBeanList.get(i).getKey());
                    rightTitle.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                    rightTitle.setPadding(SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding),
                            SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding));
                    tableRow.addView(rightTitle);
                    tableLayout.addView(tableRow);
                    break;
                case "edit":
                    TableRow tableRow2= new TableRow(context);
                    tableRow2.addView(leftTitle);
                    //
                    EditText rightEdit=new EditText(context);
                    rightEdit.setHint("请输入"+keyValueBeanList.get(i).getTitle());
                    rightEdit.setTextColor(ContextCompat.getColor(context,R.color.textColorSecondary));
                    rightEdit.setText(keyValueBeanList.get(i).getValue());
                    rightEdit.setSelectAllOnFocus(true);
                    rightEdit.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
                    rightEdit.setLines(1);
                    rightEdit.setTag(R.id.hold_view_key,keyValueBeanList.get(i).getKey());
                    rightEdit.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                    rightEdit.setPadding(SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding),
                            SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding));
                    tableRow2.addView(rightEdit);
                    tableLayout.addView(tableRow2);
                    break;
                case "time":
                    TableRow tableRow3= new TableRow(context);
                    tableRow3.addView(leftTitle);
                    //
                    DateSelectView rightDate=new DateSelectView(context);
                    //rightDate.setupDateText(kvbList.get(i).getKey());
                    rightDate.setTextColor(ContextCompat.getColor(context,R.color.textColorSecondary));
                    rightDate.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                    rightDate.setText(keyValueBeanList.get(i).getValue());
                    rightDate.setLines(1);
                    rightDate.setTag(R.id.hold_view_key,keyValueBeanList.get(i).getKey());
                    rightDate.setPadding(SizeTool.dp2px(context,paddingCustomLeftRight),SizeTool.dp2px(context,paddingCustomTopBottom),
                            SizeTool.dp2px(context,paddingCustomLeftRight),SizeTool.dp2px(context,paddingCustomTopBottom));
                    tableRow3.addView(rightDate);
                    tableLayout.addView(tableRow3);
                    break;
                case "lines":
                    TableRow tableRow4= new TableRow(context);
                    tableRow4.addView(leftTitle);
                    //
                    LinesEditView rightLines=new LinesEditView(context);
                    rightLines.setHintText("请输入"+keyValueBeanList.get(i).getTitle());
                    rightLines.setContentText(keyValueBeanList.get(i).getValue());
                    rightLines.setTag(R.id.hold_view_key,keyValueBeanList.get(i).getKey());
                    rightLines.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                    tableRow4.addView(rightLines);
                    tableLayout.addView(tableRow4);
                    break;
                case "select":
                    TableRow tableRow5= new TableRow(context);
                    tableRow5.addView(leftTitle);
                    //
                    TextView rightSelect=new TextView(context);
                    rightSelect.setText(keyValueBeanList.get(i).getValue());
                    rightSelect.setLines(1);
                    rightSelect.setTag(R.id.hold_view_key,keyValueBeanList.get(i).getKey());
                    rightSelect.setTag(R.id.hold_view_code,keyValueBeanList.get(i).getCode());
                    rightSelect.setOnClickListener(new OnNotFastClickListener() {
                        @Override
                        protected void onNotFastClick(View view) {
                            //
                            List<ItemSelectBean> itemSelectBeanList = new ArrayList<>();
                            for (int i = 0; i < 15; i++) {
                                ItemSelectBean itemSelectBean = new ItemSelectBean();
                                itemSelectBean.setItemid(i);
                                itemSelectBean.setItemTitle("选择" + i);
                                itemSelectBeanList.add(itemSelectBean);
                            }
                            ClassicItemSelectorDataHelper.setDataAndToItemSelect("吸收",view,fragmentActivity,itemSelectBeanList);
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
    }


    public static void generateYZTZChildView(final FragmentActivity fragmentActivity, TableLayout tableLayout,
                                         List<YZTZDetailBean.KeyValueBean> keyValueBeanList) {
        tableLayout.removeAllViews();
        Context context=fragmentActivity;
        for (int i = 0; i < keyValueBeanList.size(); i++) {
            String inputType = keyValueBeanList.get(i).getInput_type();
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
            leftTitle.setText(keyValueBeanList.get(i).getTitle());
            leftTitle.setPadding(SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding),
                    SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding));
            leftTitle.setBackgroundResource(R.drawable.shape_form_frame_right_bottom);
            //
            switch (inputType) {
                case "text":
                    TableRow tableRow= new TableRow(context);
                    tableRow.addView(leftTitle);
                    //
                    TextView rightTitle=new TextView(context);
                    rightTitle.setText(keyValueBeanList.get(i).getValue());
                    rightTitle.setLines(1);
                    rightTitle.setTag(R.id.hold_view_key,keyValueBeanList.get(i).getKey());
                    rightTitle.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                    rightTitle.setPadding(SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding),
                            SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding));
                    tableRow.addView(rightTitle);
                    tableLayout.addView(tableRow);
                    break;
                case "edit":
                    TableRow tableRow2= new TableRow(context);
                    tableRow2.addView(leftTitle);
                    //
                    EditText rightEdit=new EditText(context);
                    rightEdit.setHint("请输入"+keyValueBeanList.get(i).getTitle());
                    rightEdit.setTextColor(ContextCompat.getColor(context,R.color.textColorSecondary));
                    rightEdit.setText(keyValueBeanList.get(i).getValue());
                    rightEdit.setSelectAllOnFocus(true);
                    rightEdit.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
                    rightEdit.setLines(1);
                    rightEdit.setTag(R.id.hold_view_key,keyValueBeanList.get(i).getKey());
                    rightEdit.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                    rightEdit.setPadding(SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding),
                            SizeTool.dp2px(context,padding),SizeTool.dp2px(context,padding));
                    tableRow2.addView(rightEdit);
                    tableLayout.addView(tableRow2);
                    break;
                case "time":
                    TableRow tableRow3= new TableRow(context);
                    tableRow3.addView(leftTitle);
                    //
                    DateSelectView rightDate=new DateSelectView(context);
                    //rightDate.setupDateText(kvbList.get(i).getKey());
                    rightDate.setTextColor(ContextCompat.getColor(context,R.color.textColorSecondary));
                    rightDate.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                    rightDate.setText(keyValueBeanList.get(i).getValue());
                    rightDate.setLines(1);
                    rightDate.setTag(R.id.hold_view_key,keyValueBeanList.get(i).getKey());
                    rightDate.setPadding(SizeTool.dp2px(context,paddingCustomLeftRight),SizeTool.dp2px(context,paddingCustomTopBottom),
                            SizeTool.dp2px(context,paddingCustomLeftRight),SizeTool.dp2px(context,paddingCustomTopBottom));
                    tableRow3.addView(rightDate);
                    tableLayout.addView(tableRow3);
                    break;
                case "lines":
                    TableRow tableRow4= new TableRow(context);
                    tableRow4.addView(leftTitle);
                    //
                    LinesEditView rightLines=new LinesEditView(context);
                    rightLines.setHintText("请输入"+keyValueBeanList.get(i).getTitle());
                    rightLines.setContentText(keyValueBeanList.get(i).getValue());
                    rightLines.setTag(R.id.hold_view_key,keyValueBeanList.get(i).getKey());
                    rightLines.setBackgroundResource(R.drawable.selector_classic_form_bottom_item_bg);
                    tableRow4.addView(rightLines);
                    tableLayout.addView(tableRow4);
                    break;
                case "select":
                    TableRow tableRow5= new TableRow(context);
                    tableRow5.addView(leftTitle);
                    //
                    TextView rightSelect=new TextView(context);
                    rightSelect.setText(keyValueBeanList.get(i).getValue());
                    rightSelect.setLines(1);
                    rightSelect.setTag(R.id.hold_view_key,keyValueBeanList.get(i).getKey());
                    rightSelect.setTag(R.id.hold_view_code,keyValueBeanList.get(i).getCode());
                    rightSelect.setOnClickListener(new OnNotFastClickListener() {
                        @Override
                        protected void onNotFastClick(View view) {
                            //
                            List<ItemSelectBean> itemSelectBeanList = new ArrayList<>();
                            for (int i = 0; i < 15; i++) {
                                ItemSelectBean itemSelectBean = new ItemSelectBean();
                                itemSelectBean.setItemid(i);
                                itemSelectBean.setItemTitle("选择" + i);
                                itemSelectBeanList.add(itemSelectBean);
                            }
                            ClassicItemSelectorDataHelper.setDataAndToItemSelect("吸收",view,fragmentActivity,itemSelectBeanList);
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
    }
}
