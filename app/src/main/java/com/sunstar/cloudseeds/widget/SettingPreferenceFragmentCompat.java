package com.sunstar.cloudseeds.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.widget.Switch;

import com.classichu.classichu.basic.extend.ACache;
import com.classichu.classichu.basic.tool.AppTool;
import com.classichu.classichu.basic.tool.ToastTool;
import com.classichu.dialogview.manager.DialogManager;
import com.classichu.dialogview.ui.ClassicDialogFragment;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.activity.ShowFailUpImgsActivity;
import com.sunstar.cloudseeds.logic.login.LoginActivity;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.logic.usercenter.AboutActivity;
import com.sunstar.cloudseeds.logic.usercenter.ChangePasswordActivity;
import com.sunstar.cloudseeds.service.OnWifiUpLoadService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_FIRST_USER;
import static com.sunstar.cloudseeds.MainActivity.imageArr;

/**
 * Created by louisgeek on 2017/3/8.
 */

public class SettingPreferenceFragmentCompat extends PreferenceFragmentCompat
        implements Preference.OnPreferenceClickListener, com.sunstar.cloudseeds.cache.ACache.ACacheChangeStateListener {

    private Context mContext;
    private Preference preference_password;
    private static Preference preference_onwifiupload;
    private Preference preference_clear;
    private Preference preference_about;
    private Preference preference_exit;
    private static ArrayList failUpImgsBean = new ArrayList<>();
    private static boolean isDoClick = false;
    private static boolean isClear = false;
    private static boolean isFirstAdd = true;
    private static int flag = 90000;

    private static SettingPreferenceFragmentCompat settingPreferenceFragmentCompat = null;


    public static SettingPreferenceFragmentCompat getSettingPreferenceFragmentCompat() {
        if (settingPreferenceFragmentCompat == null) {
            settingPreferenceFragmentCompat = new SettingPreferenceFragmentCompat();
            return settingPreferenceFragmentCompat;
        }
        return settingPreferenceFragmentCompat;
    }

    public SettingPreferenceFragmentCompat() {

    }

    public static Handler MyHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    /**
                     * wifi 按钮开启，选择图片后无wifi，然后开启wifi链接，图片上传
                     */
                    Bundle data = msg.getData();
                    int ySum = data.getInt("ySum");
                    setMSummary(ySum);
                    if (ySum == 0) {
                        String currentTime = getCurrentTime();
                        setOverSummary(":" + currentTime);
                    }
                    isClear = true;
                    isDoClick = false;
                    break;
                case 1:
                    /**
                     * 1.wifi按钮开启，有WiFi，选择图片后即可上传
                     * 2.wifi按钮关闭，有网络连接，且可用（wifi或移动数据连接），选择图片后即可上传
                     */
                    Bundle data1 = msg.getData();
                    String none = data1.getString("none");
                    if (none.equals("none")) {
                        preference_onwifiupload.setSummary("暂无图片需要同步到云端");
                    }
                    isClear = true;
                    isDoClick = false;
                    //理论上来说：summary是暂无图片上传
                    break;
                case 2:
                    /**
                     * wifi按钮开启，有wifi链接，选择图片后即可上传
                     */
//                    Bundle data2 = msg.getData();
//                    String imageTime = data2.getString("imageTime");
                    String currentTime = getCurrentTime();
                    setOverSummary(":" + currentTime);
                    isDoClick = false;
                    isClear = true;
                    break;
                case 3:
                    /**
                     * wifi链接情况下图片同步失败，需要将图片数据传到下一个界面展示
                     */
                    flag = 90003;
                    Bundle data3 = msg.getData();
                    ArrayList failedUpLoadArr = (ArrayList) data3.getSerializable("failedUpLoadArr");
                    int size = failedUpLoadArr.size();
//                    failUpImgsBean=failedUpLoadArr;
                    failUpImgsBean.addAll(failedUpLoadArr);
                    preference_onwifiupload.setSummary(size + "张同步失败，请连接WiFi(点击查看详情)");
                    isDoClick = true;
                    break;
                case 4:
                    /**
                     * "仅在wifi下上传开启"，当前只有移动数据，没有wifi连接。保存图片的情况，图片待上传
                     *
                     * 图片同步失败，需要将图片数据传到下一个界面展示
                     */
                    // TODO: 2017/9/12 需要过滤下图片的数据----核实
                    flag = 90000;
                    int imageArrSize4 = getImageArrSize(imageArr);
                    Bundle data4 = msg.getData();
                    int size4 = data4.getInt("size4");
                    int sum = 0;
                    sum = sum + size4;
                    if (isClear) {
                        imageArrSize4 = 0;
                        sum = 0;
                    }
                    if (isFirstAdd) {
                        int count = imageArrSize4 + size4;
                        preference_onwifiupload.setSummary("有" + count + "张照片等待同步到云端(当前是移动数据连接)");
                        isFirstAdd = false;
                    } else {
                        int count = imageArrSize4 + size4 + sum;
                        preference_onwifiupload.setSummary("有" + count + "张照片等待同步到云端(当前是移动数据连接)");
                    }
                    isDoClick = true;
                    break;
                case 5:
                    Bundle data5 = msg.getData();
                    int size5 = data5.getInt("size");
                    if (size5==0){
                        String currentTime5 = getCurrentTime();
                        setOverSummary(":" + currentTime5);
                        isDoClick = false;
                        isClear = true;
                    }else {
                        preference_onwifiupload.setSummary("有" + size5 + "张照片等待同步到云端");
                    }
                    break;
            }
        }
    };
    // TODO: 2017/9/11

    /**
     * 进入APP需要初始化wifi按钮开关
     */

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference_setting);
        FragmentActivity activity = getActivity();
        mContext = activity;
        //初始化剩余还未上传的图片的数量
        getImageArr();
        SwitchPreference switchPreference = (SwitchPreference) findPreference("preference_onwifiupload");
        initFindPreference();
        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("onwifiupload", Context.MODE_PRIVATE);
        /**
         * =======***====wifi开关按钮
         */
        switchPreference.mSwitchClickListener = new SwitchPreference.OnSwitchClickListener() {
            @Override
            public void OnSwitchClick(Switch aSwitch) {
                Log.v("preference", aSwitch.isChecked() + "");
                boolean isTrue = aSwitch.isChecked();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("onwifiupload", isTrue);
//                int imageArrSize = getImageArrSize(imageArr);
                ArrayList imageArr = getImageArr();
                if (imageArr!=null&&imageArr.size()>0){
                    int imageArrSize = imageArr.size();
                    if (isTrue) {
                        if (imageArrSize == 0) {
                            preference_onwifiupload.setSummary("暂无图片需要同步到云端");
                        } else {
                            preference_onwifiupload.setSummary("有" + imageArrSize + "张照片等待同步到云端");
                        }
                    } else {
                        preference_onwifiupload.setSummary("开启后，手机采集的图片在WIFI下才会同步到云端");
                    }
                }else {
                    if (isTrue){
                        preference_onwifiupload.setSummary("暂无图片需要同步到云端");
                    }else {
                        preference_onwifiupload.setSummary("开启后，手机采集的图片在WIFI下才会同步到云端");
                    }
                }
                editor.commit();
            }

            @Override
            public void setSwitchState(Switch aSwitch) {
                boolean isTrue = sharedPreferences.getBoolean("onwifiupload", false);
                aSwitch.setChecked(isTrue);
            }
        };
        String versionFormat = getResources().getString(R.string.classic_version_format);
        String versionStr = String.format(versionFormat,
                AppTool.getVersionName(), AppTool.getVersionCode());
        clickEvent();
    }

    /**
     * 设置preference的item的点击事件
     */
    private void clickEvent() {
        preference_onwifiupload.setOnPreferenceClickListener(this);
        preference_password.setOnPreferenceClickListener(this);
        preference_clear.setOnPreferenceClickListener(this);
        preference_about.setOnPreferenceClickListener(this);
        preference_exit.setOnPreferenceClickListener(this);

    }

    /**
     * 获取preference的item的id
     */
    private void initFindPreference() {
        preference_password = findPreference("preference_password");
        preference_onwifiupload = findPreference("preference_onwifiupload");
        preference_clear = findPreference("preference_clear");
        preference_about = findPreference("preference_about");
        preference_exit = findPreference("preference_exit");

        initView();
    }

    public static String getCurrentTime() {
        /**
         *  当前时间
         */
        long l = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date(l);
        String currenttime = format.format(date);
        return currenttime;
    }

    /**
     * 获取ACache中imageView的实时数量
     * <p>
     * 写接口回调监听ACache中内容的变化
     *
     * @param imageArrSize
     */
    @Override
    public void mACacheChangeStateListener(int imageArrSize) {

    }

    @Override
    public void mACacheChangeListener(com.sunstar.cloudseeds.cache.ACache aCache) {

    }


    public static int getImageArrSize(ArrayList imageArr) {
//        if(imageArr.size()==0||imageArr==null){
//            return 0;
//        }
        int size = imageArr.size();
        return size;
    }

    private ArrayList getImageArr() {
        ACache aCache = ACache.get(mContext);
        ArrayList imageArr = (ArrayList) aCache.getAsObject("imageCache");
//        getACache(aCache);
        return imageArr;
    }

    private void initView() {
        /**
         * 初始化summary
         */
        ArrayList imageArr = getImageArr();
        int count = imageArr.size();
        OnWifiUpLoadService.NetWorkStatesMonitor netWorkMonitor =
                new OnWifiUpLoadService().getNetWorkMonitor(mContext);
        //获取网络的状态
        int netWorkStates = netWorkMonitor.getNetWorkStates();
        //获取wifi的状态
        boolean wifiAndAvaiable = netWorkMonitor.isWifiAndAvaiable();
        //获取移动数据网络状态
        boolean mobileAndAviable = netWorkMonitor.isMobileAndAviable();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences
                ("onwifiupload", Context.MODE_PRIVATE);
        /**
         * ======》判断“仅在wifi下上传”的按钮是否开启
         */
        boolean isTrue = sharedPreferences.getBoolean("onwifiupload", false);
        initSummary(isTrue, count, imageArr, netWorkStates, wifiAndAvaiable, mobileAndAviable);
    }

    private void initSummary(boolean isTrue, int size, ArrayList imageArr, int netWorkStates,
                             boolean wifiAndAvaiable, boolean mobileAndAviable) {
        if (!isTrue) {
            preference_onwifiupload.setSummary("开启后，手机采集的图片在WIFI下才会同步到云端");
        } else {
            if (imageArr == null || size == 0) {
                preference_onwifiupload.setSummary("暂无图片需要同步到云端");
            } else {
                if (netWorkStates == -1) {
                    preference_onwifiupload.setSummary("网络不可用，请检查网络");
                } else {
                    if (wifiAndAvaiable) {
                        preference_onwifiupload.setSummary("有" + size + "张照片等待同步到云端(点击查看详情)");
                    } else {
                        preference_onwifiupload.setSummary(size + "张同步失败，请连接WiFi(点击查看详情)");
                    }
                }
            }
        }
    }

    /**
     * 正在同步
     *
     * @param yuCount
     */
    public static void setMSummary(int yuCount) {
        preference_onwifiupload.setSummary("正在同步 (还剩" + yuCount + "张)");
    }

    /**
     * 同步完成
     */
    private static void setOverSummary(String currentTime) {
        preference_onwifiupload.setSummary("上次同步完成时间 " + currentTime);
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
            case "preference_onwifiupload":
                ArrayList imageArr = getImageArr();
                if (imageArr != null && imageArr.size() > 0) {
                    isDoClick = true;
                }
                if (isDoClick) {
                    Intent intent = new Intent(getActivity(), ShowFailUpImgsActivity.class);
                    Bundle bundle = new Bundle();
                    if (flag == 90003) {
                        /**
                         * wifi状态下上传失败
                         */
                        bundle.putSerializable("failUpImgsBean", failUpImgsBean);
                        bundle.putInt("flag",flag);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else if (flag == 90000) {
                        /**
                         * 数据下，缓存中未上传的图片
                         */
                        ArrayList imageArr1 = getImageArr();
                        bundle.putSerializable("failUpImgsBean", imageArr1);
                        bundle.putInt("flag",flag);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                }
                break;
            case "preference_password":
                //Toast.makeText(getActivity(), "preference_password", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
                startActivityForResult(new Intent(getActivity(), ChangePasswordActivity.class), 0);
                break;
            case "preference_clear":
                // startActivity(new Intent(getActivity(), GuideActivity.class));
                //###EventBus.getDefault().post(new EventPreferenceShowAskDialog());
                DialogManager.showClassicDialog(getActivity(), "温馨提示", "是否确定清除缓存?",
                        new ClassicDialogFragment.OnBtnClickListener() {
                            @Override
                            public void onBtnClickOk(DialogInterface dialogInterface) {
                                super.onBtnClickOk(dialogInterface);
                                //开始清理缓存
                                ACache macache = ACache.get(getContext());
                                macache.clear();
                                ToastTool.showShortCenter("清除完成");
                            }
                        });
                ArrayList imageArr1 = getImageArr();
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("onwifiupload", Context.MODE_PRIVATE);
                boolean onwifiupload = sharedPreferences.getBoolean("onwifiupload", false);
                if (imageArr1==null){
                    if (onwifiupload){
                        preference_onwifiupload.setSummary("暂无图片需要同步到云端");
                    }else {
                        preference_onwifiupload.setSummary("开启后，手机采集的图片在WIFI下才会同步到云端");
                    }
                }
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

            case "preference_exit":

                DialogManager.showClassicDialog(getActivity(), "注销登录", "您是否确定退出当前账号？",
                        new ClassicDialogFragment.OnBtnClickListener() {

                            @Override
                            public void onBtnClickOk(DialogInterface dialogInterface) {
                                super.onBtnClickOk(dialogInterface);
                                if (UserLoginHelper.loginOut()) {

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
        if (requestCode == 0 && resultCode == RESULT_FIRST_USER) {
            showDialog();
        }
    }

    private void showDialog() {

        DialogManager.showTipDialog(getActivity(), "密码修改成功!", "您的密码已修改，请重新登录!",
                new ClassicDialogFragment.OnBtnClickListener() {
                    @Override
                    public void onBtnClickOk(DialogInterface dialogInterface) {
                        super.onBtnClickOk(dialogInterface);
                        goTOLoginActivity();
                    }
                });
    }

    private void goTOLoginActivity() {

        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

}