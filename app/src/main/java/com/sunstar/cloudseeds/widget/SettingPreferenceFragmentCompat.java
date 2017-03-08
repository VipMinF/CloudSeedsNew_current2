package com.sunstar.cloudseeds.widget;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.classichu.classichu.basic.tool.AppTool;
import com.classichu.dialogview.manager.DialogManager;
import com.classichu.dialogview.ui.ClassicDialogFragment;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.changepd.ChangePasswordActivity;

/**
 * Created by louisgeek on 2017/3/8.
 */

public class SettingPreferenceFragmentCompat extends PreferenceFragmentCompat
        implements Preference.OnPreferenceClickListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference_setting);

        //#### Preference preference_user = findPreference("preference_user");
        Preference preference_password = findPreference("preference_password");

        //###CheckBoxPreference checkbox_preference_nopic = (CheckBoxPreference) findPreference("checkbox_preference_nopic");

        Preference preference_clear = findPreference("preference_clear");
        Preference preference_update = findPreference("preference_update");

        String versionFormat = getResources().getString(R.string.classic_version_format);
        String versionStr = String.format(versionFormat,
                AppTool.getVersionName(getActivity()), AppTool.getVersionCode(getActivity()));
        preference_update.setSummary(versionStr);

        //#####!!!  Preference preference_feedback = findPreference("preference_feedback");
        Preference preference_about = findPreference("preference_about");

        //#### preference_user.setOnPreferenceClickListener(this);
        preference_password.setOnPreferenceClickListener(this);

        //setOnPreferenceChangeListener
        //####  checkbox_preference_nopic.setOnPreferenceChangeListener(this);

        preference_clear.setOnPreferenceClickListener(this);
        preference_update.setOnPreferenceClickListener(this);


        //#####!!!   preference_feedback.setOnPreferenceClickListener(this);
        preference_about.setOnPreferenceClickListener(this);


        /* #####!!!    boolean isNoPic = PreferenceManagerHelper.isNoPic(getActivity());
            checkbox_preference_nopic.setChecked(isNoPic);*/
            /*//程序获取android实现的保存状态的SharedPreferences值 【方法1】

            SharedPreferences sp_big = checkbox_preference_big.getSharedPreferences();
            checkbox_preference_big.setChecked(sp_big.getBoolean("checkbox_preference_big", false));

            SharedPreferences sp_msg = checkbox_preference_msg.getSharedPreferences();
            checkbox_preference_msg.setChecked(sp_wifi.getBoolean("checkbox_preference_msg", false));*/

           /* // 程序获取android实现的保存状态的SharedPreferences值 【方法2】
            // 得到我们的存储Preferences值的对象，然后对其进行相应操作
            SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(this);
            boolean apply_wifiChecked = shp.getBoolean("apply_wifi", false);*/
    }

   /* #####!!!   @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            switch (preference.getKey()) {

                case "checkbox_preference_nopic":
                    //ToastTool.showShort("checkbox_preference_nopic newValue:" + newValue);
                    boolean isNoPic = (boolean) newValue;
                    PreferenceManagerHelper.setNoPic(preference.getEditor(), isNoPic);
                    break;
            }
            return true;
            // return false;
        }*/

        @Override
        public boolean onPreferenceClick(Preference preference) {
            switch (preference.getKey()) {
              /*  case "preference_user":
                    //Toast.makeText(getActivity(), "preference_user", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), UserLoginActivity.class));
                    break;*/
                case "preference_password":
                    //Toast.makeText(getActivity(), "preference_password", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
                    break;
                case "preference_clear":
                    // startActivity(new Intent(getActivity(), GuideActivity.class));
                    //###EventBus.getDefault().post(new EventPreferenceShowAskDialog());
                    DialogManager.showClassicDialog(getActivity(), "温馨提示", "是否确定清除缓存?", new ClassicDialogFragment.OnBtnClickListener() {
                        @Override
                        public void onBtnClickOk(DialogInterface dialogInterface) {
                            super.onBtnClickOk(dialogInterface);

                        }
                    });
                    break;
                case "preference_update":
                    // ToastTool.showImageOk("已经是最新版本了！");
                    //###  EventBus.getDefault().post(new EventCheckUpdate());
                    break;
             /*   case "preference_feedback":
                    //  Toast.makeText(getActivity(), "preference_feedback", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), FeedBackActivity.class));
                    break;*/
                case "preference_about":
                    // Toast.makeText(getActivity(), "preference_about", Toast.LENGTH_SHORT).show();
                    //###   startActivity(new Intent(getActivity(), QrCodeActivity.class));
                    break;
            }
            return true;
            // return false;
        }
    }