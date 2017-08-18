package com.sunstar.cloudseeds.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by xiaoxian on 2017/6/20.
 */

public class MyCheckBoxPreference extends CheckBoxPreference {

    public MyCheckBoxPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyCheckBoxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCheckBoxPreference(Context context) {
        super(context);

    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        TextView textView = (TextView) holder.findViewById(android.R.id.title);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        textView.setTextColor(Color.parseColor("#8A000000"));
    }
}
