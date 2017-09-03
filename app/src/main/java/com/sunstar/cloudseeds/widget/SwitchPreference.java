package com.sunstar.cloudseeds.widget;

import android.content.Context;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.sunstar.cloudseeds.R;


/**
 * Created by xiaoxian on 2017/9/3.
 */

public class SwitchPreference extends Preference {

  public   interface OnSwitchClickListener {
      void  OnSwitchClick(Switch aSwitch);
      void  setSwitchState(Switch aSwitch);
    }

    private Switch aSwitch;
    public OnSwitchClickListener mSwitchClickListener;

    public SwitchPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitchPreference(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        Log.v("switchItem",holder.itemView.toString());
        aSwitch = (Switch) holder.itemView.findViewById(R.id.id_preference_switch);
        mSwitchClickListener.setSwitchState(aSwitch);
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mSwitchClickListener.OnSwitchClick((Switch)v);
            }
        });

    }


}
