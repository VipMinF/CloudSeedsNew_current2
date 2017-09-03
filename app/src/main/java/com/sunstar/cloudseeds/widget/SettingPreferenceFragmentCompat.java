package com.sunstar.cloudseeds.widget;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Context;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;


import com.classichu.classichu.basic.extend.ACache;
import com.classichu.classichu.basic.tool.AppTool;
import com.classichu.classichu.basic.tool.ToastTool;
import com.classichu.dialogview.manager.DialogManager;
import com.classichu.dialogview.ui.ClassicDialogFragment;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.login.LoginActivity;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.logic.usercenter.AboutActivity;
import com.sunstar.cloudseeds.logic.usercenter.ChangePasswordActivity;
import com.sunstar.cloudseeds.widget.SwitchPreference;

import static android.app.Activity.RESULT_FIRST_USER;

/**
 * Created by louisgeek on 2017/3/8.
 */

public class SettingPreferenceFragmentCompat extends PreferenceFragmentCompat
        implements Preference.OnPreferenceClickListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference_setting);

//        MyCheckBoxPreference preference_onWifiUpLoadImage = (MyCheckBoxPreference) findPreference("preference_onwifiupload");
//        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("onwifiupload",Context.MODE_PRIVATE);
//        boolean isTrue = sharedPreferences.getBoolean("onwifiupload",false);
//        preference_onWifiUpLoadImage.setChecked(isTrue);
//        preference_onWifiUpLoadImage.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                Log.v("preference",newValue.toString());
//                boolean isTrue = newValue.toString().equals("true");
//                SharedPreferences.Editor editor =  sharedPreferences.edit();
//                editor.putBoolean("onwifiupload",isTrue);
//                editor.commit();
//                return true;
//            }
//        });


         SwitchPreference switchPreference = (SwitchPreference) findPreference("preference_onwifiupload");
        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("onwifiupload",Context.MODE_PRIVATE);
         switchPreference.mSwitchClickListener = new SwitchPreference.OnSwitchClickListener() {
             @Override
             public void OnSwitchClick(Switch aSwitch) {
                Log.v("preference",aSwitch.isChecked()+"");
                boolean isTrue = aSwitch.isChecked();
                SharedPreferences.Editor editor =  sharedPreferences.edit();
                editor.putBoolean("onwifiupload",isTrue);
                editor.commit();
             }

             @Override
             public void setSwitchState(Switch aSwitch) {

                 boolean isTrue = sharedPreferences.getBoolean("onwifiupload",false);
                 aSwitch.setChecked(isTrue);
             }
         };

        //#### Preference preference_user = findPreference("preference_user");
        Preference preference_password = findPreference("preference_password");

        //###CheckBoxPreference checkbox_preference_nopic = (CheckBoxPreference) findPreference("checkbox_preference_nopic");

        Preference preference_clear = findPreference("preference_clear");
        //Preference preference_update = findPreference("preference_update");

        String versionFormat = getResources().getString(R.string.classic_version_format);
        String versionStr = String.format(versionFormat,
                AppTool.getVersionName(), AppTool.getVersionCode());
        //preference_update.setSummary(versionStr);

        //#####!!!  Preference preference_feedback = findPreference("preference_feedback");
        Preference preference_about = findPreference("preference_about");
        Preference preference_exit = findPreference("preference_exit");


        //#### preference_user.setOnPreferenceClickListener(this);
        preference_password.setOnPreferenceClickListener(this);

        //setOnPreferenceChangeListener
        //####  checkbox_preference_nopic.setOnPreferenceChangeListener(this);

        preference_clear.setOnPreferenceClickListener(this);
        //preference_update.setOnPreferenceClickListener(this);


        //#####!!!   preference_feedback.setOnPreferenceClickListener(this);
        preference_about.setOnPreferenceClickListener(this);

        preference_exit.setOnPreferenceClickListener(this);

        //preference_onWifiUpLoadImage.setOnPreferenceClickListener(this);

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
                //startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
                startActivityForResult(new Intent(getActivity(), ChangePasswordActivity.class),0);

                break;
            case "preference_clear":
                // startActivity(new Intent(getActivity(), GuideActivity.class));
                //###EventBus.getDefault().post(new EventPreferenceShowAskDialog());
                DialogManager.showClassicDialog(getActivity(), "温馨提示", "是否确定清除缓存?", new ClassicDialogFragment.OnBtnClickListener() {
                    @Override
                    public void onBtnClickOk(DialogInterface dialogInterface) {
                        super.onBtnClickOk(dialogInterface);
                        //开始清理缓存
                        ACache macache = ACache.get(getContext());
                        macache.clear();
                        ToastTool.showShortCenter("清除完成");
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
                startActivity(new Intent(getActivity(), AboutActivity.class));

                break;

            case  "preference_exit":

                DialogManager.showClassicDialog(getActivity(), "注销登录", "您是否确定退出当前账号？", new ClassicDialogFragment.OnBtnClickListener() {

                    @Override
                    public void onBtnClickOk(DialogInterface dialogInterface) {
                        super.onBtnClickOk(dialogInterface);
                       if( UserLoginHelper.loginOut()){

                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            getActivity().finish();
                        }
                    }
                });
                break;
        }
        return true;
        // return false;
    }

    //add by lzy --2017.3.23  修改密码成功->finish MainActivity->退回登录页
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode== RESULT_FIRST_USER){
            showDialog();
        }
    }

    private void showDialog(){

        DialogManager.showTipDialog(getActivity(), "密码修改成功!", "您的密码已修改，请重新登录!", new ClassicDialogFragment.OnBtnClickListener() {
            @Override
            public void onBtnClickOk(DialogInterface dialogInterface) {
                super.onBtnClickOk(dialogInterface);
                goTOLoginActivity();
            }
        });
    }

    private void  goTOLoginActivity(){

        startActivity(new Intent(getActivity(),LoginActivity.class));
        getActivity().finish();
    }

}