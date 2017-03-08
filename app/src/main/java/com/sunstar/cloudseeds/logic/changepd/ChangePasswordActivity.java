package com.sunstar.cloudseeds.logic.changepd;

import android.support.design.widget.TextInputLayout;

import com.classichu.classichu.basic.tool.PasswordToggleViewTool;
import com.classichu.classichu.classic.ClassicActivity;
import com.sunstar.cloudseeds.R;


public class ChangePasswordActivity extends ClassicActivity{

    /**
     * @deprecated  com.android.support:design:25.2.0 已修复
     */
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

        /**
         * fix
         *
         * @deprecated  com.android.support:design:25.2.0 已修复
         */
       //### fixPasswordToggleViewInCenter();

        setAppBarTitle("密码修改");
    }

    @Override
    protected AppBarStyle configAppBarStyle() {
        return AppBarStyle.ClassicTitleBar;
    }

    @Override
    protected void initListener() {

    }
}
