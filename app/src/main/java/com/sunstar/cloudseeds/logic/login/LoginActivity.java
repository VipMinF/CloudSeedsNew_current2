package com.sunstar.cloudseeds.logic.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.extend.ACache;
import com.classichu.classichu.basic.tool.SizeTool;
import com.classichu.classichu.basic.tool.ToastTool;
import com.classichu.classichu.classic.ClassicActivity;
import com.sunstar.cloudseeds.MainActivity;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;
import com.sunstar.cloudseeds.logic.login.model.LoginModelImpl;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends ClassicActivity  {

    public static final int LOGIN_V = 2;
    public static final String FILENAME_USERLOGIN = "userlogin"+ LOGIN_V+".dat";
    public Context mcontext;
    private UserLoginTask mAuthTask = null;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ACache macache;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "123456@qq.com:123456", "111111@qq.com:111111"
    };
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext=this;
       macache = ACache.get(this);
    }

    @Override
    protected int setupLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

        initAppBar();
        //账号输入框
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();
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
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        //登录界面和进度条界面
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

    }

    @Override
    protected AppBarStyle configAppBarStyle() {
        return AppBarStyle. ClassicTitleBar;
    }

    @Override
    protected void initListener() {

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

    //加载本地账户
    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }
       // getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {

            showProgress(true);
            //mAuthTask = new UserLoginTask(email, password);
            //mAuthTask.execute((Void) null);

            LoginModelImpl loginmodelimpl= new LoginModelImpl();
            loginmodelimpl.loadData(UrlDatas.Login_URL ,email,password,new BasicCallBack<UserLoginBean>(){
                @Override
                public void onSuccess(UserLoginBean userloginBean) {

                    showProgress(false);
                    UserLoginHelper.saveUserLoginBean_ToAcahe(mcontext,userloginBean);
                    ToastTool.showShort("登录成功");
                    startAty(MainActivity.class);
                    finish();
                }
                @Override
                public void onError(String s) {
                    showProgress(false);
                    ToastTool.showShort(s);
                }
            });
        }
    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
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
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }

    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     * 显示异步登录注册任务，继承异步任务
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                // Simulate network access.
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            return false;
        }



        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {

                //File file = new File(getFilesDir(),FILENAME_USERLOGIN);
                //UserLoginHelper.saveUserLoginBean(file);

                UserLoginBean userloginbean = new UserLoginBean();
                userloginbean.setUserid("111111@qq.com");
                userloginbean.setUsername("Polo");
                userloginbean.setPassword("111111");
                userloginbean.setAddress("振宁路1号");
                userloginbean.setCompany("浙江瞬时达网络有限公司");
                userloginbean.setEmail("111111@qq.com");
                userloginbean.setMoible("13000000000");
                userloginbean.setTickname("刘章湧");
                userloginbean.setUserface("");
                userloginbean.setShow_code("1");
                userloginbean.setShow_msg("登录成功");
                UserLoginHelper.saveUserLoginBean_ToAcahe(mcontext,userloginbean);

                ToastTool.showShort("登录成功");
                startAty(MainActivity.class);
                finish();

            } else {

                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }




}
