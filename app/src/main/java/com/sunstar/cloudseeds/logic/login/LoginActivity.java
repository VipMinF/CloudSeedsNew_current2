package com.sunstar.cloudseeds.logic.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.classichu.classichu.basic.tool.SizeTool;
import com.classichu.classichu.classic.ClassicActivity;
import com.classichu.dialogview.manager.DialogManager;
import com.sunstar.cloudseeds.MainActivity;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;
import com.sunstar.cloudseeds.logic.login.contract.LoginContract;
import com.sunstar.cloudseeds.logic.login.presenter.LoginPresenterImpl;

import static com.sunstar.cloudseeds.R.id.email;

public class LoginActivity extends ClassicActivity implements LoginContract.View<UserLoginBean>{

    public static final int LOGIN_V = 2;
    public static final String FILENAME_USERLOGIN = "userlogin"+ LOGIN_V+".dat";
    public Context mcontext;
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Handler handler=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext=this;
        handler=new Handler();
    }

    @Override
    protected int setupLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

        initAppBar();
        //账号输入框
        mUsernameView = (AutoCompleteTextView) findViewById(email);
        //populateAutoComplete();
        //密码输入框
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                attemptLogin();
                return false;
            }
        });

        //登录按钮
        Button mUsernameSignInButton = (Button) findViewById(R.id.username_sign_in_button);
        mUsernameSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        //登录界面和进度条界面
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        //// FIXME: 2017/3/23
        mUsernameSignInButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startAty(MainActivity.class);
                return true;
            }
        });
    }

    @Override
    protected AppBarStyle configAppBarStyle() {
        return AppBarStyle. ClassicTitleBar;
    }

    @Override
    protected void initListener() {
    }

    @Override
    public void showProgress() {
        showProgress(true);
    }
    @Override
    public void hideProgress() {

        handler.post(runnableUi);
    }

    //runnable中更新界面
    Runnable  runnableUi=new  Runnable(){
        @Override
        public void run() {
            showProgress(false);
        }
    };

    @Override
    public void showMessage(String msg) {
        DialogManager.showTipDialog(this,"提示", msg, null);
    }
    @Override
    public void setupData(UserLoginBean userloginbean) {

        UserLoginHelper.saveUserLoginBean_ToAcahe(mcontext,userloginbean);
        startAty(MainActivity.class);
        finish();
    }
    @Override
    public void setupMoreData(UserLoginBean userloginbean) {

    }

    private void initAppBar(){
        //
        if (mClassicTitleBar!=null){
            mClassicTitleBar.setLeftImage(null);
            mClassicTitleBar.setLeftText("登录云种")
                    .setLeftAndRightTextSize(SizeTool.dp2px(this,22))
                    .setLeftMaxWidth(SizeTool.dp2px(this,200));
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);
        // Store values at the time of the login attempt.
        String username =mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isloginNameValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_email));
            focusView = mUsernameView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {

            //强制隐藏键盘
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);

            //执行登录
            String entrytedPsw = UserLoginHelper.entryptionPassword(password);
            Boolean bb=UserLoginHelper.validatePassWord(password,entrytedPsw);
            LoginPresenterImpl loginperenter= new LoginPresenterImpl(this);
            loginperenter.gainData(username,entrytedPsw);

        }
    }


    private boolean isloginNameValid(String loginname) {
        //TODO: Replace this with your own logic
        //return email.contains("@");
        return loginname.length() > 0;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 0;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
