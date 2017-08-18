package com.sunstar.cloudseeds.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.classichu.dateselectview.tool.DateTool;
import com.classichu.dateselectview.tool.KeyBoardTool;
import com.classichu.dateselectview.tool.SizeTool;
import com.classichu.dateselectview.widget.DateSelectPopupWindow;

import java.util.Date;

/**
 * Created by xiaoxian on 2017/4/27.
 */

public class DateSelectView extends AppCompatTextView implements View.OnClickListener {
    private Context mContext;
    private String mNowDateText;
    private String startDateText;
    private String endDateText;
    private final String DEFAULT_DATA_TIME;
    public static final String DEFAULT_DATA = "1970-01-01";
    public static final String DEFAULT_STR = "请选择记录时间";
    private static final String TAG = "DateSelectView";

    public DateSelectView(Context context) {
        this(context, (AttributeSet)null);

    }

    public DateSelectView(Context context , String placeHolder) {
        this(context, (AttributeSet)null);
    }
    public DateSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.DEFAULT_DATA_TIME = "1970-01-01 00:00:00";
        this.init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.initDate();
        if(this.getPaddingTop() == 0 && this.getPaddingBottom() == 0 && this.getPaddingLeft() == 0 && this.getPaddingRight() == 0) {
            int paddingLeft_Right = SizeTool.dp2px(this.mContext, 10.0F);
            int paddingTop_Bottom = SizeTool.dp2px(this.mContext, 6.0F);
            this.setPadding(paddingLeft_Right, paddingTop_Bottom, paddingLeft_Right, paddingTop_Bottom);
        }

        this.setOnClickListener(this);
        if(this.getCompoundDrawables()[2] == null) {
            this.setCompoundDrawablesWithIntrinsicBounds(0, 0, com.classichu.dateselectview.R.drawable.ic_expand_more_black_24dp, 0);
        }

        if(this.getBackground() == null) {
            this.setBackgroundResource(com.classichu.dateselectview.R.drawable.selector_classic_text_item_bg);
        }

        if(this.getGravity() == 8388659) {
            this.setGravity(16);
        }

    }

    public void onClick(final View v) {
        KeyBoardTool.hideKeyboard(v);
        DateSelectPopupWindow myPopupwindow = new DateSelectPopupWindow(this.mContext, this.mNowDateText, this.startDateText, this.endDateText);
        myPopupwindow.showAtLocation(v, 81, 0, 0);
        myPopupwindow.setOnDateSelectListener(new DateSelectPopupWindow.OnDateSelectListener() {
            public void onDateSelect(int year, int monthOfYear, int dayOfMonth) {
                if(year == 0 && monthOfYear == 0 && dayOfMonth == 0) {
                    if(DateSelectView.this.mNowDateText.trim().equals("") || DateSelectView.this.mNowDateText.trim().equals(DEFAULT_STR)) {
                        DateSelectView.this.mNowDateText = DateTool.getChinaDate();
                    }
                } else {
                    DateSelectView.this.mNowDateText = DateTool.getChinaDateFromCalendar(year, monthOfYear, dayOfMonth);
                }

                ((DateSelectView)v).setText(DateSelectView.this.mNowDateText);
            }
        });
    }

    public void setupDateText(String showDateText) {
        Log.i("DateSelectView", "setupDateText  showDateText Only: showDateText:" + showDateText);
        this.setupDateText(showDateText, (String)null, (String)null);
    }

    public void setupDateText(String showDateText, String startDateText, String endDateText) {
        Log.i("DateSelectView", "setupDateText: nowDateText:" + showDateText);
        Log.i("DateSelectView", "setupDateText: startDateText:" + startDateText);
        Log.i("DateSelectView", "setupDateText: endDateText:" + endDateText);
        if(showDateText != null && !showDateText.trim().equals("") && !showDateText.trim().equals("null") && !showDateText.contains("1970-01-01") && !showDateText.equals(DEFAULT_STR)) {
            Date date = DateTool.parseStr2Date(showDateText, "yyyy-MM-dd");
            if(date != null) {
                this.mNowDateText = DateTool.parseDate2Str(date, "yyyy-MM-dd");
            } else {
                this.mNowDateText = DEFAULT_STR;
            }

            if(this.mNowDateText.contains("1970-01-01")) {
                this.mNowDateText = DEFAULT_STR;
            }
        } else {
            this.mNowDateText = DEFAULT_STR;
        }

        this.setText(this.mNowDateText);
        this.startDateText = startDateText;
        this.endDateText = endDateText;
    }

    private void initDate() {
        this.setupDateText((String)null);
    }

    /** @deprecated */
    @Deprecated
    public CharSequence getText() {
        return super.getText();
    }

    public String getNowSelectData() {
        String nowData = "";
        if(this.getText() != null) {
            nowData = this.getText().toString();
        }

        return !nowData.trim().equals("") && !nowData.trim().equals(DEFAULT_STR)?nowData:"1970-01-01";
    }

    public String getNowSelectDataFixedNowData() {
        return this.getNowSelectData() == null?DateTool.getChinaDate():this.getNowSelectData();
    }


}

