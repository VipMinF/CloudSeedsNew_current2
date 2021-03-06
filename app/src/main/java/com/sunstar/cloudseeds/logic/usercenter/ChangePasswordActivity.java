package com.sunstar.cloudseeds.logic.usercenter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.tool.PasswordToggleViewTool;
import com.classichu.classichu.basic.tool.ToastTool;
import com.classichu.classichu.classic.ClassicActivity;
import com.classichu.dialogview.manager.DialogManager;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;
import com.sunstar.cloudseeds.logic.usercenter.bean.SimpleBean;
import com.sunstar.cloudseeds.logic.usercenter.model.ChangePassWordImpl;


public class ChangePasswordActivity extends ClassicActivity {

    private TextInputEditText old_password;
    private TextInputEditText password;
    private TextInputEditText password_again;

    /**
     * @deprecated  com.android.support:design:25.2.0 已修复
     */
    @Deprecated
    private void fixPasswordToggleViewInCenter() {
        TextInputLayout id_til_old_password = findById(R.id.id_til_old_password);
        PasswordToggleViewTool.fixPasswordToggleViewInCenter(id_til_old_password);
        TextInputLayout id_til_password = findById(R.id.id_til_password);
        PasswordToggleViewTool.fixPasswordToggleViewInCenter(id_til_password);
        TextInputLayout id_til_password_again = findById(R.id.id_til_password_again);
        PasswordToggleViewTool.fixPasswordToggleViewInCenter(id_til_password_again);
    }

    @Override
    protected int setupLayoutResId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initView() {

        setAppBarTitle("密码修改");

        old_password = (TextInputEditText)findById(R.id.id_et_old_password);
        password = (TextInputEditText)findById(R.id.id_et_password);
        password_again = (TextInputEditText)findById(R.id.id_et_password_again);

        Button submit = (Button)findById(R.id.id_btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subMitNewPassWord();
            }
        });
    }

    @Override
    protected AppBarStyle configAppBarStyle() {
        return AppBarStyle.ClassicTitleBar;
    }

    @Override
    protected void initListener() {

    }


    private Boolean checkPassWordInfo(){

        String old_paw = old_password.getText().toString();
        String psw = password.getText().toString();
        String psw_again = password_again.getText().toString();

        if (old_paw==null || old_paw.length()==0){

            ToastTool.showShortCenter("请输入原密码");
            return false;
        }

        if (psw==null || psw.length()==0){

            ToastTool.showShortCenter("请输入新密码");
            return false;
        }
        if (psw_again==null || psw_again.length()==0){

            ToastTool.showShortCenter("请确认新密码");
            return false;
        }

        if (old_paw.length()<6 || old_paw.length()>20){

            ToastTool.showShortCenter("密码应为:6-20位英文或数字的组合");
            old_password.setText("");
            old_password.requestFocus();;
            return false;
        }

        if (psw.length()<6 || psw.length()>20){

            ToastTool.showShortCenter("密码应为:6-20位英文或数字的组合");
            password.requestFocus();
            password.setText("");
            return false;
        }

        if (psw_again.length()<6 || psw_again.length()>20){

            ToastTool.showShortCenter("密码应为:6-20位英文或数字的组合");
            password_again.requestFocus();
            password_again.setText("");
            return false;
        }

        if (!psw.equals(psw_again)){
            ToastTool.showShortCenter("确认密码不一致，请重新输入");
            password_again.requestFocus();
            password_again.setText("");
            return false;
        }

        return true;
    }

    private void subMitNewPassWord() {

        if (!checkPassWordInfo()) return;

        DialogManager.showLoadingDialog(this,true);
        String old_paw =UserLoginHelper.entryptionPassword(old_password.getText().toString());
        String psw =UserLoginHelper.entryptionPassword(password.getText().toString());
        String psw_again =UserLoginHelper.entryptionPassword(password_again.getText().toString());
        UserLoginBean userloginben = UserLoginHelper.userLoginBean();
        ChangePassWordImpl changepswImpl = new ChangePassWordImpl();
        changepswImpl.loadData(UrlDatas.ChangePassWord_URL,userloginben.getUserid(), old_paw, psw, psw_again, new BasicCallBack<SimpleBean>() {
            @Override
            public void onSuccess(SimpleBean simpleBean) {

                DialogManager.hideLoadingDialog();
                ToastTool.showShortCenter(simpleBean.getShow_msg());
                UserLoginHelper.loginOut();
                delayJump(1000);
            }
            @Override
            public void onError(String s) {
                DialogManager.hideLoadingDialog();
                ToastTool.showShortCenter(s);
            }

        });
    }

    /**
     * 延迟x毫秒进入
     */
    private void delayJump(long delayMilliseconds) {
        //
        mMyHandler.postDelayed(mMyRunnable, delayMilliseconds);
    }


    private void  goBack(){

        Intent intent = new Intent();
        setResult(RESULT_FIRST_USER,intent);
        this.finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //
        mMyHandler.removeCallbacksAndMessages(null);
    }

    private MyHandler mMyHandler = new MyHandler();
    private MyRunnable mMyRunnable = new MyRunnable() {
        @Override
        public void run() {
            super.run();
            //
            goBack();
        }
    };
    //静态内部类
    private static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    //匿名类的静态实例
  /*  private static Runnable sMyRunnable = new Runnable() {
        @Override
        public void run() {
                //
            nextWhere();
        }
    };*/
    //静态内部类
    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
        }
    }
}
