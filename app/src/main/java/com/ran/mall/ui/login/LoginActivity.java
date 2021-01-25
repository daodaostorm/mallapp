package com.ran.mall.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.ran.mall.AssessConfig;
import com.ran.mall.BuildConfig;
import com.ran.mall.R;
import com.ran.mall.base.BaseActivity_2;
import com.ran.mall.entity.bean.UserEnCodeBean;
import com.ran.mall.entity.bean.UserRegisterBean;
import com.ran.mall.entity.bean.UserRequestMoudle;
import com.ran.mall.entity.constant.Constant;
import com.ran.mall.entity.constant.SPConstant;
import com.ran.mall.system.SystemCommon;
import com.ran.mall.system.SystemHttpRequest;
import com.ran.mall.ui.mainscreen.MainScreenActivity;
import com.ran.mall.utils.CommonUtils;
import com.ran.mall.utils.DateUtils;
import com.ran.mall.utils.InfoUtils;
import com.ran.mall.utils.LogUtils;
import com.ran.mall.utils.PreferenceUtils;
import com.ran.mall.utils.SPUtils;
import com.ran.mall.utils.ToastUtils;
import com.ran.mall.widget.CustomDialog;
import com.ran.mall.widget.LoadingView;
import com.ran.mall.widget.RegisterDialog;

/**
 * Created by pc on 2017/10/18.
 */
public class LoginActivity extends BaseActivity_2 implements View.OnClickListener, LoginContract.View {
    private Button mBtnLogin;
    private Button mBtnRegister;
    private LoadingView mLoadingView;
    private EditText mEditAccount;
    private EditText mEditPassWord;
    private LoginPresenter mLoginPresenter;
    private String mAccount;
    private String mPassWord;
    private String mDeviceId = "";
    private RegisterDialog mRegisterDialog = null;

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    final static int COUNTS = 5;// 点击次数
    final static long DURATION = 1000;// 规定有效时间
    long[] mHits = new long[COUNTS];

    @Override
    public void initView() {

        mBtnLogin = findViewByIds(R.id.btn_login);
        mBtnRegister = findViewByIds(R.id.btn_register_id);
        mEditAccount = findViewByIds(R.id.etAccount);
        mEditPassWord = findViewByIds(R.id.etPassWord);

        //image_logo.setOnClickListener(v -> continuousClick());

        showNameAndPwd();

        mDeviceId = InfoUtils.getDid(this); // 取唯一标识 用来区别强制下线

        if (getIntent().getBooleanExtra(IS_F0ORCE_LOGOUT, false)) {
            showErrorTip(getResources().getString(R.string.force_logout_txt));
        }
    }

