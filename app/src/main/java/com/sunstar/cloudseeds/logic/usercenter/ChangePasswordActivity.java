package com.sunstar.cloudseeds.logic.usercenter;

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

    private void subMitNewPassWord() {

        if (!checkPassWordInfo()) return;

        DialogManager.showLoadingDialog(this);
        String old_paw = old_password.getText().toString();
        String psw = password.getText().toString();
        String psw_again = password_again.getText().toString();
        UserLoginBean userloginben = UserLoginHelper.userLoginBean(this);
        ChangePassWordImpl changepswImpl = new ChangePassWordImpl();
        changepswImpl.loadData(UrlDatas.ChangePassWord_URL,userloginben.getUserid(), old_paw, psw, psw_again, new BasicCallBack<SimpleBean>() {
            @Override
            public void onSuccess(SimpleBean simpleBean) {

                DialogManager.hideLoadingDialog();
                ToastTool.showShort(simpleBean.getShow_msg());
                finish();
            }
            @Override
            public void onError(String s) {
                ToastTool.showShort(s);
            }

        });
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
        return true;
    }
}