    private void continuousClick() {
        //每次点击时，数组向前移动一位
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //为数组最后一位赋值
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
            mHits = new long[COUNTS];//重新初始化数组
            ToastUtils.shortShow("点击" + COUNTS);
        }
    }

    @Override
    protected boolean isNeedTitleBar() {
        return false;
    }

    @Override
    public void initEvent() {
        mLoginPresenter = new LoginPresenter(this, this);
//        getTitleBar().getLeftView().setVisibility(View.INVISIBLE);
        mLoadingView = new LoadingView(LoginActivity.this, getResources().getString(R.string.loading_login), LoadingView.SHOWLOADING);
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);

    }

    public void showErrorTip(String tip) {
        CustomDialog.bulider().showRadioDialog(this, tip, new CustomDialog.DialogClickListener() {
            @Override
            public void confirm() {
            }

            @Override
            public void cancel() {

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constant.PermissionMode.READ_EXTERNAL_STORAGE) {
            Log.d(TAG, "onRequestPermissionsResult: 1");
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mDeviceId = InfoUtils.getDid(this);
                Log.e("fl", "deviceId:" + mDeviceId);
            }
            mDeviceId = "";
            return;
        }
        if (grantResults.length > 0 && requestCode == Constant.PermissionMode.WRITE_EXTERNAL_STORAGE) {
            Log.d(TAG, "onRequestPermissionsResult: 2");
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mDeviceId = InfoUtils.getDid(this);
            }
            mDeviceId = "";
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public void showRegisterDialog(){

        mRegisterDialog = new RegisterDialog(this);
        mRegisterDialog.setCanceledOnTouchOutside(true);
        mRegisterDialog.setOnDialohClickListener(new RegisterDialog.onDialogListenerCallBack() {
            @Override
            public void onOkCliclck(String userId, String userPass, String userPhone) {
                LogUtils.i("userid: " + userId);
                LogUtils.i("Origin Pass: " + userPass);
                UserRegisterBean createBean = new UserRegisterBean();
                createBean.setUsername(userId);
                LogUtils.i("Pass: " + CommonUtils.encPass(userId, userPass));
                createBean.setPassword(CommonUtils.encPass(userId, userPass));
                createBean.setPhone(userPhone);
                createBean.setTimestamp(DateUtils.getSystemTimestamp());
                mLoginPresenter.requestRegister(new Gson().toJson(createBean));
            }
        });
        mRegisterDialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                mAccount = mEditAccount.getText().toString().trim();
                mPassWord = mEditPassWord.getText().toString().trim();
                mLoginPresenter.requestServerLogin();
                break;
            case R.id.btn_register_id:
                showRegisterDialog();
                break;
            default:
                break;
        }

    }


    @Override
    public void showLoading() {
        if (mLoadingView == null) {
            mLoadingView = new LoadingView(LoginActivity.this, getResources().getString(R.string.loading_login), LoadingView.SHOWLOADING);
        }
        mLoadingView.show();
    }

    @Override
    public void hideLoading() {
        mLoadingView.dismiss();
    }


    @Override
    public void LoginSuccess() {
        runOnUiThread(new Runnable() {
            @Override public void run() {
                hideLoading();
                PreferenceUtils.setLogin(true);

                Intent intent = new Intent(LoginActivity.this, MainScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void LoginBFail(String err, int errCode) {
        if (errCode == -100) {
            showErrorTip(getResources().getString(R.string.network_err));
        } else {
            showErrorTip(err);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRegisterDialog != null && mRegisterDialog.isShowing()) {
            mRegisterDialog.dismiss();
        }
    }

    @Override
    public void RegisterSuccess() {

        if (mRegisterDialog != null && mRegisterDialog.isShowing()) {
            mRegisterDialog.dismiss();
        }
        ToastUtils.shortShow("账号注册成功!");

    }

    @Override
    public void RegisterFail(String err, int errCode) {
        ToastUtils.shortShow(err);
    }

    @Override
    public SystemHttpRequest getSystemRequest() {
        return getSystem(SystemHttpRequest.class);
    }

    @Override
    public String getRequestBody() {
        if (TextUtils.isEmpty(mAccount)) {
            showErrorTip(getResources().getString(R.string.tip_input_account));
            return "";
        }

        if (TextUtils.isEmpty(mPassWord)) {
            showErrorTip(getResources().getString(R.string.tip_input_password));
            return "";
        }
        //String rid = JPushInterface.getRegistrationID(getApplicationContext());

        UserRequestMoudle userRequest = new UserRequestMoudle();
        userRequest.setUsername(mAccount);
        userRequest.setPassword(mPassWord);
        userRequest.setDeviceID(mDeviceId);
        return new Gson().toJson(userRequest);
    }

    //保存登录账号和密码
    @Override
    public void saveNameAndPwd() {
        SPUtils.put(LoginActivity.this, SPConstant.LOGIN_NAME, mAccount);
        SPUtils.put(LoginActivity.this, SPConstant.LOGIN_PWD, mPassWord);

    }

    public void showNameAndPwd() {


        if (BuildConfig.DEBUG) {
            mEditAccount.setText(AssessConfig.DEBUG_LOGINNAME);
            mEditPassWord.setText(AssessConfig.DEBUG_LOGINPWD);
            return;
        }
        String login_name = (String) SPUtils.get(LoginActivity.this, SPConstant.LOGIN_NAME, "");
        String login_pwd = (String) SPUtils.get(LoginActivity.this, SPConstant.LOGIN_PWD, "");

        if (!TextUtils.isEmpty(login_name)) mEditAccount.setText(login_name);
        if (!TextUtils.isEmpty(login_pwd)) mEditPassWord.setText(login_pwd);


    }


    @Override
    public void onBackPressed() {
        //退出App
        getSystem(SystemCommon.class).exitApp();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
